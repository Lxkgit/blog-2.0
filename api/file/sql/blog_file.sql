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

 Date: 11/01/2024 17:39:49
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
INSERT INTO `blog_data` VALUES (1, NULL, 736, 3, 1, 0, 0, 0, 0, 0, 0, 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '博客设置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_setting
-- ----------------------------
INSERT INTO `blog_setting` VALUES (1, '0-0', 1, 0, '信息展示', NULL);
INSERT INTO `blog_setting` VALUES (2, '0-0', 1, 1, '公告', '{\"id\":2,\"userId\":1,\"value\":\"最近没有公告99999999111\"}');
INSERT INTO `blog_setting` VALUES (3, '0-0', 1, 6, '轮播图', '{\"id\":3,\"num\":2,\"userId\":1,\"valueList\":[\"http://127.0.0.1/files/1/doc/img/2023-08-28_16-26-28_dCOQlW_Snipaste_2023-08-28_16-25-29.jpg\",\"http://127.0.0.1/files/1/doc/img/2023-08-28_16-26-35_ourYOY_Snipaste_2023-08-26_14-54-11.jpg\"]}');
INSERT INTO `blog_setting` VALUES (5, '0-1', 1, 0, '背景壁纸', NULL);
INSERT INTO `blog_setting` VALUES (6, '0-1', 1, 5, '轮播图', '{\"id\":6,\"userId\":1,\"valueList\":[\"http://127.0.0.1/files/1/doc/img/2023-08-28_16-27-21_ZXypgB_Snipaste_2023-08-26_14-54-11.jpg\"]}');
INSERT INTO `blog_setting` VALUES (7, '0-0', 1, 0, 'socket设置', NULL);
INSERT INTO `blog_setting` VALUES (8, '0-0', 1, 1, '服务器ip(用于socket连接)', '{\"id\":8,\"userId\":1,\"value\":\"127.0.0.1\"}');
INSERT INTO `blog_setting` VALUES (9, '0-0', 1, 3, '全局socket', '{\"bool\":false,\"id\":9,\"userId\":1}');
INSERT INTO `blog_setting` VALUES (10, '0-0', 1, 3, '用户socket', '{\"bool\":false,\"id\":10,\"userId\":1}');

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '博客用户内容数量统计' ROW_FORMAT = Dynamic;

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
  `dir_type` tinyint(4) NULL DEFAULT 0 COMMENT '目录类型 0:本地目录 1:同步目录',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '同步文件位置状态 0:远程服务器 1:本地服务器 2:正在同步远程服务器 3:正在同步本地服务器',
  `file_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '同步文件唯一编码',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '文件创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件云盘功能数据库' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for file_sync
-- ----------------------------
DROP TABLE IF EXISTS `file_sync`;
CREATE TABLE `file_sync`  (
  `id` int(11) NOT NULL,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '文件所属用户',
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件存放本地目录',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `file_sn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件唯一标识码',
  `file_client` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件当前存放服务器编码（mqtt客户端编码 ClientId ）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件同步数据库' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
