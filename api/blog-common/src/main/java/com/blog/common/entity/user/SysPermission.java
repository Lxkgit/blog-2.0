package com.blog.common.entity.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: lxk
 * @date 2022/6/6 17:08
 * @description:
 */

@Data
public class SysPermission implements Serializable {

    private static final long serialVersionUID = -3075133684027009758L;
    private Integer id;
    private Integer parentId;
    private String menuName;
    private String menuIcon;
    private String menuPath;
    private String permission;
    private String component;
    private String menuType;
}
