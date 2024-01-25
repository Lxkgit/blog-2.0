/*
 Navicat Premium Data Transfer

 Source Server         : 本机MySQL
 Source Server Type    : MySQL
 Source Server Version : 80035
 Source Host           : localhost:3306
 Source Schema         : blog_pi

 Target Server Type    : MySQL
 Target Server Version : 80035
 File Encoding         : 65001

 Date: 25/01/2024 18:21:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blog_file_sync
-- ----------------------------
DROP TABLE IF EXISTS `blog_file_sync`;
CREATE TABLE `blog_file_sync`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `file_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '文件编码（同一文件与服务器中编码相同）',
  `local_file_path` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '本地文件目录',
  `local_file_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '本地文件名称',
  `service_file_path` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '服务器文件目录',
  `service_file_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '服务器文件名称',
  `file_size` int(0) NULL DEFAULT NULL COMMENT '文件大小（单位kb）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '博客服务器文件同步表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sensor_data
-- ----------------------------
DROP TABLE IF EXISTS `sensor_data`;
CREATE TABLE `sensor_data`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `chip_type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '单片机芯片编号',
  `sensor_type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '传感器型号',
  `data` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'mqtt 接收的数据',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '接收消息时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '传感器数据表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
