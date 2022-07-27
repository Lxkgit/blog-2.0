package com.blog.user.service;


import com.blog.common.entity.auth.LoginUser;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.util.MyPage;

public interface UserService {

    LoginUser findByUsername(String username);
    BlogUser selectUserById(Integer userId);
    String registerUser(BlogUser blogUser);
    BlogUser selectUserByUsername(String username);
    MyPage<BlogUser> selectUserByPage(int page, int size);
}
