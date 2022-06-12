package com.blog.content;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: lxk
 * @date: 2022/6/12 20:58
 * @description: 博客内容服务 包括文档、日记、博文等
 * @modified By:
 */

@EnableFeignClients
@SpringBootApplication
@MapperScan("com.blog.content.dao")
public class ContentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentApplication.class, args);
        System.out.println("博客内容服务启动成功...");
    }
}

