package com.blog.common.entity.user;

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

    private static final long serialVersionUID = 8809205744130731028L;

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
