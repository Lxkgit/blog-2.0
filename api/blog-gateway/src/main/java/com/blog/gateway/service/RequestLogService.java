package com.blog.gateway.service;

import com.blog.common.entity.gateway.RequestLog;

public interface RequestLogService {

    int saveRequestLog(RequestLog requestLog);
}
