package com.blog.content.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.content.doc.DocCatalog;
import com.blog.common.entity.content.doc.vo.DocCatalogVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DocCatalogDAO extends BaseMapper<DocCatalog> {

    List<DocCatalogVo> selectListByDocTypeAndUserId(@Param("docLevelList") List<Integer> docLevelList, @Param("userId") Integer userId, @Param("docType") Integer docType);
    List<Integer> selectDocUserList();

}