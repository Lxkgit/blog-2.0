package com.blog.common.entity.file;

import lombok.Data;

/**
 * @description: 登录第三方中间件实体类
 * @Author: 308501
 * @date 2023/8/8 14:33
 */

@Data
public class LoginConfig {

    private String ip;

    private Integer port;

    private String userName;

    private String password;
}
