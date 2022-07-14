package com.blog.content.controller;

/**
 * @author: lxk
 * @date: 2022/6/22 22:50
 * @description:
 * @modified By:
 */

import com.blog.common.constant.Constant;
import com.blog.common.entity.content.diary.Diary;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.util.JwtUtil;
import com.blog.content.service.DiaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: lxk
 * @date 2022/6/22 16:05
 * @description: 日记接口
 */

@Slf4j
@RestController
@RequestMapping("/diary")
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @GetMapping("/select")
    public Result selectDiaryByDate(@RequestParam(required = false, value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") String date,
                                    @RequestParam(required = false, value = "dateMonth") @DateTimeFormat(pattern = "yyyy-MM") String dateMonth){
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("Authorization");
        }
        try {
            BlogUser blogUser = JwtUtil.getUserInfo(token);
            return ResultFactory.buildSuccessResult(diaryService.selectDiaryByDate(date, dateMonth, blogUser.getId()));
        } catch (Exception e){
            log.warn(Constant.JWTError, e);
        }
        return ResultFactory.buildFailResult("日记查询失败 ... ");
    }

    @PostMapping("/save")
    public Result saveDiary(@RequestBody Diary diary){
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("Authorization");
        }
        try {
            BlogUser blogUser = JwtUtil.getUserInfo(token);
            diary.setUserId(blogUser.getId());
            return ResultFactory.buildSuccessResult(diaryService.saveDiary(diary));
        } catch (Exception e){
            log.warn(Constant.JWTError, e);
        }
        return ResultFactory.buildFailResult("日记保存失败 ... ");
    }

    @PostMapping("/save/list")
    Result saveDiaryList(@RequestBody Map<Integer, Diary> map){
        return ResultFactory.buildSuccessResult(diaryService.saveDiaryList(map));
    }

    @PostMapping("/update")
    public Result updateDiary(@RequestBody Diary diary){
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("Authorization");
        }
        try {
            BlogUser blogUser = JwtUtil.getUserInfo(token);
            diary.setUserId(blogUser.getId());
            return ResultFactory.buildSuccessResult(diaryService.updateDiary(diary));
        } catch (Exception e){
            log.warn(Constant.JWTError, e);
        }
        return ResultFactory.buildFailResult("日记修改失败 ... ");
    }

    @DeleteMapping("/delete")
    public Result deleteDiaryByDate(@RequestParam(required = false, value = "ids") String ids){
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("Authorization");
        }
        try {
            BlogUser blogUser = JwtUtil.getUserInfo(token);
            return ResultFactory.buildSuccessResult(diaryService.deleteDiaryByDate(ids, blogUser.getId()));
        } catch (Exception e){
            log.warn(Constant.JWTError, e);
        }
        return ResultFactory.buildFailResult("日记删除失败 ... ");
    }
}
