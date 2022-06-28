package com.blog.common.entity.user.vo;

import com.blog.common.entity.user.SysRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author: lxk
 * @date 2022/6/27 19:24
 * @description: 角色Vo类
 */

@Getter
@Setter
public class SysRoleVo extends SysRole {

    List<Integer> perIds;

}
