package com.blog.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @description: shell 脚本执行工具
 * @Author: lxk
 * @date 2024/3/11 14:16
 */

@Slf4j
public class ShellUtil {

    public static boolean shell(String command) {

        try {
            log.info("执行shell脚本: " + command);
            // 执行shell脚本
            Process process = Runtime.getRuntime().exec(command);

            // 读取脚本的输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                log.info(line);
            }

            // 等待脚本执行完成
            int exitVal = process.waitFor();
            if (exitVal == 1) {
                log.info("shell脚本(" + command + ")执行结果执行成功");
            }
            log.error("shell脚本(" + command + ")执行结果执行失败");
        } catch (Exception e) {
            log.error("shell脚本(" + command + ")执行结果执行异常: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
