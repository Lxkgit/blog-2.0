package com.blog.file.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.common.constant.Constant;
import com.blog.common.constant.ErrorMessage;
import com.blog.common.entity.file.*;
import com.blog.common.entity.file.vo.ChipVo;
import com.blog.common.entity.file.vo.SensorControlVo;
import com.blog.common.entity.file.vo.sensor.control.SensorCommandVo;
import com.blog.common.enums.file.SensorTypeEnum;
import com.blog.common.exception.ValidException;
import com.blog.common.util.BeanValidationUtil;
import com.blog.common.util.MyPage;
import com.blog.common.util.MyPageUtils;
import com.blog.common.valication.group.AddGroup;
import com.blog.file.dao.ChipDAO;
import com.blog.file.dao.DeviceDAO;
import com.blog.file.dao.SensorControlDAO;
import com.blog.file.dao.SensorDAO;
import com.blog.file.netty.dto.NettyPacket;
import com.blog.file.netty.dto.NettySyncBlogFile;
import com.blog.file.netty.enums.NettyTopicEnum;
import com.blog.file.netty.service.NettyServer;
import com.blog.file.service.SensorControlService;
import com.blog.file.service.SensorService;
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
 * @Author: 308501
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

        SensorCommandVo sensorCommandVo = JSONObject.toJavaObject(JSONObject.parseObject(sensorControlVo.getControlMessage()),
                SensorTypeEnum.getRuleImpl(sensorControlVo.getSensorCode()));
        validateIvsRuleInfo(sensorCommandVo);
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
        wrapper.eq("userId", userId);
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
        wrapper.eq("userId", userId);
        SensorControl sensorControl = sensorControlDAO.selectOne(wrapper);
        SensorControlVo sensorControlVo = new SensorControlVo();
        BeanUtils.copyProperties(sensorControl, sensorControlVo);
        return sensorControlVo;
    }

    /**
     * 下发传感器控制指令
     *
     * @param userId
     * @param id
     * @throws ValidException
     */
    @Override
    public void controlSensor(Integer userId, Integer id) throws ValidException {
        QueryWrapper<SensorControl> sensorControlQueryWrapper = new QueryWrapper<>();
        sensorControlQueryWrapper.eq("id", id);
        sensorControlQueryWrapper.eq("user_id", userId);
        SensorControl sensorControl = sensorControlDAO.selectOne(sensorControlQueryWrapper);
        if (sensorControl == null) {
            throw new ValidException(ErrorMessage.SENSOR_CONTROL_NOT_EXISTS);
        }
        QueryWrapper<Sensor> sensorQueryWrapper = new QueryWrapper<>();
        sensorQueryWrapper.eq("id", sensorControl.getSensorId());
        sensorQueryWrapper.eq("user_id", userId);
        Sensor sensor = sensorDAO.selectOne(sensorQueryWrapper);
        if (sensor == null) {
            throw new ValidException(ErrorMessage.SENSOR_NOT_EXISTS);
        }

        QueryWrapper<Chip> chipQueryWrapper = new QueryWrapper<>();
        chipQueryWrapper.eq("id", sensor.getChipId());
        chipQueryWrapper.eq("user_id", userId);
        Chip chip = chipDAO.selectOne(chipQueryWrapper);
        if (chip == null) {
            throw new ValidException(ErrorMessage.CHIP_NOT_EXISTS);
        }

        QueryWrapper<Device> deviceQueryWrapper = new QueryWrapper<>();
        deviceQueryWrapper.eq("id", chip.getDeviceId());
        deviceQueryWrapper.eq("user_id", userId);
        Device device = deviceDAO.selectOne(deviceQueryWrapper);
        if (device == null) {
            throw new ValidException(ErrorMessage.DEVICE_NOT_EXISTS);
        }

        SensorCommandVo commandVo = JSONObject.toJavaObject(JSONObject.parseObject(sensorControl.getControlMessage()), SensorTypeEnum.getRuleImpl("duo"));
        commandVo.setChipType(chip.getChipType());
        commandVo.setSensorType(sensor.getSensorCode());

        NettyPacket<SensorCommandVo> sensorCommandRequest = NettyPacket.buildRequest(commandVo);
        sensorCommandRequest.setTopic(NettyTopicEnum.BLOG_SENSOR_CONTROL.getTopic());

        nettyServer.channelWriteByClientId(device.getDeviceCode(), JSONObject.toJSONString(sensorCommandRequest));
    }

    private static void validateIvsRuleInfo(SensorCommandVo sensorCommandVo) throws ValidException {

        Map<String, String> errorMap = BeanValidationUtil.validationBean(sensorCommandVo, AddGroup.class);

        if (!CollectionUtils.isEmpty(errorMap)) {
            throw new ValidException(ErrorMessage.PARAMETER_VERIFICATION_ERROR, errorMap);
        }
    }
}
