package com.blog.pi.mqtt.data;

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

    private String clientId;

    public LoginConfig(String ip, Integer port, String userName, String password, String clientId) {
        this.ip = ip;
        this.port = port;
        this.userName = userName;
        this.password = password;
        this.clientId = clientId;
    }
}
