package com.blog.file.netty.vo.sensor.receive;

import lombok.Data;

import java.util.List;

/**
 * @author lxk
 * @description 传感器消息接收类
 * @date 2024/08/26
 */

@Data
public class SensorReceiveVo {

    /**
     * 单片机编码
     */
    private String chipCode;

    /**
     * 消息编码
     */
    private String msgCode;

    /**
     * 消息条数
     * 单片机每次上电从1开始计数
     */
    private String msgCount;

    /**
     * 传感器接受消息列表
     */
    private List<ReceiveData> msgList;

    public static class ReceiveData {

        /**
         * 传感器编码
         */
        private String sensorCode;

        /**
         * 传感器数据
         * 多组数据以json格式写入
         */
        private String sensorData;

        public String getSensorCode() {
            return sensorCode;
        }

        public void setSensorCode(String sensorCode) {
            this.sensorCode = sensorCode;
        }

        public String getSensorData() {
            return sensorData;
        }

        public void setSensorData(String sensorData) {
            this.sensorData = sensorData;
        }
    }
}
