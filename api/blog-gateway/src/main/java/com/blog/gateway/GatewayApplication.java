package com.blog.gateway;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author: lxk
 * @date: 2022/6/12 15:19
 * @description: 网关
 * @modified By:
 */


@EnableScheduling //开启定时任务
@SpringBootApplication
@MapperScan("com.blog.gateway.dao")
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
        System.out.println("网关服务启动成功...");
    }
}