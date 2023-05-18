package com.blog.content.service;

import com.blog.common.entity.content.doc.DocCatalog;
import com.blog.common.entity.content.doc.DocContent;
import com.blog.common.entity.content.doc.vo.DocCatalogVo;
import com.blog.common.util.MyPage;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface DocService {

    List<DocCatalogVo> selectDocCatalogTree(DocCatalogVo docCatalogVo);
    DocContent selectDocContentById(Integer catalogId);



    List<DocCatalogVo> selectCatalogForDoc(Integer parentId);
//    MyPage<DocCatalogVo> selectDocCatalogTree(DocCatalogVo docCatalogVo);
    List<DocCatalogVo>  selectDocCatalogListById(DocCatalogVo docCatalogVo);

    Map<String, Object> saveDoc(DocCatalog docCatalog);
    Map<String, Object> updateDocCatalog(DocCatalog docCatalog);
    Map<String, Object> deleteDocCatalogByIds(String ids, Integer userId);

}
