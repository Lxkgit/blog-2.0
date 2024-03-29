#! /bin/bash
# 压缩包安装博客

oldServiceIP="124.221.12.158"
newServiceIp=""

# MySQL登陆密码
mysqlPassword="MySql@Admin123*."
# redis登陆密码
redisPassword="redis-960"
# ftp登录默认用户
ftpUsername="test"
ftpPassword="test"

# jar包名字
blogAuthJar="blog-auth"
blogContentJar="blog-content"
blogGatewayJar="blog-gateway"
blogUserJar="blog-user"
blogFileJar="blog-file"

# 数据库名字
blogAuthSql="blog_auth"
blogContentSql="blog_content"
blogUserSql="blog_user"
blogFileSql="blog_file"
blogGatewaySql="blog_gateway"
nacosSql="nacos"

mkdir() {

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

  # ------------ 配置文件移动 ----------
  # docker 配置文件
  mv /opt/package/conf/daemon.json /etc/docker

  # mysql 数据文件
  mv /opt/package/files/*.sql /opt/docker/mysql/sql
  # mysql my.cnf 文件（文件配置存在变量使用函数生成）
  touchMyCnf

  # rocketmq 配置文件
  # runserver 配置文件与默认相比注释了 calculate_heap_sizes
  mv /opt/package/conf/runserver.sh /opt/docker/rocketmq/namesrv/bin/runserver.sh
  mv /opt/package/conf/broker.sh /opt/docker/rocketmq/broker/conf/broker.conf
  mv /opt/package/conf/runbroker.sh /opt/docker/rocketmq/broker/bin/runbroker.sh

}

touchMyCnf() {
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

# 安装docker
docker() {

	# 一键安装docker
	curl -fsSL https://get.docker.com | bash -s docker --mirror Aliyun
	# 启动docker
	sudo systemctl start docker
	# 创建自定义网络
	docker network create blog_network
}

# 安装jdk
jdk() {
	docker pull openjdk:8
	docker run -d -it --name java8 --restart=always --network blog_network openjdk:8
}

# 安装MySQL
mysql() {

  # 安装mysql:8.0.20
  docker pull mysql:8.0.20

  # 启动mysql
	docker run -d -p 3306:3306 --privileged=true --name mysql8.0.20 --restart=always --network blog_network -e MYSQL_ROOT_PASSWORD=${mysqlPassword} -d -v /opt/docker/mysql/data:/var/lib/mysql -v /opt/docker/mysql/conf/my.cnf:/etc/mysql/my.cnf -v /opt/docker/mysql/logs:/var/log/mysql -v /opt/docker/files:/opt/docker/files mysql:8.0.20

  # 导入mysql数据
  mysqlData
}


# 导入MySQL数据
mysqlData() {

	docker exec -it mysql8.0.20 bash

	mysql -uroot -p${mysqlPassword} <<EOF

	drop database if exists ${blogAuthSql};
	CREATE DATABASE ${blogAuthSql} DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
	use ${blogAuthSql};
	source /opt/docker/mysql/sql/${blogAuthSql}.sql;

  drop database if exists ${blogContentSql};
	CREATE DATABASE ${blogContentSql} DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
	use ${blogContentSql};
	source /opt/docker/mysql/sql/${blogContentSql}.sql;

	drop database if exists ${blogUserSql};
	CREATE DATABASE ${blogUserSql} DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
	use ${blogUserSql};
	source /opt/docker/mysql/sql/${blogUserSql}.sql;

	drop database if exists ${blogFileSql};
	CREATE DATABASE ${blogFileSql} DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
	use ${blogFileSql};
	source /opt/docker/mysql/sql/${blogFileSql}.sql;

	drop database if exists ${blogGatewaySql};
	CREATE DATABASE ${blogGatewaySql} DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
	use ${blogGatewaySql};
	source /opt/docker/mysql/sql/${blogGatewaySql}.sql;

	drop database if exists ${nacosSql};
	CREATE DATABASE ${nacosSql} DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
	use ${nacosSql};
	source /opt/docker/mysql/sql/${nacosSql}.sql;

	exit
EOF
	exit
}

ftp() {

  docker pull fauria/vsftpd
  docker run -d -v /opt/docker/ftp:/home/vsftpd -p 61120:20 -p 61121:21 -p  61100-61110:61100-61110 -e FTP_USER=${ftpUsername} -e FTP_PASS=${ftpPassword} -e PASV_ADDRESS=124.221.12.158 -e PASV_MIN_PORT=61100 -e PASV_MAX_PORT=61110 --name vsftpd --restart=always fauria/vsftpd

  # 创建用户流程
  # 进入容器
  # docker exec -i -t vsftpd bash
  # 创建用户文件夹
  # mkdir /home/vsftpd/test2
  # 编辑用户配置文件 新增俩行数据 分别是账号密码
  # vi /etc/vsftpd/virtual_users.txt
  # 保存退出后执行如下命令，把登录的验证信息写入数据库。
  # /usr/bin/db_load -T -t hash -f /etc/vsftpd/virtual_users.txt /etc/vsftpd/virtual_users.db
  # 重启容器
  # exit
  # docker restart vsftpd
}


# 安装nginx
nginx() {

	docker pull nginx:1.20.2

	docker run --name nginx1.20.2 --restart=always --network blog_network -p 80:80 -d nginx:1.20.2

	docker cp nginx1.20.2:/etc/nginx/nginx.conf /opt/docker/nginx/conf/nginx.conf
	docker cp nginx1.20.2:/etc/nginx/conf.d /opt/docker/nginx/conf.d
	docker cp nginx1.20.2:/usr/share/nginx/html /opt/docker/nginx

	docker stop nginx1.20.2
	docker rm nginx1.20.2

	docker run -p 80:80 --name nginx1.20.2 --restart=always --network blog_network -v /opt/docker/nginx/conf/nginx.conf:/etc/nginx/nginx.conf -v /opt/docker/nginx/html:/usr/share/nginx/html -v /opt/docker/nginx/logs:/var/log/nginx  -v /opt/docker/files:/opt/docker/files -d nginx:1.20.2

}

# 安装rockermq  https://blog.csdn.net/qq_43600166/article/details/136187969
rocketMq() {

  # 重启mqnamesrv
  docker run -d --privileged=true --restart=always --name rmqnamesrv -p 9876:9876 -v /opt/docker/rocketmq/namesrv/logs:/home/rocketmq/logs -v /opt/docker/rocketmq/namesrv/store:/root/store -e "MAX_POSSIBLE_HEAP=100000000" -e "MAX_HEAP_SIZE=256M" -e "HEAP_NEWSIZE=128M" --network blog_network apache/rocketmq:5.1.3 sh mqnamesrv

  docker run -d --privileged=true --restart=always --name rmqbroker --link rmqnamesrv:namesrv -p 10911:10911 -p 10909:10909 -v /opt/docker/rocketmq/broker/logs:/root/logs -v /opt/docker/rocketmq/broker/store:/root/store -v /opt/docker/rocketmq/broker/conf/broker.conf:/home/rocketmq/broker.conf -e "NAMESRV_ADDR=namesrv:9876"  -e "MAX_POSSIBLE_HEAP=200000000" -e "MAX_HEAP_SIZE=512M" -e "HEAP_NEWSIZE=256M" --network blog_network apache/rocketmq:5.1.3 sh mqbroker -c /home/rocketmq/broker.conf

  # rocketmq-console可视化界面
#  docker pull pangliang/rocketmq-console-ng
#  docker run -d --restart=always --name rmqadmin -e "JAVA_OPTS=-Drocketmq.namesrv.addr=124.221.12.158:9876 -Dcom.rocketmqsendMessageWithVIPChannel=false"  -p 9999:8080 --network blog_network pangliang/rocketmq-console-ng

}

redis() {
  # 安装redis6.2.5
  docker pull redis:6.2.5

  # redis 配置
  touch /opt/docker/redis/conf/redis.conf

  docker run -p 6379:6379 --name redis6.2.5 --restart=always --network blog_network -v /opt/docker/redis/conf/redis.conf:/etc/redis/redis.conf -v /opt/docker/redis/data:/data  -v /opt/docker/files:/opt/docker/files -d redis:6.2.5 redis-server /etc/redis/redis.conf

}
# 安装nacos
nacos() {

	docker pull nacos/nacos-server:v2.1.0

	docker run -p 8848:8848 --name nacos2.1.0 --restart=always --network blog_network -d nacos/nacos-server:v2.1.0
	docker cp nacos2.1.0:/home/nacos/logs/ /opt/docker/nacos/
	docker cp nacos2.1.0:/home/nacos/conf/ /opt/docker/nacos/

	docker stop nacos2.1.0
	docker rm nacos2.1.0

	# 修改nacos配置文件
	echo "spring.datasource.platform=mysql"  >> /opt/docker/nacos/conf/application.properties
	echo "db.num=1"  >> /opt/docker/nacos/conf/application.properties
	echo "db.url.0=jdbc:mysql://localhost:3306/nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC"  >> /opt/docker/nacos/conf/application.properties
	echo "db.user.0=root"  >> /opt/docker/nacos/conf/application.properties
	echo "db.password.0=${mysqlPassword}"  >> /opt/docker/nacos/conf/application.properties

	docker run -d --name nacos2.1.0 --restart=always --network blog_network -p 8848:8848 -p 9848:9848 -p 9849:9849 --privileged=true -e JVM_XMS=256m -e JVM_XMX=256m -e MODE=standalone -v /opt/docker/nacos/logs/:/home/nacos/logs -v /opt/docker/nacos/conf/:/home/nacos/conf/ -v /opt/docker/files:/opt/docker/files --restart=always nacos/nacos-server:v2.1.0
}

# 添加4g的虚拟内存
addVirtualMemory() {
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
	echo "服务器环境所需依赖..."
	# 压缩解压工具
	yum install -y unzip zip
	yum install -y lrzsz
}

createDir() {

	# 博客文件服务器目录
	mkdir -p /opt/files
	# 博客微服务目录
	mkdir -p /opt/blog/{blog-auth,blog-content,blog-gateway,blog-user,blog-file}
	# 博客微服务日志目录
	mkdir /opt/log

	# 创建docker共享文件目录
	mkdir -p /opt/docker/files

}

main() {
  $(cd `dirname $0`;pwd)
  if [ ! -f "blog.zip" ]; then
      echo "博客压缩包文件不存在，退出安装程序"
      exit
  fi
  mkdir /opt/package
  mv blog.zip /opt/package/blog.zip

  echo "创建虚拟内存空间 ... "
  addVirtualMemory

  cd /opt/package
  unzip blog.zip

  echo "正在创建服务器目录 ... "
  createDir

}

main


# 执行脚本
# yum install -y lrzsz
# chmod 777 blog_package.sh
# nohup sh blog_package.sh >my.log 2>&1 &