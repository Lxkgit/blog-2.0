package com.blog.file.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.file.FileData;

/**
 * @description: 文件服务DAO
 * @Author: lxk
 * @date 2023/8/3 19:58
 */

public interface FileDataDAO extends BaseMapper<FileData> {

    /**
     * 查询系统中全部文章（删除状态的除外）
     * @return
     */
    Integer selectImgCount();
}
