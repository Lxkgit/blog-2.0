package com.blog.file.controller;

import com.blog.common.entity.file.vo.ChipVo;
import com.blog.common.exception.ValidException;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.valication.group.AddGroup;
import com.blog.common.valication.group.SelectIdGroup;
import com.blog.common.valication.group.SelectListGroup;
import com.blog.common.valication.group.UpdateGroup;
import com.blog.file.service.ChipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @description: 单片机服务接口
 * @Author: 308501
 * @date 2024/1/30 19:56
 */

@Slf4j
@RestController
@RequestMapping("/chip")
public class ChipController extends BaseController {

    @Resource
    private ChipService chipService;

    /**
     * 新增单片机接口
     *
     * @param request
     * @param chipVo
     * @return
     * @throws ValidException
     */
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('sys:chip:save')")
    public Result addChip(HttpServletRequest request, @Validated(value = {AddGroup.class}) @RequestBody ChipVo chipVo) throws ValidException {
        return ResultFactory.buildSuccessResult(chipService.addChip(getBlogUser(request).getId(), chipVo));
    }

    /**
     * 删除单片机
     *
     * @param request
     * @param chipVo
     * @return
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:chip:delete')")
    public Result deleteChip(HttpServletRequest request, @Validated(value = {DeleteMapping.class}) ChipVo chipVo) {
        return ResultFactory.buildSuccessResult(chipService.deleteChips(getBlogUser(request).getId(), chipVo.getIds()));
    }

    /**
     * 修改单片机信息
     *
     * @param request
     * @param chipVo
     * @return
     * @throws ValidException
     */
    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('sys:chip:update')")
    public Result updateChip(HttpServletRequest request, @Validated(value = {UpdateGroup.class}) @RequestBody ChipVo chipVo) throws ValidException {
        return ResultFactory.buildSuccessResult(chipService.updateChip(getBlogUser(request).getId(), chipVo));
    }

    /**
     * 分页查询单片机列表
     *
     * @param request
     * @param chipVo
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:chip:select')")
    public Result selectChipList(HttpServletRequest request, @Validated(value = {SelectListGroup.class}) ChipVo chipVo) {
        return ResultFactory.buildSuccessResult(chipService.selectChipList(getBlogUser(request).getId(), chipVo));
    }

    /**
     * 查询指定单片机信息
     *
     * @param request
     * @param chipVo
     * @return
     */
    @GetMapping("/id")
    @PreAuthorize("hasAnyAuthority('sys:chip:select')")
    public Result selectChipId(HttpServletRequest request, @Validated(value = {SelectIdGroup.class}) ChipVo chipVo) {
        return ResultFactory.buildSuccessResult(chipService.selectChipId(getBlogUser(request).getId(), chipVo.getId()));
    }

}
