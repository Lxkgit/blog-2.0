package com.blog.pi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Map;

/**
 * @description: 传感器数据表
 * @Author: 308501
 * @date 2024/1/9 10:56
 */

@Data
@TableName("sensor_data")
public class SensorData {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String chipType;

    private String sensorType;

    // mqtt 接收的数据
    private String data;

    // 接收消息时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
