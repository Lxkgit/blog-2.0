package com.blog.user.service.impl;


import com.blog.common.entity.user.SysRole;
import com.blog.user.dao.SysRoleDAO;
import com.blog.user.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @Author: lxk
 * @date 2022/6/6 17:11
 * @description:
 */

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleDAO sysRoleDAO;

    @Override
    public Set<SysRole> selectRoleByUserId(Integer userId) {
        return sysRoleDAO.selectRoleByUserId(userId);
    }
}
