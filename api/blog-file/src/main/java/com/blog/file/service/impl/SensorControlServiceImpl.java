package com.blog.file.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.common.constant.Constant;
import com.blog.common.constant.ErrorMessage;
import com.blog.common.entity.file.*;
import com.blog.common.entity.file.vo.SensorControlVo;
import com.blog.common.entity.user.BlogUser;
import com.blog.file.dao.*;
import com.blog.file.feign.service.UserService;
import com.blog.file.netty.dto.sensor.control.SensorCommandCheckDto;
import com.blog.file.netty.dto.sensor.control.SensorCommandDto;
import com.blog.file.netty.dto.sensor.control.SteeringEngineDto;
import com.blog.file.netty.enums.sensor.SensorTypeEnum;
import com.blog.common.exception.ValidException;
import com.blog.common.util.BeanValidationUtil;
import com.blog.common.util.MyPage;
import com.blog.common.util.MyPageUtils;
import com.blog.common.valication.group.AddGroup;
import com.blog.file.netty.dto.NettyPacket;
import com.blog.file.netty.enums.NettyTopicEnum;
import com.blog.file.netty.service.NettyServer;
import com.blog.file.service.SensorControlService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @description: 传感器控制服务类
 * @Author: lxk
 * @date 2024/2/2 10:43
 */

@Slf4j
@Service
public class SensorControlServiceImpl implements SensorControlService {

    @Resource
    private DeviceDAO deviceDAO;

    @Resource
    private ChipDAO chipDAO;

    @Resource
    private SensorDAO sensorDAO;

    @Resource
    private SensorControlDAO sensorControlDAO;

    @Resource
    private NettyServer nettyServer;

    @Resource
    private UserService userService;

    @Resource
    private UserDeviceDAO userDeviceDAO;

    /**
     * 下发传感器控制指令
     *
     * @param userId
     * @param id 控制命令消息id
     * @throws ValidException
     */
    @Override
    public Boolean controlSensor(Integer userId, Integer id) throws ValidException {

        SensorControl sensorControl = sensorControlDAO.selectById(id);

        if (sensorControl == null) {
            throw new ValidException(ErrorMessage.SENSOR_CONTROL_NOT_EXISTS);
        }

        if (!userId.equals(sensorControl.getUserId())) {
            throw new ValidException("只能控制自己的传感器");
        }

        Sensor sensor = sensorDAO.selectById(sensorControl.getSensorId());

        List<SteeringEngineDto> list = JSONArray.parseArray(sensorControl.getControlMessage(), SteeringEngineDto.class);

        SensorCommandDto<SteeringEngineDto> commandVo = new SensorCommandDto<>();
        commandVo.setChipCode(sensor.getChipCode());
        commandVo.setSensorCode(sensor.getSensorCode());
        commandVo.setCommandList(list);

        NettyPacket<SensorCommandDto<SteeringEngineDto>> sensorCommandRequest = NettyPacket.buildRequest(commandVo);
        sensorCommandRequest.setTopic(NettyTopicEnum.BLOG_SENSOR_CONTROL.getTopic());

        return nettyServer.channelWriteByRegisterId(sensor.getDeviceCode(), JSONObject.toJSONString(sensorCommandRequest));
    }

    /**
     * 创建传感器控制指令
     *
     * @param userId
     * @param sensorControlVo
     * @return
     * @throws ValidException
     */
    @Override
    public Integer createSensorControl(Integer userId, SensorControlVo sensorControlVo) throws ValidException {

        SensorCommandCheckDto sensorCommandCheckVo = JSONObject.toJavaObject(JSONObject.parseObject(sensorControlVo.getControlMessage()),
                SensorTypeEnum.getRuleImpl(sensorControlVo.getSensorCode()));

        validateIvsRuleInfo(sensorCommandCheckVo);
        sensorControlVo.setUserId(userId);
        sensorControlVo.setCreateTime(new Date());
        sensorControlVo.setUpdateTime(new Date());

        sensorControlDAO.insert(sensorControlVo);
        return null;
    }

    /**
     * 删除传感器控制指令
     *
     * @param userId
     * @param id
     * @return
     */
    @Override
    public Integer deleteSensorControl(Integer userId, Integer id) {
        QueryWrapper<SensorControl> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        wrapper.eq("user_id", userId);
        return sensorControlDAO.delete(wrapper);
    }

    /**
     * 修改传感器控制指令
     *
     * @param userId
     * @param sensorControlVo
     * @return
     */
    @Override
    public Integer updateSensorControl(Integer userId, SensorControlVo sensorControlVo) {
        sensorControlVo.setUserId(userId);
        sensorControlVo.setUpdateTime(new Date());
        sensorControlDAO.updateSensorControlById(sensorControlVo);
        return sensorControlVo.getId();
    }

    /**
     * 分页查询传感器指令
     *
     * @param userId
     * @param sensorControlVoParam
     * @return
     */
    @Override
    public MyPage<SensorControlVo> selectSensorControlList(Integer userId, SensorControlVo sensorControlVoParam) {

        QueryWrapper<SensorControl> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("sensor_id", sensorControlVoParam.getSensorId());

        PageHelper.startPage(sensorControlVoParam.getPageNum(), sensorControlVoParam.getPageSize());
        Page<SensorControl> sensorControlPage = (Page<SensorControl>) sensorControlDAO.selectList(wrapper);

        List<SensorControlVo> sensorControlVoList = new ArrayList<>();
        for (SensorControl sensorControl : sensorControlPage) {
            SensorControlVo sensorControlVo = new SensorControlVo();
            BeanUtils.copyProperties(sensorControl, sensorControlVo);
            sensorControlVoList.add(sensorControlVo);
        }

        return MyPageUtils.pageUtil(sensorControlVoList, sensorControlPage.getPageNum(), sensorControlPage.getPageSize(), (int) sensorControlPage.getTotal());
    }

    /**
     * 根据id查询传感器指令
     *
     * @param userId
     * @param id
     * @return
     */
    @Override
    public SensorControlVo selectSensorControlById(Integer userId, Integer id) {
        QueryWrapper<SensorControl> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        wrapper.eq("user_id", userId);
        SensorControl sensorControl = sensorControlDAO.selectOne(wrapper);
        SensorControlVo sensorControlVo = new SensorControlVo();
        BeanUtils.copyProperties(sensorControl, sensorControlVo);
        return sensorControlVo;
    }



    private static void validateIvsRuleInfo(SensorCommandCheckDto sensorCommandCheckVo) throws ValidException {

        Map<String, String> errorMap = BeanValidationUtil.validationBean(sensorCommandCheckVo, AddGroup.class);

        if (!CollectionUtils.isEmpty(errorMap)) {
            throw new ValidException(ErrorMessage.PARAMETER_VERIFICATION_ERROR, errorMap);
        }
    }
}
