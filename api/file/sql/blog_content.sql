/*
 Navicat Premium Data Transfer

 Source Server         : 本机
 Source Server Type    : MySQL
 Source Server Version : 50651
 Source Host           : localhost:3306
 Source Schema         : blog_content

 Target Server Type    : MySQL
 Target Server Version : 50651
 File Encoding         : 65001

 Date: 12/11/2022 10:55:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '文章发布用户',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章标题',
  `content_md` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'markdown格式文章内容',
  `content_html` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'html格式文章内容',
  `article_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章类型（分类由大到小使用 , 隔开）',
  `article_label` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章标签（标签顺序不分先后 使用 , 隔开）',
  `article_status` tinyint(4) NULL DEFAULT NULL COMMENT '文章状态（2：置顶 1：发布 0：草稿）',
  `browse_count` int(11) NULL DEFAULT NULL COMMENT '文章浏览次数',
  `like_count` int(11) NULL DEFAULT NULL COMMENT '文章点赞次数',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '文章创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '文章最近更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (3, 1, '【洛谷】P2330-繁忙的都市--（并查集、kruskal、prim解法）-3', '# 题目描述\n\n四方定理是众所周知的：任意一个正整数n，可以分解为不超过四个整数的平方和。例如：25=1^2^+2^2^+2^2^+4^2^ ，当然还有其他的分解方案，25=3^2^+4^2^和25=5^2^ 。给定的正整数n，编程统计它能分解的方案总数。注意: 25=3^2^+4^2^和25=4^2^+3^2^ 视为一种方案。\n\n# 输入格式\n\n第一行为正整数t(t≤100)，接下来t行，每行一个正整数n(n≤32768)。\n\n# 输出格式\n对于每个正整数n，输出方案总数。\n\n# 解析\n\n\n\n```c++\n#include <bits/stdc++.h>\n\nusing namespace std;\n\nint n,k;\nint arr[181];\nint dp[32769][5];\n\nvoid plan(){\n    dp[0][0] = 1;\n    for(int i=0; i<181; i++){\n        arr[i] = i*i;\n    }\n    for(int i=1;i<181; i++){\n        for(int j=arr[i]; j<32769; j++){\n            for(int num=1; num <=4; num++){\n                dp[j][num] += dp[j-arr[i]][num-1];\n            }\n        }\n    }\n}\n\nint main(){\n    plan();\n    scanf(\"%d\", &n);\n    while(n--){\n        scanf(\"%d\", &k);\n        printf(\"%d\\n\", dp[k][1]+dp[k][2]+dp[k][3]+dp[k][4]);\n    }\n}\n```\n', '-', '1,2,3', '1', 1, 0, 0, '2022-06-09 11:18:30', '2022-11-10 17:26:52');
INSERT INTO `article` VALUES (38, 1, '123123', '1231231', NULL, '1', '', 1, 0, 0, '2022-11-10 14:03:42', '2022-11-10 14:03:42');
INSERT INTO `article` VALUES (39, 1, '123123', '123123', NULL, '1', '', 1, 0, 0, '2022-11-10 14:27:49', '2022-11-10 14:27:49');
INSERT INTO `article` VALUES (40, 1, '23123', 'asdasd123123', NULL, '1', '', 1, 0, 0, '2022-11-10 14:56:11', '2022-11-10 14:56:11');
INSERT INTO `article` VALUES (41, 1, 'asdasd', 'asdasd', NULL, '3', '', 1, 0, 0, '2022-11-10 23:16:48', '2022-11-10 23:16:48');

-- ----------------------------
-- Table structure for article_label
-- ----------------------------
DROP TABLE IF EXISTS `article_label`;
CREATE TABLE `article_label`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `label_type` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '标签类型id',
  `label_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签名称',
  `article_num` tinyint(4) NULL DEFAULT NULL COMMENT '文章数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of article_label
-- ----------------------------
INSERT INTO `article_label` VALUES (1, 1, 1, '算法', 2);
INSERT INTO `article_label` VALUES (2, 1, 1, '多线程', 2);
INSERT INTO `article_label` VALUES (3, 1, 1, 'mybatis', 1);
INSERT INTO `article_label` VALUES (4, 1, 2, 'dfs', 1);

-- ----------------------------
-- Table structure for article_label_type
-- ----------------------------
DROP TABLE IF EXISTS `article_label_type`;
CREATE TABLE `article_label_type`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `type_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章标签分类名称',
  `label_num` tinyint(4) NULL DEFAULT NULL COMMENT '标签数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of article_label_type
-- ----------------------------
INSERT INTO `article_label_type` VALUES (1, 1, 'java', 3);
INSERT INTO `article_label_type` VALUES (2, 1, '算法', 1);
INSERT INTO `article_label_type` VALUES (3, 1, '888', 0);

-- ----------------------------
-- Table structure for article_type
-- ----------------------------
DROP TABLE IF EXISTS `article_type`;
CREATE TABLE `article_type`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '文章分类上级 根分级为0',
  `type_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `num` int(11) NULL DEFAULT NULL COMMENT '文章数量',
  `node` tinyint(2) NULL DEFAULT NULL COMMENT '是否有子节点 （0：无 1：有）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of article_type
-- ----------------------------
INSERT INTO `article_type` VALUES (1, 0, '算法', 2, 1);
INSERT INTO `article_type` VALUES (2, 1, '洛谷', 2, 1);
INSERT INTO `article_type` VALUES (3, 2, '排序', 1, 1);
INSERT INTO `article_type` VALUES (4, 0, 'Java', 0, 1);
INSERT INTO `article_type` VALUES (5, 4, '多线程', 0, 1);
INSERT INTO `article_type` VALUES (6, 4, '定时任务', 0, 1);
INSERT INTO `article_type` VALUES (7, 6, '自旋锁', 0, 1);

-- ----------------------------
-- Table structure for blog_diary
-- ----------------------------
DROP TABLE IF EXISTS `blog_diary`;
CREATE TABLE `blog_diary`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NULL DEFAULT NULL COMMENT '创建用户',
  `diary_md` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '日记md格式',
  `diary_date` date NULL DEFAULT NULL COMMENT '日记日期',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '最近更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog_diary
-- ----------------------------
INSERT INTO `blog_diary` VALUES (41, 1, '这个是7-13的日记测试 我再次修改', '2022-07-13', '2022-07-14 17:12:29', '2022-07-15 09:13:39');
INSERT INTO `blog_diary` VALUES (42, 1, '这个是7-14', '2022-07-14', '2022-07-14 17:12:29', '2022-07-14 17:16:31');
INSERT INTO `blog_diary` VALUES (43, 1, '11-04日期', '2022-11-04', '2022-07-15 09:13:39', '2022-07-15 09:13:39');
INSERT INTO `blog_diary` VALUES (44, 1, '11-05日期', '2022-11-05', '2022-08-04 14:07:37', '2022-08-04 14:07:40');

-- ----------------------------
-- Table structure for doc_catalog
-- ----------------------------
DROP TABLE IF EXISTS `doc_catalog`;
CREATE TABLE `doc_catalog`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '创建者id',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父目录id',
  `doc_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文档名字',
  `doc_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文档类型（catalog：目录页 content：内容页）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of doc_catalog
-- ----------------------------
INSERT INTO `doc_catalog` VALUES (24, 1, 0, '25365', 'catalog');
INSERT INTO `doc_catalog` VALUES (25, 1, 0, '787', 'content');
INSERT INTO `doc_catalog` VALUES (26, 1, 24, '25365', 'content');
INSERT INTO `doc_catalog` VALUES (30, 1, 26, '26262626', 'content');

-- ----------------------------
-- Table structure for doc_content
-- ----------------------------
DROP TABLE IF EXISTS `doc_content`;
CREATE TABLE `doc_content`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '创建者id',
  `catalog_id` int(11) NULL DEFAULT NULL COMMENT '目录id',
  `doc_content_md` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'md格式文档',
  `browse_count` int(11) NULL DEFAULT NULL COMMENT '文档浏览次数',
  `like_count` int(11) NULL DEFAULT NULL COMMENT '文档点赞次数',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '文档创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '文档最近修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of doc_content
-- ----------------------------
INSERT INTO `doc_content` VALUES (6, 1, 26, NULL, 0, 0, '2022-06-22 13:36:17', '2022-06-22 13:36:17');
INSERT INTO `doc_content` VALUES (7, 1, 25, NULL, 0, 0, '2022-06-22 13:37:42', '2022-06-22 13:37:42');
INSERT INTO `doc_content` VALUES (11, 1, 30, '232', 0, 0, '2022-06-22 15:43:52', '2022-06-22 15:43:52');

SET FOREIGN_KEY_CHECKS = 1;
