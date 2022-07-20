package com.blog.gateway.filiter;

import com.blog.common.entity.gateway.RequestLog;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.util.IpUtil;
import com.blog.common.util.JwtUtil;
import com.blog.gateway.service.BlacklistIpService;
import com.blog.gateway.service.RequestLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

/**
 * @Author: lxk
 * @date 2022/7/19 16:25
 * @description:
 */

@Component
@Slf4j
public class GatewayFilter implements GlobalFilter, Ordered {

    @Autowired
    private RequestLogService requestLogService;

    @Autowired
    private BlacklistIpService blacklistIpService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        RequestLog requestLog = new RequestLog();
        requestLog.setCreateTime(new Date());
        requestLog.setUrlPath(exchange.getRequest().getPath().toString());
        String ip = IpUtil.getIpAddress(exchange.getRequest());
        requestLog.setRequestIp(ip);
        List<String> authorization = exchange.getRequest().getHeaders().get("Authorization");
        if (authorization != null && authorization.size()>0) {
            BlogUser blogUser = JwtUtil.getUserInfo(authorization.get(0));
            requestLog.setUserId(blogUser.getId());
        }
        requestLogService.saveRequestLog(requestLog);

        List<String> ipList = blacklistIpService.selectBlacklistIpList();
        if (ipList.contains(ip)) {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
