/*
 Navicat Premium Data Transfer

 Source Server         : 本机MySQL
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : blog_user

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 02/08/2022 20:05:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父菜单id',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `menu_icon` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `menu_path` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `permission` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `menu_type` int(5) NULL DEFAULT NULL COMMENT '类型 0:目录 1:菜单 2:按钮',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 104 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限标识表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, 0, '系统管理', NULL, NULL, 'sys:manage', NULL, 0);
INSERT INTO `sys_permission` VALUES (2, 0, '内容管理', NULL, NULL, 'sys:content', NULL, 0);
INSERT INTO `sys_permission` VALUES (3, 0, '系统设置', NULL, NULL, 'sys:setting', NULL, 0);
INSERT INTO `sys_permission` VALUES (50, 1, '角色管理', '/src/assets/img/user.png', '/admin/role', 'sys:role', NULL, 1);
INSERT INTO `sys_permission` VALUES (51, 1, '用户管理', '/src/assets/img/role.png', '/admin/user', 'sys:user', NULL, 1);
INSERT INTO `sys_permission` VALUES (60, 2, '文章管理', '/src/assets/img/content.png', '/admin/article', 'sys:article', NULL, 1);
INSERT INTO `sys_permission` VALUES (61, 2, '日记管理', NULL, '/admin/diary', 'sys:diary', NULL, 1);
INSERT INTO `sys_permission` VALUES (71, 3, '全局设置', NULL, NULL, 'sys:setting:all', NULL, 1);
INSERT INTO `sys_permission` VALUES (72, 3, '个人设置', NULL, NULL, 'sys:setting:user', NULL, 1);
INSERT INTO `sys_permission` VALUES (100, 50, '添加角色', NULL, NULL, 'sys:role:insert', NULL, 2);
INSERT INTO `sys_permission` VALUES (101, 50, '删除角色', NULL, NULL, 'sys:role:delete', NULL, 2);
INSERT INTO `sys_permission` VALUES (102, 50, '修改角色', NULL, NULL, 'sys:role:update', '', 2);
INSERT INTO `sys_permission` VALUES (103, 50, '修改角色权限', NULL, NULL, 'sys:role:permission:update', NULL, 2);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'ADMIN', '超级管理员');
INSERT INTO `sys_role` VALUES (2, 'USER', '普通用户');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `permission_id` int(11) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`role_id`, `permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色权限关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (1, 1);
INSERT INTO `sys_role_permission` VALUES (1, 2);
INSERT INTO `sys_role_permission` VALUES (1, 3);
INSERT INTO `sys_role_permission` VALUES (1, 50);
INSERT INTO `sys_role_permission` VALUES (1, 51);
INSERT INTO `sys_role_permission` VALUES (1, 60);
INSERT INTO `sys_role_permission` VALUES (1, 61);
INSERT INTO `sys_role_permission` VALUES (1, 71);
INSERT INTO `sys_role_permission` VALUES (1, 72);
INSERT INTO `sys_role_permission` VALUES (1, 100);
INSERT INTO `sys_role_permission` VALUES (1, 101);
INSERT INTO `sys_role_permission` VALUES (1, 102);
INSERT INTO `sys_role_permission` VALUES (1, 103);

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user`  (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色用户关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES (1, 1);
INSERT INTO `sys_role_user` VALUES (2, 1);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `nickname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `head_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像url',
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态（1有效,0无效）',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'gszero', '$2a$10$xGoFa7bqOTurkUvy9roreeON0j/CvXysaXauswXf5RHol/pMCSuGy', 'GSZero', NULL, NULL, 1, '2022-06-07 00:00:00', '2022-06-07 02:02:03');
INSERT INTO `sys_user` VALUES (2, 'admin', '$2a$10$ZKtsubiIek8Vms11vimLku6hJTALdxRTQxf5SH7w.KrrBcykGH1a6', '超管', NULL, NULL, 1, '2022-06-07 00:00:00', '2022-06-07 00:00:00');

SET FOREIGN_KEY_CHECKS = 1;
