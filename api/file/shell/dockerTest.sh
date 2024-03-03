#! /bin/bash
# docker 测试shell脚本

# MySQL登陆密码
mysqlPassword="MySql@Admin123*."
# redis登陆密码
redisPassword="redis-960"
# ftp登录默认用户
ftpUsername="test"
ftpPassword="test"


createDir() {
  echo "开始创建服务器目录..."
  # 上传部署压缩包解压目录
  mkdir -p /opt/package

  # docker镜像存放目录
  mkdir -p /etc/docker
  mkdir -p /opt/docker/images
  # docker 全部容器共享目录
  mkdir -p /opt/docker/files

  # mysql文件目录
  # mysql 初始化数据文件目录
  mkdir -p /opt/docker/mysql/sql
  # 宿主机创建数据存放目录映射到容器
  mkdir -p /opt/docker/mysql/data
  # 宿主机创建配置文件目录映射到容器
  mkdir -p /opt/docker/mysql/conf
  # 宿主机创建日志目录映射到容器
  mkdir -p /opt/docker/mysql/logs

  # ftp 目录创建
  mkdir -p /opt/docker/ftp

  # nginx 目录创建
  mkdir -p /opt/docker/nginx/conf.d
	mkdir -p /opt/docker/nginx/html
	mkdir -p /opt/docker/nginx/logs
	mkdir -p /opt/docker/nginx/conf

	# redis 目录创建
	mkdir -p /opt/docker/redis/conf
  mkdir -p /opt/docker/redis/data

  # rocketMq 目录创建
  # 创建namesrv数据存储路径
  mkdir -p /opt/docker/rocketmq/namesrv/logs
  mkdir -p /opt/docker/rocketmq/namesrv/store
  #创建broker
  mkdir -p /opt/docker/rocketmq/broker/logs
  mkdir -p /opt/docker/rocketmq/broker/store
  mkdir -p /opt/docker/rocketmq/broker/conf

  # nacos 目录创建
  mkdir -p /opt/docker/nacos/logs/
	mkdir -p /opt/docker/nacos/conf/

}

# 安装docker
dockerInstall() {
  echo "开始安装docker..."
	# 一键安装docker
	curl -fsSL https://get.docker.com | bash -s docker --mirror Aliyun
	# 启动docker
	sudo systemctl start docker
	# 创建自定义网络
	docker network create blog_network
}

# 安装jdk
jdk() {
  echo "开始安装jdk..."
	docker pull openjdk:8
	docker run -d -it --name java8 --restart=always --network blog_network openjdk:8
}

# 安装MySQL
mysql() {
  echo "开始安装mysql..."
  # 安装mysql:8.0.20
  docker pull mysql:8.0.20

  # 启动mysql
	docker run -d -p 3306:3306 --privileged=true --name mysql8.0.20 --restart=always --network blog_network -e MYSQL_ROOT_PASSWORD=${mysqlPassword} -d -v /opt/docker/mysql/data:/var/lib/mysql -v /opt/docker/mysql/conf/my.cnf:/etc/mysql/my.cnf -v /opt/docker/mysql/logs:/var/log/mysql -v /opt/docker/files:/opt/docker/files mysql:8.0.20

  nohup sudo docker exec mysql8.0.20 bash /opt/docker/files/mysql.sh >/dev/null 2>&1
}

touchSql() {
  echo "开始创建MySQL数据恢复脚本文件..."
  cat > /opt/docker/files/mysql.sh << EOOF
#! /bin/bash
# docker 测试shell脚本

# MySQL登陆密码
mysqlPassword="${mysqlPassword}"

mysqlSQL() {
  sleep 1m
  mysql -uroot -p\${mysqlPassword} <<EOF

  drop database if exists blog_pi;
  CREATE DATABASE  blog_pi DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

  exit

EOF
}

mysqlSQL
EOOF
}

touchMyCnf() {
  echo "开始创建MySQL配置文件..."
  cd /opt/docker/mysql/conf/
	touch my.cnf
	chmod 644 my.cnf
	echo "[mysqld]"  >> /opt/docker/mysql/conf/my.cnf
	echo "port = 3306"  >> /opt/docker/mysql/conf/my.cnf
	echo "server_id = 1"  >> /opt/docker/mysql/conf/my.cnf
	echo "user = mysql"  >> /opt/docker/mysql/conf/my.cnf
	echo "character_set_server = utf8"  >> /opt/docker/mysql/conf/my.cnf
	echo "max_connections = 100"  >> /opt/docker/mysql/conf/my.cnf
	echo "max_connect_errors = 10"  >> /opt/docker/mysql/conf/my.cnf
	echo "max_allowed_packet = 10M"  >> /opt/docker/mysql/conf/my.cnf
	echo "secure_file_priv=/var/lib/mysql"  >> /opt/docker/mysql/conf/my.cnf
	echo "[mysqldump]"  >> /opt/docker/mysql/conf/my.cnf
	echo "user=root"  >> /opt/docker/mysql/conf/my.cnf
	echo "password=${mysqlPassword}"  >> /opt/docker/mysql/conf/my.cnf
}

ftp() {
  echo "开始安装ftp..."
  docker pull fauria/vsftpd
  docker run -d -v /opt/docker/ftp:/home/vsftpd -p 61120:20 -p 61121:21 -p  61100-61110:61100-61110 -e FTP_USER=${ftpUsername} -e FTP_PASS=${ftpPassword} -e PASV_ADDRESS=124.221.12.158 -e PASV_MIN_PORT=61100 -e PASV_MAX_PORT=61110 --name vsftpd --restart=always fauria/vsftpd
}

# 安装nginx
nginx() {
  echo "开始安装nginx..."
	docker pull nginx:1.20.2

	docker run --name nginx1.20.2 --restart=always --network blog_network -p 80:80 -d nginx:1.20.2

	docker cp nginx1.20.2:/etc/nginx/nginx.conf /opt/docker/nginx/conf/nginx.conf
	docker cp nginx1.20.2:/etc/nginx/conf.d /opt/docker/nginx/conf.d
	docker cp nginx1.20.2:/usr/share/nginx/html /opt/docker/nginx

	docker stop nginx1.20.2
	docker rm nginx1.20.2

	docker run -p 80:80 --name nginx1.20.2 --restart=always --network blog_network -v /opt/docker/nginx/conf/nginx.conf:/etc/nginx/nginx.conf -v /opt/docker/nginx/html:/usr/share/nginx/html -v /opt/docker/nginx/logs:/var/log/nginx  -v /opt/docker/files:/opt/docker/files -d nginx:1.20.2

}

redis() {
  echo "开始安装redis..."
  # 安装redis6.2.5
  docker pull redis:6.2.5

  # redis 配置
  touch /opt/docker/redis/conf/redis.conf

  docker run -p 6379:6379 --name redis6.2.5 --restart=always --network blog_network -v /opt/docker/redis/conf/redis.conf:/etc/redis/redis.conf -v /opt/docker/redis/data:/data  -v /opt/docker/files:/opt/docker/files -d redis:6.2.5 redis-server /etc/redis/redis.conf

}

# 添加4g的虚拟内存
addVirtualMemory() {
  echo "开始创建虚拟内存..."
  cd /usr
  mkdir swap
  cd swap/
  dd if=/dev/zero of=/usr/swap/swapfile bs=1M count=4096
  du -sh /usr/swap/swapfile
  mkswap /usr/swap/swapfile
  swapon /usr/swap/swapfile
  free -m
  echo "/usr/swap/swapfile swap swap defaults 0 0"  >> /etc/fstab
}

util(){
	echo "下载服务器环境所需依赖..."
	# 压缩解压工具
	yum install -y unzip zip
	yum install -y lrzsz
}

main() {
  util
  addVirtualMemory
  createDir
  dockerInstall
  jdk
  touchMyCnf
  touchSql
  mysql
  ftp
  nginx
  redis

}

main

# nohup sh dockerTest.sh >my.log 2>&1 &