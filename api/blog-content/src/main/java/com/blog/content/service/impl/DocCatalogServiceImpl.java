package com.blog.content.service.impl;

import com.blog.common.entity.content.doc.DocCatalog;
import com.blog.common.entity.content.doc.enums.DocType;
import com.blog.common.entity.content.doc.vo.DocCatalogVo;
import com.blog.content.dao.DocCatalogDAO;
import com.blog.content.service.DocCatalogService;
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


    @Override
    public List<DocCatalog> selectDocCatalogList() {
        return docCatalogDAO.selectDocCatalogList();
    }

    @Override
    public List<DocCatalogVo> selectDocCatalogTree(Integer treeNode, Integer userId) {
        return docCatalogDAO.selectDocCatalogTree(treeNode, userId);
    }

    @Override
    public int saveDocCatalog(DocCatalog docCatalog) {
        docCatalog.setDocType(docCatalog.getDocType().toLowerCase());
        if (docCatalog.getDocType().equals(DocType.CATALOG.getDocType()) || docCatalog.getDocType().equals(DocType.CONTENT.getDocType())){
            return docCatalogDAO.insert(docCatalog);
        }
        return 0;
    }

    @Override
    public int updateDocCatalog(DocCatalog docCatalog) {
        return docCatalogDAO.updateDocCatalog(docCatalog);
    }

    @Override
    public Map<String, Object> deleteDocCatalogByIds(String catalogIds, Integer userId) {
        Map<String, Object> map = new HashMap<>();
        String[] ids = catalogIds.split(",");
        int num = 0;
        for (String id : ids){
            if (docCatalogDAO.selectCountByParentId(id) == 0){
                num += docCatalogDAO.deleteDocCatalogById(id, userId);
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
