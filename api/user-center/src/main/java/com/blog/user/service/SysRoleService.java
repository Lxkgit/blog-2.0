package com.blog.user.service;


import com.blog.common.entity.user.SysRole;

import java.util.Set;

public interface SysRoleService {
    Set<SysRole> selectRoleByUserId(Integer userId);
}
