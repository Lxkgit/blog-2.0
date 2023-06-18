package com.blog.common.entity.user.vo;

import com.blog.common.entity.user.BlogUser;
import lombok.Getter;
import lombok.Setter;

/**
 * @description:
 * @Author: 308501
 * @date 2023/6/16 15:04
 */

@Getter
@Setter
public class BlogUserVo extends BlogUser {

    private static final long serialVersionUID = 693209553706963957L;
    private String code;
}
