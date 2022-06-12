package com.blog.common.entity.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: lxk
 * @date 2022/6/6 14:57
 * @description:
 */

@Data
public class SysRole implements Serializable {
    private static final long serialVersionUID = 6043137253225659914L;

    private Integer id;
    private String code;
    private String name;
}
