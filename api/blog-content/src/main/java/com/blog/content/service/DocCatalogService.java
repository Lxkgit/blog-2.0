package com.blog.content.service;

import com.blog.common.entity.content.doc.DocCatalog;
import com.blog.common.entity.content.doc.vo.DocCatalogVo;
import com.blog.common.util.MyPage;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface DocCatalogService {

    MyPage<DocCatalogVo>  selectDocCatalogListByPage(DocCatalogVo docCatalogVo);
    List<DocCatalogVo>  selectDocCatalogListById(Integer id);
    List<DocCatalogVo> selectDocCatalogTree(Integer treeNode, Integer userId);
    Map<String, Object> saveDoc(DocCatalog docCatalog);
    Map<String, Object> updateDocCatalog(DocCatalog docCatalog);
    Map<String, Object> deleteDocCatalogByIds(String ids, Integer userId);

}
