package com.blog.gateway.service.impl;

import com.blog.common.entity.gateway.RequestLog;
import com.blog.gateway.dao.RequestLogDAO;
import com.blog.gateway.service.RequestLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: lxk
 * @date 2022/7/20 17:12
 * @description: 接口请求日志服务
 */

@Service
public class RequestLogServiceImpl implements RequestLogService {

    @Resource
    private RequestLogDAO requestLogDAO;

    @Override
    public int saveRequestLog(RequestLog requestLog) {
        return requestLogDAO.insert(requestLog);

    }

}
