package com.blog.common.entity.gateway;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author: lxk
 * @date 2022/7/20 15:28
 * @description: 接口请求记录表
 */

@Data
@TableName("request_log")
public class RequestLog {

    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    private int userId;
    private String urlPath;
    private String requestIp;
    private Date createTime;

    public RequestLog() {
    }

    public RequestLog(int userId, String urlPath, String userIp, Date createTime) {
        this.userId = userId;
        this.urlPath = urlPath;
        this.requestIp = userIp;
        this.createTime = createTime;
    }
}
