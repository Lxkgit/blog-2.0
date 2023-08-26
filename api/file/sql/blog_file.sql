/*
 Navicat Premium Data Transfer

 Source Server         : 本机MySQL
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : blog_file

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 25/08/2023 16:31:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blog_data
-- ----------------------------
DROP TABLE IF EXISTS `blog_data`;
CREATE TABLE `blog_data`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `deployment_time` datetime(0) NULL DEFAULT NULL COMMENT '博客部署时间',
  `visits` int(11) NULL DEFAULT NULL COMMENT '接口访问总次数',
  `user_count` int(11) NULL DEFAULT NULL COMMENT '用户数量',
  `ip_count` int(11) NULL DEFAULT NULL COMMENT '访问ip数',
  `article_count` int(11) NULL DEFAULT NULL COMMENT '文章总数',
  `article_type_count` int(11) NULL DEFAULT NULL COMMENT '文章分类数',
  `article_label_count` int(11) NULL DEFAULT NULL COMMENT '文章标签数',
  `doc_count` int(11) NULL DEFAULT NULL COMMENT '文档总数',
  `doc_type_count` int(11) NULL DEFAULT NULL COMMENT '文档分类数',
  `diary_count` int(11) NULL DEFAULT NULL COMMENT '日记总数',
  `img_count` int(11) NULL DEFAULT NULL COMMENT '图片总数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_data
-- ----------------------------
INSERT INTO `blog_data` VALUES (1, '2023-07-01 11:54:51', 16481, 3, 4, 9, 2, 16, 1, 4, 3, 63);

-- ----------------------------
-- Table structure for blog_setting
-- ----------------------------
DROP TABLE IF EXISTS `blog_setting`;
CREATE TABLE `blog_setting`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `setting_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设置类型 0-0：全局设置-站点设置 0-1：全局设置-壁纸设置 1：个人设置 ',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '个人设置用户id',
  `type` tinyint(4) NULL DEFAULT NULL COMMENT '设置类型 0：分割线 1: 输入框 2：数字输入框 3：开关 4：长文本输入框 5：单图片设置 6：多图片设置',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设置名称',
  `setting` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'JSON格式设置信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '博客设置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_setting
-- ----------------------------
INSERT INTO `blog_setting` VALUES (1, '0-0', 1, 0, '信息展示', NULL);
INSERT INTO `blog_setting` VALUES (2, '0-0', 1, 1, '公告', '{\"id\":2,\"userId\":1,\"value\":\"最近没有公告\"}');
INSERT INTO `blog_setting` VALUES (3, '0-0', 1, 6, '轮播图', '{\"id\":3,\"num\":3,\"userId\":1,\"valueList\":[\"http://localhost/files/1/doc/img/2023-08-17_14-56-10_Gpfgwi_Snipaste_2023-08-17_14-56-02.jpg\",\"http://localhost/files/1/doc/img/2023-08-17_14-57-05_BMuMCk_Snipaste_2023-08-17_14-56-58.jpg\",\"http://localhost/files/1/doc/img/2023-08-18_16-21-05_RwqLIb_Snipaste_2023-08-17_14-56-02.jpg\"]}');
INSERT INTO `blog_setting` VALUES (5, '0-1', 1, 0, '背景壁纸', NULL);
INSERT INTO `blog_setting` VALUES (6, '0-1', 1, 5, '轮播图', '{\"id\":6,\"userId\":1,\"valueList\":[\"http://localhost/files/1/doc/img/2023-08-17_14-55-53_KooHLF_2023-08-15_09-10-36_PngxNK_image.png\"]}');
INSERT INTO `blog_setting` VALUES (7, '0-0', 1, 0, 'socket设置', NULL);
INSERT INTO `blog_setting` VALUES (8, '0-0', 1, 3, '全局socket', '{\"bool\":true,\"id\":8,\"userId\":1}');
INSERT INTO `blog_setting` VALUES (9, '0-0', 1, 3, '用户socket', '{\"bool\":true,\"id\":9,\"userId\":1}');

-- ----------------------------
-- Table structure for content_count
-- ----------------------------
DROP TABLE IF EXISTS `content_count`;
CREATE TABLE `content_count`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `doc_count` int(11) NULL DEFAULT 0 COMMENT '文档数量',
  `article_count` int(11) NULL DEFAULT 0 COMMENT '文章数量',
  `diary_count` int(11) NULL DEFAULT 0 COMMENT '日记数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 594 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '博客用户内容数量统计' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of content_count
-- ----------------------------
INSERT INTO `content_count` VALUES (593, 1, 1, 9, 3);

-- ----------------------------
-- Table structure for file_data
-- ----------------------------
DROP TABLE IF EXISTS `file_data`;
CREATE TABLE `file_data`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件/目录名称',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件/目录全路径（不包含当前目录/文件）',
  `type` tinyint(4) NULL DEFAULT NULL COMMENT '文件类型 0：目录 1：图片 2：其它文件',
  `file_size` bigint(20) NULL DEFAULT NULL COMMENT '文件大小（kb）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 152 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for upload_file
-- ----------------------------
DROP TABLE IF EXISTS `upload_file`;
CREATE TABLE `upload_file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '上传用户id',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `file_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件链接',
  `upload_time` datetime(0) NULL DEFAULT NULL COMMENT '上传时间',
  `file_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件类型',
  `file_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件路径',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 157 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for upload_log
-- ----------------------------
DROP TABLE IF EXISTS `upload_log`;
CREATE TABLE `upload_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '上传用户id',
  `file_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上传文件名字',
  `file_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件类型',
  `upload_state` tinyint(4) NULL DEFAULT NULL COMMENT '文件上传状态 0：待处理 1：上传成功 2：上传失败',
  `upload_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  `upload_time` datetime(0) NULL DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1289 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
