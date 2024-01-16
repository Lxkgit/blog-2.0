package com.blog.common.util;

import java.util.Random;

/**
 * @description: 字符串校验工具
 * @Author: 308501
 * @date 2024/1/15 16:56
 */

public class StringUtils {

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

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
