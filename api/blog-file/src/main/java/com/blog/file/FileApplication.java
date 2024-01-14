package com.blog.file;

import com.blog.file.utils.AppContextHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

/**
 * @author: lxk
 * @date: 2022/7/7 20:42
 * @description: 文件上传服务中心
 * @modified By:
 */

@EnableAsync //启用异步
@EnableScheduling
@EnableFeignClients
@SpringBootApplication
@MapperScan("com.blog.file.dao")
public class FileApplication {

    public static void main(String[] args) {
//        SpringApplication.run(FileApplication.class, args);
//
        SpringApplication application = new  SpringApplication(FileApplication.class);
        ApplicationContext applicationContext = application.run(args);
        AppContextHelper.setContext(applicationContext, true);
    }
}
