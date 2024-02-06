package com.blog.pi.mqtt;

import com.alibaba.fastjson.JSONObject;
import com.blog.pi.enums.netty.NettyTopicEnum;
import com.blog.pi.mqtt.data.MQTTSensorData;
import com.blog.pi.netty.client.NettyClient;
import com.blog.pi.netty.dto.NettyPacket;
import com.blog.pi.netty.enums.NettyPacketType;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;

import javax.annotation.Resource;

@Slf4j
public class PushCallback implements MqttCallback {

    @Resource
    private final NettyClient nettyClient = SpringUtils.getBean(NettyClient.class);

    /**
     * mqtt断线重连
     *
     * @param throwable
     */
    @Override
    public void connectionLost(Throwable throwable) {
        long reconnectTimes = 0;
        while (true) {
            try {
                if (MqttPushClient.getClient().isConnected()) {
                    // 判断已经重新连接成功  需要重新订阅主题 可以在这个if里面订阅主题  或者 connectComplete（方法里面）
                    log.warn("MQTT 重新连接成功");
                    MqttPushClient.subscribe();
                    return;
                }
                reconnectTimes += 1;
                log.warn("MQTT 重连次数: {}", reconnectTimes);
                MqttPushClient.getClient().reconnect();
            } catch (MqttException e) {
                log.error("mqtt断连异常", e);
            }
            try {
                // 5秒执行异常重新连接
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * mqtt消息接收
     *
     * @param topic
     * @param message
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) {
        try {
            String data = new String(message.getPayload());
            log.info("MQTT Topic:【{}】 data:【{}】", topic, data);
            MQTTSensorData mqttSensorData = JSONObject.toJavaObject(JSONObject.parseObject(data), MQTTSensorData.class);
            NettyPacket<MQTTSensorData> nettyRequest = NettyPacket.buildRequest(mqttSensorData);
            nettyRequest.setNettyPacketType(NettyPacketType.REQUEST.getValue());
            nettyRequest.setTopic(NettyTopicEnum.BLOG_SENSOR_DATA.getTopic());
            nettyClient.sendMsg(JSONObject.toJSONString(nettyRequest));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * mqtt消息发送完成 回调消息
     *
     * @param token
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
//        try {
//            System.out.println(token.getMessage());
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }
    }
}
