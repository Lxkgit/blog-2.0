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

 Date: 05/01/2024 17:11:09
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
) ENGINE = InnoDB AUTO_INCREMENT = 1002 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限标识表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, 0, '用户管理', 'icon-people', '', 'sys:manage', '', 0);
INSERT INTO `sys_permission` VALUES (2, 0, '内容管理', 'icon-book', '', 'sys:content', '', 0);
INSERT INTO `sys_permission` VALUES (3, 0, '文件管理', 'icon-file', '', 'sys:file', '', 0);
INSERT INTO `sys_permission` VALUES (4, 0, '系统设置', 'icon-setting', '', 'sys:setting', '', 0);
INSERT INTO `sys_permission` VALUES (50, 1, '角色管理', '', '/admin/role', 'sys:role', '', 1);
INSERT INTO `sys_permission` VALUES (51, 1, '用户管理', '', '/admin/user', 'sys:user', '', 1);
INSERT INTO `sys_permission` VALUES (60, 2, '文章管理', '', '/admin/article', 'sys:article', '', 1);
INSERT INTO `sys_permission` VALUES (61, 2, '文章分类', '', '/admin/article/type', 'sys:article:type', '', 1);
INSERT INTO `sys_permission` VALUES (62, 2, '文章标签', '', '/admin/article/label', 'sys:article:label', '', 1);
INSERT INTO `sys_permission` VALUES (63, 2, '日记管理', '', '/admin/diary', 'sys:diary', '', 1);
INSERT INTO `sys_permission` VALUES (64, 2, '文档管理', '', '/admin/doc', 'sys:doc', '', 1);
INSERT INTO `sys_permission` VALUES (70, 3, '文件云盘', '', '/admin/file', 'sys:file:user', '', 1);
INSERT INTO `sys_permission` VALUES (80, 4, '网站设置', '', '/admin/setting/web', 'sys:setting:all', '', 1);
INSERT INTO `sys_permission` VALUES (81, 4, '个人设置', '', '/admin/setting/user', 'sys:setting:user', '', 1);
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
INSERT INTO `sys_permission` VALUES (800, 70, '查看文件', '', '', 'sys:file:user:select', '', 2);
INSERT INTO `sys_permission` VALUES (801, 70, '创建目录', '', '', 'sys:file:user:save', '', 2);
INSERT INTO `sys_permission` VALUES (802, 70, '上传文件', '', '', 'sys:file:user:upload', '', 2);
INSERT INTO `sys_permission` VALUES (803, 70, '修改文件或目录名称', '', '', 'sys:file:user:update', '', 2);
INSERT INTO `sys_permission` VALUES (804, 70, '删除文件或目录', '', '', 'sys:file:user:delete', '', 2);
INSERT INTO `sys_permission` VALUES (805, 70, '获取剩余空间', '', '', 'sys:file:user:space', '', 2);
INSERT INTO `sys_permission` VALUES (806, 70, '同步远程文件', '', '', 'sys:file:user:sync', '', 2);
INSERT INTO `sys_permission` VALUES (900, 80, '查看网站设置', '', '', 'sys:setting:all:select', '', 2);
INSERT INTO `sys_permission` VALUES (901, 80, '修改网站设置', '', '', 'sys:setting:all:update', '', 2);
INSERT INTO `sys_permission` VALUES (1000, 81, '查看个人设置', '', '', 'sys:setting:user:select', '', 2);
INSERT INTO `sys_permission` VALUES (1001, 81, '修改个人设置', '', '', 'sys:setting:user:update', '', 2);

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
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `sys_role_permission` VALUES (1, 4);
INSERT INTO `sys_role_permission` VALUES (1, 50);
INSERT INTO `sys_role_permission` VALUES (1, 51);
INSERT INTO `sys_role_permission` VALUES (1, 60);
INSERT INTO `sys_role_permission` VALUES (1, 61);
INSERT INTO `sys_role_permission` VALUES (1, 62);
INSERT INTO `sys_role_permission` VALUES (1, 63);
INSERT INTO `sys_role_permission` VALUES (1, 64);
INSERT INTO `sys_role_permission` VALUES (1, 70);
INSERT INTO `sys_role_permission` VALUES (1, 80);
INSERT INTO `sys_role_permission` VALUES (1, 81);
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
INSERT INTO `sys_role_permission` VALUES (1, 800);
INSERT INTO `sys_role_permission` VALUES (1, 801);
INSERT INTO `sys_role_permission` VALUES (1, 802);
INSERT INTO `sys_role_permission` VALUES (1, 803);
INSERT INTO `sys_role_permission` VALUES (1, 804);
INSERT INTO `sys_role_permission` VALUES (1, 805);
INSERT INTO `sys_role_permission` VALUES (1, 900);
INSERT INTO `sys_role_permission` VALUES (1, 901);
INSERT INTO `sys_role_permission` VALUES (1, 1000);
INSERT INTO `sys_role_permission` VALUES (1, 1001);
INSERT INTO `sys_role_permission` VALUES (2, 2);
INSERT INTO `sys_role_permission` VALUES (2, 4);
INSERT INTO `sys_role_permission` VALUES (2, 60);
INSERT INTO `sys_role_permission` VALUES (2, 62);
INSERT INTO `sys_role_permission` VALUES (2, 63);
INSERT INTO `sys_role_permission` VALUES (2, 64);
INSERT INTO `sys_role_permission` VALUES (2, 81);
INSERT INTO `sys_role_permission` VALUES (2, 300);
INSERT INTO `sys_role_permission` VALUES (2, 301);
INSERT INTO `sys_role_permission` VALUES (2, 302);
INSERT INTO `sys_role_permission` VALUES (2, 303);
INSERT INTO `sys_role_permission` VALUES (2, 500);
INSERT INTO `sys_role_permission` VALUES (2, 501);
INSERT INTO `sys_role_permission` VALUES (2, 502);
INSERT INTO `sys_role_permission` VALUES (2, 503);
INSERT INTO `sys_role_permission` VALUES (2, 504);
INSERT INTO `sys_role_permission` VALUES (2, 505);
INSERT INTO `sys_role_permission` VALUES (2, 506);
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
INSERT INTO `sys_role_permission` VALUES (2, 1000);
INSERT INTO `sys_role_permission` VALUES (2, 1001);
INSERT INTO `sys_role_permission` VALUES (3, 1);
INSERT INTO `sys_role_permission` VALUES (3, 2);
INSERT INTO `sys_role_permission` VALUES (3, 3);
INSERT INTO `sys_role_permission` VALUES (3, 50);
INSERT INTO `sys_role_permission` VALUES (3, 51);
INSERT INTO `sys_role_permission` VALUES (3, 60);
INSERT INTO `sys_role_permission` VALUES (3, 61);
INSERT INTO `sys_role_permission` VALUES (3, 62);
INSERT INTO `sys_role_permission` VALUES (3, 63);
INSERT INTO `sys_role_permission` VALUES (3, 64);
INSERT INTO `sys_role_permission` VALUES (3, 70);
INSERT INTO `sys_role_permission` VALUES (3, 100);
INSERT INTO `sys_role_permission` VALUES (3, 101);
INSERT INTO `sys_role_permission` VALUES (3, 102);
INSERT INTO `sys_role_permission` VALUES (3, 103);
INSERT INTO `sys_role_permission` VALUES (3, 104);
INSERT INTO `sys_role_permission` VALUES (3, 105);
INSERT INTO `sys_role_permission` VALUES (3, 200);
INSERT INTO `sys_role_permission` VALUES (3, 201);
INSERT INTO `sys_role_permission` VALUES (3, 202);
INSERT INTO `sys_role_permission` VALUES (3, 300);
INSERT INTO `sys_role_permission` VALUES (3, 301);
INSERT INTO `sys_role_permission` VALUES (3, 302);
INSERT INTO `sys_role_permission` VALUES (3, 303);
INSERT INTO `sys_role_permission` VALUES (3, 400);
INSERT INTO `sys_role_permission` VALUES (3, 401);
INSERT INTO `sys_role_permission` VALUES (3, 402);
INSERT INTO `sys_role_permission` VALUES (3, 403);
INSERT INTO `sys_role_permission` VALUES (3, 500);
INSERT INTO `sys_role_permission` VALUES (3, 501);
INSERT INTO `sys_role_permission` VALUES (3, 502);
INSERT INTO `sys_role_permission` VALUES (3, 503);
INSERT INTO `sys_role_permission` VALUES (3, 504);
INSERT INTO `sys_role_permission` VALUES (3, 505);
INSERT INTO `sys_role_permission` VALUES (3, 506);
INSERT INTO `sys_role_permission` VALUES (3, 600);
INSERT INTO `sys_role_permission` VALUES (3, 601);
INSERT INTO `sys_role_permission` VALUES (3, 602);
INSERT INTO `sys_role_permission` VALUES (3, 603);
INSERT INTO `sys_role_permission` VALUES (3, 700);
INSERT INTO `sys_role_permission` VALUES (3, 701);
INSERT INTO `sys_role_permission` VALUES (3, 702);
INSERT INTO `sys_role_permission` VALUES (3, 703);
INSERT INTO `sys_role_permission` VALUES (3, 704);
INSERT INTO `sys_role_permission` VALUES (3, 705);
INSERT INTO `sys_role_permission` VALUES (9, 1);
INSERT INTO `sys_role_permission` VALUES (9, 2);
INSERT INTO `sys_role_permission` VALUES (9, 3);
INSERT INTO `sys_role_permission` VALUES (9, 4);
INSERT INTO `sys_role_permission` VALUES (9, 50);
INSERT INTO `sys_role_permission` VALUES (9, 51);
INSERT INTO `sys_role_permission` VALUES (9, 60);
INSERT INTO `sys_role_permission` VALUES (9, 61);
INSERT INTO `sys_role_permission` VALUES (9, 62);
INSERT INTO `sys_role_permission` VALUES (9, 63);
INSERT INTO `sys_role_permission` VALUES (9, 64);
INSERT INTO `sys_role_permission` VALUES (9, 70);
INSERT INTO `sys_role_permission` VALUES (9, 80);
INSERT INTO `sys_role_permission` VALUES (9, 81);
INSERT INTO `sys_role_permission` VALUES (9, 100);
INSERT INTO `sys_role_permission` VALUES (9, 101);
INSERT INTO `sys_role_permission` VALUES (9, 102);
INSERT INTO `sys_role_permission` VALUES (9, 103);
INSERT INTO `sys_role_permission` VALUES (9, 104);
INSERT INTO `sys_role_permission` VALUES (9, 105);
INSERT INTO `sys_role_permission` VALUES (9, 200);
INSERT INTO `sys_role_permission` VALUES (9, 201);
INSERT INTO `sys_role_permission` VALUES (9, 202);
INSERT INTO `sys_role_permission` VALUES (9, 300);
INSERT INTO `sys_role_permission` VALUES (9, 301);
INSERT INTO `sys_role_permission` VALUES (9, 302);
INSERT INTO `sys_role_permission` VALUES (9, 303);
INSERT INTO `sys_role_permission` VALUES (9, 400);
INSERT INTO `sys_role_permission` VALUES (9, 401);
INSERT INTO `sys_role_permission` VALUES (9, 402);
INSERT INTO `sys_role_permission` VALUES (9, 403);
INSERT INTO `sys_role_permission` VALUES (9, 500);
INSERT INTO `sys_role_permission` VALUES (9, 501);
INSERT INTO `sys_role_permission` VALUES (9, 502);
INSERT INTO `sys_role_permission` VALUES (9, 503);
INSERT INTO `sys_role_permission` VALUES (9, 504);
INSERT INTO `sys_role_permission` VALUES (9, 505);
INSERT INTO `sys_role_permission` VALUES (9, 506);
INSERT INTO `sys_role_permission` VALUES (9, 600);
INSERT INTO `sys_role_permission` VALUES (9, 601);
INSERT INTO `sys_role_permission` VALUES (9, 602);
INSERT INTO `sys_role_permission` VALUES (9, 603);
INSERT INTO `sys_role_permission` VALUES (9, 700);
INSERT INTO `sys_role_permission` VALUES (9, 701);
INSERT INTO `sys_role_permission` VALUES (9, 702);
INSERT INTO `sys_role_permission` VALUES (9, 703);
INSERT INTO `sys_role_permission` VALUES (9, 704);
INSERT INTO `sys_role_permission` VALUES (9, 705);
INSERT INTO `sys_role_permission` VALUES (9, 800);
INSERT INTO `sys_role_permission` VALUES (9, 801);
INSERT INTO `sys_role_permission` VALUES (9, 802);
INSERT INTO `sys_role_permission` VALUES (9, 803);
INSERT INTO `sys_role_permission` VALUES (9, 804);
INSERT INTO `sys_role_permission` VALUES (9, 805);
INSERT INTO `sys_role_permission` VALUES (9, 900);
INSERT INTO `sys_role_permission` VALUES (9, 901);
INSERT INTO `sys_role_permission` VALUES (9, 1000);
INSERT INTO `sys_role_permission` VALUES (9, 1001);

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
INSERT INTO `sys_user` VALUES (2, 'admin', '$2a$10$ZKtsubiIek8Vms11vimLku6hJTALdxRTQxf5SH7w.KrrBcykGH1a6', '超管311111', 'http://localhost/files/1/other/img/2024-01-03_15-38-54_NThNEE_判别图1.png', '470687917@qq.com', 1, '2022-06-07 00:00:00', '2022-06-07 00:00:00');
INSERT INTO `sys_user` VALUES (5, 'qwer', '$2a$10$OHpyM2UUhZITdTxruZAAu.O2fAPl67ZMVFNa6wvtycpc.w5NHueVO', '1', 'http://localhost/files/1/other/img/2024-01-03_15-39-31_MbdQEN_自动化19条.png', '470687917@qq.com', 1, '2023-06-16 17:10:40', '2023-06-16 17:10:40');

SET FOREIGN_KEY_CHECKS = 1;
