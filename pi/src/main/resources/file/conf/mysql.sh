#! /bin/bash
# mysql数据导入脚本

# 数据库名字
blogPiSql="blog_pi"

# MySQL登陆密码
mysqlPassword=

mysqlSQL() {
  # 等待MySQL启动完成
  sleep 1m
  mysql -uroot -p${mysqlPassword} <<EOF

  USE mysql;
  update user set host='%' where user='root';
  flush privileges;

  drop database if exists ${blogPiSql};
  CREATE DATABASE  ${blogPiSql} DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
  use ${blogPiSql};
  source /opt/docker/files/sql/${blogPiSql}.sql;

  exit

EOF
  exit 0
}

mysqlSQL