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

 Date: 02/08/2023 18:01:12
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
INSERT INTO `blog_data` VALUES (1, '2023-07-14 11:33:36', 6400, 3, 5, 2, 1, 5, 1, 2, 1, 94);

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
INSERT INTO `blog_setting` VALUES (1, '0-0', NULL, 0, '信息展示', NULL);
INSERT INTO `blog_setting` VALUES (2, '0-0', 1, 1, '公告', '{\"id\":2,\"userId\":1,\"value\":\"很长很长的网站公告------------------很长很长的网站公告------------------很长很长的网站公告------------------\"}');
INSERT INTO `blog_setting` VALUES (3, '0-0', 1, 6, '轮播图', '{\"id\":3,\"num\":3,\"userId\":1,\"valueList\":[\"http://localhost/files/doc/img/2023-07-27_19-06-22_LLMJoB_Snipaste_2023-07-26_20-08-46.jpg\",\"http://localhost/files/doc/img/2023-08-01_20-03-37_WehrqV_2023-05-11_16-40-49_TjXVgH_Snipaste_2023-05-11_15-51-44.jpg\",\"http://localhost/files/doc/img/2023-08-02_15-53-34_YqCiiM_Snipaste_2023-08-02_15-43-39.jpg\"]}');
INSERT INTO `blog_setting` VALUES (5, '0-1', 1, 0, '背景壁纸', NULL);
INSERT INTO `blog_setting` VALUES (6, '0-1', 1, 5, '轮播图', '{\"id\":6,\"userId\":1,\"valueList\":[]}');

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
) ENGINE = InnoDB AUTO_INCREMENT = 572 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '博客用户内容数量统计' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of content_count
-- ----------------------------
INSERT INTO `content_count` VALUES (571, 1, 1, 2, 1);

-- ----------------------------
-- Table structure for file_data
-- ----------------------------
DROP TABLE IF EXISTS `file_data`;
CREATE TABLE `file_data`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件/目录名称',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件/目录全路径',
  `type` tinyint(4) NULL DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `fileSize` bigint(20) NULL DEFAULT NULL COMMENT '文件大小（kb）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 95 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of upload_file
-- ----------------------------
INSERT INTO `upload_file` VALUES (1, 1, '2023-07-11_19-53-37_kqbGQc_image.png', 'http://localhost/files/article/img/2023-07-11_19-53-37_kqbGQc_image.png', '2023-07-11 19:53:38', 'png', 'D:/files/article/img');
INSERT INTO `upload_file` VALUES (2, 1, '2023-07-11_19-55-01_fcjeCK_image.png', 'http://localhost/files/article/img/2023-07-11_19-55-01_fcjeCK_image.png', '2023-07-11 19:55:02', 'png', 'D:/files/article/img');
INSERT INTO `upload_file` VALUES (3, 1, '2023-07-26_16-18-42_ogJPGA_Snipaste_2023-07-26_16-05-52.jpg', 'http://localhost/files/doc/img/2023-07-26_16-18-42_ogJPGA_Snipaste_2023-07-26_16-05-52.jpg', '2023-07-26 16:18:43', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (4, 1, '2023-07-26_16-21-40_DGGhWW_Snipaste_2023-07-26_16-05-52.jpg', 'http://localhost/files/doc/img/2023-07-26_16-21-40_DGGhWW_Snipaste_2023-07-26_16-05-52.jpg', '2023-07-26 16:21:40', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (5, 1, '2023-07-26_16-22-56_afybEC_Snipaste_2023-07-26_16-05-52.jpg', 'http://localhost/files/doc/img/2023-07-26_16-22-56_afybEC_Snipaste_2023-07-26_16-05-52.jpg', '2023-07-26 16:22:57', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (6, 1, '2023-07-26_16-23-22_VYswoP_Snipaste_2023-07-26_16-05-52.jpg', 'http://localhost/files/doc/img/2023-07-26_16-23-22_VYswoP_Snipaste_2023-07-26_16-05-52.jpg', '2023-07-26 16:23:23', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (7, 1, '2023-07-26_16-23-50_XYpqEF_Snipaste_2023-07-26_16-05-52.jpg', 'http://localhost/files/doc/img/2023-07-26_16-23-50_XYpqEF_Snipaste_2023-07-26_16-05-52.jpg', '2023-07-26 16:23:50', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (8, 1, '2023-07-26_16-24-56_rUjHSV_Snipaste_2023-07-26_16-05-52.jpg', 'http://localhost/files/doc/img/2023-07-26_16-24-56_rUjHSV_Snipaste_2023-07-26_16-05-52.jpg', '2023-07-26 16:24:56', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (9, 1, '2023-07-26_16-28-43_rZtrez_Snipaste_2023-07-26_16-05-52.jpg', 'http://localhost/files/doc/img/2023-07-26_16-28-43_rZtrez_Snipaste_2023-07-26_16-05-52.jpg', '2023-07-26 16:28:43', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (10, 1, '2023-07-26_16-30-57_ZaUfJT_Snipaste_2023-07-26_16-05-52.jpg', 'http://localhost/files/doc/img/2023-07-26_16-30-57_ZaUfJT_Snipaste_2023-07-26_16-05-52.jpg', '2023-07-26 16:30:57', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (11, 1, '2023-07-26_16-43-47_YKoqlb_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_16-43-47_YKoqlb_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 16:43:47', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (12, 1, '2023-07-26_16-46-24_gIvTus_Snipaste_2023-07-26_16-05-52.jpg', 'http://localhost/files/doc/img/2023-07-26_16-46-24_gIvTus_Snipaste_2023-07-26_16-05-52.jpg', '2023-07-26 16:46:24', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (13, 1, '2023-07-26_16-48-34_XUdMyc_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_16-48-34_XUdMyc_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 16:48:34', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (14, 1, '2023-07-26_16-49-01_TfRQDs_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_16-49-01_TfRQDs_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 16:49:02', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (15, 1, '2023-07-26_16-49-45_lNAeeD_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_16-49-45_lNAeeD_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 16:49:46', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (16, 1, '2023-07-26_16-51-11_hOcwiw_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_16-51-11_hOcwiw_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 16:51:12', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (17, 1, '2023-07-26_16-54-05_fodtuN_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_16-54-05_fodtuN_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 16:54:06', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (18, 1, '2023-07-26_17-17-29_oDvMDl_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_17-17-29_oDvMDl_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 17:17:29', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (19, 1, '2023-07-26_17-20-30_JZnGRz_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_17-20-30_JZnGRz_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 17:20:31', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (20, 1, '2023-07-26_17-24-02_akWFuP_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_17-24-02_akWFuP_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 17:24:02', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (21, 1, '2023-07-26_17-25-27_ZNbETP_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_17-25-27_ZNbETP_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 17:25:28', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (22, 1, '2023-07-26_17-26-14_GmDGzq_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_17-26-14_GmDGzq_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 17:26:15', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (23, 1, '2023-07-26_17-26-57_eSMOcC_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_17-26-57_eSMOcC_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 17:26:57', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (24, 1, '2023-07-26_17-28-53_knEhVL_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_17-28-53_knEhVL_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 17:28:54', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (25, 1, '2023-07-26_19-20-59_xsnkxZ_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_19-20-59_xsnkxZ_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 19:20:59', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (26, 1, '2023-07-26_19-21-58_UGjLhm_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_19-21-58_UGjLhm_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 19:21:58', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (27, 1, '2023-07-26_19-22-15_WkZOTU_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_19-22-15_WkZOTU_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 19:22:16', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (28, 1, '2023-07-26_19-24-01_qFbWKn_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_19-24-01_qFbWKn_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 19:24:01', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (29, 1, '2023-07-26_19-33-17_MgVcEl_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_19-33-17_MgVcEl_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 19:33:17', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (30, 1, '2023-07-26_19-34-01_hOjrLn_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_19-34-01_hOjrLn_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 19:34:01', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (31, 1, '2023-07-26_19-35-22_HmngaN_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_19-35-22_HmngaN_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 19:35:22', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (32, 1, '2023-07-26_19-38-09_Hfwhfh_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_19-38-09_Hfwhfh_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 19:38:10', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (33, 1, '2023-07-26_19-38-40_BJCVJn_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_19-38-40_BJCVJn_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 19:38:41', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (34, 1, '2023-07-26_19-39-11_qyjHfy_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_19-39-11_qyjHfy_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 19:39:11', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (35, 1, '2023-07-26_19-39-57_oqotyj_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_19-39-57_oqotyj_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 19:39:57', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (36, 1, '2023-07-26_19-40-29_heasRf_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_19-40-29_heasRf_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 19:40:30', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (37, 1, '2023-07-26_19-40-43_CCUzoZ_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_19-40-43_CCUzoZ_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 19:40:43', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (38, 1, '2023-07-26_19-41-15_IwxHou_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_19-41-15_IwxHou_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 19:41:15', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (39, 1, '2023-07-26_19-41-53_fUUMkN_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_19-41-53_fUUMkN_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 19:41:54', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (40, 1, '2023-07-26_19-44-42_fcRqaY_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_19-44-42_fcRqaY_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 19:44:43', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (41, 1, '2023-07-26_19-45-07_BhCqRz_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_19-45-07_BhCqRz_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 19:45:08', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (42, 1, '2023-07-26_19-46-43_MJTWYA_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_19-46-43_MJTWYA_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 19:46:43', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (43, 1, '2023-07-26_19-47-45_OWljMe_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_19-47-45_OWljMe_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 19:47:45', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (44, 1, '2023-07-26_20-00-01_sgdKlH_Snipaste_2023-07-26_16-05-52.jpg', 'http://localhost/files/doc/img/2023-07-26_20-00-01_sgdKlH_Snipaste_2023-07-26_16-05-52.jpg', '2023-07-26 20:00:01', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (45, 1, '2023-07-26_20-00-26_ttBPDU_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_20-00-26_ttBPDU_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 20:00:27', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (46, 1, '2023-07-26_20-01-42_ezzqTc_Snipaste_2023-07-26_16-05-52.jpg', 'http://localhost/files/doc/img/2023-07-26_20-01-42_ezzqTc_Snipaste_2023-07-26_16-05-52.jpg', '2023-07-26 20:01:43', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (47, 1, '2023-07-26_20-01-57_JCrniR_Snipaste_2023-07-26_16-05-52.jpg', 'http://localhost/files/doc/img/2023-07-26_20-01-57_JCrniR_Snipaste_2023-07-26_16-05-52.jpg', '2023-07-26 20:01:58', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (48, 1, '2023-07-26_20-02-04_nFlYuM_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_20-02-04_nFlYuM_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 20:02:05', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (49, 1, '2023-07-26_20-02-11_CbMuZq_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_20-02-11_CbMuZq_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 20:02:11', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (50, 1, '2023-07-26_20-03-23_MWNdbS_Snipaste_2023-07-26_16-05-52.jpg', 'http://localhost/files/doc/img/2023-07-26_20-03-23_MWNdbS_Snipaste_2023-07-26_16-05-52.jpg', '2023-07-26 20:03:23', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (51, 1, '2023-07-26_20-04-53_kIVyDR_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_20-04-53_kIVyDR_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 20:04:54', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (52, 1, '2023-07-26_20-05-00_LcqCvN_Snipaste_2023-07-26_16-05-52.jpg', 'http://localhost/files/doc/img/2023-07-26_20-05-00_LcqCvN_Snipaste_2023-07-26_16-05-52.jpg', '2023-07-26 20:05:00', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (53, 1, '2023-07-26_20-07-44_zfAmlE_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_20-07-44_zfAmlE_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 20:07:45', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (54, 1, '2023-07-26_20-07-51_lViqhV_Snipaste_2023-07-26_16-43-37.jpg', 'http://localhost/files/doc/img/2023-07-26_20-07-51_lViqhV_Snipaste_2023-07-26_16-43-37.jpg', '2023-07-26 20:07:51', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (55, 1, '2023-07-26_20-09-19_LbRSWm_Snipaste_2023-07-26_20-08-22.jpg', 'http://localhost/files/doc/img/2023-07-26_20-09-19_LbRSWm_Snipaste_2023-07-26_20-08-22.jpg', '2023-07-26 20:09:19', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (56, 1, '2023-07-26_20-09-25_iiHkey_Snipaste_2023-07-26_20-08-46.jpg', 'http://localhost/files/doc/img/2023-07-26_20-09-25_iiHkey_Snipaste_2023-07-26_20-08-46.jpg', '2023-07-26 20:09:25', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (57, 1, '2023-07-26_20-09-31_NvbiBB_Snipaste_2023-07-26_20-08-56.jpg', 'http://localhost/files/doc/img/2023-07-26_20-09-31_NvbiBB_Snipaste_2023-07-26_20-08-56.jpg', '2023-07-26 20:09:31', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (58, 1, '2023-07-26_20-09-37_Qrhkut_Snipaste_2023-07-26_20-09-05.jpg', 'http://localhost/files/doc/img/2023-07-26_20-09-37_Qrhkut_Snipaste_2023-07-26_20-09-05.jpg', '2023-07-26 20:09:37', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (59, 1, '2023-07-26_20-10-18_JZiUKp_Snipaste_2023-07-26_20-08-46.jpg', 'http://localhost/files/doc/img/2023-07-26_20-10-18_JZiUKp_Snipaste_2023-07-26_20-08-46.jpg', '2023-07-26 20:10:19', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (60, 1, '2023-07-26_20-10-23_PABoPA_Snipaste_2023-07-26_20-08-56.jpg', 'http://localhost/files/doc/img/2023-07-26_20-10-23_PABoPA_Snipaste_2023-07-26_20-08-56.jpg', '2023-07-26 20:10:24', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (61, 1, '2023-07-26_20-10-29_JyknCg_Snipaste_2023-07-26_20-09-05.jpg', 'http://localhost/files/doc/img/2023-07-26_20-10-29_JyknCg_Snipaste_2023-07-26_20-09-05.jpg', '2023-07-26 20:10:29', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (62, 1, '2023-07-26_20-11-29_iXafqB_Snipaste_2023-07-26_20-08-46.jpg', 'http://localhost/files/doc/img/2023-07-26_20-11-29_iXafqB_Snipaste_2023-07-26_20-08-46.jpg', '2023-07-26 20:11:29', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (63, 1, '2023-07-26_20-11-34_gORfTL_Snipaste_2023-07-26_20-08-56.jpg', 'http://localhost/files/doc/img/2023-07-26_20-11-34_gORfTL_Snipaste_2023-07-26_20-08-56.jpg', '2023-07-26 20:11:34', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (64, 1, '2023-07-26_20-11-39_IxCDKT_Snipaste_2023-07-26_20-09-05.jpg', 'http://localhost/files/doc/img/2023-07-26_20-11-39_IxCDKT_Snipaste_2023-07-26_20-09-05.jpg', '2023-07-26 20:11:39', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (65, 1, '2023-07-26_20-13-12_bwSUAT_Snipaste_2023-07-26_20-08-46.jpg', 'http://localhost/files/doc/img/2023-07-26_20-13-12_bwSUAT_Snipaste_2023-07-26_20-08-46.jpg', '2023-07-26 20:13:12', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (66, 1, '2023-07-26_20-13-17_idJIdg_Snipaste_2023-07-26_20-08-56.jpg', 'http://localhost/files/doc/img/2023-07-26_20-13-17_idJIdg_Snipaste_2023-07-26_20-08-56.jpg', '2023-07-26 20:13:17', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (67, 1, '2023-07-26_20-13-22_gVonnz_Snipaste_2023-07-26_20-09-05.jpg', 'http://localhost/files/doc/img/2023-07-26_20-13-22_gVonnz_Snipaste_2023-07-26_20-09-05.jpg', '2023-07-26 20:13:23', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (68, 1, '2023-07-26_20-15-59_yhGeoA_Snipaste_2023-07-26_20-08-46.jpg', 'http://localhost/files/doc/img/2023-07-26_20-15-59_yhGeoA_Snipaste_2023-07-26_20-08-46.jpg', '2023-07-26 20:16:00', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (69, 1, '2023-07-26_20-16-05_OPLfzd_Snipaste_2023-07-26_20-08-56.jpg', 'http://localhost/files/doc/img/2023-07-26_20-16-05_OPLfzd_Snipaste_2023-07-26_20-08-56.jpg', '2023-07-26 20:16:05', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (70, 1, '2023-07-26_20-16-10_VtpqtJ_Snipaste_2023-07-26_20-09-05.jpg', 'http://localhost/files/doc/img/2023-07-26_20-16-10_VtpqtJ_Snipaste_2023-07-26_20-09-05.jpg', '2023-07-26 20:16:10', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (71, 1, '2023-07-26_20-17-30_bCFETh_Snipaste_2023-07-26_20-08-22.jpg', 'http://localhost/files/doc/img/2023-07-26_20-17-30_bCFETh_Snipaste_2023-07-26_20-08-22.jpg', '2023-07-26 20:17:31', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (72, 1, '2023-07-26_20-17-36_KZpCJV_Snipaste_2023-07-26_20-08-46.jpg', 'http://localhost/files/doc/img/2023-07-26_20-17-36_KZpCJV_Snipaste_2023-07-26_20-08-46.jpg', '2023-07-26 20:17:36', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (73, 1, '2023-07-26_20-17-41_CnkgaQ_Snipaste_2023-07-26_20-08-56.jpg', 'http://localhost/files/doc/img/2023-07-26_20-17-41_CnkgaQ_Snipaste_2023-07-26_20-08-56.jpg', '2023-07-26 20:17:41', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (74, 1, '2023-07-26_20-17-47_SHYKfC_Snipaste_2023-07-26_20-09-05.jpg', 'http://localhost/files/doc/img/2023-07-26_20-17-47_SHYKfC_Snipaste_2023-07-26_20-09-05.jpg', '2023-07-26 20:17:47', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (75, 1, '2023-07-27_19-06-14_MZWFBH_Snipaste_2023-07-26_20-08-22.jpg', 'http://localhost/files/doc/img/2023-07-27_19-06-14_MZWFBH_Snipaste_2023-07-26_20-08-22.jpg', '2023-07-27 19:06:15', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (76, 1, '2023-07-27_19-06-22_LLMJoB_Snipaste_2023-07-26_20-08-46.jpg', 'http://localhost/files/doc/img/2023-07-27_19-06-22_LLMJoB_Snipaste_2023-07-26_20-08-46.jpg', '2023-07-27 19:06:22', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (77, 1, '2023-07-27_19-06-28_VHHSUp_Snipaste_2023-07-26_20-08-56.jpg', 'http://localhost/files/doc/img/2023-07-27_19-06-28_VHHSUp_Snipaste_2023-07-26_20-08-56.jpg', '2023-07-27 19:06:29', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (78, 1, '2023-07-27_19-06-36_mpkFHP_Snipaste_2023-07-26_20-09-05.jpg', 'http://localhost/files/doc/img/2023-07-27_19-06-36_mpkFHP_Snipaste_2023-07-26_20-09-05.jpg', '2023-07-27 19:06:36', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (79, 1, '2023-07-27_19-09-43_nZysAF_Snipaste_2023-07-26_20-08-22.jpg', 'http://localhost/files/doc/img/2023-07-27_19-09-43_nZysAF_Snipaste_2023-07-26_20-08-22.jpg', '2023-07-27 19:09:44', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (80, 1, '2023-07-27_19-09-49_CbJJSv_Snipaste_2023-07-26_20-09-05.jpg', 'http://localhost/files/doc/img/2023-07-27_19-09-49_CbJJSv_Snipaste_2023-07-26_20-09-05.jpg', '2023-07-27 19:09:50', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (81, 1, '2023-07-27_19-10-00_suPcoD_Snipaste_2023-07-26_20-08-56.jpg', 'http://localhost/files/doc/img/2023-07-27_19-10-00_suPcoD_Snipaste_2023-07-26_20-08-56.jpg', '2023-07-27 19:10:00', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (82, 1, '2023-07-27_19-10-19_VYlysy_Snipaste_2023-07-26_20-09-05.jpg', 'http://localhost/files/doc/img/2023-07-27_19-10-19_VYlysy_Snipaste_2023-07-26_20-09-05.jpg', '2023-07-27 19:10:19', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (83, 1, '2023-08-01_20-03-37_WehrqV_2023-05-11_16-40-49_TjXVgH_Snipaste_2023-05-11_15-51-44.jpg', 'http://localhost/files/doc/img/2023-08-01_20-03-37_WehrqV_2023-05-11_16-40-49_TjXVgH_Snipaste_2023-05-11_15-51-44.jpg', '2023-08-01 20:03:37', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (84, 1, '2023-08-02_15-52-40_IZuLcI_Snipaste_2023-08-02_15-43-39.jpg', 'http://localhost/files/doc/img/2023-08-02_15-52-40_IZuLcI_Snipaste_2023-08-02_15-43-39.jpg', '2023-08-02 15:52:41', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (85, 1, '2023-08-02_15-52-51_SYWpLS_Snipaste_2023-08-02_15-43-39.jpg', 'http://localhost/files/doc/img/2023-08-02_15-52-51_SYWpLS_Snipaste_2023-08-02_15-43-39.jpg', '2023-08-02 15:52:51', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (86, 1, '2023-08-02_15-53-34_YqCiiM_Snipaste_2023-08-02_15-43-39.jpg', 'http://localhost/files/doc/img/2023-08-02_15-53-34_YqCiiM_Snipaste_2023-08-02_15-43-39.jpg', '2023-08-02 15:53:34', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (87, 1, '2023-08-02_15-54-44_XZuZcx_Snipaste_2023-08-02_15-43-39.jpg', 'http://localhost/files/user/img/1/测试路径/test/2023-08-02_15-54-44_XZuZcx_Snipaste_2023-08-02_15-43-39.jpg', '2023-08-02 15:54:44', 'jpg', 'D:/files/user/img/1/测试路径/test');
INSERT INTO `upload_file` VALUES (88, 1, '2023-08-02_15-57-14_uTbvdP_Snipaste_2023-08-02_15-43-39.jpg', 'http://localhost/files/user/1/测试路径/test/2023-08-02_15-57-14_uTbvdP_Snipaste_2023-08-02_15-43-39.jpg', '2023-08-02 15:57:15', 'jpg', 'D:/files/user/1/测试路径/test');
INSERT INTO `upload_file` VALUES (89, 1, '2023-08-02_15-57-28_sTAwfZ_Snipaste_2023-08-02_15-43-39.jpg', 'http://localhost/files/user/1/测试路径/test---/2023-08-02_15-57-28_sTAwfZ_Snipaste_2023-08-02_15-43-39.jpg', '2023-08-02 15:57:28', 'jpg', 'D:/files/user/1/测试路径/test---');
INSERT INTO `upload_file` VALUES (90, 1, '2023-08-02_15-59-09_UiuOgf_Snipaste_2023-08-02_15-43-39.jpg', 'http://localhost/files/user/1/测试路径/test1/2023-08-02_15-59-09_UiuOgf_Snipaste_2023-08-02_15-43-39.jpg', '2023-08-02 15:59:10', 'jpg', 'D:/files/user/1/测试路径/test1');
INSERT INTO `upload_file` VALUES (91, 1, '2023-08-02_16-00-24_QgQyhc_Snipaste_2023-08-02_15-43-39.jpg', 'http://localhost/files/doc/img/2023-08-02_16-00-24_QgQyhc_Snipaste_2023-08-02_15-43-39.jpg', '2023-08-02 16:00:24', 'jpg', 'D:/files/doc/img');
INSERT INTO `upload_file` VALUES (92, 1, '2023-08-02_16-00-30_rvjZxH_Snipaste_2023-08-02_15-43-39.jpg', 'http://localhost/files/article/img/2023-08-02_16-00-30_rvjZxH_Snipaste_2023-08-02_15-43-39.jpg', '2023-08-02 16:00:31', 'jpg', 'D:/files/article/img');
INSERT INTO `upload_file` VALUES (93, 1, '2023-08-02_16-00-35_QrcGRJ_Snipaste_2023-08-02_15-43-39.jpg', 'http://localhost/files/article/file/2023-08-02_16-00-35_QrcGRJ_Snipaste_2023-08-02_15-43-39.jpg', '2023-08-02 16:00:36', 'jpg', 'D:/files/article/file');
INSERT INTO `upload_file` VALUES (94, 1, '2023-08-02_16-00-39_WpqbyK_Snipaste_2023-08-02_15-43-39.jpg', 'http://localhost/files/article/img/2023-08-02_16-00-39_WpqbyK_Snipaste_2023-08-02_15-43-39.jpg', '2023-08-02 16:00:40', 'jpg', 'D:/files/article/img');

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
) ENGINE = InnoDB AUTO_INCREMENT = 95 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of upload_log
-- ----------------------------
INSERT INTO `upload_log` VALUES (1, 1, '2023-07-11_19-53-37_kqbGQc_image.png', 'png', 1, '文件上传成功', '2023-07-11 19:53:38');
INSERT INTO `upload_log` VALUES (2, 1, '2023-07-11_19-55-01_fcjeCK_image.png', 'png', 1, '文件上传成功', '2023-07-11 19:55:02');
INSERT INTO `upload_log` VALUES (3, 1, '2023-07-26_16-18-42_ogJPGA_Snipaste_2023-07-26_16-05-52.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 16:18:43');
INSERT INTO `upload_log` VALUES (4, 1, '2023-07-26_16-21-40_DGGhWW_Snipaste_2023-07-26_16-05-52.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 16:21:40');
INSERT INTO `upload_log` VALUES (5, 1, '2023-07-26_16-22-56_afybEC_Snipaste_2023-07-26_16-05-52.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 16:22:57');
INSERT INTO `upload_log` VALUES (6, 1, '2023-07-26_16-23-22_VYswoP_Snipaste_2023-07-26_16-05-52.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 16:23:23');
INSERT INTO `upload_log` VALUES (7, 1, '2023-07-26_16-23-50_XYpqEF_Snipaste_2023-07-26_16-05-52.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 16:23:50');
INSERT INTO `upload_log` VALUES (8, 1, '2023-07-26_16-24-56_rUjHSV_Snipaste_2023-07-26_16-05-52.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 16:24:56');
INSERT INTO `upload_log` VALUES (9, 1, '2023-07-26_16-28-43_rZtrez_Snipaste_2023-07-26_16-05-52.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 16:28:43');
INSERT INTO `upload_log` VALUES (10, 1, '2023-07-26_16-30-57_ZaUfJT_Snipaste_2023-07-26_16-05-52.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 16:30:57');
INSERT INTO `upload_log` VALUES (11, 1, '2023-07-26_16-43-47_YKoqlb_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 16:43:47');
INSERT INTO `upload_log` VALUES (12, 1, '2023-07-26_16-46-24_gIvTus_Snipaste_2023-07-26_16-05-52.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 16:46:24');
INSERT INTO `upload_log` VALUES (13, 1, '2023-07-26_16-48-34_XUdMyc_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 16:48:34');
INSERT INTO `upload_log` VALUES (14, 1, '2023-07-26_16-49-01_TfRQDs_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 16:49:02');
INSERT INTO `upload_log` VALUES (15, 1, '2023-07-26_16-49-45_lNAeeD_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 16:49:46');
INSERT INTO `upload_log` VALUES (16, 1, '2023-07-26_16-51-11_hOcwiw_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 16:51:12');
INSERT INTO `upload_log` VALUES (17, 1, '2023-07-26_16-54-05_fodtuN_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 16:54:06');
INSERT INTO `upload_log` VALUES (18, 1, '2023-07-26_17-17-29_oDvMDl_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 17:17:29');
INSERT INTO `upload_log` VALUES (19, 1, '2023-07-26_17-20-30_JZnGRz_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 17:20:31');
INSERT INTO `upload_log` VALUES (20, 1, '2023-07-26_17-24-02_akWFuP_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 17:24:02');
INSERT INTO `upload_log` VALUES (21, 1, '2023-07-26_17-25-27_ZNbETP_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 17:25:28');
INSERT INTO `upload_log` VALUES (22, 1, '2023-07-26_17-26-14_GmDGzq_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 17:26:15');
INSERT INTO `upload_log` VALUES (23, 1, '2023-07-26_17-26-57_eSMOcC_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 17:26:57');
INSERT INTO `upload_log` VALUES (24, 1, '2023-07-26_17-28-53_knEhVL_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 17:28:54');
INSERT INTO `upload_log` VALUES (25, 1, '2023-07-26_19-20-59_xsnkxZ_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 19:20:59');
INSERT INTO `upload_log` VALUES (26, 1, '2023-07-26_19-21-58_UGjLhm_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 19:21:58');
INSERT INTO `upload_log` VALUES (27, 1, '2023-07-26_19-22-15_WkZOTU_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 19:22:16');
INSERT INTO `upload_log` VALUES (28, 1, '2023-07-26_19-24-01_qFbWKn_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 19:24:01');
INSERT INTO `upload_log` VALUES (29, 1, '2023-07-26_19-33-17_MgVcEl_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 19:33:17');
INSERT INTO `upload_log` VALUES (30, 1, '2023-07-26_19-34-01_hOjrLn_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 19:34:01');
INSERT INTO `upload_log` VALUES (31, 1, '2023-07-26_19-35-22_HmngaN_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 19:35:22');
INSERT INTO `upload_log` VALUES (32, 1, '2023-07-26_19-38-09_Hfwhfh_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 19:38:10');
INSERT INTO `upload_log` VALUES (33, 1, '2023-07-26_19-38-40_BJCVJn_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 19:38:41');
INSERT INTO `upload_log` VALUES (34, 1, '2023-07-26_19-39-11_qyjHfy_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 19:39:11');
INSERT INTO `upload_log` VALUES (35, 1, '2023-07-26_19-39-57_oqotyj_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 19:39:57');
INSERT INTO `upload_log` VALUES (36, 1, '2023-07-26_19-40-29_heasRf_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 19:40:30');
INSERT INTO `upload_log` VALUES (37, 1, '2023-07-26_19-40-43_CCUzoZ_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 19:40:43');
INSERT INTO `upload_log` VALUES (38, 1, '2023-07-26_19-41-15_IwxHou_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 19:41:15');
INSERT INTO `upload_log` VALUES (39, 1, '2023-07-26_19-41-53_fUUMkN_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 19:41:54');
INSERT INTO `upload_log` VALUES (40, 1, '2023-07-26_19-44-42_fcRqaY_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 19:44:43');
INSERT INTO `upload_log` VALUES (41, 1, '2023-07-26_19-45-07_BhCqRz_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 19:45:08');
INSERT INTO `upload_log` VALUES (42, 1, '2023-07-26_19-46-43_MJTWYA_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 19:46:43');
INSERT INTO `upload_log` VALUES (43, 1, '2023-07-26_19-47-45_OWljMe_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 19:47:45');
INSERT INTO `upload_log` VALUES (44, 1, '2023-07-26_20-00-01_sgdKlH_Snipaste_2023-07-26_16-05-52.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:00:01');
INSERT INTO `upload_log` VALUES (45, 1, '2023-07-26_20-00-26_ttBPDU_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:00:27');
INSERT INTO `upload_log` VALUES (46, 1, '2023-07-26_20-01-42_ezzqTc_Snipaste_2023-07-26_16-05-52.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:01:43');
INSERT INTO `upload_log` VALUES (47, 1, '2023-07-26_20-01-57_JCrniR_Snipaste_2023-07-26_16-05-52.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:01:58');
INSERT INTO `upload_log` VALUES (48, 1, '2023-07-26_20-02-04_nFlYuM_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:02:05');
INSERT INTO `upload_log` VALUES (49, 1, '2023-07-26_20-02-11_CbMuZq_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:02:11');
INSERT INTO `upload_log` VALUES (50, 1, '2023-07-26_20-03-23_MWNdbS_Snipaste_2023-07-26_16-05-52.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:03:23');
INSERT INTO `upload_log` VALUES (51, 1, '2023-07-26_20-04-53_kIVyDR_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:04:54');
INSERT INTO `upload_log` VALUES (52, 1, '2023-07-26_20-05-00_LcqCvN_Snipaste_2023-07-26_16-05-52.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:05:00');
INSERT INTO `upload_log` VALUES (53, 1, '2023-07-26_20-07-44_zfAmlE_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:07:45');
INSERT INTO `upload_log` VALUES (54, 1, '2023-07-26_20-07-51_lViqhV_Snipaste_2023-07-26_16-43-37.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:07:51');
INSERT INTO `upload_log` VALUES (55, 1, '2023-07-26_20-09-19_LbRSWm_Snipaste_2023-07-26_20-08-22.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:09:19');
INSERT INTO `upload_log` VALUES (56, 1, '2023-07-26_20-09-25_iiHkey_Snipaste_2023-07-26_20-08-46.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:09:25');
INSERT INTO `upload_log` VALUES (57, 1, '2023-07-26_20-09-31_NvbiBB_Snipaste_2023-07-26_20-08-56.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:09:31');
INSERT INTO `upload_log` VALUES (58, 1, '2023-07-26_20-09-37_Qrhkut_Snipaste_2023-07-26_20-09-05.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:09:37');
INSERT INTO `upload_log` VALUES (59, 1, '2023-07-26_20-10-18_JZiUKp_Snipaste_2023-07-26_20-08-46.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:10:19');
INSERT INTO `upload_log` VALUES (60, 1, '2023-07-26_20-10-23_PABoPA_Snipaste_2023-07-26_20-08-56.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:10:24');
INSERT INTO `upload_log` VALUES (61, 1, '2023-07-26_20-10-29_JyknCg_Snipaste_2023-07-26_20-09-05.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:10:29');
INSERT INTO `upload_log` VALUES (62, 1, '2023-07-26_20-11-29_iXafqB_Snipaste_2023-07-26_20-08-46.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:11:29');
INSERT INTO `upload_log` VALUES (63, 1, '2023-07-26_20-11-34_gORfTL_Snipaste_2023-07-26_20-08-56.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:11:34');
INSERT INTO `upload_log` VALUES (64, 1, '2023-07-26_20-11-39_IxCDKT_Snipaste_2023-07-26_20-09-05.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:11:39');
INSERT INTO `upload_log` VALUES (65, 1, '2023-07-26_20-13-12_bwSUAT_Snipaste_2023-07-26_20-08-46.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:13:12');
INSERT INTO `upload_log` VALUES (66, 1, '2023-07-26_20-13-17_idJIdg_Snipaste_2023-07-26_20-08-56.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:13:17');
INSERT INTO `upload_log` VALUES (67, 1, '2023-07-26_20-13-22_gVonnz_Snipaste_2023-07-26_20-09-05.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:13:23');
INSERT INTO `upload_log` VALUES (68, 1, '2023-07-26_20-15-59_yhGeoA_Snipaste_2023-07-26_20-08-46.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:16:00');
INSERT INTO `upload_log` VALUES (69, 1, '2023-07-26_20-16-05_OPLfzd_Snipaste_2023-07-26_20-08-56.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:16:05');
INSERT INTO `upload_log` VALUES (70, 1, '2023-07-26_20-16-10_VtpqtJ_Snipaste_2023-07-26_20-09-05.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:16:10');
INSERT INTO `upload_log` VALUES (71, 1, '2023-07-26_20-17-30_bCFETh_Snipaste_2023-07-26_20-08-22.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:17:31');
INSERT INTO `upload_log` VALUES (72, 1, '2023-07-26_20-17-36_KZpCJV_Snipaste_2023-07-26_20-08-46.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:17:36');
INSERT INTO `upload_log` VALUES (73, 1, '2023-07-26_20-17-41_CnkgaQ_Snipaste_2023-07-26_20-08-56.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:17:41');
INSERT INTO `upload_log` VALUES (74, 1, '2023-07-26_20-17-47_SHYKfC_Snipaste_2023-07-26_20-09-05.jpg', 'jpg', 1, '文件上传成功', '2023-07-26 20:17:47');
INSERT INTO `upload_log` VALUES (75, 1, '2023-07-27_19-06-14_MZWFBH_Snipaste_2023-07-26_20-08-22.jpg', 'jpg', 1, '文件上传成功', '2023-07-27 19:06:15');
INSERT INTO `upload_log` VALUES (76, 1, '2023-07-27_19-06-22_LLMJoB_Snipaste_2023-07-26_20-08-46.jpg', 'jpg', 1, '文件上传成功', '2023-07-27 19:06:22');
INSERT INTO `upload_log` VALUES (77, 1, '2023-07-27_19-06-28_VHHSUp_Snipaste_2023-07-26_20-08-56.jpg', 'jpg', 1, '文件上传成功', '2023-07-27 19:06:29');
INSERT INTO `upload_log` VALUES (78, 1, '2023-07-27_19-06-36_mpkFHP_Snipaste_2023-07-26_20-09-05.jpg', 'jpg', 1, '文件上传成功', '2023-07-27 19:06:36');
INSERT INTO `upload_log` VALUES (79, 1, '2023-07-27_19-09-43_nZysAF_Snipaste_2023-07-26_20-08-22.jpg', 'jpg', 1, '文件上传成功', '2023-07-27 19:09:44');
INSERT INTO `upload_log` VALUES (80, 1, '2023-07-27_19-09-49_CbJJSv_Snipaste_2023-07-26_20-09-05.jpg', 'jpg', 1, '文件上传成功', '2023-07-27 19:09:50');
INSERT INTO `upload_log` VALUES (81, 1, '2023-07-27_19-10-00_suPcoD_Snipaste_2023-07-26_20-08-56.jpg', 'jpg', 1, '文件上传成功', '2023-07-27 19:10:00');
INSERT INTO `upload_log` VALUES (82, 1, '2023-07-27_19-10-19_VYlysy_Snipaste_2023-07-26_20-09-05.jpg', 'jpg', 1, '文件上传成功', '2023-07-27 19:10:19');
INSERT INTO `upload_log` VALUES (83, 1, '2023-08-01_20-03-37_WehrqV_2023-05-11_16-40-49_TjXVgH_Snipaste_2023-05-11_15-51-44.jpg', 'jpg', 1, '文件上传成功', '2023-08-01 20:03:37');
INSERT INTO `upload_log` VALUES (84, 1, '2023-08-02_15-52-40_IZuLcI_Snipaste_2023-08-02_15-43-39.jpg', 'jpg', 1, '文件上传成功', '2023-08-02 15:52:41');
INSERT INTO `upload_log` VALUES (85, 1, '2023-08-02_15-52-51_SYWpLS_Snipaste_2023-08-02_15-43-39.jpg', 'jpg', 1, '文件上传成功', '2023-08-02 15:52:51');
INSERT INTO `upload_log` VALUES (86, 1, '2023-08-02_15-53-34_YqCiiM_Snipaste_2023-08-02_15-43-39.jpg', 'jpg', 1, '文件上传成功', '2023-08-02 15:53:34');
INSERT INTO `upload_log` VALUES (87, 1, '2023-08-02_15-54-44_XZuZcx_Snipaste_2023-08-02_15-43-39.jpg', 'jpg', 1, '文件上传成功', '2023-08-02 15:54:44');
INSERT INTO `upload_log` VALUES (88, 1, '2023-08-02_15-57-14_uTbvdP_Snipaste_2023-08-02_15-43-39.jpg', 'jpg', 1, '文件上传成功', '2023-08-02 15:57:15');
INSERT INTO `upload_log` VALUES (89, 1, '2023-08-02_15-57-28_sTAwfZ_Snipaste_2023-08-02_15-43-39.jpg', 'jpg', 1, '文件上传成功', '2023-08-02 15:57:28');
INSERT INTO `upload_log` VALUES (90, 1, '2023-08-02_15-59-09_UiuOgf_Snipaste_2023-08-02_15-43-39.jpg', 'jpg', 1, '文件上传成功', '2023-08-02 15:59:10');
INSERT INTO `upload_log` VALUES (91, 1, '2023-08-02_16-00-24_QgQyhc_Snipaste_2023-08-02_15-43-39.jpg', 'jpg', 1, '文件上传成功', '2023-08-02 16:00:24');
INSERT INTO `upload_log` VALUES (92, 1, '2023-08-02_16-00-30_rvjZxH_Snipaste_2023-08-02_15-43-39.jpg', 'jpg', 1, '文件上传成功', '2023-08-02 16:00:31');
INSERT INTO `upload_log` VALUES (93, 1, '2023-08-02_16-00-35_QrcGRJ_Snipaste_2023-08-02_15-43-39.jpg', 'jpg', 1, '文件上传成功', '2023-08-02 16:00:36');
INSERT INTO `upload_log` VALUES (94, 1, '2023-08-02_16-00-39_WpqbyK_Snipaste_2023-08-02_15-43-39.jpg', 'jpg', 1, '文件上传成功', '2023-08-02 16:00:40');

SET FOREIGN_KEY_CHECKS = 1;
