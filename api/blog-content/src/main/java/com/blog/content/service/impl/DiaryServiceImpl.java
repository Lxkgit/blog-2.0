package com.blog.content.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.common.constant.Constant;
import com.blog.common.constant.ErrorMessage;
import com.blog.common.entity.content.diary.Diary;
import com.blog.common.entity.content.diary.vo.DiaryVo;
import com.blog.common.entity.file.vo.BlogDataVo;
import com.blog.common.entity.file.vo.ContentCountVo;
import com.blog.common.enums.mq.RocketMQTopicEnum;
import com.blog.common.exception.ValidException;
import com.blog.common.message.mq.RocketMQMessage;
import com.blog.common.util.DateUtil;
import com.blog.common.util.MyPage;
import com.blog.common.util.MyPageUtils;
import com.blog.common.util.MyStringUtils;
import com.blog.content.dao.DiaryDAO;
import com.blog.content.mq.MQProducerService;
import com.blog.content.mq.send.SendSystemData;
import com.blog.content.mq.send.SendUserData;
import com.blog.content.service.DiaryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
public class DiaryServiceImpl implements DiaryService {

    @Resource
    private DiaryDAO diaryDAO;

    @Resource
    private SendSystemData sendSystemData;

    @Resource
    private SendUserData sendUserData;

    /**
     * 新增日记
     *
     * @param diaryVo
     * @return
     */
    @Override
    public Integer saveDiary(DiaryVo diaryVo) {
        diaryVo.setCreateTime(new Date());
        diaryVo.setUpdateTime(new Date());
        diaryVo.setDiaryStatus(1);
        diaryDAO.insertDiary(diaryVo);

        // 发送博客用户新增日记mq消息
        sendUserData.sendUserData(SendUserData.diary, diaryVo.getUserId(), 1);

        // 发送博客系统新增日记mq消息
        sendSystemData.sendSystemData(SendSystemData.diary, 1);
        return diaryVo.getId();
    }

    /**
     * 删除日记
     *
     * @param diaryIds
     * @param userId
     * @return
     */
    @Override
    public Integer deleteDiary(String diaryIds, Integer userId) throws ValidException {
        Set<String> idSet = MyStringUtils.splitString(diaryIds, ",");
        for (String id : idSet) {
            QueryWrapper<Diary> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            queryWrapper.ne("diary_status", Constant.DELETE);
            Diary diary = diaryDAO.selectOne(queryWrapper);
            if (diary == null) {
                throw new ValidException(ErrorMessage.DIARY_NOT_EXISTS, "日记id: " + id + " 不存在");
            }
        }
        diaryDAO.updateDiaryStatusByIds(idSet, userId, Constant.DELETE);

        // 发送博客用户删除日记mq消息
        sendUserData.sendUserData(SendUserData.diary, userId, -idSet.size());
        // 发送博客系统删除日记mq消息
        sendSystemData.sendSystemData(SendSystemData.diary, -idSet.size());

        return idSet.size();

    }

    /**
     * 修改日记
     *
     * @param diaryVo
     * @return
     */
    @Override
    public Integer updateDiary(DiaryVo diaryVo) {
        diaryVo.setUpdateTime(new Date());
        diaryDAO.updateDiary(diaryVo);
        return diaryVo.getId();
    }


    /**
     * 通过日期查询未删除日记
     *
     * @param diaryVo
     * @param userId
     * @return
     */
    @Override
    public Map<String, Object> selectDiaryByDate(DiaryVo diaryVo, Integer userId) {
        MyPage<Diary> myPage = null;
        Map<String, Object> map = new HashMap<>();
        List<Diary> list = new ArrayList<>();
        String dateDay = diaryVo.getDate();
        String dateMonth = diaryVo.getDateMonth();
        if (diaryVo.getPageNum() != null && diaryVo.getPageSize() != null) {
            PageHelper.startPage(diaryVo.getPageNum(), diaryVo.getPageSize());
            Page<Diary> articlePage = (Page<Diary>) diaryDAO.selectDiaryList(userId);
            try {
                myPage = MyPageUtils.pageUtil(articlePage, articlePage.getPageNum(), articlePage.getPageSize(), (int) articlePage.getTotal());
            } catch (Exception e) {
                log.info("查找日记报错: {}", e.getMessage(), e);
            }
            map.put("diary", myPage);
        } else if (dateDay != null && !dateDay.equals("")) {
            String pattern = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";
            boolean isMatch = Pattern.matches(pattern, dateDay);
            if (isMatch) {
                list = diaryDAO.selectDiaryByDate(dateDay, userId);
            } else {
                map.put("msg", "请输入正确的日期格式(yyyy-MM-dd) ... ");
            }
            map.put("diary", list);
        } else if (dateMonth != null && !dateMonth.equals("")) {
            String pattern = "^[0-9]{4}-[0-9]{2}$";
            boolean isMatch = Pattern.matches(pattern, dateMonth);
            if (isMatch) {
                list = diaryDAO.selectDiaryByDate(dateMonth, userId);
            } else {
                map.put("msg", "请输入正确的月份格式(yyyy-MM) ... ");
            }
            map.put("diary", list);
        } else {
            map.put("msg", "date和dateMonth参数不能同时为空 ... ");
        }
        return map;
    }


    /**
     * 批量保存日记，内部导入接口使用
     *
     * @param map
     * @return
     */
    @Override
    public Map<String, List<String>> saveDiaryList(Map<String, Diary> map) {
        Map<String, List<String>> result = new HashMap<>();
        List<String> saveList = new ArrayList<>();
        List<String> updateList = new ArrayList<>();
        List<String> failList = new ArrayList<>();
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            Diary diary = map.get(key);
            List<Diary> diaries = diaryDAO.selectDiaryByDate(DateUtil.formatDate(diary.getDiaryDate()), diary.getUserId());
            if (diaries.size() == 0) {
                if (diaryDAO.insert(diary) == 1) {
                    saveList.add(key);
                } else {
                    failList.add(key);
                }
            } else {
                diary.setId(diaries.get(0).getId());
                if (diaryDAO.updateDiary(diary) == 1) {
                    updateList.add(key);
                } else {
                    failList.add(key);
                }
            }
        }
        result.put("save", saveList);
        result.put("update", updateList);
        result.put("fail", failList);
        return result;
    }



}
