package com.blog.content.service.impl;


import com.blog.common.entity.content.diary.Diary;
import com.blog.common.entity.content.doc.DocContent;
import com.blog.content.dao.DiaryDAO;
import com.blog.content.dao.DocContentDAO;
import com.blog.content.service.DiaryService;
import com.blog.content.service.DocContentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @Author: lxk
 * @date 2022/6/21 16:40
 * @description: 文档文本内容服务
 */

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

    @Override
    public int deleteDocContentById(String ids, Integer userId) {
        String[] idArr = ids.split(",");

        return docContentDAO.deleteDocContentByCatalogIds(idArr, userId);
    }

    @Override
    public int deleteDocContentByCatalogId(Integer parentId, Integer userId) {
        return docContentDAO.deleteDocContentByCatalogId(parentId, userId);
    }
}
