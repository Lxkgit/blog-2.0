package com.blog.file.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.common.constant.Constant;
import com.blog.common.constant.ErrorMessage;
import com.blog.common.entity.content.article.Article;
import com.blog.common.entity.content.article.ArticleType;
import com.blog.common.entity.content.article.bo.ArticleBo;
import com.blog.common.entity.file.Device;
import com.blog.common.entity.file.vo.DeviceVo;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.exception.ValidException;
import com.blog.common.util.MyStringUtils;
import com.blog.file.dao.DeviceDAO;
import com.blog.file.feign.UserClient;
import com.blog.file.netty.schedule.DeviceStatusSchedule;
import com.blog.file.service.DeviceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @description: 下级设备服务
 * @Author: 308501
 * @date 2024/1/29 13:50
 */

@Service
public class DeviceServiceImpl implements DeviceService {

    @Resource
    private DeviceDAO deviceDAO;

    @Resource
    private UserClient userClient;

    /**
     * 新增设备
     *
     * @param userId
     * @param deviceVo
     * @return
     * @throws ValidException
     */
    @Override
    public Integer addDevice(Integer userId, DeviceVo deviceVo) throws ValidException {
        QueryWrapper<Device> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("device_code", deviceVo.getDeviceCode());
        Device device = deviceDAO.selectOne(wrapper);
        if (device != null) {
            throw new ValidException(ErrorMessage.DEVICE_CODE_EXISTS);
        }
        BlogUser blogUser = JSONObject.parseObject(JSONObject.toJSONString(userClient.getUserById(userId).getResult()), BlogUser.class);
        deviceVo.setUserId(blogUser.getId());
        deviceVo.setUsername(blogUser.getUsername());
        deviceVo.setDeviceStatus(Constant.DEVICE_OFFLINE);
        deviceVo.setCreateTime(new Date());
        deviceVo.setUpdateTime(new Date());
        deviceDAO.insert(deviceVo);
        return deviceVo.getId();
    }

    /**
     * 删除设备
     *
     * @param userId
     * @param ids
     * @return
     * @throws ValidException
     */
    @Override
    public Integer deleteDevice(Integer userId, String ids) throws ValidException {
        Set<String> idSet = MyStringUtils.splitString(ids, ",");
        for (String id : idSet) {
            Device device = deviceDAO.selectById(Integer.parseInt(id));
            if (device != null) {
                DeviceStatusSchedule.removeChannelByRegisterId(device.getDeviceCode(), deviceDAO);
                deviceDAO.updateDeviceStatusById(id, userId, Constant.DEVICE_DELETE);
            } else {
                throw new ValidException(ErrorMessage.DEVICE_NOT_EXISTS, "id: " + id);
            }
        }
        return idSet.size();
    }

    /**
     * 修改设备
     *
     * @param userId
     * @param deviceVo
     * @return
     * @throws ValidException
     */
    @Override
    public Integer updateDevice(Integer userId, DeviceVo deviceVo) throws ValidException {
        QueryWrapper<Device> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("device_code", deviceVo.getDeviceCode());
        wrapper.ne("id", deviceVo.getId());
        Device device = deviceDAO.selectOne(wrapper);
        if (device != null) {
            throw new ValidException(ErrorMessage.DEVICE_CODE_EXISTS);
        }
        Device oldDevice = deviceDAO.selectById(deviceVo.getId());
        // 设备编码变化需要重新连接netty通道
        if (!oldDevice.getDeviceCode().equals(deviceVo.getDeviceCode())) {
            DeviceStatusSchedule.removeChannelByRegisterId(oldDevice.getDeviceCode(), deviceDAO);
        }
        deviceDAO.updateById(deviceVo);
        return deviceVo.getId();
    }

    /**
     * 查询设备列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<Device> selectDeviceList(Integer userId) {
        QueryWrapper<Device> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return deviceDAO.selectList(wrapper);
    }
}
