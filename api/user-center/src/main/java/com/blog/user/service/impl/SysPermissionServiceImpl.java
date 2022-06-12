package com.blog.user.service.impl;

import com.blog.common.entity.user.SysPermission;
import com.blog.user.dao.SysPermissionDAO;
import com.blog.user.service.SysPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @Author: lxk
 * @date 2022/6/6 17:10
 * @description:
 */

@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Resource
    private SysPermissionDAO sysPermissionDAO;

    @Override
    public Set<SysPermission> selectPermissionByRoleIds(Set<Integer> roleIds) {
        return sysPermissionDAO.selectPermissionByRoleIds(roleIds);
    }
}
