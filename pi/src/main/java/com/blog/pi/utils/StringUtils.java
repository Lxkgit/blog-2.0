package com.blog.pi.utils;

import java.util.Random;

/**
 * @description: 字符串工具
 * @Author: 308501
 * @date 2024/1/11 15:54
 */

public class StringUtils {

    public static String getRandomString(int length) {
        String str = "0123456789abcdef";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
