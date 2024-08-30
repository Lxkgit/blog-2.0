package com.blog.common.entity.file;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description 用户申请设备编码表
 * @Author lxk
 * @CreateTime 2024-08-29
 */

@Data
@TableName("user_device")
public class UserDevice {

    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 设备编码
     */
    private String deviceCode;

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
