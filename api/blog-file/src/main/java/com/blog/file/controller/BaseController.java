package com.blog.file.controller;

import com.blog.common.entity.user.BlogUser;
import com.blog.common.exception.ValidException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * @description: 基础接口类
 * @Author: 308501
 * @date 2024/1/15 14:51
 */

@Slf4j
public class BaseController {

    /**
     * 根据request获取token：除去"bearer "后的部分
     *
     * @param request
     * @return String
     */
    public String getToken(HttpServletRequest request) {
        String accessHead = request.getHeader("Authorization");
        List<String> headList = Arrays.asList(accessHead.split(" "));
        return headList.get(1);
    }

    /**
     * 从token中获取用户id，token格式如：1:3de10ae6-9c92-42fa-b174-07f3138ce6c1
     *
     * @param request
     * @return
     */
    public BlogUser getBlogUser(HttpServletRequest request) {
        String accessToken = getToken(request);
        BlogUser blogUser = new BlogUser();
        if (StringUtils.isBlank(accessToken)) {
            return null;
        }
        String[] arr = accessToken.split(":");
        try {
            blogUser.setId((int) Long.parseLong(arr[0]));
        } catch (NumberFormatException e) {
            log.error("get userId failed", e);
        }
        return blogUser;
    }
}
