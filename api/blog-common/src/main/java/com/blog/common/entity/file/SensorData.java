package com.blog.common.entity.file;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @description: 传感器上报数据表
 * @Author: 308501
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
     * 传感器id
     */
    private Integer sensorId;

    /**
     * 传感器数据
     */
    private String sensorData;
}
