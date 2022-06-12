package com.blog.user.service;

import com.blog.common.entity.user.SysPermission;

import java.util.Set;

/**
 * @Author: lxk
 * @date 2022/6/6 17:10
 * @description:
 */

public interface SysPermissionService {
    Set<SysPermission> selectPermissionByRoleIds(Set<Integer> roleIds);
}
