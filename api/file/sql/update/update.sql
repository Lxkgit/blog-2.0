ALTER TABLE article CHANGE content_html content_img VARCHAR(255) DEFAULT NULL COMMENT '文章预览图';
ALTER TABLE article ADD COLUMN `content_memo` tinytext DEFAULT NULL COMMENT '文章描述';

ALTER TABLE blog_diary ADD COLUMN `diary_status` tinyint(4) DEFAULT 1 COMMENT '日记状态 0:草稿 1:发布 2: 3: 删除';
ALTER TABLE doc_content ADD COLUMN `doc_status` tinyint(4) DEFAULT 1 COMMENT '文档状态 0:草稿 1:发布 2: 3: 删除';
ALTER TABLE doc_catalog ADD COLUMN `doc_status` tinyint(4) DEFAULT 1 COMMENT '文档状态 0:草稿 1:发布 2: 3: 删除';

ALTER TABLE file_data ADD COLUMN `dir_type` tinyint(4) DEFAULT 0 COMMENT '目录/文件类型 0:本地目录 1:同步目录';
ALTER TABLE file_data ADD COLUMN `status` tinyint(4) DEFAULT 1 COMMENT '文件/目录状态 0:不存在 1:存在（同步目录专用）';

CREATE TABLE `file_sync` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT '文件所属用户',
  `file_path` varchar(255) DEFAULT NULL COMMENT '文件存放本地目录',
  `file_name` varchar(255) DEFAULT NULL COMMENT '文件名称',
  `file_sn` varchar(255) DEFAULT NULL COMMENT '文件唯一标识码',
  `file_client` varchar(255) DEFAULT NULL COMMENT '文件当前存放服务器编码（mqtt客户端编码 ClientId ）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件同步数据库';