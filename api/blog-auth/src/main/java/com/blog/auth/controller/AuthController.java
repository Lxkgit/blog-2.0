package com.blog.auth.controller;

import com.blog.common.entity.user.BlogUser;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.util.CollectionUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @Author: lxk
 * @date 2022/8/19 9:26
 * @description: 封装oauth2返回数据
 */

@Slf4j
@RestController
@RequestMapping("/oauth")
public class AuthController implements InitializingBean {

    @Autowired
    private TokenEndpoint tokenEndpoint;

    @Autowired
    private CheckTokenEndpoint checkTokenEndpoint;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @PostMapping(value = "/token")
    public Result postAccessToken(Principal principal, @RequestParam
            Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken accessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        return ResultFactory.buildSuccessResult(accessToken);
    }

    @GetMapping(value = "/logout")
    public Result logout(@RequestHeader HttpHeaders headers) {
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        //生成模糊匹配的对应的set集合，字符串后面追加的*号做模糊匹配使用
        Set<String> keys = redisTemplate.keys("auth:" + blogUser.getId() +":*");
        if (!CollectionUtils.isEmpty(keys)) {
            redisTemplate.delete(keys);
        }
        return ResultFactory.buildSuccessResult("退出成功");
    }

    /**
     * 重写/oauth/check_token这个默认接口，用于校验令牌，返回的数据格式统一
     */
    @PostMapping(value = "/check_token")
    public Result checkToken(@RequestParam("token") String value)  {
        Map<String, ?> map = checkTokenEndpoint.checkToken(value);
        return ResultFactory.buildSuccessResult(map);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
