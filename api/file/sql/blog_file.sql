/*
 Navicat Premium Data Transfer

 Source Server         : 本机MySQL
 Source Server Type    : MySQL
 Source Server Version : 80035
 Source Host           : localhost:3306
 Source Schema         : blog_file

 Target Server Type    : MySQL
 Target Server Version : 80035
 File Encoding         : 65001

 Date: 29/01/2024 20:25:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blog_data
-- ----------------------------
DROP TABLE IF EXISTS `blog_data`;
CREATE TABLE `blog_data`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `deployment_time` datetime(0) NULL DEFAULT NULL COMMENT '博客部署时间',
  `visits` int(0) NULL DEFAULT NULL COMMENT '接口访问总次数',
  `user_count` int(0) NULL DEFAULT NULL COMMENT '用户数量',
  `ip_count` int(0) NULL DEFAULT NULL COMMENT '访问ip数',
  `article_count` int(0) NULL DEFAULT NULL COMMENT '文章总数',
  `article_type_count` int(0) NULL DEFAULT NULL COMMENT '文章分类数',
  `article_label_count` int(0) NULL DEFAULT NULL COMMENT '文章标签数',
  `doc_count` int(0) NULL DEFAULT NULL COMMENT '文档总数',
  `doc_type_count` int(0) NULL DEFAULT NULL COMMENT '文档分类数',
  `diary_count` int(0) NULL DEFAULT NULL COMMENT '日记总数',
  `img_count` int(0) NULL DEFAULT NULL COMMENT '图片总数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_data
-- ----------------------------
INSERT INTO `blog_data` VALUES (1, NULL, 2313, 3, 1, 1, 0, 2, 0, 0, 0, 5);

-- ----------------------------
-- Table structure for blog_device
-- ----------------------------
DROP TABLE IF EXISTS `blog_device`;
CREATE TABLE `blog_device`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NULL DEFAULT NULL COMMENT '所属用户id',
  `username` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `device_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '设备名称',
  `device_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '设备注册编码',
  `device_position` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '设备部署位置',
  `device_status` tinyint(0) NULL DEFAULT NULL COMMENT '设备在离线状态(0:离线 1:在线)',
  `time_template` int(0) NULL DEFAULT NULL COMMENT '时间模板id',
  `memo` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '最近修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_device
-- ----------------------------
INSERT INTO `blog_device` VALUES (1, 1, 'gszero', '树莓派', 'SMP1', '家里', 0, 0, '无', '2024-01-29 19:58:32', '2024-01-29 19:58:36');

-- ----------------------------
-- Table structure for blog_setting
-- ----------------------------
DROP TABLE IF EXISTS `blog_setting`;
CREATE TABLE `blog_setting`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `setting_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设置类型 0-0：全局设置-站点设置 0-1：全局设置-壁纸设置 1：个人设置 ',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '个人设置用户id',
  `type` tinyint(0) NULL DEFAULT NULL COMMENT '设置类型 0：分割线 1: 输入框 2：数字输入框 3：开关 4：长文本输入框 5：单图片设置 6：多图片设置',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设置名称',
  `setting` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'JSON格式设置信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '博客设置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_setting
-- ----------------------------
INSERT INTO `blog_setting` VALUES (1, '0-0', 1, 0, '信息展示', NULL);
INSERT INTO `blog_setting` VALUES (2, '0-0', 1, 1, '公告', '{\"id\":2,\"userId\":1,\"value\":\"最近没有公告111\"}');
INSERT INTO `blog_setting` VALUES (3, '0-0', 1, 6, '轮播图', '{\"id\":3,\"num\":2,\"userId\":1,\"valueList\":[\"http://localhost/files/1/doc/img/2024-01-16_17-18-05_e46e8c_2024-01-16_16-18-43_08b13e_判别图1.jpg\",\"http://localhost/files/1/doc/img/2024-01-16_17-18-13_bf9adc_2024-01-16_17-06-21_bb371f_2024-01-16_16-18-43_0b3dba_告警图1.png\"]}');
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
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `doc_count` int(0) NULL DEFAULT 0 COMMENT '文档数量',
  `article_count` int(0) NULL DEFAULT 0 COMMENT '文章数量',
  `diary_count` int(0) NULL DEFAULT 0 COMMENT '日记数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '博客用户内容数量统计' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of content_count
-- ----------------------------
INSERT INTO `content_count` VALUES (7, 1, 0, 1, 0);

-- ----------------------------
-- Table structure for file_data
-- ----------------------------
DROP TABLE IF EXISTS `file_data`;
CREATE TABLE `file_data`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件/目录名称',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件/目录全路径（不包含当前目录/文件）',
  `type` tinyint(0) NULL DEFAULT NULL COMMENT '文件类型 0：目录 1：图片 2：其它文件',
  `file_size` bigint(0) NULL DEFAULT NULL COMMENT '文件大小（kb）',
  `dir_type` tinyint(0) NULL DEFAULT 0 COMMENT '目录类型 0:本地目录 1:同步目录',
  `status` tinyint(0) NULL DEFAULT 1 COMMENT '同步文件位置状态 0:远程服务器 1:本地服务器 2:正在同步远程服务器 3:正在同步本地服务器',
  `file_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '同步文件唯一编码',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '文件创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件云盘功能数据库' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file_data
-- ----------------------------
INSERT INTO `file_data` VALUES (1, 1, '555', 'D:/files/1/user', 0, 0, 0, 1, NULL, NULL);
INSERT INTO `file_data` VALUES (2, 1, '666', 'D:/files/1/user', 0, 105179, 0, 1, NULL, NULL);
INSERT INTO `file_data` VALUES (3, 1, 'article', 'D:/files/1', 0, 0, 0, 1, NULL, NULL);
INSERT INTO `file_data` VALUES (4, 1, 'diary', 'D:/files/1', 0, 0, 0, 1, NULL, NULL);
INSERT INTO `file_data` VALUES (5, 1, 'doc', 'D:/files/1', 0, 1732806, 0, 1, NULL, NULL);
INSERT INTO `file_data` VALUES (6, 1, 'other', 'D:/files/1', 0, 18027, 0, 1, NULL, NULL);
INSERT INTO `file_data` VALUES (7, 1, 'user', 'D:/files/1', 0, 105179, 0, 1, NULL, NULL);
INSERT INTO `file_data` VALUES (8, 1, '2024-01-05_14-25-14_hMqIye_Snipaste_2023-06-19_11-10-00.jpg', 'D:/files/1/user/666', 1, 78254, 0, 1, NULL, NULL);
INSERT INTO `file_data` VALUES (9, 1, '2024-01-05_14-25-26_DAlsEs_Snipaste_2023-06-19_11-03-06.jpg', 'D:/files/1/user/666', 1, 26925, 0, 1, NULL, NULL);
INSERT INTO `file_data` VALUES (10, 1, '333', 'D:/files/1/user', 0, 0, 1, 1, NULL, NULL);
INSERT INTO `file_data` VALUES (11, 1, '2024-01-16_15-10-20_61b0b1_告警图1.jpg', 'D:/files/1/user/333', 1, 66685, 1, 1, NULL, NULL);
INSERT INTO `file_data` VALUES (12, 1, '2024-01-16_16-23-31_a39936_判别图1.jpg', 'D:/files/1/user', 1, 69272, 0, 1, NULL, NULL);
INSERT INTO `file_data` VALUES (13, 1, '2024-01-16_16-23-31_f0e403_告警图1.jpg', 'D:/files/1/user', 1, 66685, 0, 1, NULL, NULL);

-- ----------------------------
-- Table structure for file_sync
-- ----------------------------
DROP TABLE IF EXISTS `file_sync`;
CREATE TABLE `file_sync`  (
  `id` int(0) NOT NULL,
  `user_id` int(0) NULL DEFAULT NULL COMMENT '文件所属用户',
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
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NULL DEFAULT NULL COMMENT '上传用户id',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `file_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件链接',
  `upload_time` datetime(0) NULL DEFAULT NULL COMMENT '上传时间',
  `file_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件类型',
  `file_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件路径',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of upload_file
-- ----------------------------
INSERT INTO `upload_file` VALUES (1, 1, '2024-01-16_15-10-20_61b0b1_告警图1.jpg', 'http://localhost/files/1/user/333/2024-01-16_15-10-20_61b0b1_告警图1.jpg', '2024-01-16 15:10:20', 'jpg', 'D:/files/1/user/333');
INSERT INTO `upload_file` VALUES (2, 1, '2024-01-16_15-11-43_74cba1_判别图1.jpg', 'http://localhost/files/1/user/333/2024-01-16_15-11-43_74cba1_判别图1.jpg', '2024-01-16 15:11:43', 'jpg', 'D:/files/1/user/333');
INSERT INTO `upload_file` VALUES (3, 1, '2024-01-16_15-12-06_a91567_告警图1.jpg', 'http://localhost/files/1/user/333/2024-01-16_15-12-06_a91567_告警图1.jpg', '2024-01-16 15:12:06', 'jpg', 'D:/files/1/user/333');
INSERT INTO `upload_file` VALUES (4, 1, '2024-01-16_15-12-06_b56732_判别图1.jpg', 'http://localhost/files/1/user/333/2024-01-16_15-12-06_b56732_判别图1.jpg', '2024-01-16 15:12:06', 'jpg', 'D:/files/1/user/333');
INSERT INTO `upload_file` VALUES (5, 1, '2024-01-16_16-15-50_efa9ff_告警图1.jpg', 'http://localhost/files/1/article/img/2024-01-16_16-15-50_efa9ff_告警图1.jpg', '2024-01-16 16:15:51', 'jpg', 'D:/files/1/article/img');
INSERT INTO `upload_file` VALUES (6, 1, '2024-01-16_16-15-50_a2e71a_判别图1.jpg', 'http://localhost/files/1/article/img/2024-01-16_16-15-50_a2e71a_判别图1.jpg', '2024-01-16 16:15:51', 'jpg', 'D:/files/1/article/img');
INSERT INTO `upload_file` VALUES (7, 1, '2024-01-16_16-18-43_0b3dba_告警图1.jpg', 'http://localhost/files/1/article/img/2024-01-16_16-18-43_0b3dba_告警图1.jpg', '2024-01-16 16:18:43', 'jpg', 'D:/files/1/article/img');
INSERT INTO `upload_file` VALUES (8, 1, '2024-01-16_16-18-43_08b13e_判别图1.jpg', 'http://localhost/files/1/article/img/2024-01-16_16-18-43_08b13e_判别图1.jpg', '2024-01-16 16:18:43', 'jpg', 'D:/files/1/article/img');
INSERT INTO `upload_file` VALUES (9, 1, '2024-01-16_16-23-31_f0e403_告警图1.jpg', 'http://localhost/files/1/user/2024-01-16_16-23-31_f0e403_告警图1.jpg', '2024-01-16 16:23:32', 'jpg', 'D:/files/1/user');
INSERT INTO `upload_file` VALUES (10, 1, '2024-01-16_16-23-31_a39936_判别图1.jpg', 'http://localhost/files/1/user/2024-01-16_16-23-31_a39936_判别图1.jpg', '2024-01-16 16:23:32', 'jpg', 'D:/files/1/user');
INSERT INTO `upload_file` VALUES (11, 1, '2024-01-16_16-24-03_7c4454_判别图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-24-03_7c4454_判别图1.png', '2024-01-16 16:24:03', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (12, 1, '2024-01-16_16-25-39_cd75f7_告警图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-25-39_cd75f7_告警图1.png', '2024-01-16 16:25:40', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (13, 1, '2024-01-16_16-27-16_fc6ba5_告警图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-27-16_fc6ba5_告警图1.png', '2024-01-16 16:27:16', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (14, 1, '2024-01-16_16-28-09_7e1c5a_判别图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-28-09_7e1c5a_判别图1.png', '2024-01-16 16:28:10', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (15, 1, '2024-01-16_16-28-44_f04242_判别图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-28-44_f04242_判别图1.png', '2024-01-16 16:28:45', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (16, 1, '2024-01-16_16-30-51_f19f09_判别图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-30-51_f19f09_判别图1.png', '2024-01-16 16:30:52', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (17, 1, '2024-01-16_16-31-31_35c320_判别图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-31-31_35c320_判别图1.png', '2024-01-16 16:31:32', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (18, 1, '2024-01-16_16-31-56_318e60_告警图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-31-56_318e60_告警图1.png', '2024-01-16 16:31:57', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (19, 1, '2024-01-16_16-32-48_ee9ffa_告警图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-32-48_ee9ffa_告警图1.png', '2024-01-16 16:32:49', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (20, 1, '2024-01-16_16-34-35_9ee864_告警图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-34-35_9ee864_告警图1.png', '2024-01-16 16:34:36', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (21, 1, '2024-01-16_16-35-17_33cd4d_判别图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-35-17_33cd4d_判别图1.png', '2024-01-16 16:35:17', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (22, 1, '2024-01-16_16-37-08_a9ac0c_告警图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-37-08_a9ac0c_告警图1.png', '2024-01-16 16:37:08', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (23, 1, '2024-01-16_16-37-45_815fa8_告警图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-37-45_815fa8_告警图1.png', '2024-01-16 16:37:46', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (24, 1, '2024-01-16_16-39-22_70f266_告警图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-39-22_70f266_告警图1.png', '2024-01-16 16:39:22', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (25, 1, '2024-01-16_16-40-51_1e6689_告警图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-40-51_1e6689_告警图1.png', '2024-01-16 16:40:51', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (26, 1, '2024-01-16_16-43-46_ddf0b4_告警图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-43-46_ddf0b4_告警图1.png', '2024-01-16 16:43:47', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (27, 1, '2024-01-16_16-44-59_e6a585_告警图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-44-59_e6a585_告警图1.png', '2024-01-16 16:44:59', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (28, 1, '2024-01-16_16-45-47_48bfbe_2024-01-16_16-15-50_a2e71a_判别图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-45-47_48bfbe_2024-01-16_16-15-50_a2e71a_判别图1.png', '2024-01-16 16:45:48', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (29, 1, '2024-01-16_16-45-57_f4f8bf_2024-01-16_16-18-43_08b13e_判别图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-45-57_f4f8bf_2024-01-16_16-18-43_08b13e_判别图1.png', '2024-01-16 16:45:57', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (30, 1, '2024-01-16_16-46-34_1de5cc_2024-01-16_16-18-43_08b13e_判别图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-46-34_1de5cc_2024-01-16_16-18-43_08b13e_判别图1.png', '2024-01-16 16:46:34', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (31, 1, '2024-01-16_16-47-23_74f2f9_2024-01-16_16-18-43_08b13e_判别图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-47-23_74f2f9_2024-01-16_16-18-43_08b13e_判别图1.png', '2024-01-16 16:47:24', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (32, 1, '2024-01-16_16-50-03_85e2ee_2024-01-16_16-18-43_08b13e_判别图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-50-03_85e2ee_2024-01-16_16-18-43_08b13e_判别图1.png', '2024-01-16 16:50:04', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (33, 1, '2024-01-16_16-50-51_56c1b2_2024-01-16_16-18-43_08b13e_判别图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-50-51_56c1b2_2024-01-16_16-18-43_08b13e_判别图1.png', '2024-01-16 16:50:52', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (34, 1, '2024-01-16_16-51-25_16c316_2024-01-16_16-18-43_08b13e_判别图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-51-25_16c316_2024-01-16_16-18-43_08b13e_判别图1.png', '2024-01-16 16:51:26', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (35, 1, '2024-01-16_16-53-45_a57d7c_2024-01-16_16-18-43_08b13e_判别图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-53-45_a57d7c_2024-01-16_16-18-43_08b13e_判别图1.png', '2024-01-16 16:53:46', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (36, 1, '2024-01-16_16-54-19_a4ba3d_2024-01-16_16-18-43_08b13e_判别图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-54-19_a4ba3d_2024-01-16_16-18-43_08b13e_判别图1.png', '2024-01-16 16:54:19', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (37, 1, '2024-01-16_16-55-19_ba0089_2024-01-16_16-18-43_08b13e_判别图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-55-19_ba0089_2024-01-16_16-18-43_08b13e_判别图1.png', '2024-01-16 16:55:20', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (38, 1, '2024-01-16_16-57-20_9e7bb9_2024-01-16_16-18-43_08b13e_判别图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-57-20_9e7bb9_2024-01-16_16-18-43_08b13e_判别图1.png', '2024-01-16 16:57:20', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (39, 1, '2024-01-16_16-57-43_392c8b_2024-01-16_16-18-43_0b3dba_告警图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-57-43_392c8b_2024-01-16_16-18-43_0b3dba_告警图1.png', '2024-01-16 16:57:43', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (40, 1, '2024-01-16_16-58-13_1e306a_2024-01-16_16-18-43_08b13e_判别图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-58-13_1e306a_2024-01-16_16-18-43_08b13e_判别图1.png', '2024-01-16 16:58:14', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (41, 1, '2024-01-16_16-58-56_694f3f_2024-01-16_16-18-43_0b3dba_告警图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-58-56_694f3f_2024-01-16_16-18-43_0b3dba_告警图1.png', '2024-01-16 16:58:57', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (42, 1, '2024-01-16_16-59-12_97214e_2024-01-16_16-18-43_0b3dba_告警图1.png', 'http://localhost/files/1/other/img/2024-01-16_16-59-12_97214e_2024-01-16_16-18-43_0b3dba_告警图1.png', '2024-01-16 16:59:13', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (43, 1, '2024-01-16_17-01-40_3bf610_2024-01-16_16-18-43_0b3dba_告警图1.png', 'http://localhost/files/1/other/img/2024-01-16_17-01-40_3bf610_2024-01-16_16-18-43_0b3dba_告警图1.png', '2024-01-16 17:01:40', 'png', 'D:/files/1/other/img');
INSERT INTO `upload_file` VALUES (44, 1, '2024-01-16_17-06-11_6728ed_2024-01-16_16-18-43_08b13e_判别图1.jpg', 'http://localhost/files/1/article/img/2024-01-16_17-06-11_6728ed_2024-01-16_16-18-43_08b13e_判别图1.jpg', '2024-01-16 17:06:12', 'jpg', 'D:/files/1/article/img');
INSERT INTO `upload_file` VALUES (45, 1, '2024-01-16_17-06-21_bb371f_2024-01-16_16-18-43_0b3dba_告警图1.png', 'http://localhost/files/1/article/img/2024-01-16_17-06-21_bb371f_2024-01-16_16-18-43_0b3dba_告警图1.png', '2024-01-16 17:06:21', 'png', 'D:/files/1/article/img');
INSERT INTO `upload_file` VALUES (46, 1, '2024-01-16_17-08-33_3e987f_2024-01-16_17-06-21_bb371f_2024-01-16_16-18-43_0b3dba_告警图1.png', 'http://localhost/files/1/article/img/2024-01-16_17-08-33_3e987f_2024-01-16_17-06-21_bb371f_2024-01-16_16-18-43_0b3dba_告警图1.png', '2024-01-16 17:08:34', 'png', 'D:/files/1/article/img');
INSERT INTO `upload_file` VALUES (47, 1, '2024-01-16_17-09-09_261176_2024-01-16_17-06-21_bb371f_2024-01-16_16-18-43_0b3dba_告警图1.png', 'http://localhost/files/1/doc/img/2024-01-16_17-09-09_261176_2024-01-16_17-06-21_bb371f_2024-01-16_16-18-43_0b3dba_告警图1.png', '2024-01-16 17:09:09', 'png', 'D:/files/1/doc/img');
INSERT INTO `upload_file` VALUES (48, 1, '2024-01-16_17-10-30_712dcd_2024-01-16_17-06-21_bb371f_2024-01-16_16-18-43_0b3dba_告警图1.png', 'http://localhost/files/1/doc/img/2024-01-16_17-10-30_712dcd_2024-01-16_17-06-21_bb371f_2024-01-16_16-18-43_0b3dba_告警图1.png', '2024-01-16 17:10:31', 'png', 'D:/files/1/doc/img');
INSERT INTO `upload_file` VALUES (49, 1, '2024-01-16_17-14-19_9f2598_2024-01-16_16-18-43_08b13e_判别图1.jpg', 'http://localhost/files/1/doc/img/2024-01-16_17-14-19_9f2598_2024-01-16_16-18-43_08b13e_判别图1.jpg', '2024-01-16 17:14:20', 'jpg', 'D:/files/1/doc/img');
INSERT INTO `upload_file` VALUES (50, 1, '2024-01-16_17-14-44_f9fa37_2024-01-16_17-06-11_6728ed_2024-01-16_16-18-43_08b13e_判别图1.jpg', 'http://localhost/files/1/doc/img/2024-01-16_17-14-44_f9fa37_2024-01-16_17-06-11_6728ed_2024-01-16_16-18-43_08b13e_判别图1.jpg', '2024-01-16 17:14:44', 'jpg', 'D:/files/1/doc/img');
INSERT INTO `upload_file` VALUES (51, 1, '2024-01-16_17-15-47_552dbf_2024-01-16_16-18-43_0b3dba_告警图1.jpg', 'http://localhost/files/1/doc/img/2024-01-16_17-15-47_552dbf_2024-01-16_16-18-43_0b3dba_告警图1.jpg', '2024-01-16 17:15:47', 'jpg', 'D:/files/1/doc/img');
INSERT INTO `upload_file` VALUES (52, 1, '2024-01-16_17-16-24_14ca97_2024-01-16_16-18-43_08b13e_判别图1.jpg', 'http://localhost/files/1/doc/img/2024-01-16_17-16-24_14ca97_2024-01-16_16-18-43_08b13e_判别图1.jpg', '2024-01-16 17:16:24', 'jpg', 'D:/files/1/doc/img');
INSERT INTO `upload_file` VALUES (53, 1, '2024-01-16_17-17-00_d5346d_2024-01-16_17-06-21_bb371f_2024-01-16_16-18-43_0b3dba_告警图1.png', 'http://localhost/files/1/doc/img/2024-01-16_17-17-00_d5346d_2024-01-16_17-06-21_bb371f_2024-01-16_16-18-43_0b3dba_告警图1.png', '2024-01-16 17:17:00', 'png', 'D:/files/1/doc/img');
INSERT INTO `upload_file` VALUES (54, 1, '2024-01-16_17-18-05_e46e8c_2024-01-16_16-18-43_08b13e_判别图1.jpg', 'http://localhost/files/1/doc/img/2024-01-16_17-18-05_e46e8c_2024-01-16_16-18-43_08b13e_判别图1.jpg', '2024-01-16 17:18:05', 'jpg', 'D:/files/1/doc/img');
INSERT INTO `upload_file` VALUES (55, 1, '2024-01-16_17-18-13_bf9adc_2024-01-16_17-06-21_bb371f_2024-01-16_16-18-43_0b3dba_告警图1.png', 'http://localhost/files/1/doc/img/2024-01-16_17-18-13_bf9adc_2024-01-16_17-06-21_bb371f_2024-01-16_16-18-43_0b3dba_告警图1.png', '2024-01-16 17:18:14', 'png', 'D:/files/1/doc/img');
INSERT INTO `upload_file` VALUES (56, 1, '2024-01-16_17-27-49_81d1af_伊雷娜.jpg', 'http://localhost/files/1/article/img/2024-01-16_17-27-49_81d1af_伊雷娜.jpg', '2024-01-16 17:27:50', 'jpg', 'D:/files/1/article/img');
INSERT INTO `upload_file` VALUES (57, 1, '2024-01-16_19-32-30_a57a87_伊雷娜.jpg', 'http://localhost/files/1/article/img/2024-01-16_19-32-30_a57a87_伊雷娜.jpg', '2024-01-16 19:32:31', 'jpg', 'D:/files/1/article/img');
INSERT INTO `upload_file` VALUES (58, 1, '2024-01-16_19-32-48_7a3843_伊雷娜.jpg', 'http://localhost/files/1/article/img/2024-01-16_19-32-48_7a3843_伊雷娜.jpg', '2024-01-16 19:32:48', 'jpg', 'D:/files/1/article/img');
INSERT INTO `upload_file` VALUES (59, 1, '2024-01-16_19-36-25_f4bc30_1.jpg', 'http://localhost/files/1/article/img/2024-01-16_19-36-25_f4bc30_1.jpg', '2024-01-16 19:36:26', 'jpg', 'D:/files/1/article/img');
INSERT INTO `upload_file` VALUES (60, 1, '2024-01-16_19-36-25_5c3ef4_伊雷娜.jpg', 'http://localhost/files/1/article/img/2024-01-16_19-36-25_5c3ef4_伊雷娜.jpg', '2024-01-16 19:36:26', 'jpg', 'D:/files/1/article/img');
INSERT INTO `upload_file` VALUES (61, 1, '2024-01-16_19-51-46_d9f7a4_2023-08-15_14-35-29_hLOyIf_2023-08-15_09-10-36_PngxNK_image.png', 'http://localhost/files/1/article/img/2024-01-16_19-51-46_d9f7a4_2023-08-15_14-35-29_hLOyIf_2023-08-15_09-10-36_PngxNK_image.png', '2024-01-16 19:51:47', 'png', 'D:/files/1/article/img');
INSERT INTO `upload_file` VALUES (62, 1, '2024-01-17_16-56-24_2be580_雷姆.png', 'http://localhost/files/1/article/img/2024-01-17_16-56-24_2be580_雷姆.png', '2024-01-17 16:56:24', 'png', 'D:/files/1/article/img');

-- ----------------------------
-- Table structure for upload_log
-- ----------------------------
DROP TABLE IF EXISTS `upload_log`;
CREATE TABLE `upload_log`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NULL DEFAULT NULL COMMENT '上传用户id',
  `file_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上传文件名字',
  `file_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件类型',
  `upload_state` tinyint(0) NULL DEFAULT NULL COMMENT '文件上传状态 0：待处理 1：上传成功 2：上传失败',
  `upload_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  `upload_time` datetime(0) NULL DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of upload_log
-- ----------------------------
INSERT INTO `upload_log` VALUES (1, 1, '2024-01-16_15-10-20_61b0b1_告警图1.jpg', 'jpg', 1, '文件上传成功', '2024-01-16 15:10:20');
INSERT INTO `upload_log` VALUES (2, 1, '2024-01-16_15-11-43_74cba1_判别图1.jpg', 'jpg', 1, '文件上传成功', '2024-01-16 15:11:43');
INSERT INTO `upload_log` VALUES (3, 1, '2024-01-16_15-12-06_a91567_告警图1.jpg', 'jpg', 1, '文件上传成功', '2024-01-16 15:12:06');
INSERT INTO `upload_log` VALUES (4, 1, '2024-01-16_15-12-06_b56732_判别图1.jpg', 'jpg', 1, '文件上传成功', '2024-01-16 15:12:06');
INSERT INTO `upload_log` VALUES (5, 1, '2024-01-16_16-15-50_efa9ff_告警图1.jpg', 'jpg', 1, '文件上传成功', '2024-01-16 16:15:51');
INSERT INTO `upload_log` VALUES (6, 1, '2024-01-16_16-15-50_a2e71a_判别图1.jpg', 'jpg', 1, '文件上传成功', '2024-01-16 16:15:51');
INSERT INTO `upload_log` VALUES (7, 1, '2024-01-16_16-18-43_0b3dba_告警图1.jpg', 'jpg', 1, '文件上传成功', '2024-01-16 16:18:43');
INSERT INTO `upload_log` VALUES (8, 1, '2024-01-16_16-18-43_08b13e_判别图1.jpg', 'jpg', 1, '文件上传成功', '2024-01-16 16:18:43');
INSERT INTO `upload_log` VALUES (9, 1, '2024-01-16_16-23-31_f0e403_告警图1.jpg', 'jpg', 1, '文件上传成功', '2024-01-16 16:23:32');
INSERT INTO `upload_log` VALUES (10, 1, '2024-01-16_16-23-31_a39936_判别图1.jpg', 'jpg', 1, '文件上传成功', '2024-01-16 16:23:32');
INSERT INTO `upload_log` VALUES (11, 1, '2024-01-16_16-24-03_7c4454_判别图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:24:03');
INSERT INTO `upload_log` VALUES (12, 1, '2024-01-16_16-25-39_cd75f7_告警图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:25:40');
INSERT INTO `upload_log` VALUES (13, 1, '2024-01-16_16-27-16_fc6ba5_告警图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:27:16');
INSERT INTO `upload_log` VALUES (14, 1, '2024-01-16_16-28-09_7e1c5a_判别图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:28:10');
INSERT INTO `upload_log` VALUES (15, 1, '2024-01-16_16-28-44_f04242_判别图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:28:45');
INSERT INTO `upload_log` VALUES (16, 1, '2024-01-16_16-30-51_f19f09_判别图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:30:52');
INSERT INTO `upload_log` VALUES (17, 1, '2024-01-16_16-31-31_35c320_判别图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:31:32');
INSERT INTO `upload_log` VALUES (18, 1, '2024-01-16_16-31-56_318e60_告警图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:31:57');
INSERT INTO `upload_log` VALUES (19, 1, '2024-01-16_16-32-48_ee9ffa_告警图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:32:49');
INSERT INTO `upload_log` VALUES (20, 1, '2024-01-16_16-34-35_9ee864_告警图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:34:36');
INSERT INTO `upload_log` VALUES (21, 1, '2024-01-16_16-35-17_33cd4d_判别图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:35:17');
INSERT INTO `upload_log` VALUES (22, 1, '2024-01-16_16-37-08_a9ac0c_告警图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:37:08');
INSERT INTO `upload_log` VALUES (23, 1, '2024-01-16_16-37-45_815fa8_告警图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:37:46');
INSERT INTO `upload_log` VALUES (24, 1, '2024-01-16_16-39-22_70f266_告警图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:39:22');
INSERT INTO `upload_log` VALUES (25, 1, '2024-01-16_16-40-51_1e6689_告警图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:40:51');
INSERT INTO `upload_log` VALUES (26, 1, '2024-01-16_16-43-46_ddf0b4_告警图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:43:47');
INSERT INTO `upload_log` VALUES (27, 1, '2024-01-16_16-44-59_e6a585_告警图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:44:59');
INSERT INTO `upload_log` VALUES (28, 1, '2024-01-16_16-45-47_48bfbe_2024-01-16_16-15-50_a2e71a_判别图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:45:48');
INSERT INTO `upload_log` VALUES (29, 1, '2024-01-16_16-45-57_f4f8bf_2024-01-16_16-18-43_08b13e_判别图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:45:57');
INSERT INTO `upload_log` VALUES (30, 1, '2024-01-16_16-46-34_1de5cc_2024-01-16_16-18-43_08b13e_判别图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:46:34');
INSERT INTO `upload_log` VALUES (31, 1, '2024-01-16_16-47-23_74f2f9_2024-01-16_16-18-43_08b13e_判别图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:47:24');
INSERT INTO `upload_log` VALUES (32, 1, '2024-01-16_16-50-03_85e2ee_2024-01-16_16-18-43_08b13e_判别图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:50:04');
INSERT INTO `upload_log` VALUES (33, 1, '2024-01-16_16-50-51_56c1b2_2024-01-16_16-18-43_08b13e_判别图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:50:52');
INSERT INTO `upload_log` VALUES (34, 1, '2024-01-16_16-51-25_16c316_2024-01-16_16-18-43_08b13e_判别图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:51:26');
INSERT INTO `upload_log` VALUES (35, 1, '2024-01-16_16-53-45_a57d7c_2024-01-16_16-18-43_08b13e_判别图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:53:46');
INSERT INTO `upload_log` VALUES (36, 1, '2024-01-16_16-54-19_a4ba3d_2024-01-16_16-18-43_08b13e_判别图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:54:19');
INSERT INTO `upload_log` VALUES (37, 1, '2024-01-16_16-55-19_ba0089_2024-01-16_16-18-43_08b13e_判别图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:55:20');
INSERT INTO `upload_log` VALUES (38, 1, '2024-01-16_16-57-20_9e7bb9_2024-01-16_16-18-43_08b13e_判别图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:57:20');
INSERT INTO `upload_log` VALUES (39, 1, '2024-01-16_16-57-43_392c8b_2024-01-16_16-18-43_0b3dba_告警图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:57:43');
INSERT INTO `upload_log` VALUES (40, 1, '2024-01-16_16-58-13_1e306a_2024-01-16_16-18-43_08b13e_判别图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:58:14');
INSERT INTO `upload_log` VALUES (41, 1, '2024-01-16_16-58-56_694f3f_2024-01-16_16-18-43_0b3dba_告警图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:58:57');
INSERT INTO `upload_log` VALUES (42, 1, '2024-01-16_16-59-12_97214e_2024-01-16_16-18-43_0b3dba_告警图1.png', 'png', 1, '文件上传成功', '2024-01-16 16:59:13');
INSERT INTO `upload_log` VALUES (43, 1, '2024-01-16_17-01-40_3bf610_2024-01-16_16-18-43_0b3dba_告警图1.png', 'png', 1, '文件上传成功', '2024-01-16 17:01:40');
INSERT INTO `upload_log` VALUES (44, 1, '2024-01-16_17-06-11_6728ed_2024-01-16_16-18-43_08b13e_判别图1.jpg', 'jpg', 1, '文件上传成功', '2024-01-16 17:06:12');
INSERT INTO `upload_log` VALUES (45, 1, '2024-01-16_17-06-21_bb371f_2024-01-16_16-18-43_0b3dba_告警图1.png', 'png', 1, '文件上传成功', '2024-01-16 17:06:21');
INSERT INTO `upload_log` VALUES (46, 1, '2024-01-16_17-08-33_3e987f_2024-01-16_17-06-21_bb371f_2024-01-16_16-18-43_0b3dba_告警图1.png', 'png', 1, '文件上传成功', '2024-01-16 17:08:34');
INSERT INTO `upload_log` VALUES (47, 1, '2024-01-16_17-09-09_261176_2024-01-16_17-06-21_bb371f_2024-01-16_16-18-43_0b3dba_告警图1.png', 'png', 1, '文件上传成功', '2024-01-16 17:09:09');
INSERT INTO `upload_log` VALUES (48, 1, '2024-01-16_17-10-30_712dcd_2024-01-16_17-06-21_bb371f_2024-01-16_16-18-43_0b3dba_告警图1.png', 'png', 1, '文件上传成功', '2024-01-16 17:10:31');
INSERT INTO `upload_log` VALUES (49, 1, '2024-01-16_17-14-19_9f2598_2024-01-16_16-18-43_08b13e_判别图1.jpg', 'jpg', 1, '文件上传成功', '2024-01-16 17:14:20');
INSERT INTO `upload_log` VALUES (50, 1, '2024-01-16_17-14-44_f9fa37_2024-01-16_17-06-11_6728ed_2024-01-16_16-18-43_08b13e_判别图1.jpg', 'jpg', 1, '文件上传成功', '2024-01-16 17:14:44');
INSERT INTO `upload_log` VALUES (51, 1, '2024-01-16_17-15-47_552dbf_2024-01-16_16-18-43_0b3dba_告警图1.jpg', 'jpg', 1, '文件上传成功', '2024-01-16 17:15:47');
INSERT INTO `upload_log` VALUES (52, 1, '2024-01-16_17-16-24_14ca97_2024-01-16_16-18-43_08b13e_判别图1.jpg', 'jpg', 1, '文件上传成功', '2024-01-16 17:16:24');
INSERT INTO `upload_log` VALUES (53, 1, '2024-01-16_17-17-00_d5346d_2024-01-16_17-06-21_bb371f_2024-01-16_16-18-43_0b3dba_告警图1.png', 'png', 1, '文件上传成功', '2024-01-16 17:17:00');
INSERT INTO `upload_log` VALUES (54, 1, '2024-01-16_17-18-05_e46e8c_2024-01-16_16-18-43_08b13e_判别图1.jpg', 'jpg', 1, '文件上传成功', '2024-01-16 17:18:05');
INSERT INTO `upload_log` VALUES (55, 1, '2024-01-16_17-18-13_bf9adc_2024-01-16_17-06-21_bb371f_2024-01-16_16-18-43_0b3dba_告警图1.png', 'png', 1, '文件上传成功', '2024-01-16 17:18:14');
INSERT INTO `upload_log` VALUES (56, 1, '2024-01-16_17-27-49_81d1af_伊雷娜.jpg', 'jpg', 1, '文件上传成功', '2024-01-16 17:27:50');
INSERT INTO `upload_log` VALUES (57, 1, '2024-01-16_19-32-30_a57a87_伊雷娜.jpg', 'jpg', 1, '文件上传成功', '2024-01-16 19:32:31');
INSERT INTO `upload_log` VALUES (58, 1, '2024-01-16_19-32-48_7a3843_伊雷娜.jpg', 'jpg', 1, '文件上传成功', '2024-01-16 19:32:48');
INSERT INTO `upload_log` VALUES (59, 1, '2024-01-16_19-36-25_f4bc30_1.jpg', 'jpg', 1, '文件上传成功', '2024-01-16 19:36:26');
INSERT INTO `upload_log` VALUES (60, 1, '2024-01-16_19-36-25_5c3ef4_伊雷娜.jpg', 'jpg', 1, '文件上传成功', '2024-01-16 19:36:26');
INSERT INTO `upload_log` VALUES (61, 1, '2024-01-16_19-51-46_d9f7a4_2023-08-15_14-35-29_hLOyIf_2023-08-15_09-10-36_PngxNK_image.png', 'png', 1, '文件上传成功', '2024-01-16 19:51:47');
INSERT INTO `upload_log` VALUES (62, 1, '2024-01-17_16-56-24_2be580_雷姆.png', 'png', 1, '文件上传成功', '2024-01-17 16:56:24');

SET FOREIGN_KEY_CHECKS = 1;
