package com.blog.common.util;


import com.blog.common.entity.file.LoginConfig;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.annotation.Bean;

/**
 * @description: Mqtt连接发送工具
 * @Author: 308501
 * @date 2023/8/8 14:18
 */

@Slf4j
public class MqttPushClient {

    private static final byte[] WILL_DATA;

    static {
        WILL_DATA = "offline".getBytes();
    }

    private static volatile MqttPushClient mqttPushClient = null;

    @Bean
    public static MqttPushClient getInstance(LoginConfig config) throws MqttException {

        if (null == mqttPushClient) {
            synchronized (MqttPushClient.class) {
                if (null == mqttPushClient) {
                    mqttPushClient = new MqttPushClient(config);
                }
            }

        }
        return mqttPushClient;
    }


    public MqttPushClient(LoginConfig config) throws MqttException {
        connect(config);
    }

    private MqttClient client;

    private void connect(LoginConfig config) throws MqttException {
        try {
            log.info(">>>>>mqtt 正在连接中");
            if (config == null) {
                return;
            }
            log.info(">>>>>mqtt ip:{} port:{}", config.getIp(), config.getPort());
            String url = "tcp://" + config.getIp() + ":" + config.getPort();
            String password = config.getPassword();
//            if (!StringUtils.isEmpty(config.getPassword())) {
////                password = AESUtil.dynamicDecrypt(config.getMqttPassword(), config.getMqttUsername());
//            }
            String clientId = "blog service";
            client = new MqttClient(url, clientId, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，
            // 这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(true);
            // 设置连接的用户名
            if (config.getUserName() != null && !config.getUserName().equals("")) {
                options.setUserName(config.getUserName());
            }
            // 设置连接的密码
            if (password != null && !password.equals("")) {
                options.setPassword(password.toCharArray());
            }
            options.setUserName(options.getUserName());
            options.setPassword(options.getPassword());
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(100);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送心跳判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(20);
            // 设置“遗嘱”消息的话题，若客户端与服务器之间的连接意外中断，服务器将发布客户端的“遗嘱”消息。
            options.setWill("willTopic", WILL_DATA, 2, false);
            client.setCallback(new PushCallback());
            client.connect(options);
            log.info(">>>>>mqtt连接成功，ip：{},port：{}", config.getIp(), config.getPort());
        } catch (Exception e) {
            log.error(">>>>>mqtt 连接报错：" + e.getMessage(), e);
            mqttPushClient = null;
        }
    }

    /**
     * 发布，默认qos为0，非持久化
     *
     * @param topic
     * @param pushMessage
     */
    public void publish(String topic, String pushMessage) {
        publish(1, false, topic, pushMessage);
    }

    /**
     * 发布
     *
     * @param qos
     * @param retained
     * @param topic
     * @param pushMessage
     */
    public void publish(int qos, boolean retained, String topic, String pushMessage) {
        if (client.isConnected()) {
            MqttMessage message = new MqttMessage();
            message.setQos(qos);
            message.setRetained(retained);
            message.setPayload(pushMessage.getBytes());
            MqttTopic mTopic = client.getTopic(topic);
            if (null == mTopic) {
                log.error("topic not exist");
                return;
            }
            MqttDeliveryToken token;
            try {
                token = mTopic.publish(message);
                token.waitForCompletion();
            } catch (Exception e) {
                log.info(e.getMessage(), e);
            }
        } else {
            log.error("Mqtt not connected");
        }
    }

    /**
     * 订阅某个主题，qos默认为0
     *
     * @param topic
     */
    public void subscribe(String topic) {
        subscribe(topic, 0);
    }

    /**
     * 订阅某个主题
     *
     * @param topic
     * @param qos
     */
    public void subscribe(String topic, int qos) {
        try {
            client.subscribe(topic, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    public static void clearInstance() {
        mqttPushClient = null;
    }

    /**
     * 关闭mqtt
     */
    public void close() {
        try {
            client.close();
            log.info(">>>>>mqtt断开成功");
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }
}