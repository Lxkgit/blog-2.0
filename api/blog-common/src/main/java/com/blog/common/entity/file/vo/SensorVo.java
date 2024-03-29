package com.blog.common.entity.file.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.blog.common.entity.file.Sensor;
import com.blog.common.entity.file.SensorType;
import com.blog.common.valication.group.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

/**
 * @description: 传感器Vo类
 * @Author: 308501
 * @date 2024/2/1 10:31
 */

@Getter
@Setter
public class SensorVo extends Sensor {

    /**
     * 传感器id
     */
    @NotNull(message = "传感器id不为空", groups = {UpdateGroup.class, SelectIdGroup.class})
    @Min(value = 1, message = "传感器id值最小为1", groups = {UpdateGroup.class, SelectIdGroup.class})
    private Integer id;

    /**
     * 单片机id
     */
    @NotNull(message = "单片机id不为空", groups = {UpdateGroup.class})
    @Min(value = 1, message = "单片机id值最小为1", groups = {UpdateGroup.class})
    private Integer chipId;

    /**
     * 传感器类型id
     */
    @NotNull(message = "传感器类型id不为空", groups = {UpdateGroup.class})
    @Min(value = 1, message = "传感器类型id值最小为1", groups = {UpdateGroup.class})
    private Integer sensorTypeId;

    /**
     * 传感器名称
     */
    @Size(min = 1, max = 50, message = "传感器名称长度范围是1-50个字符", groups = {AddGroup.class, UpdateGroup.class})
    private String sensorName;

    /**
     * 传感器编码
     */
    @NotNull(message = "传感器编码不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @NotEmpty(message = "传感器编码不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Pattern(regexp = "^[a-zA-Z0-9-_]{1,32}$", message = "传感器编码只允许输入最长32位字母数字下划线和'-'", groups = {AddGroup.class, UpdateGroup.class})
    private String sensorCode;

    /**
     * 传感器id列表 多个id使用 , 间隔
     */
    @Pattern(regexp = "^[0-9]+(,[0-9]+)+|[0-9]+$", message = "请输入正确的设备id字符串", groups = {DeleteGroup.class})
    private String ids;

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

    /**
     * 传感器类型
     */
    private SensorType sensorType;
}
