package com.blog.common.test;

/**
 * @description:
 * @Author: 308501
 * @date 2023/12/23 15:18
 */

public class Thread1 extends Thread {

    public void run() {
        try {
            SynLock.lock.lock();
            for (int i=0; i<10; i++) {
                Thread.sleep(500);
                System.out.println("Thread1: " + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SynLock.lock.unlock();
        }
    }
}
