package com.blog.file.mqtt;

import com.blog.file.ftp.FtpsUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;


/**
 * @description: mqtt回调 https://blog.csdn.net/qq_42862247/article/details/125536672
 * @Author: lxk
 * @date 2024/1/2 16:15
 */
@Slf4j
public class MyMQTTCallback implements MqttCallbackExtended {

    private FtpsUtil ftpsUtil = SpringUtils.getBean(FtpsUtil.class);

    private final MyMQTTClient myMQTTClient;

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
        log.error("mqtt connectionLost 连接断开，5S之后尝试重连: {}", throwable.getMessage());
        long reconnectTimes = 0;
        while (true) {
            try {
                if (MyMQTTClient.getClient().isConnected()) {
                    //判断已经重新连接成功  需要重新订阅主题 可以在这个if里面订阅主题  或者 connectComplete（方法里面）
                    log.warn("mqtt reconnect success end  重新连接  重新订阅成功");
                    return;
                }
                reconnectTimes += 1;
                log.warn("mqtt reconnect times = {} try again...  mqtt重新连接时间 {}", reconnectTimes, reconnectTimes);
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
        log.info("接收消息主题 : {}，接收消息内容 : {}", topic, new String(mqttMessage.getPayload()));
        // 处理订阅的消息
        // todo 消息处理
//        try {
//            File file = new File("D:\\test.jpg");
//            byte[] byteArray = Files.readAllBytes(file.toPath());
//            ftpsUtil.uploadFtpFile("/opt/ftps/test", "ftpsUtil.jpg", byteArray);
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
        myMQTTClient.subscribe("nodes/java/user/heartbeat/1", 1);
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

