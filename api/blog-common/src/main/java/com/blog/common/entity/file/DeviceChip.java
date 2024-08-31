package com.blog.common.entity.file;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description 设备单片机对应表
 * @Author lxk
 * @CreateTime 2024-08-31
 */

@Data
@TableName("device_chip")
public class DeviceChip {

    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 设备编码
     */
    private String deviceCode;

    /**
     * 单片机编码
     */
    private String chipCode;

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
