package com.blog.auth.service.impl;


import com.blog.auth.feign.UserClient;
import com.blog.common.constant.CredentialType;
import com.blog.common.entity.auth.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author: lxk
 * @date 2022/6/10 10:12
 * @description:
 */
@Slf4j
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 为了支持多类型登录，这里username后面拼装上登录类型,如username|type
        String[] params = username.split("\\|");
        username = params[0];// 真正的用户名

        LoginUser loginUser = userClient.findByUsername(username);
        if (loginUser == null) {
            throw new AuthenticationCredentialsNotFoundException("用户不存在");
        } else if (!loginUser.isEnabled()) {
            throw new DisabledException("用户已作废");
        }

        if (params.length > 1) {
            // 登录类型
            CredentialType credentialType = CredentialType.valueOf(params[1]);
            if (CredentialType.PHONE == credentialType) {// 短信登录
                handlerPhoneSmsLogin(loginUser, params);
            } else if (CredentialType.WECHAT_OPENID == credentialType) {// 微信登陆
                handlerWechatLogin(loginUser, params);
            }
        }

        return loginUser;
    }

    private void handlerWechatLogin(LoginUser loginUser, String[] params) {
//        if (params.length < 3) {
//            throw new IllegalArgumentException("非法请求");
//        }
//
//        String openid = params[0];
//        String tempCode = params[2];
//
//        userClient.wechatLoginCheck(tempCode, openid);
//
//        // 其实这里是将密码重置，网关层的微信登录接口，密码也用同样规则即可
//        loginUser.setPassword(passwordEncoder.encode(tempCode));
//        log.info("微信登陆，{},{}", loginUser, openid);
    }

    /**
     * 手机号+短信验证码登陆，处理逻辑
     *
     * @param loginUser
     * @param params
     */
    private void handlerPhoneSmsLogin(LoginUser loginUser, String[] params) {
//        if (params.length < 5) {
//            throw new IllegalArgumentException("非法请求");
//        }
//
//        String phone = params[0];
//        String key = params[2];
//        String code = params[3];
//        String md5 = params[4];
//        if (!DigestUtils.md5Hex(key + code).equals(md5)) {
//            throw new IllegalArgumentException("非法请求");
//        }
//
//        String value = smsClient.matcheCodeAndGetPhone(key, code, false, 30);
//        if (!StringUtils.equals(phone, value)) {
//            throw new IllegalArgumentException("验证码错误");
//        }
//
//        // 其实这里是将密码重置，网关层的短信登录接口，密码也用同样规则即可
//        loginUser.setPassword(passwordEncoder.encode(phone));
//        log.info("手机号+短信验证码登陆，{},{}", phone, code);
    }
}
