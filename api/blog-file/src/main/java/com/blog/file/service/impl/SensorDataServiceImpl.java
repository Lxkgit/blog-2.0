package com.blog.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.common.entity.file.SensorControl;
import com.blog.common.entity.file.SensorData;
import com.blog.common.entity.file.vo.SensorControlVo;
import com.blog.common.entity.file.vo.SensorDataVo;
import com.blog.common.util.MyPage;
import com.blog.common.util.MyPageUtils;
import com.blog.file.dao.SensorDataDAO;
import com.blog.file.service.SensorDataService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lxk
 * @description 传感器数据服务类
 * @date 2024/02/05
 */

@Service
public class SensorDataServiceImpl implements SensorDataService {

    @Resource
    private SensorDataDAO sensorDataDAO;

    /**
     * 保存传感器上报数据
     *
     * @param sensorData
     * @return
     */
    @Override
    public Integer saveSensorData(SensorData sensorData) {
        sensorData.setCreateTime(new Date());
        return sensorDataDAO.insert(sensorData);
    }

    /**
     * 查询数据传感器上报数据
     *
     * @param userId
     * @param sensorDataVoParam
     * @return
     */
    @Override
    public MyPage<SensorDataVo> selectSensorDataList(Integer userId, SensorDataVo sensorDataVoParam) {

        QueryWrapper<SensorData> wrapper = new QueryWrapper<>();
        wrapper.eq("sensor_id", sensorDataVoParam.getSensorId());

        PageHelper.startPage(sensorDataVoParam.getPageNum(), sensorDataVoParam.getPageSize());
        Page<SensorData> sensorDataPage = (Page<SensorData>) sensorDataDAO.selectList(wrapper);

        List<SensorDataVo> sensorDataVoList = new ArrayList<>();
        for (SensorData sensorData : sensorDataPage) {
            SensorDataVo sensorDataVo = new SensorDataVo();
            BeanUtils.copyProperties(sensorData, sensorDataVo);
            sensorDataVoList.add(sensorDataVo);
        }

        return MyPageUtils.pageUtil(sensorDataVoList, sensorDataPage.getPageNum(), sensorDataPage.getPageSize(), (int) sensorDataPage.getTotal());
    }
}
