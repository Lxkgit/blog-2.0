package com.blog.common.entity.file;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @Author: lxk
 * @date 2022/7/15 16:33
 * @description: 上传图片类
 */

@Data
public class UploadFile {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private String fileName;

    private String fileUrl;

    private Date uploadTime;

    private String fileType;

    private String filePath;

    public UploadFile(Integer userId, String fileName, String fileUrl, Date uploadTime, String fileType, String filePath) {
        this.userId = userId;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.uploadTime = uploadTime;
        this.fileType = fileType;
        this.filePath = filePath;
    }
}
