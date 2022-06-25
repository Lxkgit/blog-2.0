package com.blog.common.entity.user.vo;

import com.blog.common.entity.user.SysPermission;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author: lxk
 * @date 2022/6/24 19:12
 * @description:
 */

@Getter
@Setter
public class SysPermissionVo extends SysPermission implements Comparable<SysPermissionVo>{

    List<SysPermissionVo> list;

    @Override
    public int compareTo(SysPermissionVo o) {
        return this.getId()-o.getId();
        //所有比较最底层的逻辑都是发生两两比较逻辑的,返回比较结果
        //只关心结果结果三种:
        //正数:   this.age - o.age    >
        //负数:   this.age - o.age    <
        //0       this   ==
        //return this.age-o.age; 升序排序
        //return o.age-this.age; 降序排序

    }
}
