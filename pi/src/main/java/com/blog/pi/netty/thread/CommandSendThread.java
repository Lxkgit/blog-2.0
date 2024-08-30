package com.blog.pi.netty.thread;

import com.blog.pi.netty.service.vo.SensorCommandVo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @description:
 * @Author: lxk
 * @date 2024/3/30 15:08
 */

@Slf4j
public class CommandSendThread extends Thread {

    private List<SensorCommandVo> sensorCommandVoList;

    public CommandSendThread(List<SensorCommandVo> sensorCommandVoList) {
        this.sensorCommandVoList = sensorCommandVoList;
    }

    @Override
    public void run() {
        try {
            String sensorType = sensorCommandVoList.get(0).getChipType() + sensorCommandVoList.get(0).getSensorType();

            for (SensorCommandVo sensorCommandVo : sensorCommandVoList) {

                if (CommandThreadService.commandMap.containsKey(sensorType)) {
                    CommandThreadService.commandMap.get(sensorType).add(sensorCommandVo);
                } else {
                    List<SensorCommandVo> list = new CopyOnWriteArrayList<>();
                    list.add(sensorCommandVo);
                    CommandThreadService.commandMap.put(sensorType, list);
                }

            }

            if (!CommandThreadService.threadSet.contains(sensorType)) {
                CommandThread commandThread = new CommandThread(sensorType);
                CommandThreadService.commandPool.execute(commandThread);
            }

            sensorCommandVoList = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
