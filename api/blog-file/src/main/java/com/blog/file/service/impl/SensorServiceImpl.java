package com.blog.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.common.constant.Constant;
import com.blog.common.constant.ErrorMessage;
import com.blog.common.entity.file.Chip;
import com.blog.common.entity.file.Sensor;
import com.blog.common.entity.file.SensorData;
import com.blog.common.entity.file.SensorType;
import com.blog.common.entity.file.vo.ChipVo;
import com.blog.common.entity.file.vo.SensorVo;
import com.blog.common.exception.ValidException;
import com.blog.common.util.MyPage;
import com.blog.common.util.MyPageUtils;
import com.blog.common.util.MyStringUtils;
import com.blog.common.util.StringUtils;
import com.blog.file.dao.SensorDAO;
import com.blog.file.dao.SensorDataDAO;
import com.blog.file.dao.SensorTypeDAO;
import com.blog.file.service.SensorService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @description: 传感器服务业务层
 * @Author: 308501
 * @date 2024/1/30 20:08
 */

@Slf4j
@Service
public class SensorServiceImpl implements SensorService {

    @Resource
    private SensorDAO sensorDAO;

    @Resource
    private SensorTypeDAO sensorTypeDAO;

    /**
     * 创建传感器
     *
     * @param userId
     * @param sensorVo
     * @return
     * @throws ValidException
     */
    @Override
    public Integer addSensor(Integer userId, SensorVo sensorVo) throws ValidException {
        sensorVo.setUserId(userId);
        sensorVo.setSensorStatus(Constant.DEVICE_OFFLINE);
        sensorVo.setCreateTime(new Date());
        sensorVo.setUpdateTime(new Date());
        sensorDAO.insert(sensorVo);
        return sensorVo.getId();
    }

    /**
     * 删除传感器
     *
     * @param userId
     * @param ids
     * @return
     */
    @Override
    public Integer deleteSensors(Integer userId, String ids) {
        Set<String> idSet = MyStringUtils.splitString(ids, ",");
        sensorDAO.updateSensorStatusByIds(idSet, userId, Constant.DEVICE_DELETE);
        return null;
    }

    /**
     * 修改传感器信息
     *
     * @param userId
     * @param sensorVo
     * @return
     * @throws ValidException
     */
    @Override
    public Integer updateSensor(Integer userId, SensorVo sensorVo) throws ValidException {
        Sensor sensor = sensorDAO.selectById(sensorVo.getId());
        if (sensor == null) {
            throw new ValidException(ErrorMessage.SENSOR_NOT_EXISTS);
        }
        if (!sensor.getUserId().equals(userId)) {
            throw new ValidException(ErrorMessage.SENSOR_USER_ERROR);
        }
        sensorVo.setUserId(userId);
        sensorVo.setUpdateTime(new Date());
        sensorDAO.updateById(sensorVo);
        return sensorVo.getId();
    }

    /**
     * 分页查询传感器
     *
     * @param userId
     * @param sensorVoParam
     * @return
     */
    @Override
    public MyPage<SensorVo> selectSensorList(Integer userId, SensorVo sensorVoParam) {

        QueryWrapper<Sensor> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.ne("sensor_status", Constant.DEVICE_DELETE);

        PageHelper.startPage(sensorVoParam.getPageNum(), sensorVoParam.getPageSize());
        Page<Sensor> sensorPage = (Page<Sensor>) sensorDAO.selectList(wrapper);

        List<SensorVo> sensorVoList = new ArrayList<>();
        for (Sensor sensor : sensorPage) {
            SensorVo sensorVo = new SensorVo();
            BeanUtils.copyProperties(sensor, sensorVo);
            sensorVo.setSensorType(sensorTypeDAO.selectById(sensor.getSensorTypeId()));
            sensorVoList.add(sensorVo);
        }

        return MyPageUtils.pageUtil(sensorVoList, sensorPage.getPageNum(), sensorPage.getPageSize(), (int) sensorPage.getTotal());
    }

    /**
     * 根据id查询传感器信息
     *
     * @param userId
     * @param id
     * @return
     */
    @Override
    public SensorVo selectSensorId(Integer userId, Integer id) {
        QueryWrapper<Sensor> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        wrapper.eq("user_id", userId);
        Sensor sensor = sensorDAO.selectOne(wrapper);
        SensorVo sensorVo = new SensorVo();
        BeanUtils.copyProperties(sensor, sensorVo);
        return sensorVo;
    }

}
