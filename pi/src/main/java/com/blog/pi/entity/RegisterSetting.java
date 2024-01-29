package com.blog.pi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @description: 注册设置表
 * @Author: 308501
 * @date 2024/1/29 15:46
 */

@Data
@TableName("register_setting")
public class RegisterSetting {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String settingName;

    private String setting;
}
