package com.blog.file.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.file.Chip;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @description: 单片机数据层
 * @Author: 308501
 * @date 2024/1/30 19:58
 */

public interface ChipDAO extends BaseMapper<Chip> {

    void updateChipStatus(@Param("ids")Set<String> ids, @Param("userId") Integer userId, @Param("chipStatus") Integer chipStatus);
}
