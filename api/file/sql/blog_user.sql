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

 Date: 07/07/2023 19:40:03
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
) ENGINE = InnoDB AUTO_INCREMENT = 706 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限标识表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, 0, '系统管理', 'fa fa-home', '', 'sys:manage', '', 0);
INSERT INTO `sys_permission` VALUES (2, 0, '内容管理', 'fa fa-book', '', 'sys:content', '', 0);
INSERT INTO `sys_permission` VALUES (3, 0, '系统设置', 'fa fa-cog', '', 'sys:setting', '', 0);
INSERT INTO `sys_permission` VALUES (50, 1, '角色管理', '', '/admin/role', 'sys:role', '', 1);
INSERT INTO `sys_permission` VALUES (51, 1, '用户管理', '', '/admin/user', 'sys:user', '', 1);
INSERT INTO `sys_permission` VALUES (60, 2, '文章管理', '', '/admin/article', 'sys:article', '', 1);
INSERT INTO `sys_permission` VALUES (61, 2, '文章分类', '', '/admin/article/type', 'sys:article:type', '', 1);
INSERT INTO `sys_permission` VALUES (62, 2, '文章标签', '', '/admin/article/label', 'sys:article:label', '', 1);
INSERT INTO `sys_permission` VALUES (63, 2, '日记管理', '', '/admin/diary', 'sys:diary', '', 1);
INSERT INTO `sys_permission` VALUES (64, 2, '文档管理', '', '/admin/doc', 'sys:doc', '', 1);
INSERT INTO `sys_permission` VALUES (71, 3, '全局设置', '', '', 'sys:setting:all', '', 1);
INSERT INTO `sys_permission` VALUES (72, 3, '个人设置', '', '', 'sys:setting:user', '', 1);
INSERT INTO `sys_permission` VALUES (100, 50, '角色列表', '', '', 'sys:role:select', '', 2);
INSERT INTO `sys_permission` VALUES (101, 50, '添加角色', '', '', 'sys:role:insert', '', 2);
INSERT INTO `sys_permission` VALUES (102, 50, '删除角色', '', '', 'sys:role:delete', '', 2);
INSERT INTO `sys_permission` VALUES (103, 50, '修改角色', '', '', 'sys:role:update', '', 2);
INSERT INTO `sys_permission` VALUES (104, 50, '查看角色权限', '', '', 'sys:role:permission:select', '', 2);
INSERT INTO `sys_permission` VALUES (105, 50, '修改角色权限', '', '', 'sys:role:permission:update', '', 2);
INSERT INTO `sys_permission` VALUES (200, 51, '用户列表', '', '', 'sys:user:list', '', 2);
INSERT INTO `sys_permission` VALUES (201, 51, '注销用户', '', '', 'sys:user:delete', '', 2);
INSERT INTO `sys_permission` VALUES (202, 51, '修改用户角色', '', '', 'sys:user:role:update', '', 2);
INSERT INTO `sys_permission` VALUES (300, 60, '文章列表', '', '', 'sys:article:list', '', 2);
INSERT INTO `sys_permission` VALUES (301, 60, '创建文章', '', '', 'sys:article:insert', '', 2);
INSERT INTO `sys_permission` VALUES (302, 60, '修改文章', '', '', 'sys:article:update', '', 2);
INSERT INTO `sys_permission` VALUES (303, 60, '删除文章', '', '', 'sys:article:delete', '', 2);
INSERT INTO `sys_permission` VALUES (400, 61, '文章分类列表', '', '', 'sys:article:type:list', '', 2);
INSERT INTO `sys_permission` VALUES (401, 61, '创建分类', '', '', 'sys:article:type:insert', '', 2);
INSERT INTO `sys_permission` VALUES (402, 61, '修改分类', '', '', 'sys:article:type:update', '', 2);
INSERT INTO `sys_permission` VALUES (403, 61, '删除分类', '', '', 'sys:article:type:delete', '', 2);
INSERT INTO `sys_permission` VALUES (500, 62, '文章标签列表', '', '', 'sys:article:label:list', '', 2);
INSERT INTO `sys_permission` VALUES (501, 62, '创建标签', '', '', 'sys:article:label:insert', '', 2);
INSERT INTO `sys_permission` VALUES (502, 62, '修改标签', '', '', 'sys:article:label:update', '', 2);
INSERT INTO `sys_permission` VALUES (503, 62, '删除标签', '', '', 'sys:article:label:delete', '', 2);
INSERT INTO `sys_permission` VALUES (504, 62, '创建标签分类', '', '', 'sys:article:label:type:insert', '', 2);
INSERT INTO `sys_permission` VALUES (505, 62, '修改标签分类', '', '', 'sys:article:label:type:update', '', 2);
INSERT INTO `sys_permission` VALUES (506, 62, '删除标签分类', '', '', 'sys:article:label:type:delete', '', 2);
INSERT INTO `sys_permission` VALUES (600, 63, '日记列表', '', '', 'sys:diary:list', '', 2);
INSERT INTO `sys_permission` VALUES (601, 63, '创建日记', '', '', 'sys:diary:insert', '', 2);
INSERT INTO `sys_permission` VALUES (602, 63, '修改日记', '', '', 'sys:diary:update', '', 2);
INSERT INTO `sys_permission` VALUES (603, 63, '删除日记', '', '', 'sys:diary:delete', '', 2);
INSERT INTO `sys_permission` VALUES (700, 64, '文档列表', '', '', 'sys:doc:list', '', 2);
INSERT INTO `sys_permission` VALUES (701, 64, '文档目录列表', '', '', 'sys:doc:catalog:list', '', 2);
INSERT INTO `sys_permission` VALUES (702, 64, '创建文档', '', '', 'sys:doc:insert', '', 2);
INSERT INTO `sys_permission` VALUES (703, 64, '修改文档', '', '', 'sys:doc:update', '', 2);
INSERT INTO `sys_permission` VALUES (704, 64, '修改文档目录', '', '', 'sys:doc:catalog:update', '', 2);
INSERT INTO `sys_permission` VALUES (705, 64, '删除文档', '', '', 'sys:doc:delete', '', 2);

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
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `sys_role_permission` VALUES (1, 62);
INSERT INTO `sys_role_permission` VALUES (1, 63);
INSERT INTO `sys_role_permission` VALUES (1, 64);
INSERT INTO `sys_role_permission` VALUES (1, 71);
INSERT INTO `sys_role_permission` VALUES (1, 72);
INSERT INTO `sys_role_permission` VALUES (1, 100);
INSERT INTO `sys_role_permission` VALUES (1, 101);
INSERT INTO `sys_role_permission` VALUES (1, 102);
INSERT INTO `sys_role_permission` VALUES (1, 103);
INSERT INTO `sys_role_permission` VALUES (1, 104);
INSERT INTO `sys_role_permission` VALUES (1, 105);
INSERT INTO `sys_role_permission` VALUES (1, 200);
INSERT INTO `sys_role_permission` VALUES (1, 201);
INSERT INTO `sys_role_permission` VALUES (1, 202);
INSERT INTO `sys_role_permission` VALUES (1, 300);
INSERT INTO `sys_role_permission` VALUES (1, 301);
INSERT INTO `sys_role_permission` VALUES (1, 302);
INSERT INTO `sys_role_permission` VALUES (1, 303);
INSERT INTO `sys_role_permission` VALUES (1, 400);
INSERT INTO `sys_role_permission` VALUES (1, 401);
INSERT INTO `sys_role_permission` VALUES (1, 402);
INSERT INTO `sys_role_permission` VALUES (1, 403);
INSERT INTO `sys_role_permission` VALUES (1, 500);
INSERT INTO `sys_role_permission` VALUES (1, 501);
INSERT INTO `sys_role_permission` VALUES (1, 502);
INSERT INTO `sys_role_permission` VALUES (1, 503);
INSERT INTO `sys_role_permission` VALUES (1, 504);
INSERT INTO `sys_role_permission` VALUES (1, 505);
INSERT INTO `sys_role_permission` VALUES (1, 506);
INSERT INTO `sys_role_permission` VALUES (1, 600);
INSERT INTO `sys_role_permission` VALUES (1, 601);
INSERT INTO `sys_role_permission` VALUES (1, 602);
INSERT INTO `sys_role_permission` VALUES (1, 603);
INSERT INTO `sys_role_permission` VALUES (1, 700);
INSERT INTO `sys_role_permission` VALUES (1, 701);
INSERT INTO `sys_role_permission` VALUES (1, 702);
INSERT INTO `sys_role_permission` VALUES (1, 703);
INSERT INTO `sys_role_permission` VALUES (1, 704);
INSERT INTO `sys_role_permission` VALUES (1, 705);
INSERT INTO `sys_role_permission` VALUES (2, 2);
INSERT INTO `sys_role_permission` VALUES (2, 60);
INSERT INTO `sys_role_permission` VALUES (2, 61);
INSERT INTO `sys_role_permission` VALUES (2, 62);
INSERT INTO `sys_role_permission` VALUES (2, 63);
INSERT INTO `sys_role_permission` VALUES (2, 64);
INSERT INTO `sys_role_permission` VALUES (2, 300);
INSERT INTO `sys_role_permission` VALUES (2, 301);
INSERT INTO `sys_role_permission` VALUES (2, 302);
INSERT INTO `sys_role_permission` VALUES (2, 303);
INSERT INTO `sys_role_permission` VALUES (2, 400);
INSERT INTO `sys_role_permission` VALUES (2, 500);
INSERT INTO `sys_role_permission` VALUES (2, 600);
INSERT INTO `sys_role_permission` VALUES (2, 601);
INSERT INTO `sys_role_permission` VALUES (2, 602);
INSERT INTO `sys_role_permission` VALUES (2, 603);
INSERT INTO `sys_role_permission` VALUES (2, 700);
INSERT INTO `sys_role_permission` VALUES (2, 701);
INSERT INTO `sys_role_permission` VALUES (2, 702);
INSERT INTO `sys_role_permission` VALUES (2, 703);
INSERT INTO `sys_role_permission` VALUES (2, 704);
INSERT INTO `sys_role_permission` VALUES (2, 705);

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
INSERT INTO `sys_role_user` VALUES (1, 2);
INSERT INTO `sys_role_user` VALUES (2, 2);
INSERT INTO `sys_role_user` VALUES (3, 3);
INSERT INTO `sys_role_user` VALUES (4, 3);
INSERT INTO `sys_role_user` VALUES (5, 2);

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
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'gszero', '$2a$10$xGoFa7bqOTurkUvy9roreeON0j/CvXysaXauswXf5RHol/pMCSuGy', 'GSZero', 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg', '470687917@qq.com', 1, '2022-06-07 00:00:00', '2022-06-07 02:02:03');
INSERT INTO `sys_user` VALUES (2, 'admin', '$2a$10$ZKtsubiIek8Vms11vimLku6hJTALdxRTQxf5SH7w.KrrBcykGH1a6', '超管3', 'D:/img/2022-12-20_fisSb_Snipaste_2022-12-19_11-38-40.jpg', '470687917@qq.com', 1, '2022-06-07 00:00:00', '2022-06-07 00:00:00');
INSERT INTO `sys_user` VALUES (5, 'qwer', '$2a$10$OHpyM2UUhZITdTxruZAAu.O2fAPl67ZMVFNa6wvtycpc.w5NHueVO', NULL, NULL, '470687917@qq.com', 1, '2023-06-16 17:10:40', '2023-06-16 17:10:40');

SET FOREIGN_KEY_CHECKS = 1;
