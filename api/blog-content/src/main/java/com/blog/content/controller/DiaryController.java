package com.blog.content.controller;

import com.blog.common.entity.content.diary.Diary;
import com.blog.common.entity.content.diary.vo.DiaryVo;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.util.JwtUtil;
import com.blog.content.service.DiaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author: lxk
 * @date 2022/6/22 16:05
 * @description: 日记接口
 */

@Slf4j
@RestController
@RequestMapping("/diary")
public class DiaryController {

    @Resource
    private DiaryService diaryService;

    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:diary:list')")
    public Result selectDiaryByDate(@RequestHeader HttpHeaders headers, DiaryVo diaryVo){
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        return ResultFactory.buildSuccessResult(diaryService.selectDiaryByDate(diaryVo, blogUser.getId()));
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('sys:diary:insert')")
    public Result saveDiary(@RequestHeader HttpHeaders headers, @RequestBody Diary diary){
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        diary.setUserId(blogUser.getId());
        return ResultFactory.buildSuccessResult(diaryService.saveDiary(diary));
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('sys:diary:update')")
    public Result updateDiary(@RequestHeader HttpHeaders headers, @RequestBody Diary diary){
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        diary.setUserId(blogUser.getId());
        return ResultFactory.buildSuccessResult(diaryService.updateDiary(diary));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:diary:delete')")
    public Result deleteDiaryByDate(@RequestHeader HttpHeaders headers, @RequestParam(required = false, value = "ids") String ids){
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        return ResultFactory.buildSuccessResult(diaryService.deleteDiary(ids, blogUser.getId()));
    }

    /**
     * 内部日记上传接口
     * @param map
     * @return
     */
    @PostMapping("/save/list")
    Map<String, List<String>> saveDiaryList(@RequestBody Map<String, Diary> map){
        return diaryService.saveDiaryList(map);
    }
}
