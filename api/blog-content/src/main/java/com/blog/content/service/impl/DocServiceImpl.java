package com.blog.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.common.entity.content.doc.DocCatalog;
import com.blog.common.entity.content.doc.DocContent;
import com.blog.common.entity.content.doc.enums.DocType;
import com.blog.common.entity.content.doc.vo.DocCatalogVo;
import com.blog.common.entity.user.BlogUser;
import com.blog.content.dao.DocCatalogDAO;
import com.blog.content.dao.DocContentDAO;
import com.blog.content.service.DocService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: lxk
 * @date 2022/6/20 9:47
 * @description: 文档服务接口
 */

@Slf4j
@Service
public class DocServiceImpl implements DocService {

    @Resource
    private DocCatalogDAO docCatalogDAO;

    @Resource
    private DocContentDAO docContentDAO;

    @Override
    public Integer insertDonCatalog(BlogUser blogUser, DocCatalog docCatalog) {
        docCatalog.setUserId(blogUser.getId());
        docCatalog.setUpdateTime(new Date());
        docCatalog.setCreateTime(new Date());
        docCatalogDAO.insert(docCatalog);
        if (docCatalog.getDocType().equals(DocType.CONTENT.getId())) {
            DocContent docContent = new DocContent();
            docContent.setCatalogId(docCatalog.getId());
            docContent.setCreateTime(new Date());
            docContent.setUserId(blogUser.getId());
            docContent.setDocContentMd("");
            docContentDAO.insert(docContent);
        }
        return docCatalog.getId();
    }

    @Override
    public Integer deleteDocCatalog(BlogUser blogUser, Integer id) {
        QueryWrapper<DocCatalog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        List<DocCatalog> catalogList = docCatalogDAO.selectList(queryWrapper);
        if (catalogList != null && catalogList.size() > 0) {
            return 0;
        }
        DocCatalog docCatalog = docCatalogDAO.selectById(id);
        if (docCatalog.getDocType().equals(DocType.CATALOG.getId())) {
            docCatalogDAO.deleteById(id);
        } else {
            docCatalogDAO.deleteById(id);
            QueryWrapper<DocContent> catalogQueryWrapper = new QueryWrapper<>();
            catalogQueryWrapper.eq("catalog_id", id);
            docContentDAO.delete(catalogQueryWrapper);
        }
        return id;
    }

    @Override
    public Integer updateDocCatalog(BlogUser blogUser, DocCatalog docCatalog) {
        DocCatalog catalog = docCatalogDAO.selectById(docCatalog.getId());
        if (catalog.getUserId().equals(blogUser.getId())) {
            docCatalog.setUpdateTime(new Date());
            return docCatalogDAO.updateById(docCatalog);
        }
        return 0;
    }

    @Override
    public Integer updateDocContent(BlogUser blogUser, DocContent docContent) {
        DocContent content = docContentDAO.selectById(docContent.getId());
        if (content.getUserId().equals(blogUser.getId())) {
            docContent.setUpdateTime(new Date());
            return docContentDAO.updateById(docContent);
        }
        return 0;
    }


    /**
     * 查询文档目录树
     *
     * @param docCatalogVo
     * @return
     */
    @Override
    public List<DocCatalogVo> selectDocCatalogTree(DocCatalogVo docCatalogVo) {
        Integer lowerLimit = docCatalogVo.getTypeLowerLimit();
        Integer upperLimit = docCatalogVo.getTypeUpperLimit();
        List<Integer> docLevelList = new ArrayList<>();
        for (int i = lowerLimit; i <= upperLimit; i++) {
            docLevelList.add(i);
        }

        List<DocCatalogVo> docCatalogVoList = docCatalogDAO.selectListByDocTypeAndUserId(docLevelList, docCatalogVo.getUserId(), docCatalogVo.getDocType());
        if (docCatalogVoList != null) {
            for (DocCatalogVo vo : docCatalogVoList) {
                if (!vo.getDocLevel().equals(lowerLimit)) {
                    docCatalogVoList.forEach(docCatalogVo1 -> {
                        if (docCatalogVo1.getId().equals(vo.getParentId())) {
                            if (docCatalogVo1.getList() == null) {
                                docCatalogVo1.setList(new ArrayList<>());
                            }
                            docCatalogVo1.getList().add(vo);
                        }
                    });
                }
            }
            docCatalogVoList.removeIf(docCatalogVo1 -> !docCatalogVo1.getDocLevel().equals(lowerLimit));
            if (docCatalogVo.getParentId() != null && !docCatalogVo.getParentId().equals(0)) {
                docCatalogVoList.removeIf(docCatalogVo1 -> !docCatalogVo1.getParentId().equals(docCatalogVo.getParentId()));
            }
        }
        return docCatalogVoList;
    }

    @Override
    public DocContent selectDocContentById(Integer catalogId) {
        QueryWrapper<DocContent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("catalog_id", catalogId);
        List<DocContent> docContentList = docContentDAO.selectList(queryWrapper);
        if (docContentList != null && docContentList.size() > 0) {
            return docContentList.get(0);
        }
        return null;
    }

}
