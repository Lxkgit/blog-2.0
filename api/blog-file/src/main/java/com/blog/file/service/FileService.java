package com.blog.file.service;

import com.blog.common.entity.file.vo.FileDataVo;
import com.blog.common.entity.user.BlogUser;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @description: 文件服务类
 * @Author: 308501
 * @date 2023/8/2 15:00
 */

public interface FileService {

    List<FileDataVo> selectFileDir(BlogUser blogUser, String filePath);
}
