package com.blog.common.entity.content.doc;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Author: lxk
 * @date 2022/6/20 9:55
 * @description: 文档目录
 */

@Data
public class DocCatalog {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer parentId;

    private String docName;

    private String docType;
}
