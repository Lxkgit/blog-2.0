package com.blog.content.service;

import com.blog.common.entity.content.diary.Diary;
import com.blog.common.entity.content.diary.vo.DiaryVo;

import java.util.List;
import java.util.Map;

public interface DiaryService {

    Map<String, Object> selectDiaryByDate(DiaryVo diaryVo, Integer userId);
    int saveDiary(Diary diary);
    Map<String, List<String>> saveDiaryList(Map<String, Diary> map);
    int updateDiary(Diary diary);
    Map<String, Object> deleteDiary(String ids, Integer userId);
}