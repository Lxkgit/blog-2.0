package com.blog.file.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @description: mqtt服务
 * @Author: lxk
 * @date 2024/1/2 16:22
 */
@Slf4j
public class MyMQTTClient {

    private static MqttClient client;
    private final String host;
    private final String username;
    private final String password;
    private final String clientId;
    private final int timeout;
    private final int keepalive;

    public MyMQTTClient(String host, String username, String password, String clientId, int timeOut, int keepAlive) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.clientId = clientId;
        this.timeout = timeOut;
        this.keepalive = keepAlive;
    }

    public static MqttClient getClient() {
        return client;
    }

    public static void setClient(MqttClient client) {
        MyMQTTClient.client = client;
    }

    /**
     * 设置mqtt连接参数
     *
     * @param username
     * @param password
     * @param timeout
     * @param keepalive
     * @return
     */
    public MqttConnectOptions setMqttConnectOptions(String username, String password, int timeout, int keepalive) {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setConnectionTimeout(timeout);
        options.setKeepAliveInterval(keepalive);
        // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，
        // 这里设置为true表示每次连接到服务器都以新的身份连接
        options.setCleanSession(true);
        options.setAutomaticReconnect(true);
        return options;
    }

    /**
     * 连接mqtt服务端，得到MqttClient连接对象
     */
    public void connect() throws MqttException {
        if (client == null) {
            client = new MqttClient(host, clientId, new MemoryPersistence());
            client.setCallback(new MyMQTTCallback(MyMQTTClient.this));
        }
        MqttConnectOptions mqttConnectOptions = setMqttConnectOptions(username, password, timeout, keepalive);
        if (client.isConnected()) {
            client.disconnect();
        }
        client.connect(mqttConnectOptions);
        log.info("MQTT connect success");//未发生异常，则连接成功
    }

    /**
     * 发布，默认qos为0，非持久化
     *
     * @param pushMessage
     * @param topic
     */
    public void publish(String pushMessage, String topic) {
        publish(pushMessage, topic, 0, false);
    }

    /**
     * 发布 指定qos等级
     * @param pushMessage
     * @param topic
     * @param qos
     */
    public void publish(String pushMessage, String topic, Integer qos) {
        publish(pushMessage, topic, qos, false);
    }

    /**
     * 发布消息
     *
     * @param pushMessage
     * @param topic
     * @param qos
     * @param retained:留存
     */
    public void publish(String pushMessage, String topic, int qos, boolean retained) {
        MqttMessage message = new MqttMessage();
        message.setPayload(pushMessage.getBytes());
        message.setQos(qos);
        message.setRetained(retained);
        MqttTopic mqttTopic = MyMQTTClient.getClient().getTopic(topic);
        if (null == mqttTopic) {
            log.error("topic is not exist");
            return;
        }
        MqttDeliveryToken token;//Delivery:配送
        synchronized (this) {//注意：这里一定要同步，否则，在多线程publish的情况下，线程会发生死锁，分析见文章最后补充
            try {
                token = mqttTopic.publish(message);//也是发送到执行队列中，等待执行线程执行，将消息发送到消息中间件
                token.waitForCompletion(1000L);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 订阅某个主题
     *
     * @param topic
     * @param qos
     */
    public void subscribe(String topic, int qos) {
        try {
            MyMQTTClient.getClient().subscribe(topic, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    /**
     * 取消订阅主题
     *
     * @param topic 主题名称
     */
    public void cleanTopic(String topic) {
        if (client != null && client.isConnected()) {
            try {
                client.unsubscribe(topic);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("取消订阅失败！");
        }
    }
}
