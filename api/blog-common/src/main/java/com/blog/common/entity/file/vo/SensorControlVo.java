package com.blog.common.entity.file.vo;

import com.blog.common.entity.file.SensorControl;
import com.blog.common.valication.group.SelectIdGroup;
import com.blog.common.valication.group.SelectListGroup;
import com.blog.common.valication.group.UpdateGroup;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @description: 传感器控制Vo类
 * @Author: 308501
 * @date 2024/2/2 10:32
 */

@Getter
@Setter
public class SensorControlVo extends SensorControl {

    /**
     * 传感器id
     */
    @NotNull(message = "传感器id不为空", groups = {UpdateGroup.class, SelectIdGroup.class})
    @Min(value = 1, message = "传感器id值最小为1", groups = {UpdateGroup.class, SelectIdGroup.class})
    private Integer id;

    private String sensorCode;

    /**
     * 传感器id
     */
    @Min(value = 1, message = "传感器id值最小为1", groups = {UpdateGroup.class, SelectListGroup.class})
    private Integer sensorId;

    /**
     * 页大小
     */
    @NotNull(message = "分页查询页大小不能为空", groups = {SelectListGroup.class})
    @Max(value = 100, message = "分页大小最大为100", groups = {SelectListGroup.class})
    @Min(value = 5, message = "分页大小最小为5", groups = {SelectListGroup.class})
    private Integer pageSize;

    /**
     * 页数
     */
    @NotNull(message = "分页查询页数不能为空", groups = {SelectListGroup.class})
    @Min(value = 1, message = "分页查询页数最小1", groups = {SelectListGroup.class})
    private Integer pageNum;
}
