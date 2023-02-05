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

 Date: 02/02/2023 22:24:38
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
) ENGINE = InnoDB AUTO_INCREMENT = 5332 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of request_log
-- ----------------------------
INSERT INTO `request_log` VALUES (5142, 1, '/content/article/list', '192.168.1.239', '2023-02-02 21:44:19');
INSERT INTO `request_log` VALUES (5143, 1, '/user/user/select/user/id', '192.168.1.239', '2023-02-02 21:44:19');
INSERT INTO `request_log` VALUES (5144, 1, '/user/user/select/user/id', '192.168.1.239', '2023-02-02 21:44:21');
INSERT INTO `request_log` VALUES (5145, 1, '/content/article/list', '192.168.1.239', '2023-02-02 21:44:21');
INSERT INTO `request_log` VALUES (5146, 1, '/content/doc/catalog/id', '192.168.1.239', '2023-02-02 21:44:23');
INSERT INTO `request_log` VALUES (5147, 1, '/user/user/doc/user', '192.168.1.239', '2023-02-02 21:44:23');
INSERT INTO `request_log` VALUES (5148, NULL, '/content/doc/catalog/id', '192.168.1.239', '2023-02-02 21:44:26');
INSERT INTO `request_log` VALUES (5149, NULL, '/user/user/doc/user', '192.168.1.239', '2023-02-02 21:44:26');
INSERT INTO `request_log` VALUES (5150, NULL, '/content/article/list', '192.168.1.239', '2023-02-02 21:44:29');
INSERT INTO `request_log` VALUES (5151, NULL, '/content/article/list', '192.168.1.239', '2023-02-02 21:44:32');
INSERT INTO `request_log` VALUES (5152, NULL, '/user/user/doc/user', '192.168.1.239', '2023-02-02 21:44:35');
INSERT INTO `request_log` VALUES (5153, NULL, '/content/doc/catalog/id', '192.168.1.239', '2023-02-02 21:44:35');
INSERT INTO `request_log` VALUES (5154, NULL, '/content/article/list', '192.168.1.239', '2023-02-02 21:44:36');
INSERT INTO `request_log` VALUES (5155, NULL, '/auth/oauth/token', '192.168.1.239', '2023-02-02 21:44:39');
INSERT INTO `request_log` VALUES (5156, 1, '/user/menu/list', '192.168.1.239', '2023-02-02 21:44:41');
INSERT INTO `request_log` VALUES (5157, 1, '/user/role/list', '192.168.1.239', '2023-02-02 21:44:43');
INSERT INTO `request_log` VALUES (5158, 1, '/user/user/list', '192.168.1.239', '2023-02-02 21:44:44');
INSERT INTO `request_log` VALUES (5159, 1, '/content/article/list', '192.168.1.239', '2023-02-02 21:44:45');
INSERT INTO `request_log` VALUES (5160, 1, '/content/article/label/list', '192.168.1.239', '2023-02-02 21:44:45');
INSERT INTO `request_log` VALUES (5161, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:44:47');
INSERT INTO `request_log` VALUES (5162, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:46:47');
INSERT INTO `request_log` VALUES (5163, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:46:47');
INSERT INTO `request_log` VALUES (5164, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:46:50');
INSERT INTO `request_log` VALUES (5165, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:46:50');
INSERT INTO `request_log` VALUES (5166, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:46:51');
INSERT INTO `request_log` VALUES (5167, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:46:52');
INSERT INTO `request_log` VALUES (5168, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:46:54');
INSERT INTO `request_log` VALUES (5169, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:46:54');
INSERT INTO `request_log` VALUES (5170, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:47:03');
INSERT INTO `request_log` VALUES (5171, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:47:06');
INSERT INTO `request_log` VALUES (5172, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:47:06');
INSERT INTO `request_log` VALUES (5173, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:47:06');
INSERT INTO `request_log` VALUES (5174, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:47:06');
INSERT INTO `request_log` VALUES (5175, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:48:56');
INSERT INTO `request_log` VALUES (5176, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:48:56');
INSERT INTO `request_log` VALUES (5177, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:48:59');
INSERT INTO `request_log` VALUES (5178, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:49:04');
INSERT INTO `request_log` VALUES (5179, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:49:04');
INSERT INTO `request_log` VALUES (5180, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:49:04');
INSERT INTO `request_log` VALUES (5181, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:49:06');
INSERT INTO `request_log` VALUES (5182, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:49:10');
INSERT INTO `request_log` VALUES (5183, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:49:11');
INSERT INTO `request_log` VALUES (5184, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:49:15');
INSERT INTO `request_log` VALUES (5185, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:49:16');
INSERT INTO `request_log` VALUES (5186, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:49:19');
INSERT INTO `request_log` VALUES (5187, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:49:20');
INSERT INTO `request_log` VALUES (5188, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:49:20');
INSERT INTO `request_log` VALUES (5189, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:49:20');
INSERT INTO `request_log` VALUES (5190, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:49:23');
INSERT INTO `request_log` VALUES (5191, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:49:23');
INSERT INTO `request_log` VALUES (5192, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:49:23');
INSERT INTO `request_log` VALUES (5193, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:49:25');
INSERT INTO `request_log` VALUES (5194, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:49:27');
INSERT INTO `request_log` VALUES (5195, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:49:28');
INSERT INTO `request_log` VALUES (5196, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:49:28');
INSERT INTO `request_log` VALUES (5197, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:49:30');
INSERT INTO `request_log` VALUES (5198, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:49:33');
INSERT INTO `request_log` VALUES (5199, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:49:34');
INSERT INTO `request_log` VALUES (5200, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:49:34');
INSERT INTO `request_log` VALUES (5201, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:49:36');
INSERT INTO `request_log` VALUES (5202, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:49:38');
INSERT INTO `request_log` VALUES (5203, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:49:52');
INSERT INTO `request_log` VALUES (5204, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:49:53');
INSERT INTO `request_log` VALUES (5205, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:49:54');
INSERT INTO `request_log` VALUES (5206, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:49:55');
INSERT INTO `request_log` VALUES (5207, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:49:58');
INSERT INTO `request_log` VALUES (5208, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:50:00');
INSERT INTO `request_log` VALUES (5209, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:50:01');
INSERT INTO `request_log` VALUES (5210, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:50:02');
INSERT INTO `request_log` VALUES (5211, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:50:04');
INSERT INTO `request_log` VALUES (5212, 1, '/user/menu/list', '192.168.1.239', '2023-02-02 21:50:13');
INSERT INTO `request_log` VALUES (5213, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:50:13');
INSERT INTO `request_log` VALUES (5214, 1, '/user/menu/list', '192.168.1.239', '2023-02-02 21:50:17');
INSERT INTO `request_log` VALUES (5215, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:50:17');
INSERT INTO `request_log` VALUES (5216, 1, '/file/files/upload', '192.168.1.239', '2023-02-02 21:50:23');
INSERT INTO `request_log` VALUES (5217, 1, '/file/files/diary/import', '192.168.1.239', '2023-02-02 21:50:27');
INSERT INTO `request_log` VALUES (5218, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:50:29');
INSERT INTO `request_log` VALUES (5219, 1, '/file/files/upload', '192.168.1.239', '2023-02-02 21:51:32');
INSERT INTO `request_log` VALUES (5220, 1, '/file/files/diary/import', '192.168.1.239', '2023-02-02 21:51:35');
INSERT INTO `request_log` VALUES (5221, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:51:35');
INSERT INTO `request_log` VALUES (5222, 1, '/user/menu/list', '192.168.1.239', '2023-02-02 21:51:50');
INSERT INTO `request_log` VALUES (5223, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:51:50');
INSERT INTO `request_log` VALUES (5224, 1, '/file/files/upload', '192.168.1.239', '2023-02-02 21:54:46');
INSERT INTO `request_log` VALUES (5225, 1, '/file/files/diary/import', '192.168.1.239', '2023-02-02 21:54:48');
INSERT INTO `request_log` VALUES (5226, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:54:50');
INSERT INTO `request_log` VALUES (5227, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:55:06');
INSERT INTO `request_log` VALUES (5228, 1, '/user/menu/list', '192.168.1.239', '2023-02-02 21:55:06');
INSERT INTO `request_log` VALUES (5229, 1, '/user/menu/list', '192.168.1.239', '2023-02-02 21:55:29');
INSERT INTO `request_log` VALUES (5230, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:55:29');
INSERT INTO `request_log` VALUES (5231, 1, '/user/menu/list', '192.168.1.239', '2023-02-02 21:55:40');
INSERT INTO `request_log` VALUES (5232, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:55:40');
INSERT INTO `request_log` VALUES (5233, 1, '/file/files/upload', '192.168.1.239', '2023-02-02 21:56:22');
INSERT INTO `request_log` VALUES (5234, 1, '/file/files/diary/import', '192.168.1.239', '2023-02-02 21:56:24');
INSERT INTO `request_log` VALUES (5235, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:56:27');
INSERT INTO `request_log` VALUES (5236, 1, '/user/menu/list', '192.168.1.239', '2023-02-02 21:56:33');
INSERT INTO `request_log` VALUES (5237, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:56:33');
INSERT INTO `request_log` VALUES (5238, 1, '/user/menu/list', '192.168.1.239', '2023-02-02 21:56:35');
INSERT INTO `request_log` VALUES (5239, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:56:35');
INSERT INTO `request_log` VALUES (5240, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:57:18');
INSERT INTO `request_log` VALUES (5241, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:57:19');
INSERT INTO `request_log` VALUES (5242, 1, '/user/menu/list', '192.168.1.239', '2023-02-02 21:58:03');
INSERT INTO `request_log` VALUES (5243, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:58:03');
INSERT INTO `request_log` VALUES (5244, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:58:23');
INSERT INTO `request_log` VALUES (5245, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:58:24');
INSERT INTO `request_log` VALUES (5246, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:58:25');
INSERT INTO `request_log` VALUES (5247, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:58:25');
INSERT INTO `request_log` VALUES (5248, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:58:26');
INSERT INTO `request_log` VALUES (5249, 1, '/user/menu/list', '192.168.1.239', '2023-02-02 21:58:40');
INSERT INTO `request_log` VALUES (5250, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:58:40');
INSERT INTO `request_log` VALUES (5251, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 21:59:56');
INSERT INTO `request_log` VALUES (5252, 1, '/user/menu/list', '192.168.1.239', '2023-02-02 22:00:02');
INSERT INTO `request_log` VALUES (5253, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:00:02');
INSERT INTO `request_log` VALUES (5254, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:00:23');
INSERT INTO `request_log` VALUES (5255, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:00:28');
INSERT INTO `request_log` VALUES (5256, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:00:29');
INSERT INTO `request_log` VALUES (5257, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:00:29');
INSERT INTO `request_log` VALUES (5258, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:00:32');
INSERT INTO `request_log` VALUES (5259, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:00:32');
INSERT INTO `request_log` VALUES (5260, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:00:32');
INSERT INTO `request_log` VALUES (5261, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:00:32');
INSERT INTO `request_log` VALUES (5262, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:00:33');
INSERT INTO `request_log` VALUES (5263, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:00:33');
INSERT INTO `request_log` VALUES (5264, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:00:34');
INSERT INTO `request_log` VALUES (5265, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:00:35');
INSERT INTO `request_log` VALUES (5266, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:00:36');
INSERT INTO `request_log` VALUES (5267, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:00:40');
INSERT INTO `request_log` VALUES (5268, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:00:40');
INSERT INTO `request_log` VALUES (5269, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:00:40');
INSERT INTO `request_log` VALUES (5270, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:00:41');
INSERT INTO `request_log` VALUES (5271, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:00:42');
INSERT INTO `request_log` VALUES (5272, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:00:42');
INSERT INTO `request_log` VALUES (5273, 1, '/user/menu/list', '192.168.1.239', '2023-02-02 22:00:49');
INSERT INTO `request_log` VALUES (5274, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:00:49');
INSERT INTO `request_log` VALUES (5275, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:01:15');
INSERT INTO `request_log` VALUES (5276, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:01:16');
INSERT INTO `request_log` VALUES (5277, 1, '/user/menu/list', '192.168.1.239', '2023-02-02 22:02:40');
INSERT INTO `request_log` VALUES (5278, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:02:40');
INSERT INTO `request_log` VALUES (5279, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:02:41');
INSERT INTO `request_log` VALUES (5280, 1, '/user/menu/list', '192.168.1.239', '2023-02-02 22:02:41');
INSERT INTO `request_log` VALUES (5281, 1, '/content/diary/save', '192.168.1.239', '2023-02-02 22:02:51');
INSERT INTO `request_log` VALUES (5282, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:02:51');
INSERT INTO `request_log` VALUES (5283, 1, '/content/diary/delete', '192.168.1.239', '2023-02-02 22:02:57');
INSERT INTO `request_log` VALUES (5284, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:02:58');
INSERT INTO `request_log` VALUES (5285, 1, '/content/article/list', '192.168.1.239', '2023-02-02 22:03:14');
INSERT INTO `request_log` VALUES (5286, 1, '/content/article/label/list', '192.168.1.239', '2023-02-02 22:03:14');
INSERT INTO `request_log` VALUES (5287, 1, '/content/doc/catalog/list', '192.168.1.239', '2023-02-02 22:03:15');
INSERT INTO `request_log` VALUES (5288, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:03:15');
INSERT INTO `request_log` VALUES (5289, 1, '/content/doc/catalog/list', '192.168.1.239', '2023-02-02 22:03:30');
INSERT INTO `request_log` VALUES (5290, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:03:31');
INSERT INTO `request_log` VALUES (5291, 1, '/content/article/list', '192.168.1.239', '2023-02-02 22:03:31');
INSERT INTO `request_log` VALUES (5292, 1, '/content/article/label/list', '192.168.1.239', '2023-02-02 22:03:32');
INSERT INTO `request_log` VALUES (5293, 1, '/content/article/type/tree', '192.168.1.239', '2023-02-02 22:03:35');
INSERT INTO `request_log` VALUES (5294, 1, '/content/article/label/list', '192.168.1.239', '2023-02-02 22:03:35');
INSERT INTO `request_log` VALUES (5295, 1, '/user/menu/list', '192.168.1.239', '2023-02-02 22:03:38');
INSERT INTO `request_log` VALUES (5296, 1, '/content/article/list', '192.168.1.239', '2023-02-02 22:03:38');
INSERT INTO `request_log` VALUES (5297, 1, '/content/article/label/list', '192.168.1.239', '2023-02-02 22:03:38');
INSERT INTO `request_log` VALUES (5298, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:03:40');
INSERT INTO `request_log` VALUES (5299, 1, '/content/article/list', '192.168.1.239', '2023-02-02 22:03:41');
INSERT INTO `request_log` VALUES (5300, 1, '/content/article/label/list', '192.168.1.239', '2023-02-02 22:03:41');
INSERT INTO `request_log` VALUES (5301, 1, '/content/article/list', '192.168.1.239', '2023-02-02 22:05:57');
INSERT INTO `request_log` VALUES (5302, 1, '/content/article/label/list', '192.168.1.239', '2023-02-02 22:05:57');
INSERT INTO `request_log` VALUES (5303, 1, '/user/menu/list', '192.168.1.239', '2023-02-02 22:05:57');
INSERT INTO `request_log` VALUES (5304, 1, '/user/menu/list', '192.168.1.239', '2023-02-02 22:06:00');
INSERT INTO `request_log` VALUES (5305, 1, '/content/article/label/list', '192.168.1.239', '2023-02-02 22:06:00');
INSERT INTO `request_log` VALUES (5306, 1, '/content/article/list', '192.168.1.239', '2023-02-02 22:06:00');
INSERT INTO `request_log` VALUES (5307, 1, '/content/article/label/list', '192.168.1.239', '2023-02-02 22:06:02');
INSERT INTO `request_log` VALUES (5308, 1, '/content/article/list', '192.168.1.239', '2023-02-02 22:06:02');
INSERT INTO `request_log` VALUES (5309, 1, '/user/menu/list', '192.168.1.239', '2023-02-02 22:06:02');
INSERT INTO `request_log` VALUES (5310, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:06:05');
INSERT INTO `request_log` VALUES (5311, 1, '/file/files/upload', '192.168.1.239', '2023-02-02 22:06:21');
INSERT INTO `request_log` VALUES (5312, 1, '/file/files/diary/import', '192.168.1.239', '2023-02-02 22:06:23');
INSERT INTO `request_log` VALUES (5313, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:06:26');
INSERT INTO `request_log` VALUES (5314, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:07:37');
INSERT INTO `request_log` VALUES (5315, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:08:20');
INSERT INTO `request_log` VALUES (5316, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:08:27');
INSERT INTO `request_log` VALUES (5317, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:08:32');
INSERT INTO `request_log` VALUES (5318, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:08:33');
INSERT INTO `request_log` VALUES (5319, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:08:33');
INSERT INTO `request_log` VALUES (5320, 1, '/content/article/label/list', '192.168.1.239', '2023-02-02 22:09:10');
INSERT INTO `request_log` VALUES (5321, 1, '/content/article/list', '192.168.1.239', '2023-02-02 22:09:10');
INSERT INTO `request_log` VALUES (5322, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:09:11');
INSERT INTO `request_log` VALUES (5323, 1, '/content/doc/catalog/list', '192.168.1.239', '2023-02-02 22:09:12');
INSERT INTO `request_log` VALUES (5324, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:09:13');
INSERT INTO `request_log` VALUES (5325, 1, '/file/files/upload', '192.168.1.239', '2023-02-02 22:09:21');
INSERT INTO `request_log` VALUES (5326, 1, '/file/files/diary/import', '192.168.1.239', '2023-02-02 22:09:23');
INSERT INTO `request_log` VALUES (5327, 1, '/content/diary/list', '192.168.1.239', '2023-02-02 22:09:27');
INSERT INTO `request_log` VALUES (5328, 1, '/content/doc/catalog/list', '192.168.1.239', '2023-02-02 22:09:33');
INSERT INTO `request_log` VALUES (5329, 1, '/content/doc/catalog/id', '192.168.1.239', '2023-02-02 22:09:34');
INSERT INTO `request_log` VALUES (5330, 1, '/user/user/select/user/id', '192.168.1.239', '2023-02-02 22:09:39');
INSERT INTO `request_log` VALUES (5331, 1, '/content/article/list', '192.168.1.239', '2023-02-02 22:09:39');

SET FOREIGN_KEY_CHECKS = 1;
