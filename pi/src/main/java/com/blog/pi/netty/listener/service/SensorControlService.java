package com.blog.pi.netty.listener.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blog.pi.netty.client.NettyClient;
import com.blog.pi.netty.dto.NettyPacket;
import com.blog.pi.netty.dto.NettyResponse;
import com.blog.pi.netty.listener.service.enums.SensorTypeEnum;
import com.blog.pi.netty.listener.service.thread.CommandSendThread;
import com.blog.pi.netty.listener.service.thread.CommandThreadService;
import com.blog.pi.netty.listener.service.vo.SensorCommandVo;
import com.blog.pi.netty.listener.service.vo.SteeringEngineVo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 传感器控制服务类
 * @Author: 308501
 * @date 2024/3/29 19:56
 */

@Component
public class SensorControlService {

    @Resource
    private NettyClient nettyClient;

    public void sendCommand(String command, String requestId) {
        JSONObject jsonObject = JSONObject.parseObject(command);
        List<SensorCommandVo> sensorCommandVoList = new ArrayList<>();
        if (SensorTypeEnum.DUO_JI.getSensorCode().equals(jsonObject.get("sensorType"))) {
            JSONArray jsonArray = (JSONArray) JSONArray.parse(jsonObject.get("commandList").toString());
            for (int i=0; i<jsonArray.size(); i++) {
                SteeringEngineVo steeringEngineVo = new SteeringEngineVo();
                steeringEngineVo.setSensorType(jsonObject.getString("sensorType"));
                steeringEngineVo.setChipType(jsonObject.getString("chipType"));
                JSONObject data = JSONObject.parseObject(jsonArray.getString(i));
                steeringEngineVo.setControlIntervalTime(data.getInteger("controlIntervalTime"));
                steeringEngineVo.setData(data.getInteger("data"));
                sensorCommandVoList.add(steeringEngineVo);
            }
        }

        if (sensorCommandVoList.size() > 0) {
            CommandSendThread commandThread = new CommandSendThread(sensorCommandVoList);
            CommandThreadService.commandSendPool.execute(commandThread);
        }

        // 响应服务端处理结果
        NettyResponse nettyResponse = new NettyResponse(true);
        NettyPacket<NettyResponse> nettyPacket = NettyPacket.buildResponse(requestId, nettyResponse);
        nettyClient.sendMsg(JSONObject.toJSONString(nettyPacket));

    }
}
