package com.blog.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: lxk
 * @date: 2022/6/12 14:24
 * @description: 用户中心
 * @modified By:
 */

@SpringBootApplication
@EnableFeignClients
@MapperScan("com.blog.user.dao")
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
        System.out.println("用户中心启动成功...");
    }
}

