#! /bin/bash
# docker 测试shell脚本

# 下载失败重新尝试次数
reload=5

# 将命令赋值给变量
command=""

# MySQL登陆密码
mysqlPassword="MySql@Admin123*."
# redis登陆密码
redisPassword="redis-960"
# ftp登录默认用户
ftpUsername="system"
ftpPassword="Ftp@Admin123*."

createDir() {
  echo "开始创建服务器目录..."

  # docker镜像存放目录
  mkdir -p /etc/docker
  mkdir -p /opt/docker/images
  # docker 全部容器共享目录
  mkdir -p /opt/docker/files
  # 容器存放sql文件位置
  mkdir -p /opt/docker/files/sql
  # jar包存放目录
  mkdir -p /opt/docker/files/jar
  # jar包日志存放目录
  mkdir -p /opt/docker/files/log
  # 博客文件数据目录
  mkdir -p /opt/files
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
	mkdir -p /opt/docker/nginx/html/assets
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
  mv /opt/package/sql/*.sql /opt/docker/files/sql
  # sql执行脚本
  mv /opt/package/conf/mysql.sh /opt/docker/files/mysql.sh
  chmod +x /opt/docker/files/mysql.sh
  # mysql 配置
  mv /opt/package/conf/my.cnf /opt/docker/mysql/conf
  # redis 配置
  mv /opt/package/conf/redis.conf /opt/docker/redis/conf
  # nginx 配置文件
  mv /opt/package/conf/nginx.conf /opt/docker/nginx/conf
  # rocketmq broker配置文件
  mv /opt/package/conf/broker.conf /opt/docker/rocketmq/broker/conf/
  # jar包相关移动
  mv /opt/package/jar/*.jar /opt/docker/files/jar
  mv /opt/package/conf/Dockerfile /opt/docker/files/jar
  mv /opt/package/conf/run.sh /opt/docker/files/jar
  chmod +x /opt/docker/files/jar/run.sh
  # web页面相关
  mv /opt/package/web/dist/* /opt/docker/nginx/html
  # 博客文件数据
  mv /opt/package/files/files.zip /opt/files

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
	echo '      "https://docker.mirrors.ustc.edu.cn",'  >> /etc/docker/daemon.json
	echo '      "https://kuamavit.mirror.aliyuncs.com",'  >> /etc/docker/daemon.json
	echo '      "http://hub-mirror.c.163.com",'  >> /etc/docker/daemon.json
	echo '      "https://docker.mirrors.ustc.edu.cn",'  >> /etc/docker/daemon.json
	echo '      "https://registry.docker-cn.com"'  >> /etc/docker/daemon.json
	echo "  ]"  >> /etc/docker/daemon.json
	echo "}"  >> /etc/docker/daemon.json

	# 启动docker
	sudo systemctl start docker
	# docker开始自启动
	systemctl enable docker.service
	# 创建自定义网络
	docker network create --subnet=172.18.0.0/24 blog_network
}

# 安装jdk
jdk() {
	echo "正在启动jdk..."
	docker run -d -it --name jdk --privileged=true --restart=always --network blog_network --ip 172.18.0.2 openjdk:8
}

# 安装MySQL
mysql() {
  echo "正在启动mysql..."
	docker run -d --name mysql --privileged=true --restart=always --network blog_network --ip 172.18.0.3 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=${mysqlPassword} -v /opt/docker/mysql/data:/var/lib/mysql -v /opt/docker/mysql/conf/my.cnf:/etc/mysql/my.cnf -v /opt/docker/mysql/logs:/var/log/mysql -v /opt/docker/files:/opt/docker/files mysql:8.0.20
  # 导入sql数据
  nohup sudo docker exec mysql bash /opt/docker/files/mysql.sh >/opt/docker/mysql/logs/sql.log 2>&1
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
  docker run -d --name vsftpd --privileged=true --restart=always --network blog_network --ip 172.18.0.4 -p 61120:20 -p 61121:21 -p  61110-61119:61110-61119 -e FTP_USER=${ftpUsername} -e FTP_PASS=${ftpPassword} -e PASV_ADDRESS=124.221.12.158 -e PASV_MIN_PORT=61110 -e PASV_MAX_PORT=61119 -v /opt/docker/ftp:/home/vsftpd fauria/vsftpd
}

recoverFiles() {
  cd /opt/files
  unzip -d /opt/files/ /opt/files/files.zip
}

# 安装nginx
nginx() {
  echo "正在启动nginx..."
	docker run -d --name nginx --privileged=true --restart=always --network blog_network --ip 172.18.0.5 -p 80:80 -v /opt/docker/nginx/conf/nginx.conf:/etc/nginx/nginx.conf -v /opt/docker/nginx/html:/opt/docker/nginx/html -v /opt/docker/nginx/logs:/var/log/nginx  -v /opt/docker/files:/opt/docker/files -v /opt/files:/opt/files nginx:1.20.2
}

updateRedisConf() {
  echo "开始修改Redis配置文件..."
  sed -i "s/requirepass/requirepass ${redisPassword}/g" /opt/docker/redis/conf/redis.conf
}

redis() {
  echo "正在启动redis..."
  docker run -d --name redis --privileged=true --restart=always --network blog_network --ip 172.18.0.6 -p 6379:6379 -v /opt/docker/redis/conf/redis.conf:/etc/redis/redis.conf -v /opt/docker/redis/data:/data  -v /opt/docker/files:/opt/docker/files redis:6.2.5 redis-server /etc/redis/redis.conf
}

# 安装nacos
nacos() {
  echo "正在启动nacos..."
  docker run -d --name nacos --privileged=true --restart=always --network blog_network --ip 172.18.0.7 -p 8848:8848 -p 9848:9848 -p 9849:9849 -e JVM_XMS=256m -e JVM_XMX=256m -e MODE=standalone -e PREFER_HOST_MODE=hostname -e SPRING_DATASOURCE_PLATFORM=mysql -e MYSQL_SERVICE_HOST=172.18.0.3 -e MYSQL_SERVICE_PORT=3306 -e MYSQL_SERVICE_USER=root -e MYSQL_SERVICE_PASSWORD=${mysqlPassword} -e MYSQL_SERVICE_DB_NAME=nacos -e MYSQL_SERVICE_DB_PARAM='characterEncoding=utf8&connectTimeout=10000&socketTimeout=30000&autoReconnect=true&serverTimezone=UTC&allowPublicKeyRetrieval=true' nacos/nacos-server:v2.1.0
}

# 安装rockermq  https://blog.csdn.net/qq_43600166/article/details/136187969
rocketMq() {
  echo "正在启动rocketmq..."
  # rmqnamesrv
  docker run -d --name rmqnamesrv --privileged=true --restart=always  --network blog_network --ip 172.18.0.8 -p 9876:9876 -e "MAX_POSSIBLE_HEAP=100000000" -e "MAX_HEAP_SIZE=256M" -e "HEAP_NEWSIZE=128M" -v /opt/docker/rocketmq/namesrv/logs:/home/rocketmq/logs -v /opt/docker/rocketmq/namesrv/store:/root/store apache/rocketmq:5.1.3 sh mqnamesrv
  # rmqbroker
  docker run -d --name rmqbroker --privileged=true --restart=always --network blog_network --ip 172.18.0.9 -p 10911:10911 -p 10909:10909 -e "NAMESRV_ADDR=172.18.0.8:9876"  -e "MAX_POSSIBLE_HEAP=200000000" -e "MAX_HEAP_SIZE=512M" -e "HEAP_NEWSIZE=256M" -v /opt/docker/rocketmq/broker/logs:/root/logs -v /opt/docker/rocketmq/broker/store:/root/store -v /opt/docker/rocketmq/broker/conf/broker.conf:/home/rocketmq/broker.conf apache/rocketmq:5.1.3 sh mqbroker -c /home/rocketmq/broker.conf
}

# https://blog.csdn.net/weixin_41575023/article/details/111590597
jar() {
  # 等待nacos启动
  echo "3分钟后启动博客服务..."
  sleep 3m
  echo "正在启动博客服务..."
  cd /opt/docker/files/jar
  docker build -t blog:2.1 .
  docker run -d --name blog --privileged=true --restart=always --network blog_network --ip 172.18.0.10 -p 9527:9527 -p 9100:9100 -p 9200:9200 -p 10100:10100 -p 10200:10200 -p 9092:9092 -v /opt/docker/files:/opt/docker/files -v /opt/files:/opt/files blog:2.1
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
  echo "开始解压博客文件..."
  # 上传部署压缩包解压目录
  mkdir -p /opt/package
  mv ./blog.zip /opt/package
  cd /opt/package
  unzip blog.zip
}

# 镜像文件重新下载
reLoad() {
  count=1
  while [ $count -le $reload ]; do
    eval "$command"
    if [ $? -eq 0 ]; then
      echo "镜像文件下载成功..."
      break
    else
      echo "第 ${count} 次尝试重新下载..."
      ((count++))
    fi
    if [ $count -gt $reload ]; then
      echo "docker镜像下载失败，脚本停止执行..."
      exit 1
    fi
  done
}

dockerLoad() {
  echo "开始下载 openjdk:8 镜像文件..."
  command="docker pull openjdk:8"
  reLoad

  echo "开始下载 mysql:8.0.20 镜像文件..."
  command="docker pull mysql:8.0.20"
  reLoad

  echo "开始下载 redis6.2.5 镜像文件..."
  command="docker pull redis:6.2.5"
  reLoad

  echo "开始下载 fauria/vsftpd 镜像文件..."
  command="docker pull fauria/vsftpd"
  reLoad

  echo "开始下载 nginx:1.20.2 镜像文件..."
	command="docker pull nginx:1.20.2"
	reLoad

	echo "开始下载 apache/rocketmq:5.1.3 镜像文件..."
  command="docker pull apache/rocketmq:5.1.3"
  reLoad

  echo "开始下载 nacos/nacos-server:v2.1.0 镜像文件..."
  command="docker pull nacos/nacos-server:v2.1.0"
  reLoad
}

main() {
  timer_start=`date "+%Y-%m-%d %H:%M:%S"`
  dockerStart
  dockerLoad
  util
  unzipBlog
  createDir
  addVirtualMemory
  jdk
  updateMysqlConf
  updateSqlFile
  mysql
  ftp
  recoverFiles
  nginx
  updateRedisConf
  redis
  nacos
  rocketMq
  jar
  timer_end=`date "+%Y-%m-%d %H:%M:%S"`
  duration=`echo $(($(date +%s -d "${timer_end}") - $(date +%s -d "${timer_start}"))) | awk '{t=split("60 s 60 m 24 h 999 d",a);for(n=1;n<t;n+=2){if($1==0)break;s=$1%a[n]a[n+1]s;$1=int($1/a[n])}print s}'`
  echo "脚本执行完成 耗时： $duration "
  exit 0
}

# 获取参数
while getopts "n:" arg
  do
		case "$arg" in
		  n)
			  # 指定镜像文件重新下载次数
				reload=$OPTARG
				echo $reload
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
# - jar     # 存放博客jar包
# - sql     # 存放博客sql和nacos的sql文件
# - conf    # 存放需要替换的配置文件
# - web     # 存放前端包
# - files   # 存放博客已有数据文件