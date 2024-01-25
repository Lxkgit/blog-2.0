package com.blog.content.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.content.diary.Diary;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DiaryDAO extends BaseMapper<Diary> {

    /**
     * 查询文档总数
     * @return
     */
    Integer selectDiaryCount();

    /**
     * 以用户id分组查询用户日记数
     * @return
     */
    List<Map<String, Integer>> selectDiaryCountGroupByUserId();
    List<Diary> selectDiaryByDate(@Param("date") String date, @Param("userId") Integer userId);
    List<Diary> selectDiaryList(@Param("userId") Integer userId);
    int updateDiary(Diary diary);
    int insertDiary(Diary diary);

    /**
     * 修改日记状态
     * @param ids
     * @param userId
     * @param diaryStatus
     * @return
     */
    void updateDiaryStatusByIds(@Param("ids") Set<String> ids, @Param("userId") Integer userId, @Param("diaryStatus") Integer diaryStatus);
    int deleteDiaryByIds(@Param("ids") String[] ids, @Param("userId") Integer userId);
}
