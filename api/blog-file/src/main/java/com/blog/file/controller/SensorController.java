package com.blog.file.controller;

import com.blog.common.entity.file.vo.SensorVo;
import com.blog.common.exception.ValidException;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.valication.group.AddGroup;
import com.blog.common.valication.group.SelectIdGroup;
import com.blog.common.valication.group.SelectListGroup;
import com.blog.common.valication.group.UpdateGroup;
import com.blog.file.service.SensorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @description: 传感器接口类
 * @Author: 308501
 * @date 2024/1/30 20:07
 */

@Slf4j
@RestController
@RequestMapping("/sensor")
public class SensorController extends BaseController {

    @Resource
    private SensorService sensorService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('sys:sensor:save')")
    public Result addSensor(HttpServletRequest request, @Validated(value = {AddGroup.class}) @RequestBody SensorVo sensorVo) throws ValidException {
        return ResultFactory.buildSuccessResult(sensorService.addSensor(getBlogUser(request).getId(), sensorVo));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:sensor:delete')")
    public Result deleteSensor(HttpServletRequest request, @Validated(value = {DeleteMapping.class}) SensorVo sensorVo) {
        return ResultFactory.buildSuccessResult(sensorService.deleteSensors(getBlogUser(request).getId(), sensorVo.getIds()));
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('sys:sensor:update')")
    public Result updateSensor(HttpServletRequest request, @Validated(value = {UpdateGroup.class}) @RequestBody SensorVo sensorVo) throws ValidException {
        return ResultFactory.buildSuccessResult(sensorService.updateSensor(getBlogUser(request).getId(), sensorVo));
    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:sensor:select')")
    public Result selectSensorList(HttpServletRequest request, @Validated(value = {SelectListGroup.class}) SensorVo sensorVo) {
        return ResultFactory.buildSuccessResult(sensorService.selectSensorList(getBlogUser(request).getId(), sensorVo));
    }

    @GetMapping("/id")
    @PreAuthorize("hasAnyAuthority('sys:sensor:select')")
    public Result selectSensorId(HttpServletRequest request, @Validated(value = {SelectIdGroup.class}) SensorVo sensorVo) {
        return ResultFactory.buildSuccessResult(sensorService.selectSensorId(getBlogUser(request).getId(), sensorVo.getId()));
    }

    /**
     * 发送传感器控制命令
     *
     * @param request
     * @param sensorVo
     * @return
     */
    @GetMapping("/control")
    @PreAuthorize("hasAnyAuthority('sys:sensor:control')")
    public Result controlSensor(HttpServletRequest request, @Validated(value = {SelectIdGroup.class}) SensorVo sensorVo) {
        sensorService.controlSensor(getBlogUser(request).getId(), sensorVo.getId());
        return ResultFactory.buildSuccessResult();
    }

}
