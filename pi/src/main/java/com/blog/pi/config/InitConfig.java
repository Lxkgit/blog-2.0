package com.blog.pi.config;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.pi.dao.RegisterSettingDAO;
import com.blog.pi.entity.RegisterSetting;
import com.blog.pi.mqtt.MqttPushClient;
import com.blog.pi.mqtt.data.LoginConfig;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 加载配置
 * @Author: 308501
 * @date 2024/1/29 15:56
 */

@Component
public class InitConfig implements ApplicationRunner {

    @Value("${spring.profiles.active}")
    private String type;

    public static Map<String, Object> registerConfigMap = new HashMap<>();

    @Resource
    private RegisterSettingDAO registerSettingDAO;

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("开始加载配置 ");
        InitRegisterConfig();
        MqttPushClient.connect(new LoginConfig("192.168.0.106", 1883, "admin", "public", "SMP1"));
    }

    /**
     * 加载注册配置信息
     */
    private void InitRegisterConfig() {
        QueryWrapper<RegisterSetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("setting_type", type);
        List<RegisterSetting> registerSettingList = registerSettingDAO.selectList(queryWrapper);
        for (RegisterSetting r : registerSettingList) {
            registerConfigMap.put(r.getSettingName(), r.getSetting());
        }
    }

    public static Object getRegisterConfig(String settingName, String settingField) {
        JSONObject jsonObject = JSONObject.parseObject((String) registerConfigMap.get(settingName));
        return jsonObject.get(settingField);
    }

}
