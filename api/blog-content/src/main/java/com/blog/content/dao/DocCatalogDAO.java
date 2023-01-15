package com.blog.content.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.content.doc.DocCatalog;
import com.blog.common.entity.content.doc.vo.DocCatalogVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DocCatalogDAO extends BaseMapper<DocCatalog> {

    int selectCountByParentId(@Param("id") String id);
    List<DocCatalogVo> selectListByParentId(DocCatalogVo docCatalogVo);
    List<DocCatalog> selectDocCatalogList(DocCatalogVo docCatalogVo);
    List<DocCatalogVo> selectDocCatalogTree(@Param("parentId") Integer treeNode, @Param("userId") Integer userId);
    int updateDocCatalog(DocCatalog docCatalog);
    int deleteDocCatalogById(@Param("id") String id, @Param("userId") Integer userId);

}