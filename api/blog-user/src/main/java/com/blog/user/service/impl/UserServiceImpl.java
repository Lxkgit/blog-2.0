package com.blog.user.service.impl;

import com.blog.common.constant.ErrorMessage;
import com.blog.common.constant.RedisCode;
import com.blog.common.entity.auth.LoginUser;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.entity.user.SysPermission;
import com.blog.common.entity.user.SysRole;
import com.blog.common.entity.user.vo.BlogUserVo;
import com.blog.common.entity.user.vo.SysUserVo;
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
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
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

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

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
    public String getUserVerifyCode(String email) {
        if (Objects.equals(redisTemplate.hasKey(email),Boolean.TRUE)) {
            return ErrorMessage.USER_VERIFICATION_CODE_ALREADY_EXISTS.getDesc();
        }
        int code = ((Double)(Math.random() * 10000)).intValue();
        StringBuilder str = new StringBuilder(Integer.toString(code));
        if (str.length() < 4) {
            for (int i = str.length(); i < 4; i++) {
                str.append("0");
            }
        }
        redisTemplate.boundValueOps(RedisCode.USER_VERIFICATION_CODE.getKey() + email).set(str.toString(), 30, TimeUnit.MINUTES);
        System.out.println(str.toString());
        return ErrorMessage.USER_VERIFICATION_CODE_SEND_SUCCESS.getDesc();
    }

    @Override
    public String registerUser(BlogUserVo blogUserVo) {
        String username = blogUserVo.getUsername();
        if (StringUtils.isBlank(username)) {
            return "用户名不能为空";
        }
        if (StringUtils.isBlank(blogUserVo.getPassword())) {
            return "密码不能为空";
        }
        BlogUser user = sysUserDAO.selectUserByUsername(username);
        if (user != null){
            return "用户名已存在";
        }
        if (Objects.equals(redisTemplate.hasKey(RedisCode.USER_VERIFICATION_CODE.getKey() + blogUserVo.getEmail()), Boolean.FALSE)) {
            return "验证码不存在";
        }
        String code = (String) redisTemplate.boundValueOps(RedisCode.USER_VERIFICATION_CODE.getKey() + blogUserVo.getEmail()).get();
        if (code != null && !code.equals(blogUserVo.getCode())) {
            return "验证码错误";
        }
        blogUserVo.setPassword(passwordEncoder.encode(blogUserVo.getPassword()));
        blogUserVo.setStatus(1);
        blogUserVo.setCreateTime(new Date());
        blogUserVo.setUpdateTime(blogUserVo.getCreateTime());
        sysUserDAO.insert(blogUserVo);
        sysUserDAO.insertUserRole(Collections.singletonList(2), blogUserVo.getId());
        return "注册成功";
    }

    @Override
    public BlogUser selectUserByUsername(String username) {
        return sysUserDAO.selectUserByUsername(username);
    }

    @Override
    public MyPage<SysUserVo> selectUserByPage(int page, int size) {
        MyPage<SysUserVo> myPage = null;
        PageHelper.startPage(page, size);
        Page<BlogUser> userPage = (Page<BlogUser>) sysUserDAO.selectUserList();
        Page<SysUserVo> userVoPage = new Page<>();
        for (BlogUser user : userPage) {
            SysUserVo sysUserVo = new SysUserVo();
            BeanUtils.copyProperties(user, sysUserVo);
            Set<SysRole> sysRoles = sysRoleService.selectRoleByUserId(user.getId());
            sysUserVo.setSysRole(sysRoles);
            sysUserVo.setRoleIds(sysRoles.parallelStream().map(SysRole::getId).collect(Collectors.toList()));
            userVoPage.add(sysUserVo);
        }
        try {
            myPage = MyPageUtils.pageUtil(userVoPage, userPage.getPageNum(), userPage.getPageSize(), (int) userPage.getTotal());
        } catch (Exception e){
            e.printStackTrace();
        }
        return myPage;
    }

    @Override
    public void updateUser(SysUserVo sysUserVo, Integer perFlag) {
        sysUserDAO.updateUser(sysUserVo);
        if (perFlag == 1) {
            sysUserDAO.deleteUserRole(sysUserVo.getId());
            sysUserDAO.insertUserRole(sysUserVo.getRoleIds(), sysUserVo.getId());
        }
    }
}