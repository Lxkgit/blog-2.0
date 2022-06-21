package com.blog.content.service.impl;


import com.blog.common.entity.content.doc.DocContent;
import com.blog.content.dao.DocContentDAO;
import com.blog.content.service.DocContentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: lxk
 * @date 2022/6/21 16:40
 * @description: 文档文本内容服务
 */

@Service
public class DocContentServiceImpl implements DocContentService {

    @Resource
    private DocContentDAO docContentDAO;

    @Override
    public DocContent selectDocContentByCatalogId(Integer id) {
        return docContentDAO.selectDocContentByCatalogId(id);
    }

    @Override
    public int saveDocContent(DocContent docContent) {
        Date date = new Date();
        docContent.setUpdateTime(date);
        docContent.setCreateTime(date);
        return docContentDAO.insert(docContent);
    }

    @Override
    public int updateDocContent(DocContent docContent) {
        docContent.setUpdateTime(new Date());
        return docContentDAO.updateDocContent(docContent);
    }
}
