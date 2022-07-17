package com.blog.file.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: lxk
 * @date 2022/7/13 9:49
 * @description: Feign拦截器
 */


public class FeignClientInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        try {
            // 获取请求对象
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (servletRequestAttributes != null) {
                HttpServletRequest request = servletRequestAttributes.getRequest();
                // 获取当前请求的header，获取到jwt令牌
                if (request.getHeader("Authorization") != null) {
                    template.header("Authorization", request.getHeader("Authorization"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
