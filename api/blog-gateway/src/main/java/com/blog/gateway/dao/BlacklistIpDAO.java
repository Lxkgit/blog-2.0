package com.blog.gateway.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.gateway.BlacklistIp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: lxk
 * @date: 2022/7/20 22:50
 * @description:
 * @modified By:
 */
@Mapper
public interface BlacklistIpDAO extends BaseMapper<BlacklistIp> {

    List<String> selectIpList();
}
