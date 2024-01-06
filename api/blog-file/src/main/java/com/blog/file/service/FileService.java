package com.blog.file.service;

import com.blog.common.entity.file.vo.FileDataVo;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.exception.ValidException;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @description: 文件服务类
 * @Author: lxk
 * @date 2023/8/2 15:00
 */

public interface FileService {

    List<FileDataVo> selectFileDir(BlogUser blogUser, FileDataVo fileDataVo);

    void saveFileDir(BlogUser blogUser, FileDataVo fileDataVo) throws ValidException;

    void deleteFileOrDir(BlogUser blogUser, FileDataVo fileDataVo) throws ValidException;

    void updateFileOrDirName(BlogUser blogUser, FileDataVo fileDataVo) throws ValidException;

    Long selectUserSpace(BlogUser blogUser);

    boolean syncFile(BlogUser blogUser, FileDataVo fileDataVo);

    boolean syncFileList(BlogUser blogUser, List<FileDataVo> fileDataVoList);
}
