package com.blog.user.service;

import com.blog.common.entity.user.SysPermission;
import com.blog.common.entity.user.vo.SysPermissionVo;

import java.util.List;
import java.util.Set;

/**
 * @Author: lxk
 * @date 2022/6/6 17:10
 * @description:
 */

public interface SysPermissionService {
    Set<SysPermission> selectPermissionByRoleIds(Set<Integer> roleIds, Integer menuType);
    List<SysPermissionVo> selectPermissionListByUserId(Integer userId, Integer menuType);
    List<SysPermissionVo> selectPermissionList(Integer menuType);
}
