package com.blog.file.dao;

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
public interface UploadLogDAO {
    int saveFileUpload(UploadLog uploadLog);
    int updateFileUploadState(@Param("id") Integer id, @Param("userId") Integer userId,
                              @Param("uploadState") Integer uploadState, @Param("uploadMsg") String uploadMsg);
}
