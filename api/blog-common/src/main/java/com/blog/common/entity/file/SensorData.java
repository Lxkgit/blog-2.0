package com.blog.common.entity.file;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: 传感器上报数据表
 * @Author: lxk
 * @date 2024/1/31 19:30
 */

@Data
@TableName("blog_sensor_data")
public class SensorData {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 设备编码
     */
    private String deviceCode;

    /**
     * 传感器编码
     */
    private String chipCode;

    /**
     * 消息编码（上电随机生成）
     */
    private String msgCode;

    /**
     * 本次上电发送消息数
     */
    private Integer msgCount;

    /**
     * 传感器编码
     */
    private String sensorCode;

    /**
     * 传感器数据
     */
    private String sensorData;

    /**
     * 数据上报时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
