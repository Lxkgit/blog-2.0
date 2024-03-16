package com.blog.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @description: shell 脚本执行工具
 * @Author: 308501
 * @date 2024/3/11 14:16
 */

@Slf4j
public class ShellUtil {

    public static boolean shell(String command) {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process pro = runtime.exec(command);
            // pro.waitFor()方法会阻塞到执行结束然后返回执行结果，0为执行成功，1为执行失败
            int status = pro.waitFor();
            if (status == 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("shell脚本执行失败, command: {}", command);
        return false;
    }
}
