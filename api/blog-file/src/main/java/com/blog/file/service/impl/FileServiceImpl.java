package com.blog.file.service.impl;

import com.blog.common.entity.file.vo.FileDataVo;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.enums.file.FilePathEnum;
import com.blog.common.util.DateUtil;
import com.blog.file.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @description: 文件服务
 * @Author: 308501
 * @date 2023/8/2 16:03
 */

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.basePath}")
    private String basePath;

    @Override
    public List<FileDataVo> selectFileDir(BlogUser blogUser, String filePath) {
        System.out.println("请求接口 ... ");
        File file1 = new File(basePath + FilePathEnum.USER_PATH.getFilePath() + "/" + blogUser.getId() + filePath);
        show(file1);
        return null;
    }

    private void show(File file) {
        File[] files = file.listFiles();
        if (null != files) {
            for (File value : files) {
                if (value.isFile()) {
                    System.out.println("文件: ");
                } else {
                    System.out.println("目录: ");
                }
                System.out.println(value.getName());
                System.out.println(DateUtil.timeStampToDateTime(value.lastModified()));
                System.out.println(value.length());
            }
        }
    }
}
