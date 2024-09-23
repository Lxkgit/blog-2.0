package com.blog.common.entity.file.vo;

import com.blog.common.entity.file.DeviceHeartbeat;
import com.blog.common.netty.dto.heart.NettyHeartBeatDto;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @Author lxk
 * @CreateTime 2024-09-23
 */

@Getter
@Setter
public class DeviceHeartbeatVo extends DeviceHeartbeat {

    private NettyHeartBeatDto nettyHeartBeatDto;
}
