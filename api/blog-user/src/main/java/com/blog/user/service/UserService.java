package com.blog.user.service;


import com.blog.common.entity.auth.LoginUser;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.entity.user.vo.SysUserVo;
import com.blog.common.util.MyPage;

public interface UserService {

    LoginUser findByUsername(String username);
    BlogUser selectUserById(Integer userId);
    String registerUser(BlogUser blogUser);
    BlogUser selectUserByUsername(String username);
    MyPage<SysUserVo> selectUserByPage(int page, int size);

    /**
     * 修改用户信息
     * @param sysUserVo 用户信息
     * @param perFlag 是否修改用户权限 1修改 0不修改
     */
    void updateUser(SysUserVo sysUserVo, Integer perFlag);
}
