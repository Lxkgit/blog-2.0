package com.blog.file.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.common.constant.ErrorMessage;
import com.blog.common.entity.file.*;
import com.blog.common.entity.file.vo.SensorControlVo;
import com.blog.file.dao.*;
import com.blog.file.feign.service.UserService;
import com.blog.common.netty.dto.sensor.control.SensorCommandCheckDto;
import com.blog.common.netty.dto.sensor.control.SensorCommandDto;
import com.blog.common.netty.dto.sensor.control.SteeringEngine180Dto;
import com.blog.common.netty.enums.sensor.SensorTypeEnum;
import com.blog.common.exception.ValidException;
import com.blog.common.util.BeanValidationUtil;
import com.blog.common.util.MyPage;
import com.blog.common.util.MyPageUtils;
import com.blog.common.valication.group.AddGroup;
import com.blog.common.netty.dto.NettyPacket;
import com.blog.common.netty.enums.NettyTopicEnum;
import com.blog.file.netty.service.NettyServer;
import com.blog.file.service.SensorControlService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

        List<SteeringEngine180Dto> list = JSONArray.parseArray(sensorControl.getControlMessage(), SteeringEngine180Dto.class);

        SensorCommandDto<SteeringEngine180Dto> commandVo = new SensorCommandDto<>();
        commandVo.setChipCode(sensor.getChipCode());
        commandVo.setSensorCode(sensor.getSensorCode());
        commandVo.setCommandList(list);

        NettyPacket<SensorCommandDto<SteeringEngine180Dto>> sensorCommandRequest = NettyPacket.buildRequest(commandVo);
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
    public Integer createSensorControl(Integer userId, SensorControlVo sensorControlVo) throws ValidException, IllegalAccessException, InstantiationException, NoSuchFieldException {

        log.info(sensorControlVo.toString());

        JSONArray jsonArray = JSONArray.parseArray(sensorControlVo.getControlMessage());

        for (int i = 0; i< jsonArray.size(); i++) {

            JSONObject jsonObject = jsonArray.getJSONObject(i);

            String sensorType = jsonObject.getString("sensorType");
//            SensorCommandCheckDto sensorCommandCheckVo = JSONObject.toJavaObject(JSONObject.parseObject(sensorControlVo.getControlMessage()),
//                    SensorTypeEnum.getRuleImpl(sensorType));

            SensorCommandCheckDto commandCheckDto = Objects.requireNonNull(SensorTypeEnum.getRuleImpl(sensorType)).newInstance();

            JSONArray dataJsonArray = JSONArray.parseArray(jsonObject.getString("from"));
            for (int j = 0; j< dataJsonArray.size(); j++) {
                JSONObject dataJsonObject = dataJsonArray.getJSONObject(i);
                // 获取对象的属性
                Field field = commandCheckDto.getClass().getDeclaredField(dataJsonObject.getString("key"));
                // 设置属性访问权限，以便私有属性也能访问
                field.setAccessible(true);
                // 设置属性值
                field.set(commandCheckDto, Integer.parseInt(dataJsonObject.getString("value")));
            }





//            if (commandCheckDto instanceof SteeringEngine180Dto) {
//
////                ((SteeringEngine180Dto) commandCheckDto).setData();
//            }
            System.out.println(commandCheckDto.getSensorType().toString());
//            commandCheckDto


//            validateIvsRuleInfo(sensorCommandCheckVo);
//            sensorCommandCheckDtoList.add(sensorCommandCheckDto);
        }

        sensorControlVo.setUserId(userId);
        sensorControlVo.setCreateTime(new Date());
        sensorControlVo.setUpdateTime(new Date());
//        sensorControlVo.setControlMessage(JSONObject.toJSONString(sensorCommandCheckDtoList));
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
    public MyPage<SensorControlVo> selectSensorControlList(Integer userId, SensorControlVo sensorControlVoParam) throws ValidException {

        Integer sensorId = sensorControlVoParam.getSensorId();
        Integer chipId = sensorControlVoParam.getChipId();

        LambdaQueryWrapper<SensorControl> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SensorControl::getUserId, userId);
        if (sensorId != null) {
            wrapper.eq(SensorControl::getSensorId, sensorId);
        } else if (chipId != null) {
            wrapper.eq(SensorControl::getChipId, chipId);
            // 以单片机为条件查询时可以查询单片机下全部命令组 包括单传感器控制命令与多传感器控制命令
            if (sensorControlVoParam.getCommandGroup() != null) {
                wrapper.eq(SensorControl::getCommandGroup, sensorControlVoParam.getCommandGroup());
            }
        } else {
            throw new ValidException("传感器id与单片机id不可同时为空");
        }

        PageHelper.startPage(sensorControlVoParam.getPageNum(), sensorControlVoParam.getPageSize());
        Page<SensorControl> sensorControlPage = (Page<SensorControl>) sensorControlDAO.selectList(wrapper);

        List<SensorControlVo> sensorControlVoList = new ArrayList<>();
        Chip chip = chipDAO.selectById(chipId);

        Map<Integer, Sensor> sensorMap = sensorDAO.selectList(new LambdaQueryWrapper<Sensor>().eq(Sensor::getUserId, userId)
                .eq(Sensor::getDeviceCode, chip.getDeviceCode()).eq(Sensor::getChipCode, chip.getChipCode())).stream()
                .collect(Collectors.toMap(Sensor::getId, Function.identity()));

        for (SensorControl sensorControl : sensorControlPage) {
            SensorControlVo sensorControlVo = new SensorControlVo();
            BeanUtils.copyProperties(sensorControl, sensorControlVo);

            // 单条命令直接取传感器
            if (sensorControl.getCommandGroup() == 0) {
                sensorControlVo.setSensorList(Collections.singletonList(sensorMap.get(sensorControl.getSensorId())));
            } else {
                // 命令组切割转换之后获取传感器
                sensorControlVo.setSensorList(new ArrayList<>());
                List<String> ids = Arrays.asList(sensorControl.getSensorIdGroup().split(","));
                ids.forEach(id -> sensorControlVo.getSensorList().add(sensorMap.get(Integer.parseInt(id))));
            }
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
