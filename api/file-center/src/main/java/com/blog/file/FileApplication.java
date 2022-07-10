package com.blog.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: lxk
 * @date: 2022/7/7 20:42
 * @description: 文件上传服务中心
 * @modified By:
 */

@EnableFeignClients
@SpringBootApplication
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
        System.out.println("文件服务启动成功...");
    }
}
