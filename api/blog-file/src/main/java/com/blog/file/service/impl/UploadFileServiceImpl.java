package com.blog.file.service.impl;

import com.blog.common.constant.Constant;
import com.blog.common.entity.file.UploadFile;
import com.blog.common.entity.file.UploadLog;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.util.DateUtil;
import com.blog.file.dao.UploadFileDAO;
import com.blog.file.dao.UploadLogDAO;
import com.blog.file.feign.ContentClient;
import com.blog.file.service.UploadFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.net.InetAddress;
import java.util.*;

/**
 * @Author: lxk
 * @date 2022/7/7 15:59
 * @description: 文件上传服务
 */

@Slf4j
@Service
public class UploadFileServiceImpl implements UploadFileService {

    @Resource
    private UploadLogDAO uploadLogDAO;

    @Resource
    private UploadFileDAO uploadImgDAO;

    @Value("${file.system}")
    private String system;

    @Value("${file.path}")
    private String path;

    @Value("${file.urlPath}")
    private String urlPath;

    @Value("${file.serviceIp}")
    private String serviceIp;

    @Override
    public Result uploadFile(MultipartFile[] files, Integer userId, String type) {
        String typePath = "/file";
        Date date = new Date();
        String hostIp = null;
        try {
            hostIp = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        Map<String, String> result = new HashMap<>();
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            if (fileName != null && !fileName.equals("")) {
                String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
                String formatDate = DateUtil.formatDateTime(date).replace(" ", "_").replace(":", "-");
                String newFileName = formatDate + "_" + RandomStringUtils.randomAlphabetic(5) + "_" + fileName;
                UploadLog uploadLog = new UploadLog(userId, newFileName, fileType, 0, "", date);
                uploadLogDAO.saveFileUpload(uploadLog);
                try {
                    String imgPath;
                    if (type.equals("diary")) {
                        imgPath = "/diary";
                    } else {
                        imgPath = "";
                    }
                    File targetFile;
                    File file1 = new File(path + typePath + imgPath);
                    if (!file1.exists() && !file1.isDirectory()) {
                        file1.mkdirs();
                    }
                    targetFile = new File(file1, newFileName);
                    file.transferTo(targetFile);
                    //赋予权限
                    if (system.equals("linux")) {
                        String command = "chmod 775 -R " + targetFile;
                        Runtime runtime = Runtime.getRuntime();
                        Process proc = runtime.exec(command);
                    }
                    String url;
                    if (system.equals("linux")) {
                        url = "http://" + hostIp + urlPath + typePath + imgPath + "/" + newFileName;
                    } else {
                        url = urlPath + typePath + imgPath + "/" + newFileName;
                    }
                    result.put("fileUrl", url);
                    result.put("filePath", path + typePath + imgPath + "/" + newFileName);
                    UploadFile uploadFile = new UploadFile(userId, newFileName, url, date, fileType, path + typePath + imgPath);
                    uploadImgDAO.saveFileUrl(uploadFile);
                    uploadLogDAO.updateFileUpload(new UploadLog(uploadLog.getId(), userId, 1, "文件上传成功"));
                } catch (Exception e) {
                    uploadLogDAO.updateFileUpload(new UploadLog(uploadLog.getId(), userId, 2, "文件上传失败"));
                    log.error(e.getMessage(), e);
                }
            }
        }
        return ResultFactory.buildSuccessResult(result);
    }

    @Override
    public Result uploadImg(MultipartFile[] files, Integer userId, String type) {
        String typePath = "/img";
        Date date = new Date();
        String hostIp = null;
        try {
            hostIp = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> result = new HashMap<>();
        // 单图片限制大小为2M
        int imgMaxSize = 1024 * 1024 * 2;
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            if (fileName != null && !fileName.equals("")) {
                String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
                String formatDate = DateUtil.formatDateTime(date).replace(" ", "_").replace(":", "-");
                String newFileName = formatDate + "_" + RandomStringUtils.randomAlphabetic(5) + "_" + fileName;
                UploadLog uploadLog = new UploadLog(userId, newFileName, fileType, 0, "", date);
                uploadLogDAO.saveFileUpload(uploadLog);
                if (file.getSize() > imgMaxSize) {
                    uploadLogDAO.updateFileUpload(new UploadLog(uploadLog.getId(), userId, 2, "图片大小超出 2M 大小限制"));
                    result.put(fileName, "图片大小超出 2M 大小限制");
                } else {
                    try {
                        if (!Arrays.asList(Constant.IMG_TYPE).contains(fileType)) {
                            uploadLogDAO.updateFileUpload(new UploadLog(uploadLog.getId(), userId, 2, "文件格式不支持, 只能上传jpg,jpeg,png格式的图片"));
                            result.put(fileName, "文件格式不支持, 只能上传jpg,jpeg,png格式的图片");
                        } else {
                            String imgPath;
                            if (type.equals("article")) {
                                imgPath = "/article";
                            } else if (type.equals("head")) {
                                imgPath = "/head";
                            } else if (type.equals("doc")) {
                                imgPath = "/doc";
                            } else {
                                imgPath = "";
                            }
                            File targetFile;
                            File file1 = new File(path + typePath + imgPath);
                            if (!file1.exists() && !file1.isDirectory()) {
                                file1.mkdirs();
                            }
                            targetFile = new File(file1, newFileName);
                            file.transferTo(targetFile);
                            //赋予权限
                            if (system.equals("linux")) {
                                String command = "chmod 775 -R " + targetFile;
                                Runtime runtime = Runtime.getRuntime();
                                Process proc = runtime.exec(command);
                            }
                            String url;
                            if (system.equals("linux")) {
                                url = "http://" + hostIp + urlPath + typePath + imgPath + "/" + newFileName;
                            } else {
                                url = urlPath + typePath + imgPath + "/" + newFileName;
                            }
                            result.put("fileUrl", url);
                            result.put("filePath", path + typePath + imgPath + "/" + newFileName);
                            UploadFile uploadImg = new UploadFile(userId, newFileName, url, date, fileType, path + typePath + imgPath);
                            uploadImgDAO.saveFileUrl(uploadImg);
                            uploadLogDAO.updateFileUpload(new UploadLog(uploadLog.getId(), userId, 1, "图片上传成功"));
                        }
                    } catch (Exception e) {
                        uploadLogDAO.updateFileUpload(new UploadLog(uploadLog.getId(), userId, 2, "图片上传失败"));
                        log.error(e.getMessage(), e);
                    }
                }
            }
        }
        return ResultFactory.buildSuccessResult(result);
    }
}