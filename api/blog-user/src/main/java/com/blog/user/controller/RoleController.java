package com.blog.user.controller;

import com.blog.common.entity.user.SysRole;
import com.blog.common.entity.user.vo.SysRoleVo;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.user.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: lxk
 * @date 2022/6/24 15:48
 * @description: 角色接口
 */

@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:role:select')")
    public Result selectRoleByPage(@RequestParam(value = "page") Integer page, @RequestParam(value = "size") Integer size){
        if (page==0 || size==0){
            return ResultFactory.buildFailResult("请输入正确的查询页 ... ");
        }
        return ResultFactory.buildSuccessResult(sysRoleService.selectRoleByPage(page, size));
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('sys:role:insert')")
    public Result saveRole(@RequestBody SysRole sysRole){
        return ResultFactory.buildSuccessResult(sysRoleService.saveRole(sysRole));
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('sys:role:update')")
    public Result updateRole(@RequestBody SysRole sysRole){
        return ResultFactory.buildSuccessResult(sysRoleService.updateRole(sysRole));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:role:delete')")
    public Result deleteRoleByIds(@RequestParam(value = "ids") String ids){
        return ResultFactory.buildSuccessResult(sysRoleService.deleteRoleByIds(ids));
    }

    @GetMapping("/permission/select")
    @PreAuthorize("hasAnyAuthority('sys:role:permission:select')")
    public Result selectRolePermission(@RequestParam(value = "roleId") Integer roleId, @RequestParam(value = "menuType") Integer menuType) {
        return ResultFactory.buildSuccessResult(sysRoleService.selectRolePermission(roleId, menuType));

    }

    @PostMapping("/permission/update")
    @PreAuthorize("hasAnyAuthority('sys:role:permission:update')")
    public Result updateRolePermission(@RequestBody SysRoleVo sysRoleVo) {
        if (sysRoleVo.getId().equals(1)) {
            return ResultFactory.buildFailResult("超级管理员用户权限无法修改 ... ");
        }
        return ResultFactory.buildSuccessResult(sysRoleService.updateRolePermission(sysRoleVo));

    }
}
