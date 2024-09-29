package com.blog.file.controller;

import com.blog.common.entity.file.dto.SensorTemplateDTO;
import com.blog.common.exception.ValidException;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.valication.group.AddGroup;
import com.blog.common.valication.group.SelectIdGroup;
import com.blog.file.service.SensorTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description 传感器模板接口类
 * @Author lxk
 * @CreateTime 2024-09-29
 */

@Slf4j
@RestController
@RequestMapping("/sensorTemplate")
public class SensorTemplateController extends BaseController {

    @Resource
    private SensorTemplateService sensorTemplateService;

    /**
     *
     * @return
     */
    @GetMapping("/chipOrSensorId")
    public Result selectSensorTemplateByChipOrSensorId(HttpServletRequest request, SensorTemplateDTO sensorTemplateDTO) throws ValidException {
        return ResultFactory.buildSuccessResult(sensorTemplateService.selectSensorTemplateByChipOrSensorId(getBlogUser(request).getId(), sensorTemplateDTO));
    }

}
