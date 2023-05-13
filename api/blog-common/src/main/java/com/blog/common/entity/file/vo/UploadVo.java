package com.blog.common.entity.file.vo;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: lxk
 * @date: 2023/1/29 17:15
 * @description: 文件上传Vo
 * @modified By:
 */

@Data
public class UploadVo {

        /**
         * 上传文件数据
         */
        MultipartFile[] files;

        /**
         * 文件类型编码
         */
        Integer fileTypeCode;

        /**
         * 文件存放路径编码
         */
        Integer filePathCode;

        Integer year;
}
