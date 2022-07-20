package com.blog.gateway.service.impl;

import com.blog.gateway.dao.BlacklistIpDAO;
import com.blog.gateway.service.BlacklistIpService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lxk
 * @date 2022/7/20 17:29
 * @description: 黑名单接口服务
 */

@Service
public class BlacklistIpServiceImpl implements BlacklistIpService {

    @Resource
    private BlacklistIpDAO blacklistIpDAO;


    @Override
    public List<String> selectBlacklistIpList() {
        return blacklistIpDAO.selectIpList();
    }
}
