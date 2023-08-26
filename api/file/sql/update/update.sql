ALTER TABLE article CHANGE content_html content_img VARCHAR(255) DEFAULT NULL COMMENT '文章预览图';
ALTER TABLE article ADD COLUMN `content_memo` tinytext DEFAULT NULL COMMENT '文章描述';