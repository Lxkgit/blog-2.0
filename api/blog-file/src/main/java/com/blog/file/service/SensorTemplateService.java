package com.blog.file.service;

import com.blog.common.entity.file.dto.SensorTemplateDTO;
import com.blog.common.entity.file.vo.SensorTemplateVO;
import com.blog.common.exception.ValidException;

import java.util.List;

/**
 * @author lxk
 * @description 传感器模板服务类接口
 * @date 2024/09/29
 */

public interface SensorTemplateService {

    List<SensorTemplateVO> selectSensorTemplateByChipOrSensorId(Integer userId, SensorTemplateDTO sensorTemplateDTO) throws ValidException;
}
