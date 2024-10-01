package com.blog.pi.mqtt.http;


import com.alibaba.fastjson.JSONObject;
import com.blog.pi.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 单片机状态服务
 * @Author lxk
 * @CreateTime 2024-10-01
 */

@Slf4j
@Service
public class ChipStatusService {

    private String MQTT_AUTHORIZATION  = "";


    /**
     * 登陆mqtt 获取token
     *
     * mqtt login接口返回数据
     * {
     *     "license": {
     *         "edition": "ce"
     *     },
     *     "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3Mjc3NzU1MTM0ODUsImlzcyI6IkVNUVgifQ.JYtcr0WwLu3nRV2mWsX2Hw7m4LC0Nvu2aEkU_ITaeiI",
     *     "version": "5.3.2"
     * }
     */
    public void loginMqtt() {

        try {
            Map<String, Object> param = new HashMap<>();
            param.put("username","admin");
            param.put("password","public");

            Map<String, Object> result = HttpUtil.httpPost("http://localhost:18083/api/v5/login", null, param);

            JSONObject jsonObject = (JSONObject) result.get("data");

            if (Integer.parseInt(String.valueOf(result.get("code"))) == 200) {
                MQTT_AUTHORIZATION = "Bearer " + jsonObject.get("token");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

//        Map<String, Object> param = new HashMap<>();
//        param.put("username", "admin");
//        param.put("password", "public");
//        String result = HttpUtil.post("http://localhost:18083/api/v5/login", param);
    }

    /**
     * 获取mqtt服务中全部注册的clientId
     *
     * mqtt 返回数据格式
     * {
     *     "data": [],
     *     "meta": {
     *         "count": 0,
     *         "hasnext": false,
     *         "limit": 100,
     *         "page": 1
     *     }
     * }
     *  @param flag 登陆信息失效是否重新获取数据 true 重新登陆
     */
    public List<String> getMqttClientId(boolean flag) {

        try {

            Map<String, String> header = new HashMap<>();
            header.put("Authorization", MQTT_AUTHORIZATION);
            Map<String, Object> result = HttpUtil.httpGet("http://localhost:18083/api/v5/clients", header);
            if (Integer.parseInt(String.valueOf(result.get("code"))) == 200) {

                return null;
            } else if (Integer.parseInt(String.valueOf(result.get("code"))) == 401 && flag){
                log.error("mqtt 登陆信息失效");

                // 登陆mqtt
                loginMqtt();

                // 再次获取信息
                return getMqttClientId(false);
            } else {

            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
