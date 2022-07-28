/*
 Navicat Premium Data Transfer

 Source Server         : 本机
 Source Server Type    : MySQL
 Source Server Version : 50651
 Source Host           : localhost:3306
 Source Schema         : blog_gateway

 Target Server Type    : MySQL
 Target Server Version : 50651
 File Encoding         : 65001

 Date: 28/07/2022 22:49:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blacklist_ip
-- ----------------------------
DROP TABLE IF EXISTS `blacklist_ip`;
CREATE TABLE `blacklist_ip`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '黑名单用户id',
  `ip` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '黑名单用户ip',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blacklist_ip
-- ----------------------------

-- ----------------------------
-- Table structure for request_log
-- ----------------------------
DROP TABLE IF EXISTS `request_log`;
CREATE TABLE `request_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '接口请求用户id 0表示没有用户id',
  `url_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户请求接口url',
  `request_ip` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户ip地址',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '接口请求时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 116 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of request_log
-- ----------------------------
INSERT INTO `request_log` VALUES (1, 0, '/content/article/list/1/2', '10.34.98.217', '2022-07-20 17:16:21');
INSERT INTO `request_log` VALUES (2, 0, '/content/article/list/1/2', '10.34.98.217', '2022-07-20 17:53:49');
INSERT INTO `request_log` VALUES (3, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-20 23:04:17');
INSERT INTO `request_log` VALUES (4, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:30:10');
INSERT INTO `request_log` VALUES (5, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:30:20');
INSERT INTO `request_log` VALUES (6, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:30:44');
INSERT INTO `request_log` VALUES (7, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:30:45');
INSERT INTO `request_log` VALUES (8, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:30:48');
INSERT INTO `request_log` VALUES (9, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:30:48');
INSERT INTO `request_log` VALUES (10, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:30:49');
INSERT INTO `request_log` VALUES (11, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:30:50');
INSERT INTO `request_log` VALUES (12, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:30:51');
INSERT INTO `request_log` VALUES (13, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:30:53');
INSERT INTO `request_log` VALUES (14, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:30:54');
INSERT INTO `request_log` VALUES (15, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:30:54');
INSERT INTO `request_log` VALUES (16, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:31:03');
INSERT INTO `request_log` VALUES (17, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:31:04');
INSERT INTO `request_log` VALUES (18, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:31:05');
INSERT INTO `request_log` VALUES (19, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:31:06');
INSERT INTO `request_log` VALUES (20, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:31:08');
INSERT INTO `request_log` VALUES (21, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:31:08');
INSERT INTO `request_log` VALUES (22, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:31:09');
INSERT INTO `request_log` VALUES (23, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:31:10');
INSERT INTO `request_log` VALUES (24, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:31:10');
INSERT INTO `request_log` VALUES (25, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:31:11');
INSERT INTO `request_log` VALUES (26, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:31:12');
INSERT INTO `request_log` VALUES (27, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:31:13');
INSERT INTO `request_log` VALUES (28, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:31:14');
INSERT INTO `request_log` VALUES (29, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:31:15');
INSERT INTO `request_log` VALUES (30, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:31:15');
INSERT INTO `request_log` VALUES (31, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:31:16');
INSERT INTO `request_log` VALUES (32, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:31:17');
INSERT INTO `request_log` VALUES (33, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:31:29');
INSERT INTO `request_log` VALUES (34, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:35:11');
INSERT INTO `request_log` VALUES (35, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:35:17');
INSERT INTO `request_log` VALUES (36, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:39:36');
INSERT INTO `request_log` VALUES (37, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:52:04');
INSERT INTO `request_log` VALUES (38, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:53:34');
INSERT INTO `request_log` VALUES (39, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:53:35');
INSERT INTO `request_log` VALUES (40, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:56:20');
INSERT INTO `request_log` VALUES (41, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:56:27');
INSERT INTO `request_log` VALUES (42, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:56:33');
INSERT INTO `request_log` VALUES (43, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:56:38');
INSERT INTO `request_log` VALUES (44, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:56:41');
INSERT INTO `request_log` VALUES (45, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:56:45');
INSERT INTO `request_log` VALUES (46, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:56:49');
INSERT INTO `request_log` VALUES (47, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:56:53');
INSERT INTO `request_log` VALUES (48, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:56:57');
INSERT INTO `request_log` VALUES (49, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:57:00');
INSERT INTO `request_log` VALUES (50, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:57:05');
INSERT INTO `request_log` VALUES (51, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:57:09');
INSERT INTO `request_log` VALUES (52, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:57:13');
INSERT INTO `request_log` VALUES (53, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:57:17');
INSERT INTO `request_log` VALUES (54, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:57:20');
INSERT INTO `request_log` VALUES (55, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:57:24');
INSERT INTO `request_log` VALUES (56, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:57:28');
INSERT INTO `request_log` VALUES (57, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:57:32');
INSERT INTO `request_log` VALUES (58, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:57:36');
INSERT INTO `request_log` VALUES (59, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:57:39');
INSERT INTO `request_log` VALUES (60, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:57:43');
INSERT INTO `request_log` VALUES (61, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:57:47');
INSERT INTO `request_log` VALUES (62, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:57:51');
INSERT INTO `request_log` VALUES (63, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:57:55');
INSERT INTO `request_log` VALUES (64, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:57:59');
INSERT INTO `request_log` VALUES (65, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:58:05');
INSERT INTO `request_log` VALUES (66, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:58:10');
INSERT INTO `request_log` VALUES (67, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:58:13');
INSERT INTO `request_log` VALUES (68, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:58:43');
INSERT INTO `request_log` VALUES (69, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:58:48');
INSERT INTO `request_log` VALUES (70, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:59:06');
INSERT INTO `request_log` VALUES (71, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 22:59:46');
INSERT INTO `request_log` VALUES (72, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:05:45');
INSERT INTO `request_log` VALUES (73, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:05:47');
INSERT INTO `request_log` VALUES (74, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:05:53');
INSERT INTO `request_log` VALUES (75, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:06:04');
INSERT INTO `request_log` VALUES (76, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:06:31');
INSERT INTO `request_log` VALUES (77, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:08:55');
INSERT INTO `request_log` VALUES (78, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:08:57');
INSERT INTO `request_log` VALUES (79, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:08:58');
INSERT INTO `request_log` VALUES (80, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:08:59');
INSERT INTO `request_log` VALUES (81, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:09:00');
INSERT INTO `request_log` VALUES (82, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:09:06');
INSERT INTO `request_log` VALUES (83, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:09:13');
INSERT INTO `request_log` VALUES (84, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:09:14');
INSERT INTO `request_log` VALUES (85, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:09:21');
INSERT INTO `request_log` VALUES (86, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:09:24');
INSERT INTO `request_log` VALUES (87, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:09:26');
INSERT INTO `request_log` VALUES (88, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:09:27');
INSERT INTO `request_log` VALUES (89, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:09:29');
INSERT INTO `request_log` VALUES (90, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:09:31');
INSERT INTO `request_log` VALUES (91, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:10:11');
INSERT INTO `request_log` VALUES (92, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:10:45');
INSERT INTO `request_log` VALUES (93, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:11:33');
INSERT INTO `request_log` VALUES (94, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:11:45');
INSERT INTO `request_log` VALUES (95, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:12:23');
INSERT INTO `request_log` VALUES (96, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:13:08');
INSERT INTO `request_log` VALUES (97, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:14:02');
INSERT INTO `request_log` VALUES (98, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:14:25');
INSERT INTO `request_log` VALUES (99, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:14:44');
INSERT INTO `request_log` VALUES (100, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:14:57');
INSERT INTO `request_log` VALUES (101, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:15:09');
INSERT INTO `request_log` VALUES (102, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:15:43');
INSERT INTO `request_log` VALUES (103, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:16:26');
INSERT INTO `request_log` VALUES (104, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:16:32');
INSERT INTO `request_log` VALUES (105, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:16:56');
INSERT INTO `request_log` VALUES (106, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:17:06');
INSERT INTO `request_log` VALUES (107, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:17:57');
INSERT INTO `request_log` VALUES (108, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:18:53');
INSERT INTO `request_log` VALUES (109, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:19:08');
INSERT INTO `request_log` VALUES (110, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:20:17');
INSERT INTO `request_log` VALUES (111, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:20:41');
INSERT INTO `request_log` VALUES (112, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:20:41');
INSERT INTO `request_log` VALUES (113, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:20:47');
INSERT INTO `request_log` VALUES (114, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:20:50');
INSERT INTO `request_log` VALUES (115, 0, '/content/article/list/1/2', '192.168.2.111', '2022-07-27 23:20:54');

SET FOREIGN_KEY_CHECKS = 1;
