package com.blog.content.feign;

import com.blog.common.entity.user.BlogUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: lxk
 * @date 2022年7月27日 15点48分
 * @description:
 */

@FeignClient("blog-user")
public interface UserClient {
    @GetMapping(value = "/user/user/select/id")
    BlogUser selectUserById(@RequestParam(value = "userId") Integer userId);
}
