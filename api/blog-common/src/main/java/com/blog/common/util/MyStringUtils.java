package com.blog.common.util;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.apache.poi.ss.formula.functions.T;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description: 字符串校验工具
 * @Author: 308501
 * @date 2024/1/15 16:56
 */

public class MyStringUtils {

    /**
     * 校验字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 获取指定长度的随机字符串
     *
     * @param length
     * @return
     */
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

    /**
     * 返回字符串字符集合
     *
     * @param str
     * @return
     */
    public static Set<Character> stringToSet(String str) {
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < str.length(); i++) {
            set.add(str.charAt(i));
        }
        return set;
    }

    /**
     * 截取字符串，并返回截取后的字符串集合
     *
     * @param str 需要截取的字符串
     * @param regex 截取字符
     * @return
     */
    public static Set<String> splitString(String str, String regex) {
        return Arrays.stream(str.split(regex)).collect(Collectors.toSet());
    }
}
