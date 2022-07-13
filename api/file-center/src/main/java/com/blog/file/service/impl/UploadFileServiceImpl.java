package com.blog.file.service.impl;

import com.blog.common.entity.content.diary.Diary;
import com.blog.common.result.Result;
import com.blog.file.dao.UploadLogDAO;
import com.blog.file.feign.ContentClient;
import com.blog.file.service.UploadFileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Override
    public Result uploadFileList(MultipartFile[] files, String type,Integer userId) throws IOException {
        List<Diary> diaryList = new ArrayList<>();
        Date date = new Date();
        for (MultipartFile file : files) {
            StringBuilder buffer = new StringBuilder();
            Diary diary = new Diary();
            diary.setUpdateTime(date);
            diary.setUserId(userId);
            System.out.println(file.getOriginalFilename());
            diary.setDiaryDate(date);
//            System.out.println(file.getResource().lastModified());
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
            diaryList.add(diary);
        }
        return contentClient.saveDiaryList(diaryList);
    }

}