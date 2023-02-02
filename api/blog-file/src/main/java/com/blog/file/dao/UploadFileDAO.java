package com.blog.file.dao;

import com.blog.common.entity.file.UploadFile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UploadFileDAO {

    int saveFileUrl(UploadFile uploadImg);
}
