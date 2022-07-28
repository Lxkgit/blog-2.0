/*
 Navicat Premium Data Transfer

 Source Server         : 本机
 Source Server Type    : MySQL
 Source Server Version : 50651
 Source Host           : localhost:3306
 Source Schema         : blog-file

 Target Server Type    : MySQL
 Target Server Version : 50651
 File Encoding         : 65001

 Date: 28/07/2022 22:50:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for upload_img
-- ----------------------------
DROP TABLE IF EXISTS `upload_img`;
CREATE TABLE `upload_img`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '图片上传用户id',
  `img_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片名称',
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片链接',
  `upload_time` datetime(0) NULL DEFAULT NULL COMMENT '图片上传时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of upload_img
-- ----------------------------
INSERT INTO `upload_img` VALUES (3, 1, '2022-07-15_hIfrW_Snipaste_2022-07-15_14-02-49.png', '10.34.98.217:9527D:/img/2022-07-15_hIfrW_Snipaste_2022-07-15_14-02-49.png', '2022-07-15 17:10:12');
INSERT INTO `upload_img` VALUES (4, 1, '2022-07-15_DQZqy_Snipaste_2022-07-15_14-03-01.png', '10.34.98.217:9527D:/img/2022-07-15_DQZqy_Snipaste_2022-07-15_14-03-01.png', '2022-07-15 17:10:12');
INSERT INTO `upload_img` VALUES (5, 1, '2022-07-17_hdFGl_Snipaste_2022-07-17_11-56-40.png', '192.168.2.111:9527D:/img/2022-07-17_hdFGl_Snipaste_2022-07-17_11-56-40.png', '2022-07-17 11:58:20');

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
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of upload_log
-- ----------------------------
INSERT INTO `upload_log` VALUES (33, 1, 'Snipaste_2022-07-15_14-02-49.png', 'img', 1, '图片上传成功', '2022-07-15 17:10:12');
INSERT INTO `upload_log` VALUES (34, 1, 'Snipaste_2022-07-15_14-03-01.png', 'img', 1, '图片上传成功', '2022-07-15 17:10:12');
INSERT INTO `upload_log` VALUES (35, 1, 'Snipaste_2022-07-17_11-56-40.png', 'img', 1, '图片上传成功', '2022-07-17 11:58:20');

SET FOREIGN_KEY_CHECKS = 1;
