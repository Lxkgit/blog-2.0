package com.blog.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.common.constant.Constant;
import com.blog.common.constant.ErrorMessage;
import com.blog.common.entity.file.Chip;
import com.blog.common.entity.file.vo.ChipVo;
import com.blog.common.exception.ValidException;
import com.blog.common.util.MyPage;
import com.blog.common.util.MyPageUtils;
import com.blog.common.util.MyStringUtils;
import com.blog.file.dao.ChipDAO;
import com.blog.file.service.ChipService;
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
 * @description: 单片机服务层方法
 * @Author: 308501
 * @date 2024/1/30 19:58
 */

@Slf4j
@Service
public class ChipServiceImpl implements ChipService {

    @Resource
    private ChipDAO chipDAO;

    /**
     * 新增单片机
     *
     * @param userId
     * @param chipVo
     * @return
     * @throws ValidException
     */
    @Override
    public Integer addChip(Integer userId, ChipVo chipVo) throws ValidException {
        QueryWrapper<Chip> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("chip_code", chipVo.getChipCode());
        Chip chip = chipDAO.selectOne(wrapper);
        if (chip != null) {
            throw new ValidException(ErrorMessage.CHIP_CODE_EXISTS);
        }
        chipVo.setUserId(userId);
        chipVo.setChipStatus(Constant.DEVICE_OFFLINE);
        chipVo.setCreateTime(new Date());
        chipVo.setUpdateTime(new Date());
        chipDAO.insert(chipVo);
        return chipVo.getId();
    }

    /**
     * 批量删除单片机
     *
     * @param userId
     * @param ids
     * @return
     */
    @Override
    public Integer deleteChips(Integer userId, String ids) {
        Set<String> idSet = MyStringUtils.splitString(ids, ",");
        chipDAO.updateChipStatus(idSet, userId, Constant.DEVICE_DELETE);
        return idSet.size();
    }

    /**
     * 修改单片机信息
     *
     * @param userId
     * @param chipVo
     * @return
     * @throws ValidException
     */
    @Override
    public Integer updateChip(Integer userId, ChipVo chipVo) throws ValidException {
        QueryWrapper<Chip> wrapper = new QueryWrapper<>();
        wrapper.eq("userId", userId);
        wrapper.eq("chip_code", chipVo.getChipCode());
        wrapper.ne("id", chipVo.getId());
        Chip chip = chipDAO.selectOne(wrapper);
        if (chip != null) {
            throw new ValidException(ErrorMessage.CHIP_CODE_EXISTS);
        }
        chipVo.setUserId(userId);
        chipVo.setChipStatus(Constant.DEVICE_OFFLINE);
        chipVo.setUpdateTime(new Date());
        chipDAO.updateById(chipVo);
        return chipVo.getId();
    }

    /**
     * 分页查询单片机
     *
     * @param userId
     * @param chipVoParam
     * @return
     */
    @Override
    public MyPage<ChipVo> selectChipList(Integer userId, ChipVo chipVoParam) {
        QueryWrapper<Chip> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.ne("chip_status", Constant.DEVICE_DELETE);

        PageHelper.startPage(chipVoParam.getPageNum(), chipVoParam.getPageSize());
        Page<Chip> chipPage = (Page<Chip>) chipDAO.selectList(wrapper);

        List<ChipVo> chipVoList = new ArrayList<>();
        for (Chip chip : chipPage) {
            ChipVo chipVo = new ChipVo();
            BeanUtils.copyProperties(chip, chipVo);
            chipVoList.add(chipVo);
        }

        return MyPageUtils.pageUtil(chipVoList, chipPage.getPageNum(), chipPage.getPageSize(), (int) chipPage.getTotal());
    }

    /**
     * 查询指定单片机信息
     *
     * @param userId
     * @param id
     * @return
     */
    @Override
    public ChipVo selectChipId(Integer userId, Integer id) {
        QueryWrapper<Chip> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        wrapper.eq("user_id", userId);
        Chip chip = chipDAO.selectOne(wrapper);
        ChipVo chipVo = new ChipVo();
        BeanUtils.copyProperties(chip, chipVo);
        return chipVo;
    }
}
