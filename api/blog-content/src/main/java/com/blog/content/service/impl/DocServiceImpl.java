package com.blog.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.blog.common.constant.Constant;
import com.blog.common.entity.content.doc.DocCatalog;
import com.blog.common.entity.content.doc.DocContent;
import com.blog.common.entity.content.doc.enums.DocType;
import com.blog.common.entity.content.doc.vo.DocCatalogVo;
import com.blog.common.entity.file.ContentCount;
import com.blog.common.entity.user.BlogUser;
import com.blog.content.dao.DocCatalogDAO;
import com.blog.content.dao.DocContentDAO;
import com.blog.content.feign.UserClient;
import com.blog.content.mq.MQProducerService;
import com.blog.content.mq.send.SendSystemData;
import com.blog.content.mq.send.SendUserData;
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

    @Resource
    private UserClient userClient;

    @Resource
    private SendUserData sendUserData;

    @Resource
    private SendSystemData sendSystemData;

    /**
     * 创建文档目录组织 组织类型为目录则只创建目录、组织类型为文档则创建对应文档、文档状态为草稿
     *
     * @param blogUser
     * @param docCatalog
     * @return
     */
    @Override
    public Integer insertDocCatalog(BlogUser blogUser, DocCatalog docCatalog) {
        docCatalog.setUserId(blogUser.getId());
        docCatalog.setUpdateTime(new Date());
        docCatalog.setCreateTime(new Date());
        docCatalogDAO.insert(docCatalog);
        if (docCatalog.getDocType().equals(DocType.CONTENT.getId())) {
            DocContent docContent = new DocContent();
            docContent.setCatalogId(docCatalog.getId());
            docContent.setCreateTime(new Date());
            docContent.setUserId(blogUser.getId());
            docContent.setDocStatus(Constant.DRAFT);
            docContent.setDocContentMd("");
            docContentDAO.insert(docContent);

            // 发送博客用户新增文档mq消息
            sendUserData.sendUserData(SendUserData.doc, blogUser.getId(), 1);
            // 发送博客系统新增文档mq消息
            sendSystemData.sendSystemData(SendSystemData.doc, 1);
        }
        return docCatalog.getId();
    }

    /**
     * 删除文档（修改状态）
     *
     * @param blogUser
     * @param id
     * @return
     */
    @Override
    public Integer deleteDocCatalog(BlogUser blogUser, Integer id) {
        QueryWrapper<DocCatalog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        queryWrapper.ne("doc_status", Constant.DELETE);
        List<DocCatalog> catalogList = docCatalogDAO.selectList(queryWrapper);
        if (catalogList != null && catalogList.size() > 0) {
            return 0;
        }
        DocCatalog docCatalog = docCatalogDAO.selectById(id);

        // 修改文档目录状态为删除
        DocCatalog catalog = new DocCatalog();
        catalog.setId(id);
        catalog.setDocStatus(Constant.DELETE);
        docCatalogDAO.updateById(catalog);

        if (docCatalog.getDocType().equals(DocType.CONTENT.getId())) {
            // 修改文档状态为删除
            UpdateWrapper<DocContent> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("catalog_id", id);
            DocContent docContent = new DocContent();
            docContent.setDocStatus(Constant.DELETE);
            docContentDAO.update(docContent, updateWrapper);

            // 发送博客用户删除文档mq消息
            sendUserData.sendUserData(SendUserData.doc, blogUser.getId(), -1);
            // 发送博客系统删除文档mq消息
            sendSystemData.sendSystemData(SendSystemData.doc, -1);
        }
        return id;
    }

    /**
     * 修改文档目录
     *
     * @param blogUser
     * @param docCatalog
     * @return
     */
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
    public List<DocCatalogVo> selectDocCatalogTree(BlogUser blogUser, DocCatalogVo docCatalogVo) {
        Integer lowerLimit = docCatalogVo.getTypeLowerLimit();
        Integer upperLimit = docCatalogVo.getTypeUpperLimit();
        List<Integer> docLevelList = new ArrayList<>();
        for (int i = lowerLimit; i <= upperLimit; i++) {
            docLevelList.add(i);
        }
        if (docCatalogVo.getType() == 0) {
            docCatalogVo.setUserId(docCatalogVo.getUserId());
        } else if (docCatalogVo.getType() == 1) {
            if (blogUser != null) {
                docCatalogVo.setUserId(blogUser.getId());
            }
        }
        List<DocCatalogVo> docCatalogVoList = docCatalogDAO.selectListByDocTypeAndUserId(docLevelList, docCatalogVo.getUserId(), docCatalogVo.getDocType());
        if (docCatalogVoList != null) {
            for (DocCatalogVo vo : docCatalogVoList) {
                vo.setValue(vo.getId());
                vo.setLabel(vo.getDocName());
                if (!vo.getDocLevel().equals(lowerLimit)) {
                    docCatalogVoList.forEach(docCatalogVo1 -> {
                        if (docCatalogVo1.getId().equals(vo.getParentId())) {
                            if (docCatalogVo1.getChildren() == null) {
                                docCatalogVo1.setChildren(new ArrayList<>());
                            }
                            docCatalogVo1.getChildren().add(vo);
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

    /**
     * 查询指定文档
     *
     * @param catalogId
     * @return
     */
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

    /**
     * 查询指定文档目录
     *
     * @param catalogId
     * @return
     */
    @Override
    public DocCatalog selectDocCatalogById(Integer catalogId) {
        return docCatalogDAO.selectById(catalogId);
    }

    /**
     * 展示文档最多的九位用户
     *
     * @return
     */
    @Override
    public List<BlogUser> selectDocUserList() {
        List<Integer> userIdList = docCatalogDAO.selectDocUserList();
        List<BlogUser> blogUserList = new ArrayList<>();
        BlogUser blogUser = new BlogUser();
        blogUser.setId(0);
        blogUser.setUsername("全部用户");
        blogUserList.add(blogUser);
        Map<Integer, BlogUser> userMap = new HashMap<>();
        for (Integer userId : userIdList) {
            if (!userMap.containsKey(userId)) {
                userMap.put(userId, userClient.selectUserById(userId));
            }
            blogUserList.add(userMap.get(userId));
        }
        return blogUserList;
    }
}
