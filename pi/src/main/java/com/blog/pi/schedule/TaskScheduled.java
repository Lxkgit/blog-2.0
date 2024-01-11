package com.blog.pi.schedule;


import com.blog.pi.netty.client.NettyClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * @description:
 * @Author: 308501
 * @date 2024/1/6 15:53
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TaskScheduled implements CommandLineRunner {

    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
    private final NettyClient nettyClient;

    /**
     * 模拟服务交互
     */
    @Override
    public void run(String... args) {
        // 如果任务里面执行的时间大于 period 的时间，下一次的任务会推迟执行。
        // 本次任务执行完后下次的任务还需要延迟period时间后再执行
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
//            try {
//                // 向Netty服务端发送消息
//                RequestTestVO param = RequestTestVO.builder().key("hello").value("world").date(new Date()).build();
//                NettyPacket<RequestTestVO> nettyRequest = NettyPacket.buildRequest(param);
//                boolean success = nettyClient.sendMsg(JSONObject.toJSONString(nettyRequest));
//                NettyPacket<Object> response = nettyRequest.futureResponse();
//                if (response != null) {
//                    ResponseTestVO responseTestVO = JSONObject.parseObject(JSONObject.toJSONString(response.getData()), new TypeReference<ResponseTestVO>() {
//                    }.getType());
//                    log.info("requestId:{} response:{}", nettyRequest.getRequestId(), JSONObject.toJSONString(responseTestVO));
//                }
//            } catch (Exception e) {
//                log.error("failure!! error:{}", e.getMessage());
//            }
        }, 2, 10, TimeUnit.SECONDS);
    }
}

