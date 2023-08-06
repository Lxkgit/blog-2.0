package com.blog.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.common.constant.ErrorMessage;
import com.blog.common.entity.file.FileData;
import com.blog.common.entity.file.vo.FileDataVo;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.exception.ValidException;
import com.blog.file.dao.FileDataDAO;
import com.blog.file.service.FileService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
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

    @Resource
    private FileDataDAO fileDataDAO;

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
        File file = new File(path + "/" + fileDataVoParam.getName());
        file.mkdir();
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
        file.delete();
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

    private List<FileDataVo> show(String path, BlogUser blogUser) {
        QueryWrapper<FileData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("path", path);
        List<FileData> fileDataList = fileDataDAO.selectList(queryWrapper);
        List<FileDataVo> fileDataVoList = new ArrayList<>();
        for (FileData fileData : fileDataList) {
            FileDataVo fileDataVo = new FileDataVo();
            BeanUtils.copyProperties(fileData, fileDataVo);
            fileDataVoList.add(fileDataVo);
        }
        File[] files = (new File(path)).listFiles();
        if (null != files && files.length > 0) {
            for (File file : files) {
                AtomicReference<Boolean> flag = new AtomicReference<>(false);
                fileDataVoList.forEach(fileDataVo -> {
                    if (fileDataVo.getName().toLowerCase().equals(file.getName().toLowerCase())) {
                        flag.set(true);
                        fileDataVo.setFlag(true);
                    }
                });
                // 数据库中不存在的目录 文件中存在 将数据入库
                if (!flag.get()) {
                    FileData fileData = new FileData();
                    fileData.setName(file.getName());
                    fileData.setPath(path);
                    fileData.setUserId(blogUser.getId());
                    if (!file.isFile()) {
                        fileData.setType(0);
                    } else {
                        fileData.setType(1);
                        fileData.setFileSize(file.length());
                    }
                    fileDataDAO.insert(fileData);
                    FileDataVo fileDataVo = new FileDataVo();
                    BeanUtils.copyProperties(fileData, fileDataVo);
                    fileDataVo.setFlag(true);
                    fileDataVoList.add(fileDataVo);
                }
            }
            // 删除数据库中存在 文件目录中不存在的数据
            fileDataVoList.forEach(fileDataVo -> {
                if (!fileDataVo.isFlag()) {
                    // 删除该目录下全部数据
                    QueryWrapper<FileData> wrapper = new QueryWrapper<>();
                    wrapper.likeRight("path", path + "/" + fileDataVo.getName());
                    fileDataDAO.delete(wrapper);
                    // 删除该目录
                    fileDataDAO.deleteById(fileDataVo.getId());
                    // 标记为false移除List中当前数据
                    fileDataVo.setFlag(false);
                }
            });
            fileDataVoList.removeIf(fileDataVo -> !fileDataVo.isFlag());
        } else {
            // 文件目录为空 删除数据库中此目录下全部数据
            QueryWrapper<FileData> wrapper = new QueryWrapper<>();
            wrapper.likeRight("path", path);
            fileDataDAO.delete(wrapper);
        }
        return fileDataVoList;
    }
}
