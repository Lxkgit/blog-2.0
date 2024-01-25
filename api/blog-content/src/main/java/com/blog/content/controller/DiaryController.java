package com.blog.content.controller;

import com.blog.common.entity.content.diary.Diary;
import com.blog.common.entity.content.diary.vo.DiaryVo;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.exception.ValidException;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.util.JwtUtil;
import com.blog.common.valication.group.AddGroup;
import com.blog.common.valication.group.DeleteGroup;
import com.blog.common.valication.group.UpdateGroup;
import com.blog.content.service.DiaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
public class DiaryController extends BaseController {

    @Resource
    private DiaryService diaryService;

    /**
     * 新增日记
     *
     * @param request
     * @param diaryVo
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('sys:diary:insert')")
    public Result saveDiary(HttpServletRequest request, @RequestBody @Validated(value = {AddGroup.class}) DiaryVo diaryVo) {
        diaryVo.setUserId(getBlogUser(request).getId());
        return ResultFactory.buildSuccessResult(diaryService.saveDiary(diaryVo));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:diary:delete')")
    public Result deleteDiaryByDate(HttpServletRequest request, @Validated(value = {DeleteGroup.class}) DiaryVo diaryVo) throws ValidException {
        return ResultFactory.buildSuccessResult(diaryService.deleteDiary(diaryVo.getIds(), getBlogUser(request).getId()));
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('sys:diary:update')")
    public Result updateDiary(HttpServletRequest request, @RequestBody @Validated(value = {UpdateGroup.class}) DiaryVo diaryVo) {
        diaryVo.setUserId(getBlogUser(request).getId());
        return ResultFactory.buildSuccessResult(diaryService.updateDiary(diaryVo));
    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:diary:list')")
    public Result selectDiaryByDate(HttpServletRequest request, DiaryVo diaryVo) {
        return ResultFactory.buildSuccessResult(diaryService.selectDiaryByDate(diaryVo, getBlogUser(request).getId()));
    }

    /**
     * 内部日记上传接口
     *
     * @param map
     * @return
     */
    @PostMapping("/save/list")
    Map<String, List<String>> saveDiaryList(@RequestBody Map<String, Diary> map) {
        return diaryService.saveDiaryList(map);
    }
}
