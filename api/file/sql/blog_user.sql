/*
 Navicat Premium Data Transfer

 Source Server         : 121.4.126.60
 Source Server Type    : MySQL
 Source Server Version : 50737
 Source Host           : 121.4.126.60:3306
 Source Schema         : blog_user

 Target Server Type    : MySQL
 Target Server Version : 50737
 File Encoding         : 65001

 Date: 27/01/2023 17:48:54
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
) ENGINE = InnoDB AUTO_INCREMENT = 109 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限标识表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, 0, '系统管理', 'fa fa-home', NULL, 'sys:manage', '', 0);
INSERT INTO `sys_permission` VALUES (2, 0, '内容管理', 'fa fa-book', NULL, 'sys:content', '', 0);
INSERT INTO `sys_permission` VALUES (3, 0, '系统设置', 'fa fa-cog', NULL, 'sys:setting', '', 0);
INSERT INTO `sys_permission` VALUES (50, 1, '角色管理', '', '/admin/role', 'sys:role', '', 1);
INSERT INTO `sys_permission` VALUES (51, 1, '用户管理', '', '/admin/user', 'sys:user', '', 1);
INSERT INTO `sys_permission` VALUES (60, 2, '文章管理', '', '/admin/article', 'sys:article', '', 1);
INSERT INTO `sys_permission` VALUES (61, 2, '日记管理', NULL, '/admin/diary', 'sys:diary', '', 1);
INSERT INTO `sys_permission` VALUES (62, 2, '文档管理', NULL, '/admin/doc', 'sys:doc', NULL, 1);
INSERT INTO `sys_permission` VALUES (71, 3, '全局设置', NULL, NULL, 'sys:setting:all', '', 1);
INSERT INTO `sys_permission` VALUES (72, 3, '个人设置', NULL, NULL, 'sys:setting:user', '', 1);
INSERT INTO `sys_permission` VALUES (100, 50, '角色列表', NULL, NULL, 'sys:role:select', '', 2);
INSERT INTO `sys_permission` VALUES (101, 50, '添加角色', NULL, NULL, 'sys:role:insert', '', 2);
INSERT INTO `sys_permission` VALUES (102, 50, '删除角色', NULL, NULL, 'sys:role:delete', '', 2);
INSERT INTO `sys_permission` VALUES (103, 50, '修改角色', NULL, NULL, 'sys:role:update', '', 2);
INSERT INTO `sys_permission` VALUES (104, 50, '查看角色权限', NULL, NULL, 'sys:role:permission:select', '', 2);
INSERT INTO `sys_permission` VALUES (105, 50, '修改角色权限', NULL, NULL, 'sys:role:permission:update', '', 2);
INSERT INTO `sys_permission` VALUES (106, 51, '用户列表', NULL, NULL, 'sys:user:list', '', 2);
INSERT INTO `sys_permission` VALUES (107, 51, '注销用户', NULL, NULL, 'sys:user:delete', '', 2);
INSERT INTO `sys_permission` VALUES (108, 51, '修改用户角色', NULL, NULL, 'sys:user:update', '', 2);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编码',
  `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'ADMIN', '超级管理员', '2022-12-16 15:23:24', '2022-12-16 15:23:27');
INSERT INTO `sys_role` VALUES (2, 'USER', '普通用户', '2022-12-16 15:23:29', '2022-12-16 15:23:30');
INSERT INTO `sys_role` VALUES (3, 'VISITOR', ' 游客', '2022-12-20 15:16:31', '2022-12-20 15:16:34');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `permission_id` int(11) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`role_id`, `permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色权限关系表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (1, 0);
INSERT INTO `sys_role_permission` VALUES (1, 1);
INSERT INTO `sys_role_permission` VALUES (1, 2);
INSERT INTO `sys_role_permission` VALUES (1, 3);
INSERT INTO `sys_role_permission` VALUES (1, 50);
INSERT INTO `sys_role_permission` VALUES (1, 51);
INSERT INTO `sys_role_permission` VALUES (1, 60);
INSERT INTO `sys_role_permission` VALUES (1, 61);
INSERT INTO `sys_role_permission` VALUES (1, 62);
INSERT INTO `sys_role_permission` VALUES (1, 71);
INSERT INTO `sys_role_permission` VALUES (1, 72);
INSERT INTO `sys_role_permission` VALUES (1, 100);
INSERT INTO `sys_role_permission` VALUES (1, 101);
INSERT INTO `sys_role_permission` VALUES (1, 102);
INSERT INTO `sys_role_permission` VALUES (1, 103);
INSERT INTO `sys_role_permission` VALUES (1, 104);
INSERT INTO `sys_role_permission` VALUES (1, 105);
INSERT INTO `sys_role_permission` VALUES (1, 106);
INSERT INTO `sys_role_permission` VALUES (1, 107);
INSERT INTO `sys_role_permission` VALUES (1, 108);
INSERT INTO `sys_role_permission` VALUES (2, 1);
INSERT INTO `sys_role_permission` VALUES (2, 50);
INSERT INTO `sys_role_permission` VALUES (2, 51);
INSERT INTO `sys_role_permission` VALUES (2, 100);
INSERT INTO `sys_role_permission` VALUES (2, 101);
INSERT INTO `sys_role_permission` VALUES (2, 102);
INSERT INTO `sys_role_permission` VALUES (2, 103);
INSERT INTO `sys_role_permission` VALUES (2, 104);
INSERT INTO `sys_role_permission` VALUES (2, 105);
INSERT INTO `sys_role_permission` VALUES (2, 106);
INSERT INTO `sys_role_permission` VALUES (2, 107);
INSERT INTO `sys_role_permission` VALUES (2, 108);
INSERT INTO `sys_role_permission` VALUES (3, 1);
INSERT INTO `sys_role_permission` VALUES (3, 50);
INSERT INTO `sys_role_permission` VALUES (3, 51);
INSERT INTO `sys_role_permission` VALUES (3, 100);
INSERT INTO `sys_role_permission` VALUES (3, 104);
INSERT INTO `sys_role_permission` VALUES (3, 106);

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user`  (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色用户关系表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES (1, 1);
INSERT INTO `sys_role_user` VALUES (2, 1);
INSERT INTO `sys_role_user` VALUES (3, 3);
INSERT INTO `sys_role_user` VALUES (4, 3);

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
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态（1有效,0无效）',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'gszero', '$2a$10$xGoFa7bqOTurkUvy9roreeON0j/CvXysaXauswXf5RHol/pMCSuGy', 'GSZero', 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg', '470687917@qq.com', 1, '2022-06-07 00:00:00', '2022-06-07 02:02:03');
INSERT INTO `sys_user` VALUES (2, 'admin', '$2a$10$ZKtsubiIek8Vms11vimLku6hJTALdxRTQxf5SH7w.KrrBcykGH1a6', '超管3', 'D:/img/2022-12-20_fisSb_Snipaste_2022-12-19_11-38-40.jpg', '470687917@qq.com', 1, '2022-06-07 00:00:00', '2022-06-07 00:00:00');
INSERT INTO `sys_user` VALUES (3, 'a的', '$2a$10$ZKtsubiIek8Vms11vimLku6hJTALdxRTQxf5SH7w.KrrBcykGH1a6', '超管', NULL, '470687917@qq.com', 1, '2022-06-07 00:00:00', '2022-06-07 00:00:00');
INSERT INTO `sys_user` VALUES (4, 'a都', '$2a$10$ZKtsubiIek8Vms11vimLku6hJTALdxRTQxf5SH7w.KrrBcykGH1a6', '超管', NULL, '470687917@qq.com', 1, '2022-06-07 00:00:00', '2022-06-07 00:00:00');
INSERT INTO `sys_user` VALUES (5, 'a1', '$2a$10$ZKtsubiIek8Vms11vimLku6hJTALdxRTQxf5SH7w.KrrBcykGH1a6', '超管', NULL, '470687917@qq.com', 1, '2022-06-07 00:00:00', '2022-06-07 00:00:00');
INSERT INTO `sys_user` VALUES (6, '12', '$2a$10$ZKtsubiIek8Vms11vimLku6hJTALdxRTQxf5SH7w.KrrBcykGH1a6', '超管', NULL, '470687917@qq.com', 1, '2022-06-07 00:00:00', '2022-06-07 00:00:00');
INSERT INTO `sys_user` VALUES (7, 'a3', '$2a$10$ZKtsubiIek8Vms11vimLku6hJTALdxRTQxf5SH7w.KrrBcykGH1a6', '超管', NULL, '470687917@qq.com', 1, '2022-06-07 00:00:00', '2022-06-07 00:00:00');
INSERT INTO `sys_user` VALUES (8, 'aa', '$2a$10$ZKtsubiIek8Vms11vimLku6hJTALdxRTQxf5SH7w.KrrBcykGH1a6', '超管', NULL, '470687917@qq.com', 1, '2022-06-07 00:00:00', '2022-06-07 00:00:00');
INSERT INTO `sys_user` VALUES (9, 'asd', '$2a$10$ZKtsubiIek8Vms11vimLku6hJTALdxRTQxf5SH7w.KrrBcykGH1a6', '超管', NULL, '470687917@qq.com', 1, '2022-06-07 00:00:00', '2022-06-07 00:00:00');
INSERT INTO `sys_user` VALUES (10, 'zxc', '$2a$10$ZKtsubiIek8Vms11vimLku6hJTALdxRTQxf5SH7w.KrrBcykGH1a6', '超管', NULL, '470687917@qq.com', 1, '2022-06-07 00:00:00', '2022-06-07 00:00:00');
INSERT INTO `sys_user` VALUES (11, 'asdacx', '$2a$10$ZKtsubiIek8Vms11vimLku6hJTALdxRTQxf5SH7w.KrrBcykGH1a6', '超管', NULL, '470687917@qq.com', 1, '2022-06-07 00:00:00', '2022-06-07 00:00:00');
INSERT INTO `sys_user` VALUES (12, 'asdas', '$2a$10$ZKtsubiIek8Vms11vimLku6hJTALdxRTQxf5SH7w.KrrBcykGH1a6', '超管', NULL, '470687917@qq.com', 1, '2022-06-07 00:00:00', '2022-06-07 00:00:00');

SET FOREIGN_KEY_CHECKS = 1;
