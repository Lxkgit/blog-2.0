package com.blog.common.entity.file;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @Author: lxk
 * @date 2022/7/7 16:10
 * @description: 文件上传记录表
 */

@Data
public class UploadLog {

    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    private int userId;
    private String fileName;
    private String fileType;
    private int uploadState;
    private String uploadMsg;
    private Date uploadTime;

    public UploadLog(int userId, String fileName, String fileType, int uploadState, String uploadMsg, Date uploadTime) {
        this.userId = userId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.uploadState = uploadState;
        this.uploadMsg = uploadMsg;
        this.uploadTime = uploadTime;
    }

    public UploadLog(int id, int userId, int uploadState, String uploadMsg) {
        this.id = id;
        this.userId = userId;
        this.uploadState = uploadState;
        this.uploadMsg = uploadMsg;
    }
}
