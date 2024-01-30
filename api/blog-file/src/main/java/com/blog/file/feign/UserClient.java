package com.blog.file.feign;

import com.blog.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description: 用户服务
 * @Author: 308501
 * @date 2024/1/29 15:03
 */

@FeignClient("blog-user")
public interface UserClient {

    @GetMapping("/user/user/username")
    Result getUserByUsername(@RequestParam(value = "username") String username);

    @GetMapping("/user/user/id")
    Result getUserById(@RequestParam(value = "id") Integer id);
}
