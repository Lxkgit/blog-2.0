package com.blog.file.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.file.UploadFile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UploadFileDAO extends BaseMapper<UploadFile> {

}
