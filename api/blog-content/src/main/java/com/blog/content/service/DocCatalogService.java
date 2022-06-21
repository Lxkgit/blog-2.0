package com.blog.content.service;

import com.blog.common.entity.content.doc.DocCatalog;
import com.blog.common.entity.content.doc.vo.DocCatalogVo;

import java.util.List;
import java.util.Map;

public interface DocCatalogService {

    List<DocCatalog> selectDocCatalogList();
    List<DocCatalogVo> selectDocCatalogTree(Integer treeNode, Integer userId);
    int saveDocCatalog(DocCatalog docCatalog);
    int updateDocCatalog(DocCatalog docCatalog);
    Map<String, Object> deleteDocCatalogByIds(String ids, Integer userId);

}
