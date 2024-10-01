package com.blog.pi.config;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.pi.dao.RegisterSettingDAO;
import com.blog.pi.entity.RegisterSetting;
import com.blog.pi.mqtt.MqttPushClient;
import com.blog.pi.mqtt.data.LoginConfig;
import com.blog.pi.mqtt.http.ChipStatusService;
import com.blog.pi.netty.service.DeviceInfoService;
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
 * @Author: lxk
 * @date 2024/1/29 15:56
 */

@Component
public class InitConfig implements ApplicationRunner {

    /**
     * 服务启动类型
     */
    @Value("${spring.profiles.active}")
    private String type;

    /**
     * netty设备注册码
     */
    @Value("${netty.deviceCode}")
    private String deviceCode;

    @Resource
    private DeviceInfoService service;

    public static Map<String, Object> registerConfigMap = new HashMap<>();

    @Resource
    private RegisterSettingDAO registerSettingDAO;

    @Resource
    private ChipStatusService chipStatusService;

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("开始加载配置 ");
        InitRegisterConfig();
        // 加载netty注册码
        InitNettyRegisterConfig();
        MqttPushClient.connect(new LoginConfig(
                (String) getRegisterConfig("mqtt","ip"),
                (Integer) getRegisterConfig("mqtt","port"),
                (String) getRegisterConfig("mqtt","userName"),
                (String) getRegisterConfig("mqtt","password"),
                (String) getRegisterConfig("mqtt","clientId")
        ));
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

    private void InitNettyRegisterConfig() {
        registerConfigMap.put("nettyDeviceCode", deviceCode);
    }
}
