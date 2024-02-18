#! /bin/bash

mkdir() {

  mkdir -p /opt/docker/package
  mv pi.zip /opt/docker/package/pi.zip

  mkdir -p /opt/docker/files/jar
  mkdir -p /opt/docker/files/sql

  cd /opt/docker/package
  unzip pi.zip

  mv blog-pi.jar /opt/docker/files/jar
  mv Dockerfile /opt/docker/files/jar

  mv blog_pi.sql /opt/docker/files/sql

  cd /
}

# 安装并配置docker
docker() {
  # 一键安装docker
	curl -fsSL https://get.docker.com | bash -s docker --mirror Aliyun
	# 启动docker
	systemctl start docker
}

mqtt() {

  # 下载emqx
  docker pull emqx/emqx:5.4.1

  # 运行emqx
  docker run -d --name emqx -p 1883:1883 -p 8083:8083 -p 8084:8084 -p 8883:8883 -p 18083:18083 emqx/emqx:5.4.1
}

# 安装jdk
jdk() {
	docker pull openjdk:8
	docker run -d -it --name java8 --restart=always openjdk:8
#	docker exec -it java8 /bin/bash
#	java -version
#	exit
}



# 安装MySQL
mysql() {

  docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -v /opt/docker/files:/opt/docker/files --name mysql01 mysql/mysql-server:8.0.32

  docker exec -it mysql01 /bin/bash

  mysql -uroot -proot <<EOF
  USE mysql;
  update user set host='%' where user='root';
  flush privileges;

  drop database if exists blog_pi;
  CREATE DATABASE  blog_pi DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
  use blog_pi;
  source /opt/docker/files/sql/blog_pi.sql;

exit
EOF

  exit
}

jar() {

  cd /opt/docker/files/jar

  docker build -t pi:1 .

  docker run -d --name pi -p 10201:10201 pi:1
}

main() {
  mkdir
  mqtt
  jdk
  mysql
  jar
}

# apt-get install lrzsz

# docker logs -f pi