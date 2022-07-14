package com.blog.file.service.impl;

import com.blog.common.entity.content.diary.Diary;
import com.blog.common.entity.file.UploadLog;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
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

}