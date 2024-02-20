package com.blog.common.entity.file;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
     * 传感器状态 0:离线 1:在线 2:删除
     */
    private Integer sensorStatus;

    /**
     * 传感器备注信息
     */
    private String memo;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 最近修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
