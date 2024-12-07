package com.blog.file.service.impl;

import cn.hutool.core.stream.CollectorUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.common.constant.ErrorMessage;
import com.blog.common.entity.file.*;
import com.blog.common.entity.file.vo.SensorControlVo;
import com.blog.common.entity.file.vo.SensorVo;
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

    @Resource
    private SensorTemplateDAO sensorTemplateDAO;

    /**
     * 下发传感器控制指令
     *
     * @param userId
     * @param id     控制命令消息id
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

        JSONArray jsonArray = JSONArray.parseArray(sensorControlVo.getControlMessage());
        List<SensorCommandCheckDto> sensorCommandCheckDtoList = new ArrayList<>();
        StringBuilder sensorIdGroup = new StringBuilder();
        for (int i = 0; i < jsonArray.size(); i++) {
            // 获取传感器组中数据
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            // 通过传感器型号构建对应参数接收类型
            String sensorType = jsonObject.getString("sensorType");
            SensorCommandCheckDto commandCheckDto = Objects.requireNonNull(SensorTypeEnum.getRuleImpl(sensorType)).newInstance();

            // 填充命令下发对象
            commandCheckDto.setSensorCode(jsonObject.getString("sensorCode"));
            commandCheckDto.setDelay(jsonObject.getInteger("delay"));
            commandCheckDto.setIdx(jsonObject.getInteger("idx"));

            // 修改命令数据回显需要
            commandCheckDto.setSensorType(sensorType);
            commandCheckDto.setId(jsonObject.getInteger("id"));

            if (i != 0) {
                sensorIdGroup.append(",");
            }
            sensorIdGroup.append(jsonObject.getInteger("id"));

            // 解析传入参数表单 一个传感器可以有多个参数 循环解析 数据放入同一对象
            JSONArray dataJsonArray = JSONArray.parseArray(jsonObject.getString("from"));
            for (int j = 0; j < dataJsonArray.size(); j++) {
                JSONObject dataJsonObject = dataJsonArray.getJSONObject(j);
                // 获取对象的属性
                Field field = commandCheckDto.getClass().getDeclaredField(dataJsonObject.getString("columnKey"));
                // 设置属性访问权限，以便私有属性也能访问
                field.setAccessible(true);
                if (dataJsonObject.getString("columnType").equals("Integer")) {
                    // 设置属性值
                    field.set(commandCheckDto, dataJsonObject.getInteger("value"));
                } else if (dataJsonObject.getString("columnType").equals("String")) {
                    // 设置属性值
                    field.set(commandCheckDto, dataJsonObject.getString("value"));
                } else {
                    throw new ValidException("传感器属性值类型错误");
                }
            }
            // 校验命令
            validateIvsRuleInfo(commandCheckDto);
            sensorCommandCheckDtoList.add(commandCheckDto);
        }

        sensorControlVo.setUserId(userId);
        sensorControlVo.setSensorIdGroup(sensorIdGroup.toString());
        sensorControlVo.setCreateTime(new Date());
        sensorControlVo.setUpdateTime(new Date());

        // 保存命令
        sensorControlVo.setControlMessage(JSONObject.toJSONString(sensorCommandCheckDtoList));

        if (sensorControlVo.getId() != null) {
            sensorControlDAO.updateById(sensorControlVo);
        } else {
            sensorControlDAO.insert(sensorControlVo);
        }
        return sensorControlVo.getId();
    }

    /**
     * 删除传感器控制指令
     *
     * @param userId
     * @param ids
     * @return
     */
    @Override
    public Integer deleteSensorControl(Integer userId, List<Integer> ids) {
        LambdaQueryWrapper<SensorControl> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SensorControl::getId, ids);
        lambdaQueryWrapper.eq(SensorControl::getUserId, userId);
        return sensorControlDAO.delete(lambdaQueryWrapper);
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

        // 传感器与单片机id
        Integer sensorId = sensorControlVoParam.getSensorId();
        Integer chipId = sensorControlVoParam.getChipId();

        LambdaQueryWrapper<SensorControl> wrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Sensor> sensorLambdaQueryWrapper;
        wrapper.eq(SensorControl::getUserId, userId);

        // 传感器map 组装到控制命令中 key为传感器id
        Map<Integer, Sensor> sensorMap;

        // 通过传感器id查询传感器控制命令
        if (sensorId != null) {
            wrapper.eq(SensorControl::getSensorId, sensorId);
            sensorLambdaQueryWrapper = new LambdaQueryWrapper<Sensor>().eq(Sensor::getUserId, userId).eq(Sensor::getId, sensorId);
        } else if (chipId != null) {
            // 通过单片机id查询传感器控制命令
            wrapper.eq(SensorControl::getChipId, chipId);
            // 以单片机为条件查询时可以查询单片机下全部命令组 包括单传感器控制命令与多传感器控制命令
            if (sensorControlVoParam.getCommandGroup() != null) {
                wrapper.eq(SensorControl::getCommandGroup, sensorControlVoParam.getCommandGroup());
            }
            Chip chip = chipDAO.selectById(chipId);
            sensorLambdaQueryWrapper = new LambdaQueryWrapper<Sensor>().eq(Sensor::getUserId, userId)
                    .eq(Sensor::getDeviceCode, chip.getDeviceCode()).eq(Sensor::getChipCode, chip.getChipCode());

        } else {
            throw new ValidException("传感器id与单片机id不可同时为空");
        }

        // 获取到传感器控制命令
        PageHelper.startPage(sensorControlVoParam.getPageNum(), sensorControlVoParam.getPageSize());
        Page<SensorControl> sensorControlPage = (Page<SensorControl>) sensorControlDAO.selectList(wrapper);

        // 获取传感器
        sensorMap = sensorDAO.selectList(sensorLambdaQueryWrapper).stream().collect(Collectors.toMap(Sensor::getId, Function.identity()));

        // 返回的传感器命令数据
        List<SensorControlVo> sensorControlVoList = new ArrayList<>();

        for (SensorControl sensorControl : sensorControlPage) {

            // 复制数据
            SensorControlVo sensorControlVo = new SensorControlVo();
            BeanUtils.copyProperties(sensorControl, sensorControlVo);

            // 命令添加传感器数据
            if (sensorControl.getCommandGroup() == 0) {
                // 单条命令直接取传感器
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
     * 数据返回格式 固定格式用于界面解析
     * {
     *      name: '',
     *      sensor: [
     *          {
     *              id: 0,
     *              idx: 0,
     *              // 与下拉框联动 保存勾选的传感器信息
     *              sensorData: {} as any,
     *              sensorType: '',
     *              sensorCode: '',
     *              delay: 0,
     *              from: [] as any
     *          }
     *      ]
     * }
     *
     * @param userId
     * @param id
     * @return
     */
    @Override
    public JSONObject selectSensorControlById(Integer userId, Integer id) {
        SensorControl sensorControl = sensorControlDAO.selectOne(new LambdaQueryWrapper<SensorControl>().eq(SensorControl::getUserId, userId).eq(SensorControl::getId, id));
        JSONObject result = new JSONObject();
        result.put("id", id);
        result.put("name", sensorControl.getControlName());
        JSONArray sensor = new JSONArray();

        // 从命令组中获取传感器id
        List<String> sensorIds;
        if (sensorControl.getCommandGroup() == 1) {
            sensorIds = Arrays.asList(sensorControl.getSensorIdGroup().split(","));
        } else {
            sensorIds = Collections.singletonList(String.valueOf(sensorControl.getSensorId()));
        }

        // 获取命令组中的传感器类型
        Set<String> sensorTypeSet = sensorDAO.selectList(new LambdaQueryWrapper<Sensor>().in(Sensor::getId, sensorIds))
                .stream().map(Sensor::getSensorType).collect(Collectors.toSet());

        Map<String, SensorTemplate> sensorTemplateMap = sensorTemplateDAO.selectList(new LambdaQueryWrapper<SensorTemplate>()
                .in(SensorTemplate::getSensorType, sensorTypeSet)).stream().collect(Collectors.toMap(SensorTemplate::getSensorType, Function.identity()));

        // 解析控制命令
        JSONArray jsonArray = JSONArray.parseArray(sensorControl.getControlMessage());
        for (int i=0; i<jsonArray.size(); i++) {
            JSONObject js = jsonArray.getJSONObject(i);
            JSONObject sensorData = new JSONObject();
            sensorData.put("idx", js.getInteger("idx"));
            sensorData.put("id", js.getInteger("id"));
            sensorData.put("sensorType", js.getString("sensorType"));
            sensorData.put("sensorCode", js.getString("sensorCode"));

            SensorVo sensorVo = new SensorVo();
            BeanUtils.copyProperties(sensorDAO.selectById(js.getInteger("id")), sensorVo);
            sensorData.put("sensorData", sensorVo);

            SensorTemplate from = sensorTemplateMap.get(js.getString("sensorType"));
            JSONArray fromJsonArray = JSONArray.parseArray(from.getTemplate());
            for (int fi = 0; fi < fromJsonArray.size(); fi++) {
                JSONObject fjs = fromJsonArray.getJSONObject(fi);
                fjs.put("value", js.getInteger("data"));
            }
            sensorData.put("from", fromJsonArray);


            sensorData.put("delay", i == 0 ? 0 : js.getString("delay"));

            sensor.add(sensorData);
        }
        result.put("sensor", sensor.toString());
        return result;
    }

    /**
     * 校验命令数据
     *
     * @param sensorCommandCheckVo
     * @throws ValidException
     */
    private static void validateIvsRuleInfo(SensorCommandCheckDto sensorCommandCheckVo) throws ValidException {

        Map<String, String> errorMap = BeanValidationUtil.validationBean(sensorCommandCheckVo, AddGroup.class);

        if (!CollectionUtils.isEmpty(errorMap)) {
            throw new ValidException(ErrorMessage.PARAMETER_VERIFICATION_ERROR, errorMap);
        }
    }
}
