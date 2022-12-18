package com.blog.user.service.impl;

import com.blog.common.entity.auth.LoginUser;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.entity.user.SysPermission;
import com.blog.common.entity.user.SysRole;
import com.blog.common.util.MyPage;
import com.blog.common.util.MyPageUtils;
import com.blog.user.dao.SysUserDAO;
import com.blog.user.service.SysPermissionService;
import com.blog.user.service.SysRoleService;
import com.blog.user.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: lxk
 * @date 2022/6/6 16:50
 * @description:
 */

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private SysUserDAO sysUserDAO;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public LoginUser findByUsername(String username) {
        BlogUser blogUser = sysUserDAO.selectUserByUsername(username);
        if (blogUser != null) {
            LoginUser loginAppUser = new LoginUser();
            BeanUtils.copyProperties(blogUser, loginAppUser);

            Set<SysRole> sysRoles = sysRoleService.selectRoleByUserId(blogUser.getId());
            loginAppUser.setSysRoles(sysRoles);// 设置角色

            if (!CollectionUtils.isEmpty(sysRoles)) {
                Set<Integer> roleIds = sysRoles.parallelStream().map(SysRole::getId).collect(Collectors.toSet());
                Set<SysPermission> sysPermissions = sysPermissionService.selectPermissionByRoleIds(roleIds, 3);
                if (!CollectionUtils.isEmpty(sysPermissions)) {
                    Set<String> permissions = sysPermissions.parallelStream().map(SysPermission::getPermission)
                            .collect(Collectors.toSet());
                    loginAppUser.setPermissions(permissions);// 设置权限集合
                }

            }

            return loginAppUser;
        }

        return null;
    }

    @Override
    public BlogUser selectUserById(Integer userId) {
        return sysUserDAO.selectUserById(userId);
    }

    @Override
    public String registerUser(BlogUser blogUser) {
        String username = blogUser.getUsername();
        if (StringUtils.isBlank(username)) {
            return "用户名不能为空";
        }
        if (StringUtils.isBlank(blogUser.getPassword())) {
            return "密码不能为空";
        }
        BlogUser user = sysUserDAO.selectUserByUsername(username);
        if (user != null){
            return "用户名已存在";
        }
        blogUser.setPassword(passwordEncoder.encode(blogUser.getPassword()));
        blogUser.setStatus(1);
        blogUser.setCreateTime(new Date());
        blogUser.setUpdateTime(blogUser.getCreateTime());
        sysUserDAO.insertUser(blogUser);
        return "注册成功";
    }

    @Override
    public BlogUser selectUserByUsername(String username) {
        return sysUserDAO.selectUserByUsername(username);
    }

    @Override
    public MyPage<BlogUser> selectUserByPage(int page, int size) {
        MyPage<BlogUser> myPage = null;
        PageHelper.startPage(page, size);
        Page<BlogUser> userPage = (Page<BlogUser>) sysUserDAO.selectUserList();
        try {
//            int count = sysUserDAO.selectUserCount();
            myPage = MyPageUtils.pageUtil(userPage, userPage.getPageNum(), userPage.getPageSize(), (int) userPage.getTotal());
        } catch (Exception e){
            e.printStackTrace();
        }
        return myPage;
    }
}