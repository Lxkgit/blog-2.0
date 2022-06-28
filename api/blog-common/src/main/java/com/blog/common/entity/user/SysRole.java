package com.blog.common.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: lxk
 * @date 2022/6/6 14:57
 * @description:
 */

@Data
public class SysRole implements Serializable {

    private static final long serialVersionUID = -2065620443741793238L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String roleCode;
    private String roleName;
}
