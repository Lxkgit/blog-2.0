package com.blog.auth.feign;

import com.blog.common.entity.auth.LoginUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: lxk
 * @date 2022/6/6 16:15
 * @description:
 */

@FeignClient("user-center")
public interface UserClient {

    @GetMapping(value = "/user/user/login", params = "username")
    LoginUser findByUsername(@RequestParam("username") String username);

}
