package com.blog.content.service;

import com.blog.common.entity.content.diary.Diary;
import com.blog.common.entity.content.diary.vo.DiaryVo;
import com.blog.common.exception.ValidException;

import java.util.List;
import java.util.Map;

public interface DiaryService {

    /**
     * 新增日记接口
     *
     * @param diaryVo
     * @return
     */
    Integer saveDiary(DiaryVo diaryVo);

    /**
     * 删除日记接口
     *
     * @param ids
     * @param userId
     * @return
     */
    Integer deleteDiary(String ids, Integer userId) throws ValidException;

    /**
     * 修改日记接口
     *
     * @param diaryVo
     * @return
     */
    Integer updateDiary(DiaryVo diaryVo);

    /**
     * 查询日记接口
     *
     * @param diaryVo
     * @param userId
     * @return
     */
    Map<String, Object> selectDiaryByDate(DiaryVo diaryVo, Integer userId);

    Map<String, List<String>> saveDiaryList(Map<String, Diary> map);


}