package com.blog.file.config;

import feign.Logger;
import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: lxk
 * @date 2022/7/13 10:00
 * @description: feign配置
 */

@Configuration
public class FeignConfig {

    @Bean
    public Logger.Level feignLoggerLevel(){
        return Logger.Level.BASIC;
    }

    /**
     * 超时时间配置
     */
    @Bean
    public Request.Options options(){
        return new Request.Options(5000,10000);
    }

    @Bean
    public FeignClientInterceptor feignClientInterceptor(){
        return new FeignClientInterceptor();
    }
}
