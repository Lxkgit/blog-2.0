package com.blog.content.service.impl;

import com.blog.common.entity.content.doc.DocCatalog;
import com.blog.common.entity.content.doc.DocContent;
import com.blog.common.entity.content.doc.enums.DocType;
import com.blog.common.entity.content.doc.vo.DocCatalogVo;
import com.blog.content.dao.DocCatalogDAO;
import com.blog.content.service.DocCatalogService;
import com.blog.content.service.DocContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lxk
 * @date 2022/6/20 9:47
 * @description: 文档服务接口
 */

@Service
public class DocCatalogServiceImpl implements DocCatalogService {

    @Resource
    private DocCatalogDAO docCatalogDAO;

    @Autowired
    private DocContentService docContentService;


    @Override
    public List<DocCatalog> selectDocCatalogList() {
        return docCatalogDAO.selectDocCatalogList();
    }

    @Override
    public List<DocCatalogVo> selectDocCatalogTree(Integer treeNode, Integer userId) {
        return docCatalogDAO.selectDocCatalogTree(treeNode, userId);
    }

    @Override
    public Map<String, Object> saveDoc(DocCatalog docCatalog) {
        Map<String, Object> map = new HashMap<>();
        DocContent docContent = new DocContent();
        docContent.setUserId(docCatalog.getUserId());

        docCatalog.setDocType(docCatalog.getDocType().toLowerCase());
        if (docCatalog.getDocType().equals(DocType.CATALOG.getDocType()) || docCatalog.getDocType().equals(DocType.CONTENT.getDocType())){
            int catalogFlag = docCatalogDAO.insert(docCatalog);
            if (catalogFlag==1) {
                map.put("catalog", "目录创建成功 ... ");
            } else {
                map.put("catalog", "目录创建失败 ... ");
            }
            if (docCatalog.getDocType().equals(DocType.CONTENT.getDocType())) {
                docContent.setCatalogId(docCatalog.getId());
                int contentFlag = docContentService.saveDocContent(docContent);
                if (contentFlag==1) {
                    map.put("content", "文档创建成功 ... ");
                } else {
                    map.put("content", "文档创建失败 ... ");
                }
            }
        } else {
            map.put("msg", "请输入正确的文档目录类型 ... ");
        }
        return map;
    }

    @Override
    public Map<String, Object> updateDocCatalog(DocCatalog docCatalog) {
        Map<String, Object> map = new HashMap<>();
        if (docCatalog.getDocType().equals(DocType.CATALOG.getDocType()) || docCatalog.getDocType().equals(DocType.CONTENT.getDocType())){
            int flag = docCatalogDAO.updateDocCatalog(docCatalog);
            if (flag == 1){
                map.put("catalog", "文档目录修改成功 ... ");
            } else {
                map.put("catalog", "文档目录修改失败 ... ");
            }

            if (docCatalog.getDocType().equals(DocType.CONTENT.getDocType())){
                DocContent docContentSelect = docContentService.selectDocContentByCatalogId(docCatalog.getId());
                if (docContentSelect == null){
                    DocContent docContent = new DocContent();
                    docContent.setCatalogId(docCatalog.getId());
                    docContent.setUserId(docCatalog.getUserId());
                    docContentService.saveDocContent(docContent);
                    map.put("content", "文档内容创建成功 ... ");
                }

            }
            if (docCatalog.getDocType().equals(DocType.CATALOG.getDocType())){
                DocContent docContentSelect = docContentService.selectDocContentByCatalogId(docCatalog.getId());
                if (docContentSelect != null) {
                    docContentService.deleteDocContentByCatalogId(docCatalog.getId(), docCatalog.getUserId());
                    map.put("content", "文档内容删除成功 ... ");
                }
            }
        } else {
            map.put("msg", "请输入正确的目录分类 ... ");
        }
        return map;
    }

    @Override
    public Map<String, Object> deleteDocCatalogByIds(String catalogIds, Integer userId) {
        Map<String, Object> map = new HashMap<>();
        String[] ids = catalogIds.split(",");
        int num = 0;
        for (String id : ids){
            if (docCatalogDAO.selectCountByParentId(id) == 0){
                num += docCatalogDAO.deleteDocCatalogById(id, userId);
                docContentService.deleteDocContentByCatalogId(Integer.parseInt(id), userId);
            }
        }
        map.put("delete", ids.length);
        map.put("success", num);
        if (ids.length != num) {
            map.put("msg", "请确认所选文档下是否有子目录");
        }
        return map;
    }

}
