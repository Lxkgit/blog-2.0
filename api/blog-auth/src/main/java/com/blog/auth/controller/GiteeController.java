package com.blog.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.util.GiteeHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * @Author: lxk
 * @date 2023/2/7 19:51
 * @description:
 */

@RestController
@RequestMapping("/gitee")
public class GiteeController {


    @Value("${oauth.gitee.clientId}")
    public String clientId;

    @Value("${oauth.gitee.clientSecret}")
    public String clientSecret;

    @Value("${oauth.gitee.callbackUrl}")
    public String url;

    /**
     * 请求授权页面
     */
//    @GetMapping(value = "/auth")
//    public Result qqAuth(HttpSession session) {
//        // 用于第三方应用防止CSRF攻击
//        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//        session.setAttribute("state", uuid);
//
//        // Step1：获取Authorization Code
//        String url = "https://gitee.com/oauth/authorize?response_type=code" +
//                "&client_id=" + clientId +
//                "&redirect_uri=" + URLEncoder.encode(this.url) +
//                "&state=" + uuid +
//                "&scope=user_info";
//        //因为使用的是thymeleaf模板引擎，所以是无法解析一个网址的，只能重定向
//        return ResultFactory.buildSuccessResult("ok");
//    }

    /**
     * 授权回调
     */
    @GetMapping(value = "/callback")
    public Result qqCallback(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        // 得到Authorization Code
        String code = request.getParameter("code");
        // 我们放在地址中的状态码
        String state = request.getParameter("state");
        String uuid = (String) session.getAttribute("state");

        // 验证信息我们发送的状态码
        if (null != uuid) {
            // 状态码不正确，直接返回登录页面
            if (!uuid.equals(state)) {
                return ResultFactory.buildFailResult("状态码不正确");
            }
        }

        // Step2：通过Authorization Code获取Access Token
        String url = "https://gitee.com/oauth/token?grant_type=authorization_code" +
                "&client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&code=" + code +
                "&redirect_uri=" + this.url;
        JSONObject accessTokenJson = GiteeHttpClient.getAccessToken(url);

        // Step3: 获取用户信息
        url = "https://gitee.com/api/v5/user?access_token=" + accessTokenJson.get("access_token");
        JSONObject jsonObject = GiteeHttpClient.getUserInfo(url);
        /**
         * 获取到用户信息之后，就该写你自己的业务逻辑了
         */
        System.out.println(jsonObject);
        return ResultFactory.buildSuccessResult("ok");
    }
}
