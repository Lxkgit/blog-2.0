package com.blog.pi.utils;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

import java.util.UUID;

/**
 * @Description uuid生成类
 * @Author lxk
 * @CreateTime 2024-08-28
 */

public class MyUUID {


    /**
     * 可以返回不包含 ‘-’ uuid
     * @param flag false： 返回无 - 的uuid字符串
     * @return uuid字符串
     */
    public static String getRandomString(boolean flag) {
        if (!flag) {
            return UUID.randomUUID().toString().replace("-", "");
        }
        return UUID.randomUUID().toString();
    }

    /**
     * 重载方法，返回原始uuid
     * @return uuid字符串
     */
    public static String getRandomString() {
        return UUID.randomUUID().toString();
    }

    public static void main(String[] args) {
//        System.out.println(MyUUID.getRandomString(false));
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor processor = hal.getProcessor();
        System.out.println(processor);
    }

}
