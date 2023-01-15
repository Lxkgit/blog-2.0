/*
 Navicat Premium Data Transfer

 Source Server         : 本机MySQL
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : blog_content

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 13/01/2023 17:21:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '文章发布用户',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章标题',
  `content_md` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'markdown格式文章内容',
  `content_html` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'html格式文章内容',
  `article_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章类型（分类由大到小使用 , 隔开）',
  `article_label` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章标签（标签顺序不分先后 使用 , 隔开）',
  `article_status` tinyint(4) NULL DEFAULT NULL COMMENT '文章状态（2：置顶 1：发布 0：草稿）',
  `browse_count` int(11) NULL DEFAULT NULL COMMENT '文章浏览次数',
  `like_count` int(11) NULL DEFAULT NULL COMMENT '文章点赞次数',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '文章创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '文章最近更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 82 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (77, 2, '123123', '123123', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `article` VALUES (81, 1, '123123', '12312', NULL, '1', '3,2', 1, 0, 0, '2023-01-12 20:05:14', '2023-01-12 20:05:14');

-- ----------------------------
-- Table structure for article_label
-- ----------------------------
DROP TABLE IF EXISTS `article_label`;
CREATE TABLE `article_label`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `label_type` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '标签类型id',
  `label_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签名称',
  `article_num` tinyint(4) NULL DEFAULT NULL COMMENT '文章数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_label
-- ----------------------------
INSERT INTO `article_label` VALUES (1, 1, 1, '算法', 0);
INSERT INTO `article_label` VALUES (2, 1, 1, '多线程', 0);
INSERT INTO `article_label` VALUES (3, 1, 1, 'mybatis', 0);
INSERT INTO `article_label` VALUES (4, 1, 2, 'dfs', 0);

-- ----------------------------
-- Table structure for article_label_type
-- ----------------------------
DROP TABLE IF EXISTS `article_label_type`;
CREATE TABLE `article_label_type`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `type_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章标签分类名称',
  `label_num` tinyint(4) NULL DEFAULT NULL COMMENT '标签数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_label_type
-- ----------------------------
INSERT INTO `article_label_type` VALUES (1, 1, 'java', 0);
INSERT INTO `article_label_type` VALUES (2, 1, '算法', 0);
INSERT INTO `article_label_type` VALUES (3, 1, '888', 0);

-- ----------------------------
-- Table structure for article_type
-- ----------------------------
DROP TABLE IF EXISTS `article_type`;
CREATE TABLE `article_type`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '文章分类上级 根分级为0',
  `type_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `num` int(11) NULL DEFAULT NULL COMMENT '文章数量',
  `node` tinyint(2) NULL DEFAULT NULL COMMENT '是否有子节点 （0：无 1：有）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_type
-- ----------------------------
INSERT INTO `article_type` VALUES (1, 0, '算法', 0, 1);
INSERT INTO `article_type` VALUES (2, 1, '洛谷', 0, 1);
INSERT INTO `article_type` VALUES (3, 2, '排序', 0, 1);
INSERT INTO `article_type` VALUES (4, 0, 'Java', 0, 1);
INSERT INTO `article_type` VALUES (5, 4, '多线程', 0, 1);
INSERT INTO `article_type` VALUES (6, 4, '定时任务', 0, 1);
INSERT INTO `article_type` VALUES (7, 6, '自旋锁', 0, 1);

-- ----------------------------
-- Table structure for blog_diary
-- ----------------------------
DROP TABLE IF EXISTS `blog_diary`;
CREATE TABLE `blog_diary`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NULL DEFAULT NULL COMMENT '创建用户',
  `diary_md` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '日记md格式',
  `diary_date` date NULL DEFAULT NULL COMMENT '日记日期',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '最近更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 78 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_diary
-- ----------------------------
INSERT INTO `blog_diary` VALUES (76, 1, '1231222123asdaczxc', '2023-01-03', '2023-01-03 20:13:25', '2023-01-03 20:13:25');
INSERT INTO `blog_diary` VALUES (77, 1, '1今天的测试日记，稍微测试下保存时间间隔，我现在已经写完了一行日记，正在开始写第二行，日记已经自动保存了俩次，再看看第三次是什么时候自动保存，现在还没有自动保存，我再等等，稍微多写几行，但是还没有保存第三次，快一点啊，好了。', '2023-01-12', '2023-01-12 19:57:47', '2023-01-12 19:59:22');

-- ----------------------------
-- Table structure for doc_catalog
-- ----------------------------
DROP TABLE IF EXISTS `doc_catalog`;
CREATE TABLE `doc_catalog`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '创建者id',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父目录id',
  `doc_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文档名字',
  `doc_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文档类型（catalog：目录页 content：内容页）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of doc_catalog
-- ----------------------------
INSERT INTO `doc_catalog` VALUES (24, 1, 0, '25365', 'catalog', '2023-01-12 16:43:14', '2023-01-12 16:43:16');
INSERT INTO `doc_catalog` VALUES (25, 1, 0, '787', 'content', '2023-01-12 16:43:18', '2023-01-12 16:43:20');
INSERT INTO `doc_catalog` VALUES (26, 1, 24, '25365', 'content', '2023-01-12 16:43:24', '2023-01-12 16:43:27');
INSERT INTO `doc_catalog` VALUES (30, 1, 26, '26262626', 'content', '2023-01-12 16:43:30', '2023-01-12 16:43:32');
INSERT INTO `doc_catalog` VALUES (31, 2, 0, 'asdas', 'catalog', '2023-01-12 16:43:35', '2023-01-12 16:43:38');
INSERT INTO `doc_catalog` VALUES (32, 1, 24, 'a撒大苏打2', 'catalog', NULL, NULL);
INSERT INTO `doc_catalog` VALUES (33, 1, 32, '123123', 'catalog', NULL, NULL);
INSERT INTO `doc_catalog` VALUES (34, 1, 33, '222', 'content', NULL, NULL);
INSERT INTO `doc_catalog` VALUES (35, 1, NULL, '根', 'catalog', NULL, NULL);
INSERT INTO `doc_catalog` VALUES (36, 1, NULL, '根', 'catalog', NULL, NULL);
INSERT INTO `doc_catalog` VALUES (37, 1, 0, '庚', 'catalog', NULL, NULL);
INSERT INTO `doc_catalog` VALUES (38, 1, 0, '123', 'catalog', '2023-01-13 16:46:11', '2023-01-13 16:46:11');

-- ----------------------------
-- Table structure for doc_content
-- ----------------------------
DROP TABLE IF EXISTS `doc_content`;
CREATE TABLE `doc_content`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '创建者id',
  `catalog_id` int(11) NULL DEFAULT NULL COMMENT '目录id',
  `doc_content_md` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'md格式文档',
  `browse_count` int(11) NULL DEFAULT NULL COMMENT '文档浏览次数',
  `like_count` int(11) NULL DEFAULT NULL COMMENT '文档点赞次数',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '文档创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '文档最近修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of doc_content
-- ----------------------------
INSERT INTO `doc_content` VALUES (6, 1, 26, NULL, 0, 0, '2022-06-22 13:36:17', '2022-06-22 13:36:17');
INSERT INTO `doc_content` VALUES (7, 1, 25, NULL, 0, 0, '2022-06-22 13:37:42', '2022-06-22 13:37:42');
INSERT INTO `doc_content` VALUES (11, 1, 30, '232', 0, 0, '2022-06-22 15:43:52', '2022-06-22 15:43:52');
INSERT INTO `doc_content` VALUES (12, 1, 34, NULL, 0, 0, '2023-01-13 16:35:53', '2023-01-13 16:35:53');

SET FOREIGN_KEY_CHECKS = 1;
