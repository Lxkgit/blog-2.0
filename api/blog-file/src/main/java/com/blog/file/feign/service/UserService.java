package com.blog.file.feign.service;

import com.alibaba.fastjson.JSONObject;
import com.blog.common.constant.ErrorMessage;
import com.blog.common.entity.file.UserInfo;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.exception.ValidException;
import com.blog.file.feign.UserClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description 用户信息服务
 * @Author lxk
 * @CreateTime 2024-09-05
 */

@Service
public class UserService {

    @Resource
    private UserClient userClient;

    public BlogUser getBlogUserByUsername(String username) throws ValidException {
        BlogUser blogUser = JSONObject.parseObject(JSONObject.toJSONString(userClient.getUserByUsername(username).getResult()), BlogUser.class);

        if (blogUser != null) {
            return blogUser;
        }

        throw new ValidException("20001", "用户查找失败");
    }


    public BlogUser getBlogUserById(Integer id) throws ValidException {
        BlogUser blogUser = JSONObject.parseObject(JSONObject.toJSONString(userClient.getUserById(id).getResult()), BlogUser.class);

        if (blogUser != null) {
            return blogUser;
        }

        throw new ValidException("20001", "用户查找失败");
    }

}
