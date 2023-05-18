package com.blog.content.service;

import com.blog.common.entity.content.doc.DocCatalog;
import com.blog.common.entity.content.doc.DocContent;
import com.blog.common.entity.content.doc.vo.DocCatalogVo;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.util.MyPage;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface DocService {

    Integer insertDonCatalog(BlogUser blogUser, DocCatalog docCatalog);
    Integer deleteDocCatalog(BlogUser blogUser, Integer id);
    Integer updateDocContent(BlogUser blogUser, DocContent docContent);
    Integer updateDocCatalog(BlogUser blogUser, DocCatalog docCatalog);
    List<DocCatalogVo> selectDocCatalogTree(DocCatalogVo docCatalogVo);
    DocContent selectDocContentById(Integer catalogId);

}
