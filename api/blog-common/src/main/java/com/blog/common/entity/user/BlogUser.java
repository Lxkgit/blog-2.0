package com.blog.common.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: lxk
 * @date 2022/6/6 14:55
 * @description:
 */
@Data
public class BlogUser implements Serializable {

    private static final long serialVersionUID = -1202327391177030056L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String headImg;
    private String email;

    /**
     * 状态
     */
    private Boolean enabled;
    private Date createTime;
    private Date updateTime;

}
