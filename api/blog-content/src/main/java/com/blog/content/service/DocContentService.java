package com.blog.content.service;

import com.blog.common.entity.content.doc.DocContent;

/**
 * @Author: lxk
 * @date 2022/6/21 16:39
 * @description:
 */

public interface DocContentService {

    DocContent selectDocContentByCatalogId(Integer id);
    int saveDocContent(DocContent docContent);
    int updateDocContent(DocContent docContent);
    int deleteDocContentById(String ids, Integer userId);
    int deleteDocContentByCatalogId(Integer parentId, Integer userId);
}
