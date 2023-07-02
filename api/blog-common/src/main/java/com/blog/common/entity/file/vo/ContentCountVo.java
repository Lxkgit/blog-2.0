package com.blog.common.entity.file.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.blog.common.entity.file.ContentCount;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @description: 博客用户内容数量统计表
 * @Author: 308501
 * @date 2023/6/28 17:23
 */

@Getter
@Setter
public class ContentCountVo extends ContentCount {

    /**
     * mq消息类型 0：全量 1：增量
     */
    private Integer mqMsgType;


}
