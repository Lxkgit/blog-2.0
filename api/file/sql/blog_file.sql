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

 Date: 16/12/2022 20:15:21
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
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of upload_img
-- ----------------------------
INSERT INTO `upload_img` VALUES (6, 1, '2022-07-17_BsXBJ_Snipaste_2022-07-17_14-47-23.png', '127.0.0.1:9527/opt/file/img2022-07-17_BsXBJ_Snipaste_2022-07-17_14-47-23.png', '2022-07-17 14:47:47');
INSERT INTO `upload_img` VALUES (7, 1, '2022-07-17_zxuZC_Snipaste_2022-07-17_14-47-23.png', '127.0.0.1:9527/opt/file/img2022-07-17_zxuZC_Snipaste_2022-07-17_14-47-23.png', '2022-07-17 14:54:23');
INSERT INTO `upload_img` VALUES (8, 1, '2022-07-17_JHqec_Snipaste_2022-07-17_14-47-23.png', '127.0.0.1/file/img/2022-07-17_JHqec_Snipaste_2022-07-17_14-47-23.png', '2022-07-17 15:04:49');
INSERT INTO `upload_img` VALUES (9, 1, '2022-07-18_PIFqD_123123.png', 'D:/img/2022-07-18_PIFqD_123123.png', '2022-07-18 16:34:48');
INSERT INTO `upload_img` VALUES (10, 1, '2022-07-18_sTeSt_asdasd.png', 'D:/img/2022-07-18_sTeSt_asdasd.png', '2022-07-18 16:34:48');

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
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of upload_log
-- ----------------------------
INSERT INTO `upload_log` VALUES (36, 1, '11.txt', 'diary', 2, '文件名称格式错误', '2022-07-17 14:45:30');
INSERT INTO `upload_log` VALUES (37, 1, '7-13.txt', 'diary', 1, '日记修改成功', '2022-07-17 14:45:53');
INSERT INTO `upload_log` VALUES (38, 1, 'Snipaste_2022-07-17_14-47-23.png', 'img', 1, '图片上传成功', '2022-07-17 14:47:47');
INSERT INTO `upload_log` VALUES (39, 1, 'Snipaste_2022-07-17_14-47-23.png', 'img', 1, '图片上传成功', '2022-07-17 14:54:23');
INSERT INTO `upload_log` VALUES (40, 1, 'Snipaste_2022-07-17_14-47-23.png', 'img', 1, '图片上传成功', '2022-07-17 15:04:49');
INSERT INTO `upload_log` VALUES (41, 1, '123123.png', 'img', 0, NULL, '2022-07-18 16:31:06');
INSERT INTO `upload_log` VALUES (42, 1, 'asdasd.png', 'img', 0, NULL, '2022-07-18 16:31:06');
INSERT INTO `upload_log` VALUES (43, 1, '123123.png', 'img', 1, '图片上传成功', '2022-07-18 16:34:48');
INSERT INTO `upload_log` VALUES (44, 1, 'asdasd.png', 'img', 1, '图片上传成功', '2022-07-18 16:34:48');

SET FOREIGN_KEY_CHECKS = 1;
