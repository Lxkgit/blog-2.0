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
        String userId = (authorization.split(" ")[1]).split(":")[0];
        blogUser.setId(Integer.parseInt(userId));
        return blogUser;
    }
}