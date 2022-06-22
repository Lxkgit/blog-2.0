package com.blog.content.service;

import com.blog.common.entity.content.diary.Diary;

import java.util.Map;

public interface DiaryService {

    Map<String, Object> selectDiaryByDate(String dateDay, String dateMonth, Integer userId);
    int saveDiary(Diary diary);
    int updateDiary(Diary diary);
    Map<String, Object> deleteDiaryByDate(String date, String dateMonth, String ids, Integer userId);
}
