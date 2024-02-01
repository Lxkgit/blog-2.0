package com.blog.common.entity.file;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @description: 传感器表字段
 * @Author: 308501
 * @date 2024/1/30 20:09
 */

@Data
@TableName("blog_sensor")
public class Sensor {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 单片机id
     */
    private Integer chipId;

    /**
     * 传感器类型id
     */
    private Integer sensorTypeId;

    /**
     * 传感器名称
     */
    private String sensorName;

    /**
     * 传感器注册编码
     */
    private String sensorCode;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最近修改时间
     */
    private Date updateTime;
}
