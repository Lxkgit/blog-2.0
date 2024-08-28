package com.blog.pi.netty.dto.heart;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.lang.management.ManagementFactory;
import java.util.Date;

/**
 * @description: 心跳消息类
 * @Author: lxk
 * @date 2024/3/14 15:12
 */

@Data
public class NettyHeartBeatDto {

    /**
     * 心跳时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date heartBeat;

    /**
     * 消息来源
     */
    private String from;

    /**
     * 心跳类型
     * 1： 树莓派等服务器设备
     */
    private Integer type;

    @Data
    public static class CpuInfo {

        /**
         * 核心数
         */
        private int cpuNum;

        /**
         * CPU总的使用率
         */
        private double total;

        /**
         * CPU系统使用率+%
         */
        private double sys;

        /**
         * CPU用户使用率+%
         */
        private double user;

        /**
         * CPU使用率+%
         */
        private double used;

        /**
         * CPU当前等待率+%
         */
        private double wait;

        /**
         * CPU当前空闲率+%
         */
        private double free;

        /**
         * 型号
         */
        private String cpuModel;

    }

    @Data
    public static class GPUInfo {

        int gpuId;

        /**
         * 名称
         */
        String name;

        /**
         * 温度
         */
        int temperature;

        /**
         * 使用占比+%
         */
        int utilization;

        /**
         * 总内存M
         */
        int memoryTotal;

        /**
         * 空闲M
         */
        int memoryFree;
    }

    @Data
    public static class JvmInfo {
        /**
         * 当前JVM占用的内存总数(M)
         */
        private double total;

        /**
         * JVM最大可用内存总数(M)
         */
        private double max;

        /**
         * JVM最大已用内存总数
         */
        private double used;

        /**
         * JVM最大已用内存占比+%
         */
        private double usage;

        /**
         * JVM空闲内存(M)
         */
        private double free;

        /**
         * JDK版本
         */
        private String version;

        /**
         * JDK路径
         */
        private String home;

        /**
         * JDK启动时间
         */
        private String startTime;

        /**
         * JDK运行时间
         */
        private String runTime;

        /**
         * JDK名称
         */
        private String name;

        public double cal(long num) {
            return NumberUtil.div(num, (1024 * 1024), 2);
        }

        public double usage() {
            return NumberUtil.mul(NumberUtil.div(total - free, total, 4), 100);
        }
        /**
         * 获取JDK名称
         */
        public String name() {
            return ManagementFactory.getRuntimeMXBean().getVmName();
        }

        /**
         * JDK启动时间
         */
        public String startTime() {
            long time = ManagementFactory.getRuntimeMXBean().getStartTime();
            Date date = new Date(time);
            return DateUtil.formatDateTime(date);
        }

        /**
         * JDK运行时间
         */
        public String runTime() {
            long time = ManagementFactory.getRuntimeMXBean().getStartTime();
            Date date = new Date(time);

            //运行多少分钟
            long runMS = DateUtil.between(date, new Date(), DateUnit.MS);

            long nd = 1000 * 24 * 60 * 60;
            long nh = 1000 * 60 * 60;
            long nm = 1000 * 60;

            long day = runMS / nd;
            long hour = runMS % nd / nh;
            long min = runMS % nd % nh / nm;
            return day + "天" + hour + "小时" + min + "分钟";
        }
    }

    @Data
    public static class MemInfo {

        /**
         * 内存总量M
         */
        private double total;

        /**
         * 已用内存M
         */
        private double used;

        /**
         * 剩余内存M
         */
        private double free;

        /**
         * 使用率  +%
         */
        private double usage;

        public double cal(double total) {
            return NumberUtil.div(total, (1024 * 1024), 2);
        }

        public double usage() {
            return NumberUtil.mul(NumberUtil.div(used, total, 4),100);
        }
    }

    @Data
    public static class NetInfo {

        /**
         * 上行流量
         */
        private String up;

        /**
         * 下行流量
         */
        private String down;
    }

    @Data
    public static class SysFile {
        /**
         * 盘符路径
         */
        private String dirName;

        /**
         * 盘符类型
         */
        private String sysTypeName;

        /**
         * 文件类型
         */
        private String typeName;

        /**
         * 总大小,会返回单位
         */
        private String total;

        /**
         * 剩余大小
         */
        private String free;

        /**
         * 已经使用量
         */
        private String used;

        /**
         * 资源的使用率+%
         */
        private double usage;
    }

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
