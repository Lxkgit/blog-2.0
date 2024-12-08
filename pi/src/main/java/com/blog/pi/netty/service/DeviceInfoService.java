package com.blog.pi.netty.service;

import cn.hutool.core.io.unit.DataUnit;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.system.oshi.CpuInfo;
import cn.hutool.system.oshi.OshiUtil;
import com.blog.pi.netty.dto.register.NettyRegisterDto;
import com.blog.pi.netty.dto.heart.NettyHeartBeatDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @Description 设备信息服务
 * @Author lxk
 * @CreateTime 2024-08-28
 */

@Slf4j
@Service
public class DeviceInfoService {

    /**
     * 注册获取设备信息
     * @param registerDto
     */
    public void setRegisterMsg(NettyRegisterDto registerDto) {

        registerDto.setSysInfo(sysInfo());
    }

    /**
     * 心跳上报获取设备信息
     * @param heartBeatDto
     */
    public void setHeartBeatMsg(NettyHeartBeatDto heartBeatDto) {
        try {
//            heartBeatDto.setCpuInfo(cpuInfo());
            heartBeatDto.setMemInfo(memInfo());
//            heartBeatDto.setNetInfo(net());
//            heartBeatDto.setSysFile(sysFiles());
        } catch (Exception e) {
            log.error("获取设备信息失败：" + e.getMessage());
            e.printStackTrace();
        }

    }

    public NettyHeartBeatDto.NetInfo net() {
        HardwareAbstractionLayer hardware = OshiUtil.getHardware();
        List<NetworkIF> networkIFs = hardware.getNetworkIFs();
        long up = 0;
        long down = 0;
        long time = 0;
        for (NetworkIF net : networkIFs) {
            long bytesRecv = net.getBytesRecv();
            long bytesSent = net.getBytesSent();
            long timeStamp = net.getTimeStamp();
            up += bytesSent;
            down += bytesRecv;
            time += timeStamp;
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            log.error("【net】 获取网络信息失败", e);
            Thread.currentThread().interrupt();
        }

        networkIFs = hardware.getNetworkIFs();
        long upload = 0L;
        long download = 0L;
        long timeT = 0L;
        for (NetworkIF net : networkIFs) {
            long bytesSent = net.getBytesSent();
            long bytesRecv = net.getBytesRecv();
            long timeStamp = net.getTimeStamp();
            timeT += timeStamp;
            upload += bytesSent;
            download += bytesRecv;
        }
        NettyHeartBeatDto.NetInfo netInfo = new NettyHeartBeatDto.NetInfo();
        if (timeT != time) {
            String downloadStr = formatData((download - down) / (timeT - time) * 1000) + "/s";
            String uploadStr = formatData((upload - up) / (timeT - time) * 1000) + "/s";
            netInfo.setDown(downloadStr);
            netInfo.setUp(uploadStr);
        }
        return netInfo;
    }

    /**
     * 设置CPU信息
     */
    public NettyHeartBeatDto.CpuInfo cpuInfo() {
        CpuInfo cpuInfo = OshiUtil.getCpuInfo();
        NettyHeartBeatDto.CpuInfo cpu = new NettyHeartBeatDto.CpuInfo();
        cpu.setCpuNum(cpuInfo.getCpuNum());
        cpu.setSys(cpuInfo.getSys());
        cpu.setTotal(cpuInfo.getToTal());
        cpu.setWait(cpuInfo.getWait());
        cpu.setFree(cpuInfo.getFree());
        cpu.setUser(cpuInfo.getUser());
        cpu.setUsed(cpuInfo.getUsed());
//        cpu.setCpuModel(cpuInfo.getCpuModel());
        return cpu;
    }

    /**
     * 设置内存信息
     */
    public NettyHeartBeatDto.MemInfo memInfo() {
        GlobalMemory memory = OshiUtil.getMemory();
        NettyHeartBeatDto.MemInfo memInfo = new NettyHeartBeatDto.MemInfo();
        long total = memory.getTotal();
        long available = memory.getAvailable();
        memInfo.setTotal(memInfo.cal(total));
        memInfo.setUsed(memInfo.cal(total - available));
        memInfo.setFree(memInfo.cal(available));
        memInfo.setUsage(memInfo.usage());
        return memInfo;
    }

    /**
     * 设置服务器信息
     */
    public NettyRegisterDto.SysInfo sysInfo() {
        NettyRegisterDto.SysInfo sys = new NettyRegisterDto.SysInfo();
        Properties props = System.getProperties();
        sys.setComputerName(NetUtil.getLocalHostName());
        sys.setComputerIp(NetUtil.getLocalhostStr());
        sys.setOsName(props.getProperty("os.name"));
        sys.setOsArch(props.getProperty("os.arch"));
        sys.setUserDir(props.getProperty("user.dir"));
        return sys;
    }

    /**
     * 设置磁盘信息
     */
    public List<NettyHeartBeatDto.SysFile> sysFiles() {
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();
        FileSystem fileSystem = os.getFileSystem();
        List<OSFileStore> fsArray = fileSystem.getFileStores();
        List<NettyHeartBeatDto.SysFile> sysFiles = new LinkedList<>();
        for (OSFileStore fs : fsArray) {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;
            NettyHeartBeatDto.SysFile sysFile = new NettyHeartBeatDto.SysFile();
            sysFile.setDirName(fs.getMount());
            sysFile.setSysTypeName(fs.getType());
            sysFile.setTypeName(fs.getName());
            sysFile.setTotal(convertFileSize(total));
            sysFile.setFree(convertFileSize(free));
            sysFile.setUsed(convertFileSize(used));
            sysFile.setUsage(NumberUtil.mul(NumberUtil.div(used, total, 4), 100));
            sysFiles.add(sysFile);
        }
        return sysFiles;
    }

    /**
     * 字节转换
     *
     * @param size 字节大小
     * @return 转换后值
     */
    private String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format("%d B", size);
        }
    }

    /**
     * 格式化输出大小 B/KB/MB...
     *
     * @param size size
     * @return S
     */
    private String formatData(long size) {
        if (size <= 0L) {
            return "0B";
        } else {
            int digitGroups = Math.min(DataUnit.UNIT_NAMES.length - 1, (int) (Math.log10(size) / Math.log10(1024.0D)));
            return (new DecimalFormat("#,##0.##")).format(size / Math.pow(1024.0D, digitGroups)) + " " + DataUnit.UNIT_NAMES[digitGroups];
        }
    }
}
