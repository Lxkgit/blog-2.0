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
    private Date uploadTime;
}
