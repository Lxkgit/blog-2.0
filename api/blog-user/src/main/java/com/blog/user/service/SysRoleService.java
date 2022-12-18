package com.blog.user.service;


import com.blog.common.entity.user.SysRole;
import com.blog.common.entity.user.vo.SysRoleVo;
import com.blog.common.util.MyPage;

import java.util.Map;
import java.util.Set;

public interface SysRoleService {
    Set<SysRole> selectRoleByUserId(Integer userId);
    MyPage<SysRole> selectRoleByPage(int page, int size);
    int saveRole(SysRole sysRole);
    int updateRole(SysRole sysRole);
    Map<String, Object> selectRolePermission(Integer roleId, Integer menuType);
    Map<String, Object> deleteRoleByIds(String ids);
    Map<String, Object> updateRolePermission(SysRoleVo sysRoleVo);
}
