package com.blog.gateway.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.gateway.RequestLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RequestLogDAO extends BaseMapper<RequestLog> {
}
