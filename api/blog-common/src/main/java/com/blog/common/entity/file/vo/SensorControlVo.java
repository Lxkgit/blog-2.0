package com.blog.common.entity.file.vo;

import com.blog.common.entity.file.Sensor;
import com.blog.common.entity.file.SensorControl;
import com.blog.common.valication.annotation.Equal;
import com.blog.common.valication.group.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @description: 传感器控制Vo类
 * @Author: lxk
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

    /**
     * 传感器类型
     */
    private String sensorType;

    /**
     * 传感器id
     */
    @Min(value = 1, message = "传感器id值最小为1", groups = {UpdateGroup.class, SelectListGroup.class})
    private Integer sensorId;

    /**
     * 是否为命令组 1：命令组 0：单条命令
     */
    @Equal(value = "0,1", message = "传感器控制命令组必须是0或1", groups = {AddGroup.class})
    private Integer commandGroup;

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
     * 传感器ids
     */
//    @Pattern(regexp = "^[0-9]+(,[0-9]+)+|[0-9]+$", message = "请输入正确的传感器id字符串", groups = {DeleteGroup.class})
    private List<Integer> ids;

    /**
     * 命令控制的传感器
     */
    private List<Sensor> sensorList;
}
