package com.blog.file.controller;

import com.blog.common.entity.file.vo.DeviceVo;
import com.blog.common.exception.ValidException;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.valication.group.AddGroup;
import com.blog.common.valication.group.DeleteGroup;
import com.blog.common.valication.group.SelectIdGroup;
import com.blog.common.valication.group.UpdateGroup;
import com.blog.file.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @description: 下级设备接口
 * @Author: 308501
 * @date 2024/1/29 13:48
 */

@Slf4j
@RestController
@RequestMapping("/device")
public class DeviceController extends BaseController {

    @Resource
    private DeviceService deviceService;


    /**
     * 创建远程服务器
     *
     * @param request
     * @param deviceVo
     * @return
     * @throws ValidException
     */
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('sys:device:save')")
    public Result addDevice(HttpServletRequest request, @Validated(value = {AddGroup.class}) @RequestBody DeviceVo deviceVo) throws ValidException {
        return ResultFactory.buildSuccessResult(deviceService.addDevice(getBlogUser(request).getId(), deviceVo));
    }

    /**
     * 删除远程服务器
     *
     * @param request
     * @param deviceVo
     * @return
     * @throws ValidException
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:device:delete')")
    public Result deleteDevice(HttpServletRequest request, @Validated(value = {DeleteGroup.class}) DeviceVo deviceVo) throws ValidException {
        return ResultFactory.buildSuccessResult(deviceService.deleteDevice(getBlogUser(request).getId(), deviceVo.getIds()));
    }

    /**
     * 修改远程服务器配置信息
     *
     * @param request
     * @param deviceVo
     * @return
     * @throws ValidException
     */
    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('sys:device:update')")
    public Result updateDevice(HttpServletRequest request, @Validated(value = {UpdateGroup.class}) @RequestBody DeviceVo deviceVo) throws ValidException {
        return ResultFactory.buildSuccessResult(deviceService.updateDevice(getBlogUser(request).getId(), deviceVo));
    }

    /**
     * 查询用户全部的远程服务器设备
     *
     * @param request
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:device:select')")
    public Result selectDeviceList(HttpServletRequest request) {
        return ResultFactory.buildSuccessResult(deviceService.selectDeviceList(getBlogUser(request).getId()));
    }

    /**
     * 根据id查询服务器设备信息
     *
     * @param request
     * @param deviceVo
     * @return
     */
    @GetMapping("/id")
    @PreAuthorize("hasAnyAuthority('sys:device:select')")
    public Result selectDeviceById(HttpServletRequest request, @Validated(value = {SelectIdGroup.class}) DeviceVo deviceVo) {
        return ResultFactory.buildSuccessResult(deviceService.selectDeviceById(getBlogUser(request).getId(), deviceVo.getId()));
    }

}
