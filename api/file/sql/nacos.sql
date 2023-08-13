/*
 Navicat Premium Data Transfer

 Source Server         : 本机
 Source Server Type    : MySQL
 Source Server Version : 50651
 Source Host           : localhost:3306
 Source Schema         : nacos

 Target Server Type    : MySQL
 Target Server Version : 50651
 File Encoding         : 65001

 Date: 13/08/2023 20:32:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_use` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `effect` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_schema` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `encrypted_data_key` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfo_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info` VALUES (26, 'blog-auth-dev.yaml', 'DEFAULT_GROUP', 'devServiceIp: localhost\nserver:\n  port: 9200\n  servlet:\n    context-path: /auth\n  ssl:\n    # enabled: true  #开启ssl验证\n    enabled: false  #开启ssl验证\n    key-store: classpath:keystore.p12 #证书文件位置\n    key-store-password: gszero  #上面的密钥口令\n    key-store-type: PKCS12   #storetype 上面的类型\n    key-alias: tomcat    #tomcat上面的alias  别名\n\nspring:\n  application:\n    name: blog-auth\n  cloud:\n    nacos:\n      discovery:\n        server-addr: ${devServiceIp}:8848\n  redis:\n    database: 0\n    host: ${devServiceIp}\n    port: 6379\n    password: redis-960\n    jedis:\n      pool:\n        max-active: 8\n        max-idle: 8\n        min-idle: 0\n  datasource:\n    driver-class-name: com.mysql.jdbc.Driver\n    url: jdbc:mysql://${devServiceIp}:3306/blog_auth?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&serverTimezone=GMT%2B8\n    username: root\n    password: root\n\nrocketmq:\n  name-server: ${devServiceIp}:9876 # 访问地址\n  producer:\n    group: blog # 必须指定group\n    send-message-timeout: 3000 # 消息发送超时时长，默认3s\n    retry-times-when-send-failed: 3 # 同步发送消息失败重试次数，默认2\n    retry-times-when-send-async-failed: 3 # 异步发送消息失败重试次数，默认2\n    \nlogging:\n  level:\n    com.alibaba.nacos.client.config.impl: WARN\n\naccess_token:\n  jwt-signing-key: gszero\n  add-userInfo: false\n\noauth:\n  gitee:\n    clientId: b56892ea476c32e017d279841a6422bd1dce3c5d87985588c206075f8e55e0a4\n    clientSecret: 761846f04570945c1add7dba4dc9e3512238b3ce9ed3ca5449dad9a2b32d563a\n    callbackUrl: https://${devServiceIp}:9527/auth/gitee/callback', '743deb79da0e426f6e496a9fb5f0076e', '2022-07-17 03:39:42', '2023-06-26 11:43:37', 'nacos', '0:0:0:0:0:0:0:1', '', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (27, 'blog-content-dev.yaml', 'DEFAULT_GROUP', 'devServiceIp: localhost\nserver:\n  port: 10100\n  servlet:\n    context-path: /content\n\n  ssl:\n    # enabled: true  #开启ssl验证\n    enabled: false  #开启ssl验证\n    key-store: classpath:keystore.p12 #证书文件位置\n    key-store-password: gszero  #上面的密钥口令\n    key-store-type: PKCS12   #storetype 上面的类型\n    key-alias: tomcat    #tomcat上面的alias  别名\n\nspring:\n  application:\n    name: blog-content\n  cloud:\n    nacos:\n      discovery:\n        server-addr: ${devServiceIp}:8848\n  redis:\n    database: 0\n    host: ${devServiceIp}\n    port: 6379\n    password: redis-960\n    jedis:\n      pool:\n        max-active: 8\n        max-idle: 8\n        min-idle: 0\n  datasource:\n    driver-class-name: com.mysql.jdbc.Driver\n    url: jdbc:mysql://${devServiceIp}:3306/blog_content?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&serverTimezone=GMT%2B8\n    username: root\n    password: root\n\nrocketmq:\n  name-server: ${devServiceIp}:9876 # 访问地址\n  producer:\n    group: blog # 必须指定group\n    send-message-timeout: 3000 # 消息发送超时时长，默认3s\n    retry-times-when-send-failed: 3 # 同步发送消息失败重试次数，默认2\n    retry-times-when-send-async-failed: 3 # 异步发送消息失败重试次数，默认2\n    \nmybatis:\n  mapper-locations: classpath:/mapper/*.xml\n\nlogging:\n  level:\n    com.alibaba.nacos.client.config.impl: WARN\n\nsecurity:\n  oauth2:\n    resource:\n      id: blog-content\n      user-info-uri: http://${devServiceIp}:9200/auth/user-me\n      prefer-token-info: false\n', '2d14d7ccc6dd65f00d10387f383de340', '2022-07-17 03:40:08', '2023-06-26 11:43:52', 'nacos', '0:0:0:0:0:0:0:1', '', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (28, 'blog-user-dev.yaml', 'DEFAULT_GROUP', 'devServiceIp: localhost\nserver:\n  port: 9100\n  servlet:\n    context-path: /user\n  ssl:\n    # enabled: true  #开启ssl验证\n    enabled: false  #开启ssl验证\n    key-store: classpath:keystore.p12 #证书文件位置\n    key-store-password: gszero  #上面的密钥口令\n    key-store-type: PKCS12   #storetype 上面的类型\n    key-alias: tomcat    #tomcat上面的alias  别名\n\nspring:\n  application:\n    name: blog-user\n  cloud:\n    nacos:\n      discovery:\n        server-addr: ${devServiceIp}:8848\n  redis:\n    database: 0\n    host: ${devServiceIp}\n    port: 6379\n    password: redis-960\n    jedis:\n      pool:\n        max-active: 8\n        max-idle: 8\n        min-idle: 0\n  datasource:\n    driver-class-name: com.mysql.jdbc.Driver\n    url: jdbc:mysql://${devServiceIp}:3306/blog_user?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&serverTimezone=GMT%2B8\n    username: root\n    password: root\n\nrocketmq:\n  name-server: ${devServiceIp}:9876 # 访问地址\n  producer:\n    group: blog # 必须指定group\n    send-message-timeout: 3000 # 消息发送超时时长，默认3s\n    retry-times-when-send-failed: 3 # 同步发送消息失败重试次数，默认2\n    retry-times-when-send-async-failed: 3 # 异步发送消息失败重试次数，默认2\n    \nmybatis:\n  mapper-locations: classpath:/mapper/*.xml\n\nlogging:\n  level:\n    com.alibaba.nacos.client.config.impl: WARN\n\nsecurity:\n  oauth2:\n    resource:\n      id: blog-user\n      user-info-uri: http://${devServiceIp}:9200/auth/user-me\n      prefer-token-info: false\n\n', '3b637a1a247a5af1683ea1ab740c8f3b', '2022-07-17 03:40:23', '2023-06-26 11:44:37', 'nacos', '0:0:0:0:0:0:0:1', '', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (29, 'blog-file-dev.yaml', 'DEFAULT_GROUP', 'devServiceIp: localhost\nserver:\n  port: 10200\n  servlet:\n    context-path: /file\n  ssl:\n    # enabled: true  #开启ssl验证\n    enabled: false  #开启ssl验证\n    key-store: classpath:keystore.p12 #证书文件位置\n    key-store-password: gszero  #上面的密钥口令\n    key-store-type: PKCS12   #storetype 上面的类型\n    key-alias: tomcat    #tomcat上面的alias  别名\n\nspring:\n  application:\n    name: blog-file\n  cloud:\n    nacos:\n      discovery:\n        server-addr: ${devServiceIp}:8848\n  redis:\n    database: 0\n    host: ${devServiceIp}\n    port: 6379\n    password: redis-960\n    jedis:\n      pool:\n        max-active: 8\n        max-idle: 8\n        min-idle: 0\n  datasource:\n    driver-class-name: com.mysql.jdbc.Driver\n    url: jdbc:mysql://${devServiceIp}:3306/blog_file?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&serverTimezone=GMT%2B8\n    username: root\n    password: root\n\n  \n  servlet:\n    multipart:\n      max-file-size: 110MB # 设置单个文件大小\n      max-request-size: 110MB # 设置单次请求文件的总大小\n      \nrocketmq:\n  name-server: ${devServiceIp}:9876 # 访问地址\n  producer:\n    group: blog # 必须指定group\n    send-message-timeout: 3000 # 消息发送超时时长，默认3s\n    retry-times-when-send-failed: 3 # 同步发送消息失败重试次数，默认2\n    retry-times-when-send-async-failed: 3 # 异步发送消息失败重试次数，默认2\n\nmybatis:\n  mapper-locations: classpath:/mapper/*.xml\n\nlogging:\n  level:\n    com.alibaba.nacos.client.config.impl: WARN\n\nsecurity:\n  oauth2:\n    resource:\n      id: blog-file\n      user-info-uri: http://${devServiceIp}:9200/auth/user-me\n      prefer-token-info: false\n\nfile:\n  system: win\n  basePath: D:/files\n  serviceIp: http://${devServiceIp}\n  baseUri: /files\n', '982cac0ad75190f21250b47e08514702', '2022-07-17 03:40:41', '2023-08-11 08:28:54', 'nacos', '0:0:0:0:0:0:0:1', '', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (30, 'blog-gateway-dev.yaml', 'DEFAULT_GROUP', 'devServiceIp: localhost\nserver:\n  port: 9527\n  # servlet:\n  #   context-path: /gateway\n  ssl:\n    # enabled: true  #开启ssl验证\n    enabled: false  #开启ssl验证\n    key-store: classpath:keystore.p12 #证书文件位置\n    key-store-password: gszero  #上面的密钥口令\n    key-store-type: PKCS12   #storetype 上面的类型\n    key-alias: tomcat    #tomcat上面的alias  别名\n    \nspring:\n  application:\n    name: blog-gateway\n  datasource:\n    driver-class-name: com.mysql.jdbc.Driver\n    url: jdbc:mysql://${devServiceIp}:3306/blog_gateway?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&serverTimezone=GMT%2B8\n    username: root\n    password: root\n  redis:\n    database: 0\n    host: ${devServiceIp}\n    port: 6379\n    password: redis-960\n    jedis:\n      pool:\n        max-active: 8\n        max-idle: 8\n        min-idle: 0\n  cloud:\n    nacos:\n      discovery:\n        server-addr: ${devServiceIp}:8848\n    gateway:\n      globalcors:\n        # gateway 跨域设置\n        cors-configurations:\n          \'[/**]\':\n            allowedOrigins: \"*\"\n            allowedHeaders: \"*\"\n            allowCredentials: true\n            allowedMethods:\n              - GET\n              - POST\n              - PUT\n              - DELETE\n              - OPTIONS\n      # 设置与服务注册发现组件结合，这样可以采用服务名的路由策略\n      discovery:\n        locator:\n          enabled: true\n      routes:\n        # 配置路由规则\n        - id: blog-auth #payment_route    #路由的ID，没有固定规则但要求唯一，建议配合服务名\n          uri: lb://blog-auth #匹配后提供服务的路由地址\n          predicates:\n            - Path=/auth/**         # 断言，路径相匹配的进行路由\n\n        - id: blog-user\n          uri: lb://blog-user\n          predicates:\n            - Path=/user/**\n\n        - id: blog-content\n          uri: lb://blog-content\n          predicates:\n            - Path=/content/**\n\n        - id: blog-file\n          uri: lb://blog-file\n          predicates:\n            - Path=/file/**\n\nrocketmq:\n  name-server: ${devServiceIp}:9876 # 访问地址\n  producer:\n    group: blog # 必须指定group\n    send-message-timeout: 3000 # 消息发送超时时长，默认3s\n    retry-times-when-send-failed: 3 # 同步发送消息失败重试次数，默认2\n    retry-times-when-send-async-failed: 3 # 异步发送消息失败重试次数，默认2\n\nmybatis:\n  mapper-locations: classpath:/mapper/*.xml\n\nlogging:\n  level:\n    com.alibaba.nacos.client.config.impl: WARN\n\nsecurity:\n  oauth2:\n    resource:\n      id: blog-gateway\n      user-info-uri: http://${devServiceIp}:9200/auth/user-me\n      prefer-token-info: false\n\n', 'aef588fca04d9b0e39d0a78cd28b5d49', '2022-07-17 03:41:02', '2023-06-26 11:43:15', 'nacos', '0:0:0:0:0:0:0:1', '', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (31, 'blog-auth-pro.yaml', 'DEFAULT_GROUP', 'devServiceIp: localhost\nserver:\n  port: 9200\n  servlet:\n    context-path: /auth\n  \n  ssl:\n    # enabled: true  #开启ssl验证\n    enabled: false  #开启ssl验证\n    key-store: classpath:keystore.p12 #证书文件位置\n    key-store-password: gszero  #上面的密钥口令\n    key-store-type: PKCS12   #storetype 上面的类型\n    key-alias: tomcat    #tomcat上面的alias  别名\n\nspring:\n  application:\n    name: blog-auth\n  cloud:\n    nacos:\n      discovery:\n        server-addr: ${devServiceIp}:8848\n  redis:\n    database: 0\n    host: ${devServiceIp}\n    port: 6379\n    password: redis-960\n    jedis:\n      pool:\n        max-active: 8\n        max-idle: 8\n        min-idle: 0\n  datasource:\n    driver-class-name: com.mysql.jdbc.Driver\n    url: jdbc:mysql://${devServiceIp}:3306/blog_auth?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&serverTimezone=GMT%2B8\n    username: root\n    password: MySql@Admin123*.\n\nrocketmq:\n  name-server: ${devServiceIp}:9876 # 访问地址\n  producer:\n    group: blog # 必须指定group\n    send-message-timeout: 3000 # 消息发送超时时长，默认3s\n    retry-times-when-send-failed: 3 # 同步发送消息失败重试次数，默认2\n    retry-times-when-send-async-failed: 3 # 异步发送消息失败重试次数，默认2\n    \nlogging:\n  level:\n    com.alibaba.nacos.client.config.impl: WARN\n\naccess_token:\n  jwt-signing-key: gszero\n  add-userInfo: false\n\noauth:\n  gitee:\n    clientId: b56892ea476c32e017d279841a6422bd1dce3c5d87985588c206075f8e55e0a4\n    clientSecret: 761846f04570945c1add7dba4dc9e3512238b3ce9ed3ca5449dad9a2b32d563a\n    callbackUrl: https://127.0.0.1:9527/auth/gitee/callback', '56db14e110721af1e388243a965936b7', '2022-07-17 03:49:48', '2023-08-12 13:33:08', 'nacos', '0:0:0:0:0:0:0:1', '', 'pro', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (32, 'blog-content-pro.yaml', 'DEFAULT_GROUP', 'devServiceIp: localhost\nserver:\n  port: 10100\n  servlet:\n    context-path: /content\n  \n  ssl:\n    # enabled: true  #开启ssl验证\n    enabled: false  #开启ssl验证\n    key-store: classpath:keystore.p12 #证书文件位置\n    key-store-password: gszero  #上面的密钥口令\n    key-store-type: PKCS12   #storetype 上面的类型\n    key-alias: tomcat    #tomcat上面的alias  别名\n\nspring:\n  application:\n    name: blog-content\n  cloud:\n    nacos:\n      discovery:\n        server-addr: ${devServiceIp}:8848\n  redis:\n    database: 0\n    host: ${devServiceIp}\n    port: 6379\n    password: redis-960\n    jedis:\n      pool:\n        max-active: 8\n        max-idle: 8\n        min-idle: 0\n  datasource:\n    driver-class-name: com.mysql.jdbc.Driver\n    url: jdbc:mysql://${devServiceIp}:3306/blog_content?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&serverTimezone=GMT%2B8\n    username: root\n    password: MySql@Admin123*.\n\nrocketmq:\n  name-server: ${devServiceIp}:9876 # 访问地址\n  producer:\n    group: blog # 必须指定group\n    send-message-timeout: 3000 # 消息发送超时时长，默认3s\n    retry-times-when-send-failed: 3 # 同步发送消息失败重试次数，默认2\n    retry-times-when-send-async-failed: 3 # 异步发送消息失败重试次数，默认2\n\nmybatis:\n  mapper-locations: classpath:/mapper/*.xml\n\nlogging:\n  level:\n    com.alibaba.nacos.client.config.impl: WARN\n\nsecurity:\n  oauth2:\n    resource:\n      id: blog-content\n      user-info-uri: http://${devServiceIp}:9200/auth/user-me\n      prefer-token-info: false', '6507fe6b7d35de02fdaed61389d811c8', '2022-07-17 03:50:25', '2023-08-12 13:38:39', 'nacos', '0:0:0:0:0:0:0:1', '', 'pro', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (33, 'blog-user-pro.yaml', 'DEFAULT_GROUP', 'devServiceIp: localhost\nserver:\n  port: 9100\n  servlet:\n    context-path: /user\n  ssl:\n    # enabled: true  #开启ssl验证\n    enabled: false  #开启ssl验证\n    key-store: classpath:keystore.p12 #证书文件位置\n    key-store-password: gszero  #上面的密钥口令\n    key-store-type: PKCS12   #storetype 上面的类型\n    key-alias: tomcat    #tomcat上面的alias  别名\n\nspring:\n  application:\n    name: blog-user\n  cloud:\n    nacos:\n      discovery:\n        server-addr: ${devServiceIp}:8848\n  redis:\n    database: 0\n    host: ${devServiceIp}\n    port: 6379\n    password: redis-960\n    jedis:\n      pool:\n        max-active: 8\n        max-idle: 8\n        min-idle: 0\n  datasource:\n    driver-class-name: com.mysql.jdbc.Driver\n    url: jdbc:mysql://${devServiceIp}:3306/blog_user?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&serverTimezone=GMT%2B8\n    username: root\n    password: MySql@Admin123*.\n\nrocketmq:\n  name-server: ${devServiceIp}:9876 # 访问地址\n  producer:\n    group: blog # 必须指定group\n    send-message-timeout: 3000 # 消息发送超时时长，默认3s\n    retry-times-when-send-failed: 3 # 同步发送消息失败重试次数，默认2\n    retry-times-when-send-async-failed: 3 # 异步发送消息失败重试次数，默认2\n    \nmybatis:\n  mapper-locations: classpath:/mapper/*.xml\n\nlogging:\n  level:\n    com.alibaba.nacos.client.config.impl: WARN\n\nsecurity:\n  oauth2:\n    resource:\n      id: blog-user\n      user-info-uri: http://${devServiceIp}:9200/auth/user-me\n      prefer-token-info: false', '2c775164296c04aa08c793a29c9eed4e', '2022-07-17 03:50:47', '2023-08-12 13:46:17', 'nacos', '0:0:0:0:0:0:0:1', '', 'pro', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (34, 'blog-file-pro.yaml', 'DEFAULT_GROUP', 'devServiceIp: localhost\n\nserver:\n  port: 10200\n  servlet:\n    context-path: /file\n  ssl:\n    # enabled: true  #开启ssl验证\n    enabled: false  #开启ssl验证\n    key-store: classpath:keystore.p12 #证书文件位置\n    key-store-password: gszero  #上面的密钥口令\n    key-store-type: PKCS12   #storetype 上面的类型\n    key-alias: tomcat    #tomcat上面的alias  别名\n\nspring:\n  application:\n    name: blog-file\n  cloud:\n    nacos:\n      discovery:\n        server-addr: ${devServiceIp}:8848\n  redis:\n    database: 0\n    host: ${devServiceIp}\n    port: 6379\n    password: redis-960\n    jedis:\n      pool:\n        max-active: 8\n        max-idle: 8\n        min-idle: 0\n  datasource:\n    driver-class-name: com.mysql.jdbc.Driver\n    url: jdbc:mysql://${devServiceIp}:3306/blog_file?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&serverTimezone=GMT%2B8\n    username: root\n    password: MySql@Admin123*.\n  servlet:\n    multipart:\n      max-file-size: 110MB # 设置单个文件大小\n      max-request-size: 110MB # 设置单次请求文件的总大小\n\nrocketmq:\n  name-server: ${devServiceIp}:9876 # 访问地址\n  producer:\n    group: blog # 必须指定group\n    send-message-timeout: 3000 # 消息发送超时时长，默认3s\n    retry-times-when-send-failed: 3 # 同步发送消息失败重试次数，默认2\n    retry-times-when-send-async-failed: 3 # 异步发送消息失败重试次数，默认2\n\nmybatis:\n  mapper-locations: classpath:/mapper/*.xml\n\nlogging:\n  level:\n    com.alibaba.nacos.client.config.impl: WARN\n\nsecurity:\n  oauth2:\n    resource:\n      id: blog-file\n      user-info-uri: http://${devServiceIp}:9200/auth/user-me\n      prefer-token-info: false\n\nfile:\n  system: linux\n  basePath: /opt/files\n  baseUri: /files\n  serviceIp: http://124.221.12.158', 'a974be6046cf296f28d33929136415e8', '2022-07-17 03:51:35', '2023-08-13 12:31:38', 'nacos', '0:0:0:0:0:0:0:1', '', 'pro', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (35, 'blog-gateway-pro.yaml', 'DEFAULT_GROUP', 'devServiceIp: localhost\n\nserver:\n  port: 9527\n  ssl:\n    # enabled: true  #开启ssl验证\n    enabled: false  #开启ssl验证\n    key-store: classpath:keystore.p12 #证书文件位置\n    key-store-password: gszero  #上面的密钥口令\n    key-store-type: PKCS12   #storetype 上面的类型\n    key-alias: tomcat    #tomcat上面的alias  别名\n\nspring:\n  application:\n    name: blog-gateway\n  datasource:\n    driver-class-name: com.mysql.jdbc.Driver\n    url: jdbc:mysql://${devServiceIp}:3306/blog_gateway?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&serverTimezone=GMT%2B8\n    username: root\n    password: MySql@Admin123*.\n  redis:\n    database: 0\n    host: ${devServiceIp}\n    port: 6379\n    password: redis-960\n    jedis:\n      pool:\n        max-active: 8\n        max-idle: 8\n        min-idle: 0\n  cloud:\n    nacos:\n      discovery:\n        server-addr: ${devServiceIp}:8848\n    gateway:\n      globalcors:\n        # gateway 跨域设置\n        cors-configurations:\n          \'[/**]\':\n            allowedOrigins: \"*\"\n            allowedHeaders: \"*\"\n            allowCredentials: true\n            allowedMethods:\n              - GET\n              - POST\n              - PUT\n              - DELETE\n              - OPTIONS\n      # 设置与服务注册发现组件结合，这样可以采用服务名的路由策略\n      discovery:\n        locator:\n          enabled: true\n      routes:\n        # 配置路由规则\n        - id: blog-auth #payment_route    #路由的ID，没有固定规则但要求唯一，建议配合服务名\n          uri: lb://blog-auth #匹配后提供服务的路由地址\n          predicates:\n            - Path=/auth/**         # 断言，路径相匹配的进行路由\n\n        - id: blog-user\n          uri: lb://blog-user\n          predicates:\n            - Path=/user/**\n\n        - id: blog-content\n          uri: lb://blog-content\n          predicates:\n            - Path=/content/**\n\n        - id: blog-file\n          uri: lb://blog-file\n          predicates:\n            - Path=/file/**\n\nrocketmq:\n  name-server: ${devServiceIp}:9876 # 访问地址\n  producer:\n    group: blog # 必须指定group\n    send-message-timeout: 3000 # 消息发送超时时长，默认3s\n    retry-times-when-send-failed: 3 # 同步发送消息失败重试次数，默认2\n    retry-times-when-send-async-failed: 3 # 异步发送消息失败重试次数，默认2\n\nmybatis:\n  mapper-locations: classpath:/mapper/*.xml\n\nlogging:\n  level:\n    com.alibaba.nacos.client.config.impl: WARN\n\nsecurity:\n  oauth2:\n    resource:\n      id: blog-gateway\n      user-info-uri: http://${devServiceIp}:9200/auth/user-me\n      prefer-token-info: false', 'd4bd0b34b92a5d28dedc0d8caf3765cf', '2022-07-17 03:52:20', '2023-08-12 14:03:40', 'nacos', '0:0:0:0:0:0:0:1', '', 'pro', '', '', '', 'yaml', '', '');

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime(0) NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfoaggr_datagrouptenantdatum`(`data_id`, `group_id`, `tenant_id`, `datum_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '增加租户字段' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info_aggr
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfobeta_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_beta' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info_beta
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfotag_datagrouptenanttag`(`data_id`, `group_id`, `tenant_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_tag' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info_tag
-- ----------------------------

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `tag_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`) USING BTREE,
  UNIQUE INDEX `uk_configtagrelation_configidtag`(`id`, `tag_name`, `tag_type`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_tag_relation' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_tags_relation
-- ----------------------------

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_group_id`(`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '集群、各Group容量信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of group_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info`  (
  `id` bigint(64) UNSIGNED NOT NULL,
  `nid` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `op_type` char(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`nid`) USING BTREE,
  INDEX `idx_gmt_create`(`gmt_create`) USING BTREE,
  INDEX `idx_gmt_modified`(`gmt_modified`) USING BTREE,
  INDEX `idx_did`(`data_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '多租户改造' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
INSERT INTO `his_config_info` VALUES (29, 1, 'blog-file-dev.yaml', 'DEFAULT_GROUP', '', 'devServiceIp: localhost\nserver:\n  port: 10200\n  servlet:\n    context-path: /file\n  ssl:\n    # enabled: true  #开启ssl验证\n    enabled: false  #开启ssl验证\n    key-store: classpath:keystore.p12 #证书文件位置\n    key-store-password: gszero  #上面的密钥口令\n    key-store-type: PKCS12   #storetype 上面的类型\n    key-alias: tomcat    #tomcat上面的alias  别名\n\nspring:\n  application:\n    name: blog-file\n  cloud:\n    nacos:\n      discovery:\n        server-addr: ${devServiceIp}:8848\n  redis:\n    database: 0\n    host: ${devServiceIp}\n    port: 6379\n    password: redis-960\n    jedis:\n      pool:\n        max-active: 8\n        max-idle: 8\n        min-idle: 0\n  datasource:\n    driver-class-name: com.mysql.jdbc.Driver\n    url: jdbc:mysql://${devServiceIp}:3306/blog_file?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&serverTimezone=GMT%2B8\n    username: root\n    password: root\n\nrocketmq:\n  name-server: ${devServiceIp}:9876 # 访问地址\n  producer:\n    group: blog # 必须指定group\n    send-message-timeout: 3000 # 消息发送超时时长，默认3s\n    retry-times-when-send-failed: 3 # 同步发送消息失败重试次数，默认2\n    retry-times-when-send-async-failed: 3 # 异步发送消息失败重试次数，默认2\n\nmybatis:\n  mapper-locations: classpath:/mapper/*.xml\n\nlogging:\n  level:\n    com.alibaba.nacos.client.config.impl: WARN\n\nsecurity:\n  oauth2:\n    resource:\n      id: blog-file\n      user-info-uri: http://${devServiceIp}:9200/auth/user-me\n      prefer-token-info: false\n\nfile:\n  system: win\n  basePath: D:/files\n  serviceIp: http://${devServiceIp}\n  baseUri: /files\n', '603d366fa8f8886e1032cf08587dc2ff', '2023-08-11 16:27:30', '2023-08-11 08:27:26', 'nacos', '0:0:0:0:0:0:0:1', 'U', 'dev', '');
INSERT INTO `his_config_info` VALUES (29, 2, 'blog-file-dev.yaml', 'DEFAULT_GROUP', '', 'devServiceIp: localhost\nserver:\n  port: 10200\n  servlet:\n    context-path: /file\n  ssl:\n    # enabled: true  #开启ssl验证\n    enabled: false  #开启ssl验证\n    key-store: classpath:keystore.p12 #证书文件位置\n    key-store-password: gszero  #上面的密钥口令\n    key-store-type: PKCS12   #storetype 上面的类型\n    key-alias: tomcat    #tomcat上面的alias  别名\n\nspring:\n  application:\n    name: blog-file\n  cloud:\n    nacos:\n      discovery:\n        server-addr: ${devServiceIp}:8848\n  redis:\n    database: 0\n    host: ${devServiceIp}\n    port: 6379\n    password: redis-960\n    jedis:\n      pool:\n        max-active: 8\n        max-idle: 8\n        min-idle: 0\n  datasource:\n    driver-class-name: com.mysql.jdbc.Driver\n    url: jdbc:mysql://${devServiceIp}:3306/blog_file?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&serverTimezone=GMT%2B8\n    username: root\n    password: root\n\n  \n  servlet:\n    multipart:\n      max-file-size: 50MB # 设置单个文件大小\n      max-request-size: 50MB # 设置单次请求文件的总大小\n      \nrocketmq:\n  name-server: ${devServiceIp}:9876 # 访问地址\n  producer:\n    group: blog # 必须指定group\n    send-message-timeout: 3000 # 消息发送超时时长，默认3s\n    retry-times-when-send-failed: 3 # 同步发送消息失败重试次数，默认2\n    retry-times-when-send-async-failed: 3 # 异步发送消息失败重试次数，默认2\n\nmybatis:\n  mapper-locations: classpath:/mapper/*.xml\n\nlogging:\n  level:\n    com.alibaba.nacos.client.config.impl: WARN\n\nsecurity:\n  oauth2:\n    resource:\n      id: blog-file\n      user-info-uri: http://${devServiceIp}:9200/auth/user-me\n      prefer-token-info: false\n\nfile:\n  system: win\n  basePath: D:/files\n  serviceIp: http://${devServiceIp}\n  baseUri: /files\n', 'aab672cdc286b666d7a2ac83eb0f91ad', '2023-08-11 16:28:53', '2023-08-11 08:28:54', 'nacos', '0:0:0:0:0:0:0:1', 'U', 'dev', '');
INSERT INTO `his_config_info` VALUES (34, 3, 'blog-file-pro.yaml', 'DEFAULT_GROUP', '', 'server:\n  port: 10200\n  servlet:\n    context-path: /file\n\nspring:\n  application:\n    name: blog-file\n  cloud:\n    nacos:\n      discovery:\n        server-addr: localhost:8848\n  redis:\n    database: 0\n    host: localhost\n    port: 6379\n    password: redis-960\n    jedis:\n      pool:\n        max-active: 8\n        max-idle: 8\n        min-idle: 0\n  datasource:\n    driver-class-name: com.mysql.jdbc.Driver\n    url: jdbc:mysql://localhost:3306/blog_file?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&serverTimezone=GMT%2B8\n    username: root\n    password: MySql@Admin123*.\n\nmybatis:\n  mapper-locations: classpath:/mapper/*.xml\n\nlogging:\n  level:\n    com.alibaba.nacos.client.config.impl: WARN\n\nsecurity:\n  oauth2:\n    resource:\n      id: blog-file\n      user-info-uri: http://localhost:9200/auth/user-me\n      prefer-token-info: false\n\nfile:\n  system: linux\n  basePath: /opt\n  baseUri: /files\n  serviceIp: http://121.4.126.60', 'c0a791803860c7b477103ba24b30c906', '2023-08-12 21:24:32', '2023-08-12 13:24:32', 'nacos', '0:0:0:0:0:0:0:1', 'U', 'pro', '');
INSERT INTO `his_config_info` VALUES (31, 4, 'blog-auth-pro.yaml', 'DEFAULT_GROUP', '', 'server:\n  port: 9200\n  servlet:\n    context-path: /auth\n\nspring:\n  application:\n    name: blog-auth\n  cloud:\n    nacos:\n      discovery:\n        server-addr: localhost:8848\n  redis:\n    database: 0\n    host: localhost\n    port: 6379\n    password: redis-960\n    jedis:\n      pool:\n        max-active: 8\n        max-idle: 8\n        min-idle: 0\n  datasource:\n    driver-class-name: com.mysql.jdbc.Driver\n    url: jdbc:mysql://localhost:3306/blog_auth?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&serverTimezone=GMT%2B8\n    username: root\n    password: MySql@Admin123*.\nlogging:\n  level:\n    com.alibaba.nacos.client.config.impl: WARN\n\naccess_token:\n  jwt-signing-key: gszero\n  add-userInfo: false\n\noauth:\n  gitee:\n    clientId: b56892ea476c32e017d279841a6422bd1dce3c5d87985588c206075f8e55e0a4\n    clientSecret: 761846f04570945c1add7dba4dc9e3512238b3ce9ed3ca5449dad9a2b32d563a\n    callbackUrl: https://127.0.0.1:9527/auth/gitee/callback', '1ff0698f9b084686725bc7845e614f32', '2023-08-12 21:33:07', '2023-08-12 13:33:08', 'nacos', '0:0:0:0:0:0:0:1', 'U', 'pro', '');
INSERT INTO `his_config_info` VALUES (32, 5, 'blog-content-pro.yaml', 'DEFAULT_GROUP', '', 'server:\n  port: 10100\n  servlet:\n    context-path: /content\n\nspring:\n  application:\n    name: blog-content\n  cloud:\n    nacos:\n      discovery:\n        server-addr: localhost:8848\n  redis:\n    database: 0\n    host: localhost\n    port: 6379\n    password: redis-960\n    jedis:\n      pool:\n        max-active: 8\n        max-idle: 8\n        min-idle: 0\n  datasource:\n    driver-class-name: com.mysql.jdbc.Driver\n    url: jdbc:mysql://localhost:3306/blog_content?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&serverTimezone=GMT%2B8\n    username: root\n    password: MySql@Admin123*.\n\nmybatis:\n  mapper-locations: classpath:/mapper/*.xml\n\nlogging:\n  level:\n    com.alibaba.nacos.client.config.impl: WARN\n\nsecurity:\n  oauth2:\n    resource:\n      id: blog-content\n      user-info-uri: http://localhost:9200/auth/user-me\n      prefer-token-info: false', 'f9e937fd9cb387a1a2dbba75bb7f5940', '2023-08-12 21:38:39', '2023-08-12 13:38:39', 'nacos', '0:0:0:0:0:0:0:1', 'U', 'pro', '');
INSERT INTO `his_config_info` VALUES (33, 6, 'blog-user-pro.yaml', 'DEFAULT_GROUP', '', 'server:\n  port: 9100\n  servlet:\n    context-path: /user\n\nspring:\n  application:\n    name: blog-user\n  cloud:\n    nacos:\n      discovery:\n        server-addr: localhost:8848\n  redis:\n    database: 0\n    host: localhost\n    port: 6379\n    password: redis-960\n    jedis:\n      pool:\n        max-active: 8\n        max-idle: 8\n        min-idle: 0\n  datasource:\n    driver-class-name: com.mysql.jdbc.Driver\n    url: jdbc:mysql://localhost:3306/blog_user?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&serverTimezone=GMT%2B8\n    username: root\n    password: MySql@Admin123*.\n\nmybatis:\n  mapper-locations: classpath:/mapper/*.xml\n\nlogging:\n  level:\n    com.alibaba.nacos.client.config.impl: WARN\n\nsecurity:\n  oauth2:\n    resource:\n      id: blog-user\n      user-info-uri: http://localhost:9200/auth/user-me\n      prefer-token-info: false', '2aa0a8367e09f611e5443b4a5bc642bf', '2023-08-12 21:46:16', '2023-08-12 13:46:17', 'nacos', '0:0:0:0:0:0:0:1', 'U', 'pro', '');
INSERT INTO `his_config_info` VALUES (34, 7, 'blog-file-pro.yaml', 'DEFAULT_GROUP', '', 'server:\n  port: 10200\n  servlet:\n    context-path: /file\n\nspring:\n  application:\n    name: blog-file\n  cloud:\n    nacos:\n      discovery:\n        server-addr: localhost:8848\n  redis:\n    database: 0\n    host: localhost\n    port: 6379\n    password: redis-960\n    jedis:\n      pool:\n        max-active: 8\n        max-idle: 8\n        min-idle: 0\n  datasource:\n    driver-class-name: com.mysql.jdbc.Driver\n    url: jdbc:mysql://localhost:3306/blog_file?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&serverTimezone=GMT%2B8\n    username: root\n    password: MySql@Admin123*.\n  \n  servlet:\n    multipart:\n      max-file-size: 110MB # 设置单个文件大小\n      max-request-size: 110MB # 设置单次请求文件的总大小\n\nmybatis:\n  mapper-locations: classpath:/mapper/*.xml\n\nlogging:\n  level:\n    com.alibaba.nacos.client.config.impl: WARN\n\nsecurity:\n  oauth2:\n    resource:\n      id: blog-file\n      user-info-uri: http://localhost:9200/auth/user-me\n      prefer-token-info: false\n\nfile:\n  system: linux\n  basePath: /opt\n  baseUri: /files\n  serviceIp: http://121.4.126.60', '9d41310808de40afd54c21112db5f063', '2023-08-12 21:51:29', '2023-08-12 13:51:30', 'nacos', '0:0:0:0:0:0:0:1', 'U', 'pro', '');
INSERT INTO `his_config_info` VALUES (35, 8, 'blog-gateway-pro.yaml', 'DEFAULT_GROUP', '', 'server:\n  port: 9527\n\nspring:\n  application:\n    name: blog-gateway\n\n  datasource:\n    driver-class-name: com.mysql.jdbc.Driver\n    url: jdbc:mysql://localhost:3306/blog_gateway?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&serverTimezone=GMT%2B8\n    username: root\n    password: MySql@Admin123*.\n  \n  redis:\n    database: 0\n    host: localhost\n    port: 6379\n    password: redis-960\n    jedis:\n      pool:\n        max-active: 8\n        max-idle: 8\n        min-idle: 0\n        \n  cloud:\n    nacos:\n      discovery:\n        server-addr: localhost:8848\n\n    gateway:\n      globalcors:\n        # gateway 跨域设置\n        cors-configurations:\n          \'[/**]\':\n            allowedOrigins: \"*\"\n            allowedHeaders: \"*\"\n            allowCredentials: true\n            allowedMethods:\n              - GET\n              - POST\n              - PUT\n              - DELETE\n              - OPTIONS\n      # 设置与服务注册发现组件结合，这样可以采用服务名的路由策略\n      discovery:\n        locator:\n          enabled: true\n      routes:\n        # 配置路由规则\n        - id: blog-auth #payment_route    #路由的ID，没有固定规则但要求唯一，建议配合服务名\n          uri: lb://blog-auth #匹配后提供服务的路由地址\n          predicates:\n            - Path=/auth/**         # 断言，路径相匹配的进行路由\n\n        - id: blog-user\n          uri: lb://blog-user\n          predicates:\n            - Path=/user/**\n\n        - id: blog-content\n          uri: lb://blog-content\n          predicates:\n            - Path=/content/**\n\n        - id: blog-file\n          uri: lb://blog-file\n          predicates:\n            - Path=/file/**\n\nmybatis:\n  mapper-locations: classpath:/mapper/*.xml\n\nlogging:\n  level:\n    com.alibaba.nacos.client.config.impl: WARN\n\nsecurity:\n  oauth2:\n    resource:\n      id: blog-gateway\n      user-info-uri: http://localhost:9200/auth/user-me\n      prefer-token-info: false', '9ac8ac7a9b2fa2df52507e921f34186a', '2023-08-12 21:59:24', '2023-08-12 13:59:25', 'nacos', '0:0:0:0:0:0:0:1', 'U', 'pro', '');
INSERT INTO `his_config_info` VALUES (35, 9, 'blog-gateway-pro.yaml', 'DEFAULT_GROUP', '', 'devServiceIp: localhost\n\nserver:\n  port: 9527\n  ssl:\n    # enabled: true  #开启ssl验证\n    enabled: false  #开启ssl验证\n    key-store: classpath:keystore.p12 #证书文件位置\n    key-store-password: gszero  #上面的密钥口令\n    key-store-type: PKCS12   #storetype 上面的类型\n    key-alias: tomcat    #tomcat上面的alias  别名\n\nspring:\n  application:\n    name: blog-gateway\n  datasource:\n    driver-class-name: com.mysql.jdbc.Driver\n    url: jdbc:mysql://${devServiceIp}:3306/blog_gateway?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&serverTimezone=GMT%2B8\n    username: root\n    password: MySql@Admin123*.\n  redis:\n    database: 0\n    host: ${devServiceIp}\n    port: 6379\n    password: redis-960\n    jedis:\n      pool:\n        max-active: 8\n        max-idle: 8\n        min-idle: 0\n  cloud:\n    nacos:\n      discovery:\n        server-addr: ${devServiceIp}:8848\n    gateway:\n      globalcors:\n        # gateway 跨域设置\n        cors-configurations:\n          \'[/**]\':\n            allowedOrigins: \"*\"\n            allowedHeaders: \"*\"\n            allowCredentials: true\n            allowedMethods:\n              - GET\n              - POST\n              - PUT\n              - DELETE\n              - OPTIONS\n      # 设置与服务注册发现组件结合，这样可以采用服务名的路由策略\n      discovery:\n        locator:\n          enabled: true\n      routes:\n        # 配置路由规则\n        - id: blog-auth #payment_route    #路由的ID，没有固定规则但要求唯一，建议配合服务名\n          uri: lb://blog-auth #匹配后提供服务的路由地址\n          predicates:\n            - Path=/auth/**         # 断言，路径相匹配的进行路由\n\n        - id: blog-user\n          uri: lb://blog-user\n          predicates:\n            - Path=/user/**\n\n        - id: blog-content\n          uri: lb://blog-content\n          predicates:\n            - Path=/content/**\n\n        - id: blog-file\n          uri: lb://blog-file\n          predicates:\n            - Path=/file/**\n\nrocketmq:\n  name-server: ${devServiceIp}:9876 # 访问地址\n  producer:\n    group: blog # 必须指定group\n    send-message-timeout: 3000 # 消息发送超时时长，默认3s\n    retry-times-when-send-failed: 3 # 同步发送消息失败重试次数，默认2\n    retry-times-when-send-async-failed: 3 # 异步发送消息失败重试次数，默认2\n\nmybatis:\n  mapper-locations: classpath:/mapper/*.xml\n\nlogging:\n  level:\n    com.alibaba.nacos.client.config.impl: WARN\n\nsecurity:\n  oauth2:\n    resource:\n      id: blog-gateway\n      user-info-uri: http://${devServiceIp}:9200/auth/user-me\n      prefer-token-info: false', 'd4bd0b34b92a5d28dedc0d8caf3765cf', '2023-08-12 22:03:39', '2023-08-12 14:03:40', 'nacos', '0:0:0:0:0:0:0:1', 'U', 'pro', '');
INSERT INTO `his_config_info` VALUES (34, 10, 'blog-file-pro.yaml', 'DEFAULT_GROUP', '', 'devServiceIp: localhost\n\nserver:\n  port: 10200\n  servlet:\n    context-path: /file\n  ssl:\n    # enabled: true  #开启ssl验证\n    enabled: false  #开启ssl验证\n    key-store: classpath:keystore.p12 #证书文件位置\n    key-store-password: gszero  #上面的密钥口令\n    key-store-type: PKCS12   #storetype 上面的类型\n    key-alias: tomcat    #tomcat上面的alias  别名\n\nspring:\n  application:\n    name: blog-file\n  cloud:\n    nacos:\n      discovery:\n        server-addr: ${devServiceIp}:8848\n  redis:\n    database: 0\n    host: ${devServiceIp}\n    port: 6379\n    password: redis-960\n    jedis:\n      pool:\n        max-active: 8\n        max-idle: 8\n        min-idle: 0\n  datasource:\n    driver-class-name: com.mysql.jdbc.Driver\n    url: jdbc:mysql://${devServiceIp}:3306/blog_file?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&serverTimezone=GMT%2B8\n    username: root\n    password: MySql@Admin123*.\n  servlet:\n    multipart:\n      max-file-size: 110MB # 设置单个文件大小\n      max-request-size: 110MB # 设置单次请求文件的总大小\n\nrocketmq:\n  name-server: ${devServiceIp}:9876 # 访问地址\n  producer:\n    group: blog # 必须指定group\n    send-message-timeout: 3000 # 消息发送超时时长，默认3s\n    retry-times-when-send-failed: 3 # 同步发送消息失败重试次数，默认2\n    retry-times-when-send-async-failed: 3 # 异步发送消息失败重试次数，默认2\n\nmybatis:\n  mapper-locations: classpath:/mapper/*.xml\n\nlogging:\n  level:\n    com.alibaba.nacos.client.config.impl: WARN\n\nsecurity:\n  oauth2:\n    resource:\n      id: blog-file\n      user-info-uri: http://${devServiceIp}:9200/auth/user-me\n      prefer-token-info: false\n\nfile:\n  system: linux\n  basePath: /opt\n  baseUri: /files\n  serviceIp: http://124.221.12.158', 'f58612d4754b4253499b81c17f66420f', '2023-08-13 20:31:38', '2023-08-13 12:31:38', 'nacos', '0:0:0:0:0:0:0:1', 'U', 'pro', '');

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
  `role` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `resource` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `action` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  UNIQUE INDEX `uk_role_permission`(`role`, `resource`, `action`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of permissions
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  UNIQUE INDEX `idx_user_role`(`username`, `role`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('nacos', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数',
  `max_aggr_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '租户容量信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tenant_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint(20) NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_info_kptenantid`(`kp`, `tenant_id`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'tenant_info' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tenant_info
-- ----------------------------
INSERT INTO `tenant_info` VALUES (4, '1', 'dev', 'dev', '开发环境', 'nacos', 1658029099853, 1658029099853);
INSERT INTO `tenant_info` VALUES (5, '1', 'pro', 'pro', '博客生产环境配置', 'nacos', 1658029601586, 1658029609311);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);

SET FOREIGN_KEY_CHECKS = 1;
