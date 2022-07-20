package com.blog.gateway.service;

import java.util.List;

/**
 * @author: lxk
 * @date: 2022/7/20 22:51
 * @description:
 * @modified By:
 */

public interface BlacklistIpService {

    List<String> selectBlacklistIpList();
}

