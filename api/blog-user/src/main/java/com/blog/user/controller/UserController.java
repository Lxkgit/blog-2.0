package com.blog.user.controller;

import com.blog.common.entity.auth.LoginUser;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: lxk
 * @date 2022/6/6 16:32
 * @description:
 */

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/login", params = "username")
    public LoginUser findByUsername(String username) {
        LoginUser loginUser = userService.findByUsername(username);
        if (loginUser == null){
            throw new UsernameNotFoundException(username);
        }
        loginUser.setEnabled(true);
        return loginUser;
    }

    @PostMapping("/register")
    public Result registerUser(@RequestBody BlogUser blogUser){
        String msg = userService.registerUser(blogUser);
        return ResultFactory.buildSuccessResult(msg);
    }

    @GetMapping(value = "/select/username", params = "username")
    public BlogUser selectUserByUsername(String username) {
        return userService.selectUserByUsername(username);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:user')")
    public Result selectUserList(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                 @RequestParam(value = "size", defaultValue = "0") Integer size) {
        if (page==0 || size==0){
            return ResultFactory.buildFailResult("请输入正确的查询页 ... ");
        }
        return ResultFactory.buildSuccessResult(userService.selectUserByPage(page, size));
    }
}
