/*
 Navicat Premium Data Transfer

 Source Server         : 本机MySQL
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : blog_content

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 18/05/2023 17:17:13
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
) ENGINE = InnoDB AUTO_INCREMENT = 80 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


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
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '标签创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '标签最近更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_label
-- ----------------------------
INSERT INTO `article_label` VALUES (1, 1, 1, '算法', 2, '2023-04-17 23:18:17', '2023-04-17 23:18:17');
INSERT INTO `article_label` VALUES (2, 1, 1, '多线程', 2, '2023-04-17 23:18:17', '2023-04-17 23:18:17');
INSERT INTO `article_label` VALUES (3, 1, 1, 'mybatis', 1, '2023-04-17 23:18:17', '2023-04-17 23:18:17');
INSERT INTO `article_label` VALUES (4, 1, 2, 'dfs', 1, '2023-04-17 23:18:17', '2023-04-17 23:18:17');

-- ----------------------------
-- Table structure for article_label_type
-- ----------------------------
DROP TABLE IF EXISTS `article_label_type`;
CREATE TABLE `article_label_type`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `type_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章标签分类名称',
  `label_num` tinyint(4) NULL DEFAULT NULL COMMENT '标签数量',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '标签分类创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '标签分类最近更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_label_type
-- ----------------------------
INSERT INTO `article_label_type` VALUES (1, 1, 'java', 3, '2023-04-17 23:17:04', '2023-04-17 23:17:04');
INSERT INTO `article_label_type` VALUES (2, 1, '算法', 1, '2023-04-17 23:17:04', '2023-04-17 23:17:04');

-- ----------------------------
-- Table structure for article_type
-- ----------------------------
DROP TABLE IF EXISTS `article_type`;
CREATE TABLE `article_type`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '文章分类上级 根分级为0',
  `type_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `create_user` int(11) NULL DEFAULT NULL COMMENT '创建用户',
  `num` int(11) NULL DEFAULT NULL COMMENT '文章数量',
  `node` tinyint(2) NULL DEFAULT NULL COMMENT '是否有子节点 （0：无 1：有）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_type
-- ----------------------------
INSERT INTO `article_type` VALUES (1, 0, '算法', 1, 2, 1, '2023-04-17 23:15:20', '2023-04-17 23:15:24');
INSERT INTO `article_type` VALUES (2, 1, '洛谷', 1, 2, 1, '2023-04-17 23:15:20', '2023-04-17 23:15:24');
INSERT INTO `article_type` VALUES (3, 2, '排序', 1, 1, 1, '2023-04-17 23:15:20', '2023-04-17 23:15:20');
INSERT INTO `article_type` VALUES (4, 2, '搜索', 1, 0, 0, '2023-04-17 23:15:20', '2023-04-17 23:15:20');
INSERT INTO `article_type` VALUES (5, 0, 'MySQL', 1, 0, 1, '2023-04-17 23:15:20', '2023-04-17 23:15:20');
INSERT INTO `article_type` VALUES (6, 0, 'Spring', 1, 0, 1, '2023-04-17 23:15:20', '2023-04-17 23:15:20');
INSERT INTO `article_type` VALUES (7, 6, '注解', 1, 0, 1, '2023-04-17 23:15:20', '2023-04-17 23:15:20');
INSERT INTO `article_type` VALUES (8, 0, '安装教程', 1, 0, 1, '2023-04-17 23:15:20', '2023-04-17 23:15:20');
INSERT INTO `article_type` VALUES (9, 8, 'Centos', 1, 0, 1, '2023-04-17 23:15:20', '2023-04-17 23:15:20');
INSERT INTO `article_type` VALUES (10, 0, 'Mybatis', 1, 0, 1, '2023-04-17 23:15:20', '2023-04-17 23:15:20');
INSERT INTO `article_type` VALUES (11, 10, '学习', 1, 0, 1, '2023-04-17 23:15:20', '2023-04-17 23:15:20');
INSERT INTO `article_type` VALUES (12, 10, '使用', 1, 0, 1, '2023-04-17 23:15:20', '2023-04-17 23:15:20');
INSERT INTO `article_type` VALUES (13, 0, 'JAVA', 1, 0, 1, '2023-04-17 23:15:20', '2023-04-17 23:15:20');
INSERT INTO `article_type` VALUES (14, 13, 'JVM', 1, 0, 1, '2023-04-17 23:15:20', '2023-04-17 23:15:20');
INSERT INTO `article_type` VALUES (15, 0, 'ActiveMQ', 1, 0, 0, '2023-04-17 23:15:20', '2023-04-17 23:15:20');
INSERT INTO `article_type` VALUES (16, 15, '学习', 1, 0, 1, '2023-04-17 23:15:20', '2023-04-17 23:15:20');
INSERT INTO `article_type` VALUES (17, 15, '使用', 1, 0, 1, '2023-04-17 23:15:20', '2023-04-17 23:15:20');
INSERT INTO `article_type` VALUES (18, 0, '年终总结', 1, 0, 0, '2023-04-17 23:15:20', '2023-04-17 23:15:20');

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
) ENGINE = InnoDB AUTO_INCREMENT = 1104 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_diary
-- ----------------------------

-- ----------------------------
-- Table structure for doc_catalog
-- ----------------------------
DROP TABLE IF EXISTS `doc_catalog`;
CREATE TABLE `doc_catalog`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '创建者id',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父目录id',
  `doc_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文档名字',
  `doc_level` tinyint(4) NULL DEFAULT NULL COMMENT '目录层级(0-4) 0：文档分类 1：文档  2、3：文档章节&文档内容 4：文档内容',
  `doc_type` tinyint(4) NULL DEFAULT NULL COMMENT '文档分类 (0,1) 0：目录 1：内容',
  `sort` smallint(6) NULL DEFAULT NULL COMMENT '排序id',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '封面图片url (封面层级一定是1)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of doc_catalog
-- ----------------------------
INSERT INTO `doc_catalog` VALUES (1, 1, 0, '日常记录', 0, 0, 1, NULL, '2023-01-23 09:47:04', '2023-01-23 09:47:24');
INSERT INTO `doc_catalog` VALUES (2, 1, 1, '年终总结', 1, 0, 1, 'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png', '2023-02-06 22:17:09', '2023-02-06 22:17:09');
INSERT INTO `doc_catalog` VALUES (3, 1, 1, '读书笔记', 1, 0, 1, 'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png', '2023-02-19 17:38:00', '2023-02-19 17:38:00');
INSERT INTO `doc_catalog` VALUES (4, 1, 3, '《献给阿尔吉侬的花束》', 2, 1, 1, NULL, '2023-02-19 17:39:22', '2023-02-19 17:39:22');
INSERT INTO `doc_catalog` VALUES (5, 1, 0, '游戏记录', 0, 0, NULL, NULL, '2023-01-23 09:47:04', '2023-01-23 09:47:24');
INSERT INTO `doc_catalog` VALUES (6, 1, 3, '科幻小说', 2, 0, 1, NULL, '2023-02-06 22:17:09', '2023-02-06 22:17:09');
INSERT INTO `doc_catalog` VALUES (7, 1, 6, '《火星救援》', 3, 1, NULL, NULL, '2023-02-19 17:38:00', '2023-02-19 17:38:00');
INSERT INTO `doc_catalog` VALUES (8, 1, 6, '《三体》', 3, 1, NULL, NULL, '2023-02-19 17:39:22', '2023-02-19 17:39:22');
INSERT INTO `doc_catalog` VALUES (9, 1, 5, '缺氧', 1, 0, 1, 'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png', '2023-02-19 17:38:00', '2023-02-19 17:38:00');
INSERT INTO `doc_catalog` VALUES (10, 1, 5, '异星工厂', 1, 0, 1, 'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png', '2023-02-19 17:38:00', '2023-02-19 17:38:00');
INSERT INTO `doc_catalog` VALUES (11, 1, 5, '饥荒', 1, 0, 1, 'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png', '2023-02-19 17:38:00', '2023-02-19 17:38:00');
INSERT INTO `doc_catalog` VALUES (12, 1, 5, '哈迪斯', 1, 0, 1, 'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png', '2023-02-19 17:38:00', '2023-02-19 17:38:00');
INSERT INTO `doc_catalog` VALUES (13, 1, 5, 'GTA5', 1, 0, 1, 'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png', '2023-02-19 17:38:00', '2023-02-19 17:38:00');
INSERT INTO `doc_catalog` VALUES (14, 1, 10, '异星工厂第一个存档', 2, 1, 1, NULL, '2023-05-15 16:22:26', '2023-02-19 17:38:00');
INSERT INTO `doc_catalog` VALUES (15, 1, 10, '异星工厂第二个存档', 2, 1, 1, NULL, '2023-02-19 17:38:00', '2023-02-19 17:38:00');
INSERT INTO `doc_catalog` VALUES (16, 1, 1, '博客接口文档', 1, 0, 1, 'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png', '2023-02-19 17:38:00', '2023-02-19 17:38:00');
INSERT INTO `doc_catalog` VALUES (17, 1, 3, '用户服务', 2, 0, 1, NULL, '2023-02-19 17:38:00', '2023-02-19 17:38:00');
INSERT INTO `doc_catalog` VALUES (18, 1, 3, '内容服务', 2, 0, 1, NULL, '2023-05-15 16:22:26', '2023-02-19 17:38:00');
INSERT INTO `doc_catalog` VALUES (19, 1, 3, '文件服务', 2, 0, 1, NULL, '2023-02-19 17:38:00', '2023-02-19 17:38:00');
INSERT INTO `doc_catalog` VALUES (20, 1, 18, '文章接口', 3, 0, 1, NULL, '2023-02-19 17:38:00', '2023-02-19 17:38:00');
INSERT INTO `doc_catalog` VALUES (21, 1, 18, '文档接口', 3, 0, 1, NULL, '2023-02-19 17:38:00', '2023-02-19 17:38:00');
INSERT INTO `doc_catalog` VALUES (22, 1, 18, '日记接口', 3, 0, 1, NULL, '2023-05-15 16:22:26', '2023-02-19 17:38:00');
INSERT INTO `doc_catalog` VALUES (23, 1, 20, '查询文章列表', 4, 1, 1, NULL, '2023-02-19 17:38:00', '2023-02-19 17:38:00');
INSERT INTO `doc_catalog` VALUES (24, 1, 20, '修改文章信息', 4, 1, 1, NULL, '2023-02-19 17:38:00', '2023-02-19 17:38:00');

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
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of doc_content
-- ----------------------------
INSERT INTO `doc_content` VALUES (1, 1, 4, '![献给阿尔吉侬的花束-封面.jpg](http://121.4.126.60/file/img/doc/2023-02-19_17-40-54_TZhkQ_献给阿尔吉侬的花束-封面.jpg)\r\n### 书名： 《献给阿尔吉侬的花束》\r\n### 时间： 2023-01.13 ~~ 2023-02.13\r\n### 整体评价： ★★★★☆\r\n评价理由： 故事非常精彩，讲述了一个傻子从开始接受变聪明的手术到变得非常聪明最后又因为手术的原因导致智力水平又变回最开始的样子，全书以主角第一人称的笔记来记录的，有点类似《火星救援》，看书的时候稍微有点代入感，但是也因为这个原因经常就没看记录日期，需要倒回去重新看，之所以给四星是因为看到后面已经预料到结局了，读之后的剧情就好像是在看主角给自己定制棺材一样，有点凄凉。\r\n个人感想： 想半天没啥感想，但是因为这个是第一期读书笔记稍微写点吧。感觉这本书写的主要剧情也就是周围人的态度，与主角个人的心里成长和智慧上的成长。在心理方面是对纪尼安小姐的感情吧，智慧方面是从儿童智慧开始，然后不断成长，慢慢超越周围人的认知，以至于对所有人的评价包括对他做手术的教授也觉得是如此的幼稚。阿尔吉侬是和主角接受同一个手术的老鼠，但是阿尔吉侬接受手术的时间要早于主角，所以书中描写这只老鼠的变化的时候差不多也就宣判了主角的命运，书名叫这个我猜大概是表达主角内心的孤独吧，在手术之前虽然没有朋友，但周围的人至少都欢迎他，等变聪明之后发现原来当时的受欢迎也只不过是被人捉弄罢了，最后又恢复初始智商时认为这不过是一场梦，那只不过是他的另一个人格，就是不知道最后结局会不会和阿尔吉侬一样。最后总结虽然是本好书，但我不是很喜欢，也可能是我人生阅历还不够。', 0, 0, '2023-02-19 17:39:22', '2023-02-19 18:22:27');
INSERT INTO `doc_content` VALUES (2, 1, 7, '这是火星救援的文档内容\r\n### 测试标题一\r\n### 测试标题二\r\n### 测试标题三\r\n### 测试标题四', 0, 0, '2023-02-19 17:39:22', '2023-02-19 17:39:22');
INSERT INTO `doc_content` VALUES (3, 1, 23, '文档接口-11', 0, 0, '2023-02-19 17:39:22', '2023-02-19 18:22:27');
INSERT INTO `doc_content` VALUES (4, 1, 24, '文档接口-22', 0, 0, '2023-02-19 17:39:22', '2023-02-19 17:39:22');

SET FOREIGN_KEY_CHECKS = 1;
