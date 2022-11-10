package com.blog.common.entity.gateway;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author: lxk
 * @date 2022/7/20 17:22
 * @description: 黑名单ip列表
 */

@Data
@TableName("blacklist_ip")
public class BlacklistIp {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String ip;
    private Integer userId;
    private String msg;
    private Date createTime;

    public BlacklistIp() {
    }

    public BlacklistIp(String ip, int userId, String msg, Date createTime) {
        this.ip = ip;
        this.userId = userId;
        this.msg = msg;
        this.createTime = createTime;
    }
}
