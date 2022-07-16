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
public class UploadImg {

    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    private int userId;
    private String imgName;
    private String imgUrl;
    private Date uploadTime;

    public UploadImg(int userId, String imgName, String imgUrl, Date uploadTime) {
        this.userId = userId;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
        this.uploadTime = uploadTime;
    }
}
