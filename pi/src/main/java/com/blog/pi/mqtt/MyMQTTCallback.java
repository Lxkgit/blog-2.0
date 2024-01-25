package com.blog.pi.mqtt;

import com.alibaba.fastjson.JSON;
import com.blog.pi.dao.SensorDataDAO;
import com.blog.pi.entity.SensorData;
import com.blog.pi.enums.mqtt.MQTTTopicEnum;
import com.blog.pi.enums.netty.NettyTopicEnum;
import com.blog.pi.ftp.FtpsUtil;
import com.blog.pi.mqtt.data.MQTTSensorData;
import com.blog.pi.netty.client.NettyClient;
import com.blog.pi.netty.dto.NettyPacket;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;

import java.util.Date;

/**
 * @description: mqtt回调 https://blog.csdn.net/qq_42862247/article/details/125536672
 * @Author: lxk
 * @date 2024/1/2 16:15
 */
@Slf4j
public class MyMQTTCallback implements MqttCallbackExtended {

    private final FtpsUtil ftpsUtil = SpringUtils.getBean(FtpsUtil.class);

    private final NettyClient nettyClient = SpringUtils.getBean(NettyClient.class);

    private final MyMQTTClient myMQTTClient;

    private final SensorDataDAO sensorDataDAO = SpringUtils.getBean(SensorDataDAO.class);

    public MyMQTTCallback(MyMQTTClient myMQTTClient) {
        this.myMQTTClient = myMQTTClient;
    }


    /**
     * 丢失连接，可在这里做重连
     * 只会调用一次
     *
     * @param throwable
     */
    @Override
    public void connectionLost(Throwable throwable) {
        long reconnectTimes = 0;
        while (true) {
            try {
                if (MyMQTTClient.getClient().isConnected()) {
                    //判断已经重新连接成功  需要重新订阅主题 可以在这个if里面订阅主题  或者 connectComplete（方法里面）
                    log.warn("MQTT 重新连接成功");
                    return;
                }
                reconnectTimes += 1;
                log.warn("MQTT 重连次数: {}", reconnectTimes);
                MyMQTTClient.getClient().reconnect();

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
     * @param topic
     * @param mqttMessage
     * @throws Exception subscribe后得到的消息会执行到这里面
     */
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        String msg = new String(mqttMessage.getPayload());

        log.info("MQTT 接收消息主题 : {}，接收消息内容 : {}", topic, msg);
        // 处理订阅的消息
        if (topic.equals(MQTTTopicEnum.CHIP_HEART.getTopic())) {

        } else if (topic.equals(MQTTTopicEnum.SENSOR_DATA.getTopic())) {
            SensorData sensorData = JSON.parseObject(msg, MQTTSensorData.class);
            sensorData.setCreateTime(new Date());
            sensorDataDAO.insert(sensorData);

            NettyPacket<SensorData> nettyRequest = NettyPacket.buildRequest(sensorData);
            nettyRequest.setTopic(NettyTopicEnum.BLOG_SENSOR_DATA.getTopic());
            nettyRequest.setUserId(0);
            nettyClient.sendMsg(JSON.toJSONString(nettyRequest));
        }
//        try {
//            // 上传文件
//            ftpsUtil.uploadFtpFile("D:\\", "test.jpg", "/opt/ftps/test", "ftpsUtil.jpg");
//
//            // 下载文件
////            ftpsUtil.downloadFtpFile("/opt/ftps/test", "ftpsUtil.jpg");
//
//            // 删除文件
////            ftpsUtil.removeFile("/opt/ftps/test", "ftpsUtil.jpg");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }


    /**
     * 连接成功后的回调 可以在这个方法执行 订阅主题  生成Bean的 MqttConfiguration方法中订阅主题 出现bug
     * 重新连接后  主题也需要再次订阅  将重新订阅主题放在连接成功后的回调 比较合理
     *
     * @param reconnect
     * @param serverURI
     */
    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        log.info("MQTT 连接成功，连接方式：{}", reconnect ? "重连" : "直连");
        // 订阅主题
        myMQTTClient.subscribe(MQTTTopicEnum.SENSOR_DATA.getTopic(), MQTTTopicEnum.SENSOR_DATA.getQos());
    }

    /**
     * publish后，配送完成后回调的方法
     *
     * @param iMqttDeliveryToken
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        log.info("==========deliveryComplete={}==========", iMqttDeliveryToken.isComplete());
    }
}

