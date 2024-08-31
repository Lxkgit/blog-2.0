package com.blog.common.entity.file;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description 单片机传感器对应表
 * @Author lxk
 * @CreateTime 2024-08-31
 */

@Data
@TableName("chip_sensor")
public class ChipSensor {


    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 单片机编码
     */
    private String chipCode;

    /**
     * 传感器编码
     */
    private String sensorCode;

    /**
     * 设备编码状态
     */
    private Integer codeStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最近修改时间
     */
    private Date updateTime;
}
