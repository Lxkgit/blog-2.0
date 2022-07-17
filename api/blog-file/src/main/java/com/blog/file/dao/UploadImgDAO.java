package com.blog.file.dao;

import com.blog.common.entity.file.UploadImg;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UploadImgDAO {

    int saveImgUrl(UploadImg uploadImg);
}
