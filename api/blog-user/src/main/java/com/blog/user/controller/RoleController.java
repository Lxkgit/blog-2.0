package com.blog.user.controller;

import com.blog.common.entity.user.SysRole;
import com.blog.common.entity.user.vo.SysRoleVo;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.user.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/select")
    @PreAuthorize("hasAnyAuthority('sys:role')")
    public Result selectDiaryByDate(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                    @RequestParam(value = "size", defaultValue = "0") Integer size){
        if (page==0 || size==0){
            return ResultFactory.buildFailResult("请输入正确的查询页 ... ");
        }
        return ResultFactory.buildSuccessResult(sysRoleService.selectRoleByPage(page, size));
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('sys:role:insert')")
    public Result saveDiary(@RequestBody SysRole sysRole){
        return ResultFactory.buildSuccessResult(sysRoleService.saveRole(sysRole));
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('sys:role:update')")
    public Result updateDiary(@RequestBody SysRole sysRole){
        return ResultFactory.buildSuccessResult(sysRoleService.updateRole(sysRole));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:role:delete')")
    public Result deleteDiaryByDate(@RequestParam(value = "ids") String ids){
        return ResultFactory.buildSuccessResult(sysRoleService.deleteRoleByIds(ids));
    }

    @GetMapping("/permission/select")
    @PreAuthorize("hasAnyAuthority('sys:role')")
    public Result selectRolePermission(@RequestParam(value = "roleId") Integer roleId) {
        return ResultFactory.buildSuccessResult(sysRoleService.selectRolePermission(roleId));

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
