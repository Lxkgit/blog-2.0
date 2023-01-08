package com.blog.content.config;

import com.blog.common.constant.PermitUrl;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: lxk
 * @date 2022/6/7 16:54
 * @description: 资源服务器配置
 */


@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String[] permitUrl = {
            "/article/list",
            "/article/id",
            "/article/type/tree",
            "/article/type/node",
            "/usp/**"
    };

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and().authorizeRequests()
                .antMatchers(PermitUrl.permitAllUrl(permitUrl)).permitAll() // 放开权限的url
                .anyRequest().authenticated().and().httpBasic();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
//        resources
//                // 无状态模式，对于资源服务器只需要验证token 没有用户概念
//                .stateless(false);
    }
}

