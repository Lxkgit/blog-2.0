#! /bin/bash

# 安装系统基础服务
base() {
  apt update
  apt install curl -y
  apt-get update -y
  apt-get install vim -y
  apt-get install lrzsz
}

# 安装并配置docker
docker() {
  # 一键安装docker
	curl -fsSL https://get.docker.com | bash -s docker --mirror Aliyun
	# 启动docker
	systemctl start docker

	docker network create blog_network
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
	docker run -d -it --name java8 --restart=always --network blog_network openjdk:8
	docker exec -it java8 /bin/bash
	java -version
	exit
}



# 安装MySQL
mysql() {

  docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root --name mysql01 mysql/mysql-server:8.0.32

  docker exec -it mysql01 /bin/bash

  mysql -u root -proot

  USE mysql;

  update user set host='%' where user='root';

  flush privileges;

  exit;
}

jar() {

  docker build -t pi:1 .

  docker run -d --name pi -p 10201:10201 pi:1
}

main() {
  base
  mqtt
  mysql
}

#docker logs -f pi