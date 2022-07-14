package com.blog.content.service;

import com.blog.common.entity.content.diary.Diary;

import java.util.List;
import java.util.Map;

public interface DiaryService {

    Map<String, Object> selectDiaryByDate(String dateDay, String dateMonth, Integer userId);
    int saveDiary(Diary diary);
    Map<String, List<Integer>> saveDiaryList(Map<Integer, Diary> map);
    int updateDiary(Diary diary);
    Map<String, Object> deleteDiaryByDate(String ids, Integer userId);
}