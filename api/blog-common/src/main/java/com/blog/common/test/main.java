package com.blog.common.test;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.Resource;

/**
 * @description:
 * @Author: 308501
 * @date 2023/12/23 15:19
 */

public class main {

    public static void main(String[] args) {

        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();
        thread1.start();
        thread2.start();

    }
}
