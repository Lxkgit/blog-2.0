#! /bin/bash

createDir() {
  echo "创建目录 ... "
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
  # 存放docker需要导入的镜像文件
  mv ./images/mysql.tar /opt/docker/files/images
  mv ./images/emqx.tar /opt/docker/files/images
  mv ./images/jdk.tar /opt/docker/files/images

  cd /
}

#util() {
#  apt-get update
#  apt-get install -y
#}

# 安装并配置docker
dockerStart() {
  echo "启动docker ... "
  # 一键安装docker
#	curl -fsSL https://get.docker.com | bash -s docker --mirror Aliyun
	# 启动docker
	systemctl start docker
}

# docker 镜像加载
dockerLoad() {
  echo "docker 镜像加载 ... "
  cd /opt/docker/files/images

  docker load < emqx.tar
  docker load < mysql.tar
  docker load < jdk.tar
}

mqtt() {
  echo "启动mqtt ... "
  # 下载emqx
#  docker pull emqx/emqx:5.4.1
#  docker pull --platform=linux/aarch64 emqx/emqx:5.4.1

  # 运行emqx
  docker run -d --name emqx -p 1883:1883 -p 8083:8083 -p 8084:8084 -p 8883:8883 -p 18083:18083 emqx/emqx:5.4.1
}

# 安装jdk
jdk() {
  echo "启动jdk ... "
#	docker pull openjdk:8
#	docker pull --platform=linux/aarch64 openjdk:8
	docker run -d -it --name java8 --restart=always openjdk:8
}



# 安装MySQL
mysql() {
  echo "启动mysql ... "
#  docker pull mysql/mysql-server:8.0.32
#  docker pull --platform=linux/aarch64 mysql/mysql-server:8.0.32

  docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -v /opt/docker/files:/opt/docker/files --name mysql01 mysql/mysql-server:8.0.32

  sleep 5m
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
  echo "启动pi项目 ... "
  cd /opt/docker/files/jar

  docker build -t pi:1 .

  docker run -d --name pi -p 10201:10201 pi:1
}

main() {
  createDir
  dockerStart
  dockerLoad
  mqtt
  jdk
  mysql
  jar

  echo "脚本执行完成 ... "
}

main

#

# docker logs -f pi  查看pi服务启动日志
#	docker exec -it java8 /bin/bash  进入容器
# docker save <镜像名> -o <xxx.tar>

# docker stop
# docker rm
# docker rmi

# 压缩包pi.zip目录
# pi.zip
# - images    # 存放docker镜像文件
# - jar           # 存放博客jar包
# - sql           # 存放博客sql文件
# - conf          # 存放需要替换的配置文件

# 脚本执行
'''
sudo passwd root

apt-get install lrzsz

curl -fsSL https://get.docker.com | bash -s docker --mirror Aliyun
chmod 777 debian.sh
nohup sh debian.sh >my.log 2>&1 &

'''