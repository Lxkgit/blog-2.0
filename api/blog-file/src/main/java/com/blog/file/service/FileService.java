package com.blog.file.service;

import com.blog.common.result.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author: lxk
 * @date: 2022/7/7 20:50
 * @description:
 * @modified By:
 */
public interface FileService {

    Result upload(MultipartFile[] files, Integer userId, String filePath);

}
