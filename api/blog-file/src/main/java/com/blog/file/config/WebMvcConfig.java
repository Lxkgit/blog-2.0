package com.blog.file.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: lxk
 * @date 2022/12/20 14:16
 * @description: 图片绝对地址与虚拟地址映射
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        boolean win = System.getProperty("os.name").toLowerCase().contains("win");
        System.out.println(System.getProperty("os.name").toLowerCase());
        String pathPatterns;
        String pathAbsolute;
        if (!win) {
            pathPatterns = "/package/**";
            pathAbsolute = "file:/opt/package/";
        } else {
            pathPatterns = "/files/**";
            pathAbsolute = "file:D:/files/";
        }
        registry.addResourceHandler(pathPatterns)
                .addResourceLocations(pathAbsolute);
    }
}
