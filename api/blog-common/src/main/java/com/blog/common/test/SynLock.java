package com.blog.common.test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description:
 * @Author: 308501
 * @date 2023/12/23 15:18
 */

public class SynLock {
    public static Lock lock = new ReentrantLock();
}
