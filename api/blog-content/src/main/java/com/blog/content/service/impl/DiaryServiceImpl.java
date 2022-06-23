package com.blog.content.service.impl;

import com.blog.common.entity.content.diary.Diary;
import com.blog.content.dao.DiaryDAO;
import com.blog.content.service.DiaryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author: lxk
 * @date: 2022/6/22 22:45
 * @description:
 * @modified By:
 */

@Service
public class DiaryServiceImpl implements DiaryService {

    @Resource
    private DiaryDAO diaryDAO;

    @Override
    public Map<String, Object> selectDiaryByDate(String dateDay, String dateMonth, Integer userId) {
        Map<String, Object> map = new HashMap<>();
        List<Diary> list = new ArrayList<>();
        if (dateDay != null && !dateDay.equals("")) {
            String pattern = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";
            boolean isMatch = Pattern.matches(pattern, dateDay);
            if (isMatch) {
                list = diaryDAO.selectDiaryByDate(dateDay, userId);
            } else {
                map.put("msg", "请输入正确的日期格式(yyyy-MM-dd) ... ");
            }

        } else if (dateMonth != null && !dateMonth.equals("")) {
            String pattern = "^[0-9]{4}-[0-9]{2}$";
            boolean isMatch = Pattern.matches(pattern, dateMonth);
            if (isMatch) {
                list = diaryDAO.selectDiaryByDate(dateMonth, userId);
            } else {
                map.put("msg", "请输入正确的月份格式(yyyy-MM) ... ");
            }
        } else {
            map.put("msg", "date和dateMonth参数不能同时为空 ... ");
        }
        map.put("diary", list);
        return map;
    }

    @Override
    public int saveDiary(Diary diary) {
        Date date = new Date();
        diary.setCreateTime(date);
        diary.setUpdateTime(date);
        return diaryDAO.insert(diary);
    }

    @Override
    public int updateDiary(Diary diary) {
        diary.setUpdateTime(new Date());
        return diaryDAO.updateDiary(diary);
    }

    @Override
    public Map<String, Object> deleteDiaryByDate(String diaryIds, Integer userId) {
        Map<String, Object> map = new HashMap<>();
       if (diaryIds != null && !diaryIds.equals("")) {
            String[] ids = diaryIds.split(",");
            int num = diaryDAO.deleteDiaryByIds(ids, userId);
            map.put("delete", ids.length);
            map.put("success", num);
        } else {
            map.put("msg", "ids、date和dateMonth参数不能同时为空 ... ");
        }
        return map;
    }
}
