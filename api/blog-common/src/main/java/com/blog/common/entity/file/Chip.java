package com.blog.common.entity.file;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @description: 单片机表字段
 * @Author: 308501
 * @date 2024/1/30 19:59
 */

@Data
@TableName("blog_chip")
public class Chip {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户id
     */
    private Integer deviceId;

    /**
     * 单片机名称
     */
    private String chipName;

    /**
     * 单片机编码
     */
    private String chipCode;

    /**
     * 单片机状态 0:离线 1:在线 2:删除
     */
    private Integer chipStatus;

    /**
     * 单片机类型
     */
    private String chipType;

    /**
     * 备注信息
     */
    private String memo;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最近修改时间
     */
    private Date updateTime;

}
