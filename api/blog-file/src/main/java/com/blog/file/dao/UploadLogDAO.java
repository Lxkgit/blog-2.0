package com.blog.file.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.file.UploadLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author: lxk
 * @date: 2022/7/7 20:54
 * @description:
 * @modified By:
 */

@Mapper
public interface UploadLogDAO extends BaseMapper<UploadLog> {

}
