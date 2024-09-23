package com.blog.common.netty.dto.register;

import lombok.Data;

/**
 * @Description netty注册消息类
 * @Author lxk
 * @CreateTime 2024-08-28
 */

@Data
public class NettyRegisterDto {

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备备注信息
     */
    private String memo;

    /**
     * 注册上班系统消息
     */
    private SysInfo sysInfo;

    @Data
    public static class SysInfo {

        /**
         * 服务器名称
         */
        private String computerName;

        /**
         * 服务器Ip
         */
        private String computerIp;

        /**
         * 项目路径
         */
        private String userDir;

        /**
         * 操作系统
         */
        private String osName;

        /**
         * 系统架构
         */
        private String osArch;
    }
}
