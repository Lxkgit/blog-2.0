package com.blog.file.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.blog.common.constant.Constant;
import com.blog.common.constant.ErrorMessage;
import com.blog.common.entity.file.FileData;
import com.blog.common.entity.file.FileSync;
import com.blog.common.entity.file.vo.FileDataVo;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.enums.file.FileTypeEnum;
import com.blog.common.enums.mqtt.MQTTTopicEnum;
import com.blog.common.exception.ValidException;
import com.blog.common.message.mqtt.MqttMessage;
import com.blog.common.util.MqttPushClient;
import com.blog.file.dao.FileDataDAO;
import com.blog.file.dao.FileSyncDAO;
import com.blog.file.mqtt.MyMQTTClient;
import com.blog.file.service.FileService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @description: 文件服务
 * @Author: lxk
 * @date 2023/8/2 16:03
 */

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.basePath}")
    private String basePath;

    @Value("${file.serviceIp}")
    private String serviceIp;

    @Value("${file.baseUri}")
    private String baseUri;

    @Resource
    private FileDataDAO fileDataDAO;

    @Resource
    private FileSyncDAO fileSyncDAO;

    @Resource
    private MyMQTTClient mqttClient;


    @Override
    public void saveFileDir(BlogUser blogUser, FileDataVo fileDataVoParam) throws ValidException {
        if (fileDataVoParam.getName() == null || fileDataVoParam.getName().equals("")) {
            throw new ValidException(ErrorMessage.FILE_NAME_NULL_ERROR);
        }
        if (fileDataVoParam.getFilePath().equals("/")) {
            throw new ValidException(ErrorMessage.BASE_FILE_DIR_NOT_CREATE);
        }
        String path = basePath + "/" + blogUser.getId() + fileDataVoParam.getFilePath();
        List<FileDataVo> fileDataVoList = show(path, blogUser);
        for (FileDataVo fileDataVo : fileDataVoList) {
            if (fileDataVo.getName().toLowerCase().equals(fileDataVoParam.getName().toLowerCase())) {
                throw new ValidException(ErrorMessage.FILE_NAME_SAME_ERROR);
            }
        }
        FileData fileData = fileDataDAO.selectByPathAndName(path);
        File file = new File(path + "/" + fileDataVoParam.getName());
        if (file.mkdir()) {
            fileDataVoParam.setPath(path);
            fileDataVoParam.setUserId(blogUser.getId());
            fileDataVoParam.setFileSize(0L);
            // 同步目录下创建的目录全部为同步目录
            fileDataVoParam.setDirType(fileData.getDirType().equals(Constant.DIR_TYPE_SYNC) ? Constant.DIR_TYPE_SYNC : fileDataVoParam.getDirType());
            fileDataDAO.insert(fileDataVoParam);
        }
    }

    @Override
    public void deleteFileOrDir(BlogUser blogUser, FileDataVo fileDataVo) throws ValidException {
        if (fileDataVo.getName() == null || fileDataVo.getName().equals("")) {
            throw new ValidException(ErrorMessage.FILE_NAME_NULL_ERROR);
        }
        if (fileDataVo.getFilePath().equals("/")) {
            throw new ValidException(ErrorMessage.BASE_FILE_DIR_NOT_DELETE);
        }
        String path = basePath + "/" + blogUser.getId() + fileDataVo.getFilePath();
        File file = new File(path + "/" + fileDataVo.getName());
        fileDataDAO.deleteById(fileDataVo.getId());
        file.delete();
    }

    private void deleteDir(File dir) {
        File[] files = dir.listFiles();
        // 删除dir 里面的内容
        // 用到递归  此处注意不要经常用  因为java删除的内容是 在回收站找不到
        for (File file : files) {
            if (file.isFile()) {
                file.delete();
            } else {
                deleteDir(file);
            }
        }
        // 删除 dir
        dir.delete();
    }

    @Override
    public void updateFileOrDirName(BlogUser blogUser, FileDataVo fileDataVo) throws ValidException {
        if (fileDataVo.getName() == null || fileDataVo.getName().equals("")) {
            throw new ValidException(ErrorMessage.FILE_NAME_NULL_ERROR);
        }
        if (fileDataVo.getFilePath().equals("/")) {
            throw new ValidException(ErrorMessage.BASE_FILE_NOT_RENAME);
        }
        String path = basePath + "/" + blogUser.getId() + fileDataVo.getFilePath();
        new File(path + "/" + fileDataVo.getName()).renameTo(new File(path + "/" + fileDataVo.getRename()));

        QueryWrapper<FileData> wrapper = new QueryWrapper<>();
        wrapper.likeRight("path", path + "/" + fileDataVo.getName());
        fileDataDAO.delete(wrapper);
    }

    /**
     * 查询指定用户的文件目录
     *
     * @param blogUser
     * @param fileDataVo
     * @return
     */
    @Override
    public List<FileDataVo> selectFileDir(BlogUser blogUser, FileDataVo fileDataVo) {
        String path = basePath + "/" + blogUser.getId();
        if (!fileDataVo.getFilePath().equals("/")) {
            path = path + fileDataVo.getFilePath();
        }
        return show(path, blogUser);
    }

    @Override
    public Long selectUserSpace(BlogUser blogUser) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String authorization = request.getHeader("Authorization");
        System.out.println("authorization: " + authorization);
        QueryWrapper<FileData> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", blogUser.getId());
        List<FileData> fileDataList = fileDataDAO.selectList(wrapper);
        Long size = 0L;
        for (FileData fileData : fileDataList) {
            if (fileData.getFileSize() != null) {
                size += fileData.getFileSize();
            }
        }
        return size;
    }

    /**
     * 同步文件
     * @param blogUser
     * @param fileDataVoList
     * @return
     */
    @Override
    public boolean syncFileList(BlogUser blogUser, List<FileDataVo> fileDataVoList) {
        for (FileDataVo fileData : fileDataVoList) {
            if (fileData.getType().equals(Constant.FILE_TYPE_DIR)) {
                // 目录不同步
                continue;
            }
            if (fileData.getDirType().equals(Constant.DIR_TYPE_LOCAL)) {
                // 本地目录不同步
                continue;
            }


        }
        return false;
    }

    public boolean syncFile(BlogUser blogUser, FileDataVo fileDataVo) {
        if (fileDataVo.getType().equals(Constant.FILE_TYPE_DIR)) {
            // 目录不同步
            return false;
        }
        if (fileDataVo.getDirType().equals(Constant.DIR_TYPE_LOCAL)) {
            // 本地目录不同步
            return false;
        }
        FileSync fileSync = new FileSync();
        fileSync.setFilePath(fileDataVo.getFilePath());
        fileSync.setFileName(fileDataVo.getName());
        fileSync.setFileSn(RandomStringUtils.random(16, true, true));
        fileSync.setUserId(blogUser.getId());
        fileSyncDAO.insert(fileSync);

        // 发送mqtt消息
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setSender("blog");
        mqttMessage.setMessage(JSON.toJSONString(fileSync));
        mqttClient.publish(JSON.toJSONString(mqttMessage), MQTTTopicEnum.MQTT_SEND_SYNC_FILE.getTopic(), MQTTTopicEnum.MQTT_SEND_SYNC_FILE.getQos());

        return true;
    }

    /**
     * 查询用户的文件目录
     *
     * @param path     文件目录
     * @param blogUser 用户信息
     * @return 文件目录下数据列表
     */
    private List<FileDataVo> show(String path, BlogUser blogUser) {
        FileData dir = fileDataDAO.selectByPathAndName(path);
        // 获取数据库中当前目录下文件列表
        QueryWrapper<FileData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("path", path);
        List<FileData> fileDataList = fileDataDAO.selectList(queryWrapper);
        List<FileDataVo> fileDataVoList = new ArrayList<>();
        for (FileData fileData : fileDataList) {
            FileDataVo fileDataVo = new FileDataVo();
            BeanUtils.copyProperties(fileData, fileDataVo);
            fileDataVoList.add(fileDataVo);
        }
        // 获取当前目录下实际文件列表
        File[] files = (new File(path)).listFiles();
        if (null != files && files.length > 0) {
            for (File file : files) {
                String fileType = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                AtomicReference<Boolean> flag = new AtomicReference<>(false);
                // 统计目录下文件大小
                fileDataVoList.forEach(fileDataVo -> {
                    if (fileDataVo.getName().toLowerCase().equals(file.getName().toLowerCase())) {
                        flag.set(true);
                        fileDataVo.setFlag(true);
                        fileDataVo.setUpdateTime(new Date(file.lastModified()));

                        if (!file.isFile()) {
                            // 目录计算目录占用大小
                            fileDataVo.setFileSize(FileUtils.sizeOf(file));
                        } else {
                            if (FileTypeEnum.IMAGE.getTypeList().contains(fileType)) {
                                // 图片添加图片链接
                                fileDataVo.setImgPath(serviceIp + baseUri + path.substring(basePath.length()) + "/" + file.getName());
                            }
                            // 文件计算文件大小
                            fileDataVo.setFileSize(file.length());
                        }
                    }
                });

                // 数据库中不存在的目录 文件中存在 将数据入库
                if (!flag.get()) {
                    FileDataVo fileDataVo = new FileDataVo();
                    fileDataVo.setName(file.getName());
                    fileDataVo.setPath(path);
                    fileDataVo.setUserId(blogUser.getId());
                    fileDataVo.setDirType(dir.getDirType());
                    fileDataVo.setStatus(Constant.FILE_TYPE_FILE);
                    fileDataVo.setUpdateTime(new Date(file.lastModified()));
                    if (!file.isFile()) {
                        fileDataVo.setType(Constant.FILE_TYPE_DIR);
                        fileDataVo.setFileSize(FileUtils.sizeOf(file));
                    } else {
                        if (FileTypeEnum.IMAGE.getTypeList().contains(fileType)) {
                            fileDataVo.setType(Constant.FILE_TYPE_FILE);
                            fileDataVo.setImgPath(serviceIp + baseUri + path.substring(basePath.length()) + "/" + file.getName());
                        } else {
                            fileDataVo.setType(Constant.FILE_TYPE_IMAGE);
                        }
                        fileDataVo.setFileSize(file.length());
                    }
                    fileDataDAO.insert(fileDataVo);
                    fileDataVo.setFlag(true);
                    fileDataVoList.add(fileDataVo);
                }
            }
            // 删除数据库中存在 文件目录中不存在的数据 (远程同步文件除外)
            fileDataVoList.forEach(fileDataVo -> {
                if (!fileDataVo.isFlag()) {
                    // 删除该目录下全部数据
                    QueryWrapper<FileData> wrapper = new QueryWrapper<>();
                    wrapper.likeRight("path", path + "/" + fileDataVo.getName());
                    wrapper.ne("dir_type", Constant.DIR_TYPE_SYNC);
                    fileDataDAO.delete(wrapper);
                    // 删除该目录
                    if (!fileDataVo.getDirType().equals(Constant.DIR_TYPE_SYNC)) {
                        fileDataDAO.deleteById(fileDataVo.getId());
                        // 标记为false移除List中当前数据
                        fileDataVo.setFlag(false);
                    }
                    fileDataVo.setFlag(true);
                }
            });
            fileDataVoList.removeIf(fileDataVo -> !fileDataVo.isFlag());
        } else {
            // 文件目录为空 删除数据库中此目录下全部数据
            QueryWrapper<FileData> wrapper = new QueryWrapper<>();
            wrapper.likeRight("path", path);
            wrapper.ne("dir_type", Constant.DIR_TYPE_SYNC);
            fileDataDAO.delete(wrapper);
            fileDataVoList.clear();
        }
        fileDataVoList.forEach(fileDataVo -> {
            if (fileDataVo.getId() != null) {
                fileDataDAO.updateById(fileDataVo);
            }
        });
        Collections.sort(fileDataVoList);
        return fileDataVoList;
    }
}
