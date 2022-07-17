package com.blog.file.service.impl;

import com.blog.common.constant.Constant;
import com.blog.common.entity.content.diary.Diary;
import com.blog.common.entity.file.UploadImg;
import com.blog.common.entity.file.UploadLog;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.util.DateUtil;
import com.blog.file.dao.UploadImgDAO;
import com.blog.file.dao.UploadLogDAO;
import com.blog.file.feign.ContentClient;
import com.blog.file.service.UploadFileService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @Author: lxk
 * @date 2022/7/7 15:59
 * @description: 文件上传服务
 */

@Service
public class UploadFileServiceImpl implements UploadFileService {

    @Resource
    private UploadLogDAO uploadLogDAO;

    @Resource
    private ContentClient contentClient;

    @Resource
    private UploadImgDAO uploadImgDAO;

    @Value("${file.system}")
    private String system;

    @Value("${file.path}")
    private String path;

    @Override
    public Result uploadDiary(MultipartFile[] files, Integer year, Integer userId) throws IOException {
        Map<Integer, Diary> map = new HashMap<>();
        Date date = new Date();
        int flag;
        for (MultipartFile file : files) {
            StringBuilder buffer = new StringBuilder();
            UploadLog uploadLog = new UploadLog(userId, file.getOriginalFilename(),"diary", 0, "", new Date());
            uploadLogDAO.saveFileUpload(uploadLog);
            flag = uploadLog.getId();
            String[] fileName = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
            String pattern = "^[0-9]{1,2}-[0-9]{1,2}$";
            boolean isMatch = Pattern.matches(pattern, fileName[0]);
            if (isMatch) {
                Diary diary = new Diary();
                diary.setUpdateTime(date);
                diary.setCreateTime(date);
                diary.setUserId(userId);
                String[] diaryDate = fileName[0].split("-");
                int month = Integer.parseInt(diaryDate[0]);
                int day = Integer.parseInt(diaryDate[1]);
                Calendar calendar = new GregorianCalendar(year, month-1, day+1);
                diary.setDiaryDate(calendar.getTime());
                //起手转成字符流
                InputStream is = file.getInputStream();
                InputStreamReader isReader = new InputStreamReader(is, StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isReader);
                //循环逐行读取
                while (br.ready()) {
                    buffer.append(br.readLine());
                }
                diary.setDiaryMd(buffer.toString());
                //关闭流，讲究
                br.close();
                map.put(flag, diary);
            } else {
                Set<Integer> set = map.keySet();
                set.remove(flag);
                uploadLogDAO.updateFileUploadState(flag, userId, 2, "文件名称格式错误");
                for (Integer id : set) {
                    uploadLogDAO.updateFileUploadState(id, userId, 2, "本次上传有文件名称错误，数据保存取消");
                }
                return ResultFactory.buildFailResult("日记: " + file.getOriginalFilename() + " 的格式应该为 MM-dd.txt （0可以省略）");
            }
        }
        Map<String, Integer> uploadResult = new HashMap<>();
        Map<String, List<Integer>> result = (Map<String, List<Integer>>) contentClient.saveDiaryList(map).getResult();
        for (String key : result.keySet()) {
            List<Integer> resultList =  result.get(key);
            for (Integer id : resultList) {
                if (key.equals("save")) {
                    uploadLogDAO.updateFileUploadState(id, userId, 1, "日记保存成功");
                } else if (key.equals("update")) {
                    uploadLogDAO.updateFileUploadState(id, userId, 1, "日记修改成功");
                } else {
                    uploadLogDAO.updateFileUploadState(id, userId, 2, "日记修改失败");
                }
                if (uploadResult.containsKey(key)) {
                    uploadResult.put(key, uploadResult.get(key) +1 );
                } else {
                    uploadResult.put(key, 1);
                }
            }
        }
        return ResultFactory.buildSuccessResult(uploadResult);
    }

    @Override
    public Result uploadImg(MultipartFile[] files, Integer userId) {
        Date date = new Date();
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> result = new HashMap<>();
        // 单图片限制大小为2M
        int imgMaxSize = 1024*1024*2;
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            if (fileName != null && !fileName.equals("")){
                UploadLog uploadLog = new UploadLog(userId, file.getOriginalFilename(),"img", 0, "", date);
                uploadLogDAO.saveFileUpload(uploadLog);
                if (file.getSize() > imgMaxSize) {
                    uploadLogDAO.updateFileUploadState(uploadLog.getId(), userId, 2, "图片大小超出 2M 大小限制");
                    result.put(fileName, "图片大小超出 2M 大小限制");
                } else {
                    try {
                        String fileType = fileName.substring(fileName.lastIndexOf("."));
                        if (!Arrays.asList(Constant.IMG_TYPE).contains(fileType)){
                            result.put(fileName, "文件格式不支持, 只能上传jpg,jpeg,png,image格式的图片");
                        } else {
                            File targetFile;
                            String formatDate = DateUtil.formatDate(date);
                            String newFileName = formatDate + "_" + RandomStringUtils.randomAlphabetic(5) + "_" + fileName;
                            File file1 = new File(path);
                            if (!file1.exists() && !file1.isDirectory()){
                                file1.mkdirs();
                            }
                            targetFile = new File(file1, newFileName);
                            file.transferTo(targetFile);
                            //赋予权限
                            if (system.equals("linux")){
                                String command = "chmod 775 -R " + targetFile;
                                Runtime runtime = Runtime.getRuntime();
                                Process proc = runtime.exec(command);
                            }
                            assert addr != null;
                            String url = addr.getHostAddress() + ":9527" + path + newFileName;
                            result.put(fileName, url);
                            UploadImg uploadImg = new UploadImg(userId, newFileName, url, date);
                            uploadImgDAO.saveImgUrl(uploadImg);
                            uploadLogDAO.updateFileUploadState(uploadLog.getId(), userId, 1, "图片上传成功");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return ResultFactory.buildSuccessResult(result);
    }
}