package com.blog.file.service.impl;

import com.blog.file.dao.UploadLogDAO;
import com.blog.file.service.UploadFileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @Author: lxk
 * @date 2022/7/7 15:59
 * @description: 文件上传服务
 */

@Service
public class UploadFileServiceImpl implements UploadFileService {

    @Resource
    private UploadLogDAO uploadLogDAO;

    @Override
    public void uploadFileList(MultipartFile[] files, String type) throws IOException {
        for (MultipartFile file : files) {
            System.out.println(file.getResource().lastModified());
            System.out.println(file.getOriginalFilename());
            //起手转成字符流
            InputStream is = file.getInputStream();
            InputStreamReader isReader = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isReader);
            //循环逐行读取
//            while (br.ready()) {
//                System.out.println(br.readLine());
//            }
            //关闭流，讲究
            br.close();
        }
    }

}