package com.blog.common.util;

import cn.hutool.jwt.JWT;
import com.blog.common.entity.user.BlogUser;

/**
 * @Author: lxk
 * @date 2022/6/13 15:01
 * @description: jwt解析
 */

public class JwtUtil {

    public static BlogUser getUserInfo(String authorization){
        BlogUser blogUser = new BlogUser();
        String token = authorization.split(" ")[1];
        JWT jwt = JWT.of(token);

        blogUser.setId(Integer.parseInt(jwt.getPayload("user_id").toString()));
        blogUser.setUsername((String) jwt.getPayload("user_name"));

        return blogUser;
    }
}