package com.blog.common.entity.file;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @description: 传感器设备字典表
 * @Author: 308501
 * @date 2024/1/31 19:31
 */

@Data
@TableName("blog_sensor_type")
public class SensorType {

    private Integer id;

    /**
     * 传感器型号编码
     */
    private String sensorCode;

    /**
     * 传感器类型（数据上报类：0 命令控制类：1）
     */
    private Integer sensorType;

    /**
     * 传感器名称
     */
    private String sensorName;

    /**
     * 传感器图片
     */
    private String sensorImg;

    /**
     * 备注信息
     */
    private String memo;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

}
