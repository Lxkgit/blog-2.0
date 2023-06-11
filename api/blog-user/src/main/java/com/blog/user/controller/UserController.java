package com.blog.user.controller;

import com.blog.common.entity.auth.LoginUser;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.entity.user.vo.SysRoleVo;
import com.blog.common.entity.user.vo.SysUserVo;
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
        return loginUser;
    }

    @GetMapping(value = "/select/id")
    public BlogUser selectUserById(@RequestParam(value = "userId") Integer userId){
        return userService.selectUserById(userId);
    }

    @GetMapping(value = "/select/username", params = "username")
    public BlogUser selectUserByUsername(String username) {
        return userService.selectUserByUsername(username);
    }

    @PostMapping("/register")
    public Result registerUser(@RequestBody BlogUser blogUser){
        String msg = userService.registerUser(blogUser);
        return ResultFactory.buildSuccessResult(msg);
    }

    @GetMapping(value = "/select/user/id")
    public Result selectUserMsgById(@RequestParam(value = "userId") Integer userId){
        return ResultFactory.buildSuccessResult(userService.selectUserById(userId));
    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:user:list')")
    public Result selectUserList(@RequestParam(value = "page") Integer page, @RequestParam(value = "size") Integer size) {
        if (page==0 || size==0){
            return ResultFactory.buildFailResult("请输入正确的查询页 ... ");
        }
        return ResultFactory.buildSuccessResult(userService.selectUserByPage(page, size));
    }

    /**
     * 用户修改个人信息
     * @return
     */
    @PostMapping("/update")
    public Result updateUserMsg(@RequestBody SysUserVo sysUserVo) {
        userService.updateUser(sysUserVo, 0);
        return ResultFactory.buildSuccessResult();
    }

    /**
     * 管理员修改角色信息
     * @return
     */
    @PostMapping("/permission/update")
    @PreAuthorize("hasAnyAuthority('sys:user:role:update')")
    public Result updateUserPer(@RequestBody SysUserVo sysUserVo) {
        userService.updateUser(sysUserVo, 1);
        return ResultFactory.buildSuccessResult();
    }

    @GetMapping("/doc/user")
    public Result selectDocUserList() {
        return ResultFactory.buildSuccessResult(userService.selectUserByPage(1, 50));
    }

}
