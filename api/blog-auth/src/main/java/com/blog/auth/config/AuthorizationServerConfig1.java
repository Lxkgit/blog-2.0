//package com.blog.auth.config;
//
//import com.blog.auth.service.impl.RedisAuthorizationCodeServices;
//import com.blog.auth.service.impl.RedisClientDetailsService;
//import com.blog.common.entity.auth.LoginUser;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.oauth2.provider.token.*;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @Author: lxk
// * @date 2022/6/10 9:18
// * @description: 授权服务器配置
// */
//
//@Configuration
//@EnableAuthorizationServer
//public class AuthorizationServerConfig1 extends AuthorizationServerConfigurerAdapter {
//
//    // 认证管理器
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    public UserDetailsService userDetailsService;
//
//    @Autowired
//    private RedisClientDetailsService redisClientDetailsService;
//
//    @Autowired
//    private RedisAuthorizationCodeServices redisAuthorizationCodeServices;
//
//    @Value("${access_token.add-userInfo}")
//    private boolean addUserInfo;
//
//    /**
//     * jwt签名key，可随意指定<br>
//     * 如配置文件里不设置的话，冒号后面的是默认值
//     */
//    @Value("${access_token.jwt-signing-key}")
//    private String signingKey;
//
//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        security.allowFormAuthenticationForClients(); // 允许表单形式的认证
//    }
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.withClientDetails(redisClientDetailsService);
//        redisClientDetailsService.loadAllClientToCache();
//    }
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
//        enhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
////        enhancerChain.setTokenEnhancers(Collections.singletonList(tokenEnhancer()));
//        // 配置认证管理器
//        endpoints.authenticationManager(this.authenticationManager);
//        endpoints.tokenStore(tokenStore());
//        // 授权码模式下，code存储
//        endpoints.authorizationCodeServices(redisAuthorizationCodeServices);
//
////        endpoints.accessTokenConverter(accessTokenConverter());
//        endpoints.tokenEnhancer(enhancerChain);
//        endpoints.userDetailsService(userDetailsService);
//    }
//
//    @Bean
//    public TokenEnhancer tokenEnhancer(){
//        return new TokenEnhancerConfig();
//    }
//
//    @Bean
//    public TokenStore tokenStore(){
//        return new JwtTokenStore(accessTokenConverter());
//    }
//
//    /**
//     * Jwt资源令牌转换器
//     *
//     * @return accessTokenConverter
//     */
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter() {
//        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter() {
//            @Override
//            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
//                OAuth2AccessToken oAuth2AccessToken = super.enhance(accessToken, authentication);
//                addLoginUserInfo(oAuth2AccessToken, authentication); // 2018.07.13 将当前用户信息追加到登陆后返回数据里
//                return oAuth2AccessToken;
//            }
//        };
//        DefaultAccessTokenConverter defaultAccessTokenConverter = (DefaultAccessTokenConverter) jwtAccessTokenConverter
//                .getAccessTokenConverter();
//        DefaultUserAuthenticationConverter userAuthenticationConverter = new DefaultUserAuthenticationConverter();
//        userAuthenticationConverter.setUserDetailsService(userDetailsService);
//
//        defaultAccessTokenConverter.setUserTokenConverter(userAuthenticationConverter);
//        // 2018.06.29 这里务必设置一个，否则多台认证中心的话，一旦使用jwt方式，access_token将解析错误
//        jwtAccessTokenConverter.setSigningKey(signingKey);
//
//        return jwtAccessTokenConverter;
//    }
//
//    /**
//     * 将当前用户信息追加到登陆后返回的json数据里<br>
//     * 通过参数access_token.add-userinfo控制<br>
//     * 2018.07.13
//     *
//     * @param accessToken
//     * @param authentication
//     */
//    private void addLoginUserInfo(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
//        if (!addUserInfo) {
//            return;
//        }
//
//        if (accessToken instanceof DefaultOAuth2AccessToken) {
//            DefaultOAuth2AccessToken defaultOAuth2AccessToken = (DefaultOAuth2AccessToken) accessToken;
//
//            Authentication userAuthentication = authentication.getUserAuthentication();
//            Object principal = userAuthentication.getPrincipal();
//            if (principal instanceof LoginUser) {
//                LoginUser loginUser = (LoginUser) principal;
//
//                Map<String, Object> map = new HashMap<>(defaultOAuth2AccessToken.getAdditionalInformation()); // 旧的附加参数
//                map.put("loginUser", loginUser); // 追加当前登陆用户
//
//                defaultOAuth2AccessToken.setAdditionalInformation(map);
//            }
//        }
//    }
//}
