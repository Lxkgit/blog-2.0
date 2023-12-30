package com.blog.content.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.content.doc.DocContent;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: lxk
 * @date: 2022/6/21 22:38
 * @description:
 * @modified By:
 */
public interface DocContentDAO extends BaseMapper<DocContent> {

    /**
     * 查询文档总数
     * @return
     */
    Integer selectDocContentCount();

    /**
     * 以用户id分组查询用户文章数（删除状态的除外）
     * @return
     */
    List<Map<String, Integer>> selectDocCountGroupByUserId();
}
