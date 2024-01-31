package com.blog.file.service;

import com.blog.common.entity.file.Chip;
import com.blog.common.entity.file.vo.ChipVo;
import com.blog.common.exception.ValidException;
import com.blog.common.util.MyPage;

import java.util.List;

/**
 * @description: 单片机服务层
 * @Author: 308501
 * @date 2024/1/30 19:57
 */

public interface ChipService {

    Integer addChip(Integer userId, ChipVo chipVo) throws ValidException;

    Integer deleteChips(Integer userId, String ids);

    Integer updateChip(Integer userId, ChipVo chipVo) throws ValidException;

    MyPage<ChipVo> selectChipList(Integer userId, ChipVo chipVo);

    ChipVo selectChipId(Integer userId, Integer id);

}
