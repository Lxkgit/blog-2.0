package com.blog.user.service;


import com.blog.common.entity.auth.LoginUser;
import com.blog.common.entity.user.BlogUser;

public interface UserService {

    LoginUser findByUsername(String username);
    String registerUser(BlogUser blogUser);
    BlogUser selectUserByUsername(String username);
}
