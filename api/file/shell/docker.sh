#! /bin/bash
# docker 测试shell脚本

# 是否需要在线下载docker镜像文件 1:下载 0:不下载
download=1

# MySQL登陆密码
mysqlPassword="MySql@Admin123*."
# redis登陆密码
redisPassword="redis-960"
# ftp登录默认用户
ftpUsername="test"
ftpPassword="test"

createDir() {
  echo "开始创建服务器目录..."

  # docker镜像存放目录
  mkdir -p /etc/docker
  mkdir -p /opt/docker/images
  # docker 全部容器共享目录
  mkdir -p /opt/docker/files
  # 容器存放sql文件位置
  mkdir -p /opt/docker/files/sql

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

	# 文件位置移动
  # sql文件
  mv /opt/package/blog/sql/*.sql /opt/docker/files/sql
  # sql执行脚本
  mv /opt/package/blog/conf/mysql.sh /opt/docker/files/mysql.sh
  chmod +x /opt/docker/files/mysql.sh
  # mysql 配置
  mv /opt/package/blog/conf/my.cnf /opt/docker/mysql/conf
  # redis 配置
  mv /opt/package/blog/conf/redis.conf /opt/docker/redis/conf
  # nginx 配置文件
  mv /opt/package/blog/conf/nginx.conf /opt/docker/nginx/conf
  # rocketmq broker配置文件
  mv /opt/package/blog/conf/broker.conf /opt/docker/rocketmq/broker/conf/
  # jar包相关移动
  mv /opt/package/blog/jar/*.jar /opt/docker/files/jar
  mv /opt/package/blog/conf/Dockerfile /opt/docker/files/jar
  mv /opt/package/blog/conf/run.sh /opt/docker/files/jar

}

# 安装docker
dockerStart() {
  echo "开始安装docker..."
	# 一键安装docker
	curl -fsSL https://get.docker.com | bash -s docker --mirror Aliyun

  # 配置docker下载镜像源
	touch /etc/docker/daemon.json
	echo "{"  >> /etc/docker/daemon.json
	echo '  "registry-mirrors": ['  >> /etc/docker/daemon.json
	echo '      "http://hub-mirror.c.163.com",'  >> /etc/docker/daemon.json
	echo '      "https://docker.mirrors.ustc.edu.cn",'  >> /etc/docker/daemon.json
	echo '      "https://registry.docker-cn.com"'  >> /etc/docker/daemon.json
	echo "  ]"  >> /etc/docker/daemon.json
	echo "}"  >> /etc/docker/daemon.json

	# 启动docker
	sudo systemctl start docker
	# 创建自定义网络
	docker network create blog_network
}

# 安装jdk
jdk() {
	echo "正在启动jdk..."
	docker run -d -it --name jdk8 --restart=always --network blog_network openjdk:8
}

# 安装MySQL
mysql() {
  echo "正在启动mysql..."
	docker run -d -p 3306:3306 --privileged=true --name mysql8.0.20 --restart=always --network blog_network -e MYSQL_ROOT_PASSWORD=${mysqlPassword} -d -v /opt/docker/mysql/data:/var/lib/mysql -v /opt/docker/mysql/conf/my.cnf:/etc/mysql/my.cnf -v /opt/docker/mysql/logs:/var/log/mysql -v /opt/docker/files:/opt/docker/files mysql:8.0.20
  # 导入sql数据
  nohup sudo docker exec mysql8.0.20 bash /opt/docker/files/mysql.sh >/dev/null 2>&1
}

updateSqlFile() {
  echo "开始修改MySQL数据恢复脚本文件..."
  sed -i "s/mysqlPassword=/mysqlPassword=\"${mysqlPassword}\"/" /opt/docker/files/mysql.sh
}

updateMysqlConf() {
  echo "开始修改MySQL配置文件..."
  sed -i "s/password=/password=${mysqlPassword}/" /opt/docker/mysql/conf/my.cnf
}

ftp() {
  echo "正在启动ftp..."
  docker run -d -v /opt/docker/ftp:/home/vsftpd -p 61120:20 -p 61121:21 -p  61100-61110:61100-61110 -e FTP_USER=${ftpUsername} -e FTP_PASS=${ftpPassword} -e PASV_ADDRESS=124.221.12.158 -e PASV_MIN_PORT=61100 -e PASV_MAX_PORT=61110 --name vsftpd --restart=always fauria/vsftpd
}

# 安装nginx
nginx() {
  echo "正在启动nginx..."
	docker run --name nginx1.20.2 --restart=always --network blog_network -p 80:80 -d nginx:1.20.2

#	docker cp nginx1.20.2:/etc/nginx/nginx.conf /opt/docker/nginx/conf
	docker cp nginx1.20.2:/etc/nginx/conf.d /opt/docker/nginx/conf.d
	docker cp nginx1.20.2:/usr/share/nginx/html /opt/docker/nginx/html

	docker stop nginx1.20.2
	docker rm nginx1.20.2

	docker run -p 80:80 --name nginx1.20.2 --restart=always --network blog_network -v /opt/docker/nginx/conf/nginx.conf:/etc/nginx/nginx.conf -v /opt/docker/nginx/html:/usr/share/nginx/html -v /opt/docker/nginx/logs:/var/log/nginx  -v /opt/docker/files:/opt/docker/files -d nginx:1.20.2
}

updateRedisConf() {
  echo "开始修改Redis配置文件..."
  sed -i "s/requirepass/requirepass ${redisPassword}/g" /opt/docker/redis/conf/redis.conf
}

redis() {
  echo "正在启动redis..."
  docker run -p 6379:6379 --name redis6.2.5 --restart=always --network blog_network -v /opt/docker/redis/conf/redis.conf:/etc/redis/redis.conf -v /opt/docker/redis/data:/data  -v /opt/docker/files:/opt/docker/files -d redis:6.2.5 redis-server /etc/redis/redis.conf
}

# 安装nacos
nacos() {
  echo "正在启动nacos..."
  docker run -d --name nacos2.1.0 --restart=always --network blog_network -p 8848:8848 -p 9848:9848 -p 9849:9849 -e JVM_XMS=256m -e JVM_XMX=256m -e MODE=standalone -e PREFER_HOST_MODE=hostname -e SPRING_DATASOURCE_PLATFORM=mysql -e MYSQL_SERVICE_HOST=mysql8.0.20 -e MYSQL_SERVICE_PORT=3306 -e MYSQL_SERVICE_USER=root -e MYSQL_SERVICE_PASSWORD=${mysqlPassword} -e MYSQL_SERVICE_DB_NAME=nacos nacos/nacos-server:v2.1.0
}

# 安装rockermq  https://blog.csdn.net/qq_43600166/article/details/136187969
rocketMq() {
  echo "正在启动rocketmq..."
  # rmqnamesrv
  docker run -d --privileged=true --restart=always --name rmqnamesrv --network blog_network -p 9876:9876 -v /opt/docker/rocketmq/namesrv/logs:/home/rocketmq/logs -v /opt/docker/rocketmq/namesrv/store:/root/store -e "MAX_POSSIBLE_HEAP=100000000" -e "MAX_HEAP_SIZE=256M" -e "HEAP_NEWSIZE=128M" apache/rocketmq:5.1.3 sh mqnamesrv
  # rmqbroker
  docker run -d --privileged=true --restart=always --name rmqbroker --network blog_network -p 10911:10911 -p 10909:10909 -v /opt/docker/rocketmq/broker/logs:/root/logs -v /opt/docker/rocketmq/broker/store:/root/store -v /opt/docker/rocketmq/broker/conf/broker.conf:/home/rocketmq/broker.conf -e "NAMESRV_ADDR=namesrv:9876"  -e "MAX_POSSIBLE_HEAP=200000000" -e "MAX_HEAP_SIZE=512M" -e "HEAP_NEWSIZE=256M" apache/rocketmq:5.1.3 sh mqbroker -c /home/rocketmq/broker.conf
}

# https://blog.csdn.net/weixin_41575023/article/details/111590597
jar() {
  echo "正在启动博客服务..."
  cd /opt/docker/files/jar
  docker build -t blog:2.1 .
  docker run -d --privileged=true --restart=always --name --network blog_network blog -p 9527:9527 blog:2.1
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

unzipBlog() {
  # 上传部署压缩包解压目录
  mkdir -p /opt/package
  mv ./blog.zip /opt/package
  cd /opt/package
  unzip blog.zip
}

dockerLoad() {
  if [ ${download} == 1 ];then

    echo "开始下载 openjdk:8 镜像文件..."
	  docker pull openjdk:8

    echo "开始下载 mysql:8.0.20 镜像文件..."
    docker pull mysql:8.0.20

    echo "开始下载 redis6.2.5 镜像文件..."
    docker pull redis:6.2.5

    echo "开始下载 fauria/vsftpd 镜像文件..."
    docker pull fauria/vsftpd

    echo "开始下载 nginx:1.20.2 镜像文件..."
	  docker pull nginx:1.20.2

	  echo "开始下载 apache/rocketmq:5.1.3 镜像文件..."
    docker pull apache/rocketmq:5.1.3

    echo "开始下载 nacos/nacos-server:v2.1.0 镜像文件..."
    docker pull nacos/nacos-server:v2.1.0

  else

    echo "开始导入镜像文件..."
    cd /opt/package/blog/images
    docker load < jdk.tar
    docker load < mysql.tar
    docker load < redis.tar
    docker load < ftp.tar
    docker load < nginx.tar
    docker load < rocketmq.tar
    docker load < nacos.tar

  fi
}

main() {
  util
  unzipBlog
  addVirtualMemory
  createDir
  dockerStart
  dockerLoad
  jdk
  updateMysqlConf
  updateSqlFile
  mysql
  ftp
  nginx
  updateRedisConf
  redis
  nacos
  rocketMq
  jar
  echo "脚本执行完成 .. "
  exit 0
}

# 获取参数
while getopts "d:" arg
  do
		case "$arg" in
			d)
			  # 是否需要在线下载镜像文件
				download=$OPTARG
				echo $download
				;;
			?)
				echo "没有找到这条命令 ... "
				exit 1
				;;
		esac
	done

main

# 压缩包blog.zip目录
# blog.zip
# - images  # docker镜像文件目录
# - jar     # 存放博客jar包
# - sql     # 存放博客sql和nacos的sql文件
# - conf    # 存放需要替换的配置文件
# - web     # 存放前端包
# - files   # 存放博客已有数据文件