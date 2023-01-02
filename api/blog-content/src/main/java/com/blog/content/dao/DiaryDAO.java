package com.blog.content.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.content.diary.Diary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DiaryDAO extends BaseMapper<Diary> {

    List<Diary> selectDiaryByDate(@Param("date") String date, @Param("userId") Integer userId);
    List<Diary> selectDiaryList();
    int updateDiary(Diary diary);
    int insertDiary(Diary diary);
    int deleteDiaryByIds(@Param("ids") String[] ids, @Param("userId") Integer userId);
}
