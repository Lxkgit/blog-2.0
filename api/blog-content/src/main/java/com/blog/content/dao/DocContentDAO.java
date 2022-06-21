package com.blog.content.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.content.doc.DocContent;
import org.apache.ibatis.annotations.Param;

/**
 * @author: lxk
 * @date: 2022/6/21 22:38
 * @description:
 * @modified By:
 */
public interface DocContentDAO extends BaseMapper<DocContent> {

    DocContent selectDocContentByCatalogId(@Param("id") Integer id);
    int updateDocContent(DocContent docContent);
}
