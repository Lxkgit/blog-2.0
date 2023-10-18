#! /bin/bash
# 压缩包安装博客

oldServiceIP="124.221.12.158"
newServiceIp=""

# MySQL登陆密码
mysqlPassword="MySql@Admin123*."
# redis登陆密码
redisPassword="redis-960"

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

# 安装docker
docker() {

	# 一键安装docker
	curl -fsSL https://get.docker.com | bash -s docker --mirror Aliyun
	# 启动docker
	sudo systemctl start docker
	# 创建自定义网络
	docker network create blog_network
}

# 安装MySQL
mysql() {

	docker pull mysql:5.7
	docker run --privileged=true --name mysql5.7 --restart=always --network blog_network -p 3306:3306 -e MYSQL_ROOT_PASSWORD=${mysqlPassword} -d  -v /opt/docker/mysql/data:/var/lib/mysql -v /opt/docker/mysql/conf:/etc/mysql/ -v /opt/docker/mysql/logs:/var/log/mysql -v /opt/docker/files:/opt/docker/files mysql:5.7

}


# 导入MySQL数据
mysqlData() {

	docker exec -it mysql5.7 bash

	mysql -uroot -p${mysqlPassword} <<EOF

	drop database if exists ${blogAuthSql};
	CREATE DATABASE ${blogAuthSql} DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
	use ${blogAuthSql};
	source /opt/docker/files/sql/${blogAuthSql}.sql;

  drop database if exists ${blogContentSql};
	CREATE DATABASE ${blogContentSql} DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
	use ${blogContentSql};
	source /opt/docker/files/sql/${blogContentSql}.sql;

	drop database if exists ${blogUserSql};
	CREATE DATABASE ${blogUserSql} DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
	use ${blogUserSql};
	source /opt/docker/files/sql/${blogUserSql}.sql;

	drop database if exists ${blogFileSql};
	CREATE DATABASE ${blogFileSql} DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
	use ${blogFileSql};
	source /opt/docker/files/sql/${blogFileSql}.sql;

	drop database if exists ${blogGatewaySql};
	CREATE DATABASE ${blogGatewaySql} DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
	use ${blogGatewaySql};
	source /opt/docker/files/sql/${blogGatewaySql}.sql;

	drop database if exists ${nacosSql};
	CREATE DATABASE ${nacosSql} DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
	use ${nacosSql};
	source /opt/docker/files/sql/${nacosSql}.sql;

	exit
EOF
	exit
}

# 安装nginx
nginx() {

	docker pull nginx:1.20.2

	docker run --name nginx1.20.2 --restart=always --network blog_network -p 80:80 -d nginx:1.20.2

	mkdir -p /opt/docker/nginx/conf.d
	mkdir -p /opt/docker/nginx/html
	mkdir -p /opt/docker/nginx/logs
	mkdir -p /opt/docker/nginx/conf

	docker cp nginx1.20.2:/etc/nginx/nginx.conf /opt/docker/nginx/conf/nginx.conf
	docker cp nginx1.20.2:/etc/nginx/conf.d /opt/docker/nginx/conf.d
	docker cp nginx1.20.2:/usr/share/nginx/html /opt/docker/nginx

	docker stop nginx1.20.2
	docker rm nginx1.20.2

	docker run -p 80:80 --name nginx1.20.2 --restart=always --network blog_network -v /opt/docker/nginx/conf/nginx.conf:/etc/nginx/nginx.conf -v /opt/docker/nginx/html:/usr/share/nginx/html -v /opt/docker/nginx/logs:/var/log/nginx  -v /opt/docker/files:/opt/docker/files -d nginx:1.20.2

}

# 安装rockermq
rocketMq() {
  # docker 安装rocketmq
  docker pull apache/rocketmq:5.1.3

  # 创建namesrv数据存储路径
  mkdir -p /opt/docker/rocketmq/namesrv/logs
  mkdir -p /opt/docker/rocketmq/namesrv/bin

  # 设置权限
  chmod 777 -R /opt/docker/rocketmq/namesrv/*

  # 启动namesrv
  docker run -d --privileged=true --name rmqnamesrv apache/rocketmq:5.1.3 sh mqnamesrv

  # 脚本复制
  docker cp rmqnamesrv:/home/rocketmq/rocketmq-5.1.3/bin/runserver.sh /opt/docker/rocketmq/namesrv/bin/runserver.sh

  # 注释掉：calculate_heap_sizes
#  vim /opt/docker/rocketmq/namesrv/bin/runserver.sh

  # 移除nameserver容器
  docker rm -f rmqnamesrv

  # 重启mqnamesrv
  docker run -d --privileged=true --restart=always --name rmqnamesrv -p 9876:9876 -v /opt/docker/rocketmq/namesrv/logs:/home/rocketmq/logs -v /opt/docker/rocketmq/namesrv/bin/runserver.sh:/home/rocketmq/rocketmq-5.1.3/bin/runserver.sh -e "MAX_HEAP_SIZE=256M" -e "HEAP_NEWSIZE=128M" apache/rocketmq:5.1.3 sh mqnamesrv


  #创建broker
  mkdir -p /opt/docker/rocketmq/broker/logs
  mkdir -p /opt/docker/rocketmq/broker/data
  mkdir -p /opt/docker/rocketmq/broker/conf
  mkdir -p /opt/docker/rocketmq/broker/bin

  # 设置权限
  chmod 777 -R /opt/docker/rocketmq/broker/*

  touch /opt/docker/rocketmq/broker/conf/broker.conf
  chmod 777 /opt/docker/rocketmq/broker/conf/broker.conf

  echo "# nameServer 地址多个用;隔开 默认值null"  >> /opt/docker/rocketmq/broker/conf/broker.conf
  echo "# 例：127.0.0.1:6666;127.0.0.1:8888"  >> /opt/docker/rocketmq/broker/conf/broker.conf
  echo "namesrvAddr = 123.57.246.82:9876"  >> /opt/docker/rocketmq/broker/conf/broker.conf
  echo "# 集群名称"  >> /opt/docker/rocketmq/broker/conf/broker.conf
  echo "brokerClusterName = DefaultCluster"  >> /opt/docker/rocketmq/broker/conf/broker.conf
  echo "# 节点名称"  >> /opt/docker/rocketmq/broker/conf/broker.conf
  echo "brokerName = broker-a"  >> /opt/docker/rocketmq/broker/conf/broker.conf
  echo "# broker id节点ID， 0 表示 master, 其他的正整数表示 slave，不能小于0"  >> /opt/docker/rocketmq/broker/conf/broker.conf
  echo "brokerId = 0"  >> /opt/docker/rocketmq/broker/conf/broker.conf
  echo "# Broker服务地址	String	内部使用填内网ip，如果是需要给外部使用填公网ip"  >> /opt/docker/rocketmq/broker/conf/broker.conf
  echo "brokerIP1 = 123.57.246.82"  >> /opt/docker/rocketmq/broker/conf/broker.conf
  echo "# Broker角色"  >> /opt/docker/rocketmq/broker/conf/broker.conf
  echo "brokerRole = ASYNC_MASTER"  >> /opt/docker/rocketmq/broker/conf/broker.conf
  echo "# 刷盘方式"  >> /opt/docker/rocketmq/broker/conf/broker.conf
  echo "flushDiskType = ASYNC_FLUSH"  >> /opt/docker/rocketmq/broker/conf/broker.conf
  echo "# 在每天的什么时间删除已经超过文件保留时间的 commit log，默认值04"  >> /opt/docker/rocketmq/broker/conf/broker.conf
  echo "deleteWhen = 04"  >> /opt/docker/rocketmq/broker/conf/broker.conf
  echo "# 以小时计算的文件保留时间 默认值72小时"  >> /opt/docker/rocketmq/broker/conf/broker.conf
  echo "fileReservedTime = 72"  >> /opt/docker/rocketmq/broker/conf/broker.conf
  echo "# 是否允许Broker 自动创建Topic，建议线下开启，线上关闭"  >> /opt/docker/rocketmq/broker/conf/broker.conf
  echo "autoCreateTopicEnable=true"  >> /opt/docker/rocketmq/broker/conf/broker.conf
  echo "# 是否允许Broker自动创建订阅组，建议线下开启，线上关闭"  >> /opt/docker/rocketmq/broker/conf/broker.conf
  echo "autoCreateSubscriptionGroup=true"  >> /opt/docker/rocketmq/broker/conf/broker.conf

  docker run -d --name rmqbroker --privileged=true apache/rocketmq:5.1.3 sh mqbroker

  docker cp rmqbroker:/home/rocketmq/rocketmq-5.1.3/bin/runbroker.sh /opt/docker/rocketmq/broker/bin/runbroker.sh

  # 注释掉：calculate_heap_sizes
#  vim /opt/docker/rocketmq/broker/bin/runbroker.sh

  docker rm -f rmqbroker

  docker run -d --restart=always --name rmqbroker -p 10911:10911 -p 10909:10909 --privileged=true -v /opt/docker/rocketmq/broker/logs:/root/logs -v /opt/docker/rocketmq/broker/store:/root/store -v /opt/docker/rocketmq/broker/conf/broker.conf:/home/rocketmq/broker.conf -v /opt/docker/rocketmq/broker/bin/runbroker.sh:/home/rocketmq/rocketmq-5.1.3/bin/runbroker.sh -e "MAX_HEAP_SIZE=512M" -e "HEAP_NEWSIZE=256M" apache/rocketmq:5.1.3 sh mqbroker -c /home/rocketmq/broker.conf


}

# 安装jdk
jdk() {
	docker pull openjdk:8
	docker run -d -it --name java8 --restart=always --network blog_network openjdk:8
	docker exec -it java8 /bin/bash
	java -version
	exit
}

redis() {
  # 安装redis6.2.5
  docker pull redis:6.2.5

  # redis 配置

  mkdir -p /opt/docker/redis/conf
  mkdir -p /opt/docker/redis/data
  touch /opt/docker/redis/conf/redis.conf

  docker run -p 6379:6379 --name redis6.2.5 --restart=always --network blog_network -v /opt/docker/redis/conf/redis.conf:/etc/redis/redis.conf -v /opt/docker/redis/data:/data  -v /opt/docker/files:/opt/docker/files -d redis:6.2.5 redis-server /etc/redis/redis.conf

  docker stop redis6.2.5
  docker rm redis6.2.5
}
# 安装nacos
nacos() {

	docker pull nacos/nacos-server:v2.1.0

	mkdir -p /opt/docker/nacos/logs/
	mkdir -p /opt/docker/nacos/conf/

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

	# 创建docker中MySQL数据存放目录
	mkdir -p /opt/docker/mysql/data
	mkdir -p /opt/docker/mysql/conf
	mkdir -p /opt/docker/mysql/conf/conf.d
	mkdir -p /opt/docker/mysql/conf/mysql.conf.d
	mkdir -p /opt/docker/mysql/logs
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