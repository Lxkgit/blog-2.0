package com.blog.user.controller;

import com.blog.common.entity.user.BlogUser;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.util.JwtUtil;
import com.blog.user.service.SysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Author: lxk
 * @date 2022/6/24 17:24
 * @description: 系统菜单接口
 */

@Slf4j
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * 获取用户权限菜单
     * type = 1 获取到目录
     * type = 2 获取到操作
     * @param menuType 权限区分
     * @return
     */
    @GetMapping("/list")
    public Result getSysMenuList(@RequestParam(value = "type") Integer menuType){
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("Authorization");
        }
        try {
            BlogUser blogUser = JwtUtil.getUserInfo(token);
            return ResultFactory.buildSuccessResult(sysPermissionService.selectPermissionListByUserId(blogUser.getId(), menuType));
        } catch (Exception e) {
            log.warn("" + e);
        }
        return null;
    }

    /**
     * 获取全部菜单
     * type = 1 获取到目录
     * type = 2 获取到操作
     * @param menuType
     * @return
     */
    @GetMapping("/all/list")
    public Result getSysMenuAllList(@RequestParam(value = "type") Integer menuType){
        try {
            return ResultFactory.buildSuccessResult(sysPermissionService.selectPermissionList(menuType));
        } catch (Exception e) {
            log.warn("" + e);
        }
        return null;
    }
}
