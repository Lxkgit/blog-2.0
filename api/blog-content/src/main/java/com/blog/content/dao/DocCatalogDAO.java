package com.blog.content.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.content.doc.DocCatalog;
import com.blog.common.entity.content.doc.vo.DocCatalogVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DocCatalogDAO extends BaseMapper<DocCatalog> {

    List<DocCatalog> selectDocCatalogList();
    List<DocCatalogVo> selectDocCatalogTree(@Param("parentId") Integer treeNode);
    int updateDocCatalog(DocCatalog docCatalog);
    int deleteDocCatalogByIds(@Param("ids") String[] ids, @Param("userId") Integer userId);
}