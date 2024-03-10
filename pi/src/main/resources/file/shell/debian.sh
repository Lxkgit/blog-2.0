#! /bin/bash

# MySQL登陆密码
mysqlPassword="MySql@Admin123*."

createDir() {
  echo "创建目录 ... "
  mkdir -p /opt/docker/files/jar
  mkdir -p /opt/docker/files/sql
  mkdir -p /opt/docker/files/log

  # MySQL目录
  # MySQL 配置文件
  mkdir -p /opt/docker/mysql/conf
  # MySQL 导入数据日志
  mkdir -p /opt/docker/mysql/logs

  # 项目相关文件
  mv /opt/package/jar/blog-pi.jar /opt/docker/files/jar
  mv /opt/package/conf/run.sh /opt/docker/files/jar
  mv /opt/package/conf/Dockerfile /opt/docker/files/jar

  # MySQL数据文件
  mv /opt/package/sql/blog_pi.sql /opt/docker/files/sql
  # MySQL容器执行脚本
  mv /opt/package/conf/mysql.sh /opt/docker/files
  chmod +x /opt/docker/files/mysql.sh
}

util() {
  apt-get update
  apt-get install -y
}

# 安装并配置docker
dockerStart() {
  echo "启动docker ... "
  # 一键安装docker
	curl -fsSL https://get.docker.com | bash -s docker --mirror Aliyun
	# 启动docker
	sudo systemctl start docker
	# docker开始自启动
	systemctl enable docker.service
	# 创建自定义网络
	docker network create --subnet=172.18.0.0/24 blog_network
}

# docker 镜像加载
dockerLoad() {
  echo "docker 镜像加载 ... "
  cd /opt/package/images
  docker load < emqx.tar
  docker load < mysql.tar
  docker load < jdk.tar
}

# 安装jdk
jdk() {
  echo "启动jdk ... "
  docker run -d -it --name jdk8 --privileged=true --restart=always --network blog_network --ip 172.18.0.2 openjdk:8
}

updateSqlFile() {
  echo "开始修改MySQL数据恢复脚本文件..."
  sed -i "s/mysqlPassword=/mysqlPassword=\"${mysqlPassword}\"/" /opt/docker/files/mysql.sh
}

# 安装MySQL
mysql() {
  echo "启动mysql ... "
  docker run -d --name mysql --privileged=true --restart=always --network blog_network --ip 172.18.0.3 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=${mysqlPassword} -v /opt/docker/files:/opt/docker/files  mysql/mysql-server:8.0.32
  # 导入sql数据
  nohup sudo docker exec mysql bash /opt/docker/files/mysql.sh >/opt/docker/mysql/logs/sql.log 2>&1
}

mqtt() {
  echo "启动mqtt ... "
  docker run -d --name emqx --privileged=true --restart=always --network blog_network --ip 172.18.0.4 -p 1883:1883 -p 8083:8083 -p 8084:8084 -p 8883:8883 -p 18083:18083 emqx/emqx:5.4.1
}

jar() {
  echo "启动pi项目 ... "
  cd /opt/docker/files/jar
  docker build -t pi:1 .
  docker run -d --name pi --privileged=true --restart=always --network blog_network --ip 172.18.0.5 -p 10201:10201 -p 9092:9092 -v /opt/docker/files:/opt/docker/files pi:1
}

unzipPi() {
  # 上传部署压缩包解压目录
  mkdir -p /opt/package
  mv ./pi.zip /opt/package
  cd /opt/package
  unzip pi.zip
}

main() {
  timer_start=`date "+%Y-%m-%d %H:%M:%S"`
#  util
  unzipPi
  createDir
  dockerStart
  dockerLoad
  jdk
  updateSqlFile
  mysql
  mqtt
  jar
  timer_end=`date "+%Y-%m-%d %H:%M:%S"`
  duration=`echo $(($(date +%s -d "${timer_end}") - $(date +%s -d "${timer_start}"))) | awk '{t=split("60 s 60 m 24 h 999 d",a);for(n=1;n<t;n+=2){if($1==0)break;s=$1%a[n]a[n+1]s;$1=int($1/a[n])}print s}'`
  echo "脚本执行完成 耗时： $duration "
  exit 0
}

main

# 压缩包pi.zip目录
# pi.zip
# - images        # 存放docker镜像文件
# - jar           # 存放博客jar包
# - sql           # 存放博客sql文件
# - conf          # 存放需要替换的配置文件

