package com.blog.common.util;

import java.util.List;

/**
 * @author: lxk
 * @time: 2021/10/12 20:43
 * @description :分页工具
 */

public class MyPageUtils {
    public static <T> MyPage<T> pageUtil(List<T> list, int page, int size, int total) throws Exception {
        return new MyPage<>(list, size, page, total);
    }
}
