#! /bin/bash

mkdir() {

  mkdir -p /opt/docker/package
  mv pi.zip /opt/docker/package/pi.zip

  mkdir -p /opt/docker/files/jar
  mkdir -p /opt/docker/files/sql
  mkdir -p /opt/docker/files/images
  cd /opt/docker/package

  unzip pi.zip

  # 项目相关文件
  mv ./sql/blog_pi.sql /opt/docker/files/sql
  mv ./jar/blog-pi.jar /opt/docker/files/jar
  mv ./conf/Dockerfile /opt/docker/files/jar

  # 解压docker需要的images文件
  cd /opt/docker/files/images
  rm -rf *
  mv /opt/docker/package/images.zip /opt/docker/files/images
  unzip images.zip

  cd /
}

# 安装并配置docker
docker() {
  # 一键安装docker
	curl -fsSL https://get.docker.com | bash -s docker --mirror Aliyun
	# 指定docker的images文件位置
  mv /opt/docker/package/conf/daemon.json /etc/docker
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
}



# 安装MySQL
mysql() {

  docker pull mysql/mysql-server:8.0.32

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

# docker logs -f pi  查看pi服务启动日志
#	docker exec -it java8 /bin/bash  进入容器

# docker stop
# docker rm
# docker rmi

# 压缩包pi.zip目录
# pi.zip
# - images.zip    # 存放docker镜像文件
# - jar           # 存放博客jar包
# - sql           # 存放博客sql文件
# - conf          # 存放需要替换的配置文件