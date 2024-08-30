package com.blog.pi.netty.thread;

import com.blog.pi.netty.service.vo.SensorCommandVo;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @description: 命令下发线程
 * @Author: lxk
 * @date 2024/3/30 15:03
 */

public class CommandThreadService {

    public static Set<String> threadSet = new HashSet<>();

    public static Map<String, List<SensorCommandVo>> commandMap = new ConcurrentHashMap<>();

    /**
     * 命令分配线程池
     */
    public static ScheduledExecutorService commandSendPool = Executors.newScheduledThreadPool(1);

    /**
     * 命令下发线程池
     */
    public static ScheduledExecutorService commandPool = Executors.newScheduledThreadPool(4);
}
