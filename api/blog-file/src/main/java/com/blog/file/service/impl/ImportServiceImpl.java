package com.blog.file.service.impl;

import com.blog.common.entity.content.diary.Diary;
import com.blog.common.entity.file.UploadLog;
import com.blog.common.entity.file.vo.ImportDiaryVo;
import com.blog.common.util.DateUtil;
import com.blog.common.util.FileUtil;
import com.blog.common.util.ZipFileUtil;
import com.blog.file.dao.UploadFileDAO;
import com.blog.file.dao.UploadLogDAO;
import com.blog.file.feign.ContentClient;
import com.blog.file.service.ImportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @Author: lxk
 * @date 2023/2/1 15:20
 * @description: 数据导入服务类
 */

@Slf4j
@Service
public class ImportServiceImpl implements ImportService {

    @Resource
    private UploadLogDAO uploadLogDAO;

    @Resource
    private ContentClient contentClient;

    @Override
    public boolean importDiary(ImportDiaryVo importDiaryVo) {
        boolean flag = false;
        try {
            File zipFile = new File(importDiaryVo.getFilePath());
            if (!zipFile.exists()) {
                System.out.println("文件不存在 ... ");
            }
            String descPath = importDiaryVo.getFilePath().substring(0, importDiaryVo.getFilePath().lastIndexOf("/")) + "/" + RandomStringUtils.randomAlphabetic(5);
            ZipFileUtil.unZipToFold(importDiaryVo.getFilePath(), descPath);
            File diaryDir = new File(descPath);
            File[] files = diaryDir.listFiles();
            if (files != null) {
                flag = uploadDiary(files, importDiaryVo.getYear(), importDiaryVo.getUserId());
            }
            FileUtil.deleteDir(descPath);
            FileUtil.deleteFile(importDiaryVo.getFilePath());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return flag;
    }


    private boolean uploadDiary(File[] files, Integer year, Integer userId) {
        Map<String, Diary> map = new HashMap<>();
        for (File file : files) {
            String[] fileName = Objects.requireNonNull(file.getName()).split("\\.");
            String pattern = "^[0-9]{1,2}-[0-9]{1,2}$";
            boolean isMatch = Pattern.matches(pattern, fileName[0]);
            if (isMatch) {
                Diary diary = new Diary();
                diary.setUpdateTime(DateUtil.timeStampToDateTime(file.lastModified()));
                diary.setCreateTime(DateUtil.timeStampToDateTime(file.lastModified()));
                diary.setUserId(userId);
                String[] diaryDate = fileName[0].split("-");
                int month = Integer.parseInt(diaryDate[0]);
                int day = Integer.parseInt(diaryDate[1]);
                Calendar calendar = new GregorianCalendar(year, month - 1, day);
                diary.setDiaryDate(calendar.getTime());
                StringBuilder result = new StringBuilder();
                try {
                    //构造一个BufferedReader类来读取文件
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String s;
                    //使用readLine方法，一次读一行
                    while ((s = br.readLine()) != null) {
                        result.append(s);
                    }
                    br.close();
                } catch (Exception e) {
                    log.error("读取文件错误" + e.getMessage(), e);
                }
                diary.setDiaryMd(result.toString());
                map.put(DateUtil.formatDate(diary.getDiaryDate())+".txt", diary);
            }
        }
        Map<String, List<String>> result = contentClient.saveDiaryList(map);
        boolean flag = true;
        for (String key : result.keySet()) {
            List<String> resultList = result.get(key);
            for (String diaryDate : resultList) {
                int uploadState;
                String uploadStr;
                if (key.equals("save") || key.equals("update")) {
                    uploadState = 1;
                    uploadStr = "日记上传成功";
                } else {
                    flag = false;
                    uploadState = 2;
                    uploadStr = "日记上传失败";
                }
                log.info("日记名称： {}", DateUtil.dateToDateTime(diaryDate) );
                UploadLog uploadLog = new UploadLog(userId, DateUtil.dateToDateTime(diaryDate), "diary", uploadState, uploadStr, new Date());
                uploadLogDAO.insert(uploadLog);
            }
        }
        return flag;
    }
}
