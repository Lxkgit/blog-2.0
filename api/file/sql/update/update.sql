ALTER TABLE article CHANGE content_html content_img VARCHAR(255) DEFAULT NULL COMMENT '文章预览图';
ALTER TABLE article ADD COLUMN `content_memo` tinytext DEFAULT NULL COMMENT '文章描述';

ALTER TABLE blog_diary ADD COLUMN `diary_status` tinyint(4) DEFAULT 1 COMMENT '日记状态 0:草稿 1:发布 2: 3: 删除';
ALTER TABLE doc_content ADD COLUMN `doc_status` tinyint(4) DEFAULT 1 COMMENT '文档状态 0:草稿 1:发布 2: 3: 删除';
ALTER TABLE doc_catalog ADD COLUMN `doc_status` tinyint(4) DEFAULT 1 COMMENT '文档状态 0:草稿 1:发布 2: 3: 删除';
