# 一键安装docker
curl -fsSL https://get.docker.com | bash -s docker --mirror Aliyun

# 启动docker
sudo systemctl start docker

# 创建docker共享文件目录
mkdir -p /opt/docker/files

#创建自定义网络
docker network create blog_network

# 安装mysql5.7
docker pull mysql:5.7

# 宿主机创建数据存放目录映射到容器
mkdir -p /opt/docker/mysql/data

# 宿主机创建配置文件目录映射到容器
mkdir -p /opt/docker/mysql/conf
#(需要在此目录下创建"conf.d"、"mysql.conf.d"两个目录)
mkdir -p /opt/docker/mysql/conf/conf.d
#(建议在此目录创建my.cnf文件并进行相关MySQL配置)
mkdir -p /opt/docker/mysql/conf/mysql.conf.d

# 宿主机创建日志目录映射到容器
mkdir -p /opt/docker/mysql/logs

# 启动mysql
docker run --privileged=true --name mysql5.7 --restart=always --network blog_network -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 -d  -v /opt/docker/mysql/data:/var/lib/mysql -v /opt/docker/mysql/conf:/etc/mysql/ -v /opt/docker/mysql/logs:/var/log/mysql -v /opt/docker/files:/opt/docker/files mysql:5.7

# 进入容器
docker exec -it mysql5.7 bash

# 登录MySQL
mysql -u root -p123456

# 创建用户并开启远程登录
CREATE USER 'nacos'@'%'  IDENTIFIED BY 'MySql@Admin123*.';

# 给账号授权数据库
GRANT ALL PRIVILEGES ON `nacos`.* TO 'nacos'@'%';

# 刷新权限
FLUSH PRIVILEGES;

# 删除容器(参数可以是容器名称或容器ID)
docker rm mysql5.7
# 删除镜像
docker rmi mysql5.7
# 启动容器
docker start mysql5.7
# 启动容器/重启容器
docker restart mysql5.7
# 停止容器
docker stop mysql5.7
# 进入容器
docker exec -it mysql5.7 bash

# 安装nginx
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


# 安装redis6.2.5
docker pull redis:6.2.5

# redis 配置

mkdir -p /opt/docker/redis/conf
mkdir -p /opt/docker/redis/data
touch /opt/docker/redis/conf/redis.conf

docker run -p 6379:6379 --name redis6.2.5 --restart=always --network blog_network -v /opt/docker/redis/conf/redis.conf:/etc/redis/redis.conf -v /opt/docker/redis/data:/data  -v /opt/docker/files:/opt/docker/files -d redis:6.2.5 redis-server /etc/redis/redis.conf

docker stop redis6.2.5
docker rm redis6.2.5

# 安装jdk
docker pull openjdk:8

docker run -d -it --name java8 --restart=always --network blog_network openjdk:8

docker exec -it java8 /bin/bash

java -version

exit

# 安装tomcat

docker pull tomcat:9.0.63

docker run -d -it -p 8080:8080 --name tomcat9.0.63 --restart=always --network blog_network tomcat:9.0.63

# 安装nacos

docker pull nacos/nacos-server:v2.1.0

# 创建nacos配置文件目录
mkdir -p /opt/docker/nacos/logs/
mkdir -p /opt/docker/nacos/conf/

# 启动nacos并复制配置文件
docker run -p 8848:8848 --name nacos2.1.0 --restart=always --network blog_network -d nacos/nacos-server:v2.1.0
docker cp nacos2.1.0:/home/nacos/logs/ /opt/docker/nacos/
docker cp nacos2.1.0:/home/nacos/conf/ /opt/docker/nacos/

# 关闭并删除容器
docker stop nacos2.1.0
docker rm nacos2.1.0

# 修改nacos配置文件
echo "spring.datasource.platform=mysql"  >> /opt/docker/nacos/conf/application.properties
echo "db.num=1"  >> /opt/docker/nacos/conf/application.properties
echo "db.url.0=jdbc:mysql://localhost:3306/nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC"  >> /opt/docker/nacos/conf/application.properties
echo "db.user.0=nacos"  >> /opt/docker/nacos/conf/application.properties
echo "db.password.0=MySql@Admin123*."  >> /opt/docker/nacos/conf/application.properties

# 创建数据库文件
docker exec -it mysql5.7 bash

mysql -uroot -p123456 <<EOF
drop database if exists nacos;
CREATE DATABASE  nacos DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
use nacos;
source /opt/docker/files/sql/nacos.sql;
exit
EOF

exit

# 启动nacos
docker run -d --name nacos2.1.0 --restart=always --network blog_network -p 8848:8848 -p 9848:9848 -p 9849:9849 --privileged=true -e JVM_XMS=256m -e JVM_XMX=256m -e MODE=standalone -v /opt/docker/nacos/logs/:/home/nacos/logs -v /opt/docker/nacos/conf/:/home/nacos/conf/ -v /opt/docker/files:/opt/docker/files --restart=always nacos/nacos-server:v2.1.0


# 查看服务ip
docker inspect mysql5.7 | grep IPAddress

##创建自定义网络
docker network create blog_network

docker network inspect blog_network

#查看网络
docker network ls

#删除自定义网络命令
docker network rm  名称

#设置开机自启
--restart=always

#设置网络(主要保证nacos和Mysql在同一个网络下)
--network blog_network

#docker run -d ： 启动容器 -d是后台启动并返回容器id的意思
#–name nacos ：为容器指定一个名称
#-p 8848:8848 -p 9848:9848 -p 9849:9849 ： 指定端口映射，注意这里的p不能大写，大写是随机端口映射
#–privileged=true ： 扩大容器内的权限，将容器内的权限变为root权限，不加的话就是普通用户权限，可能会出现cannot open directory
#-e JVM_XMS=256m ： 为jvm启动时分配的内存
#-e JVM_XMX=256m ： 为jvm运行过程中分配的最大内存
#-e MODE=standalone ： 使用 standalone模式（单机模式）,MODE值有cluster（集群）模式/standalone模式两种，MODE必须大写
#-v /mydata/nacos/logs/:/home/nacos/logs : 将容器的/home/nacos/logs目录挂载到 /mydata/nacos/logs
#-v /mydata/nacos/conf/:/home/nacos/conf/： 将容器的/home/nacos/conf目录挂载到 /mydata/nacos/conf
#–restart=always ：重启docker时，自动启动相关容器

## 重启docker
systemctl restart docker


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
vim /opt/docker/rocketmq/namesrv/bin/runserver.sh

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
vim /opt/docker/rocketmq/broker/bin/runbroker.sh

docker rm -f rmqbroker

docker run -d --restart=always --name rmqbroker -p 10911:10911 -p 10909:10909 --privileged=true -v /opt/docker/rocketmq/broker/logs:/root/logs -v /opt/docker/rocketmq/broker/store:/root/store -v /opt/docker/rocketmq/broker/conf/broker.conf:/home/rocketmq/broker.conf -v /opt/docker/rocketmq/broker/bin/runbroker.sh:/home/rocketmq/rocketmq-5.1.3/bin/runbroker.sh -e "MAX_HEAP_SIZE=512M" -e "HEAP_NEWSIZE=256M" apache/rocketmq:5.1.3 sh mqbroker -c /home/rocketmq/broker.conf



#! /bin/bash
# 压缩包安装博客

# 安装docker
docker() {
	# 一键安装docker
	curl -fsSL https://get.docker.com | bash -s docker --mirror Aliyun

	# 启动docker
	sudo systemctl start docker

	# 创建docker共享文件目录
	mkdir -p /opt/docker/files

	# 创建自定义网络
	docker network create blog_network
}

# 安装MySQL
mysql() {

	docker pull mysql:5.7

	# 宿主机创建数据存放目录映射到容器
	mkdir -p /opt/docker/mysql/data
	mkdir -p /opt/docker/mysql/conf
	mkdir -p /opt/docker/mysql/conf/conf.d
	mkdir -p /opt/docker/mysql/conf/mysql.conf.d
	mkdir -p /opt/docker/mysql/logs

	docker run --privileged=true --name mysql5.7 --restart=always --network blog_network -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 -d  -v /opt/docker/mysql/data:/var/lib/mysql -v /opt/docker/mysql/conf:/etc/mysql/ -v /opt/docker/mysql/logs:/var/log/mysql -v /opt/docker/files:/opt/docker/files mysql:5.7

}

# 创建MySQL登录账号
mysqlAccount() {

	docker exec -it mysql5.7 bash
	mysql -u root -p123456

	CREATE USER 'nacos'@'%'  IDENTIFIED BY 'MySql@Admin123*.';

	GRANT ALL PRIVILEGES ON `nacos`.* TO 'nacos'@'%';

	FLUSH PRIVILEGES;
}

# 导入MySQL数据
mysqlData() {

	docker exec -it mysql5.7 bash

	mysql -uroot -p123456 <<EOF
	drop database if exists nacos;
	CREATE DATABASE  nacos DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
	use nacos;
	source /opt/docker/files/sql/nacos.sql;
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

# 安装jdk
jdk() {

	docker pull openjdk:8

	docker run -d -it --name java8 --restart=always --network blog_network openjdk:8

	docker exec -it java8 /bin/bash

	java -version

	exit

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
	echo "db.user.0=nacos"  >> /opt/docker/nacos/conf/application.properties
	echo "db.password.0=MySql@Admin123*."  >> /opt/docker/nacos/conf/application.properties

	docker run -d --name nacos2.1.0 --restart=always --network blog_network -p 8848:8848 -p 9848:9848 -p 9849:9849 --privileged=true -e JVM_XMS=256m -e JVM_XMX=256m -e MODE=standalone -v /opt/docker/nacos/logs/:/home/nacos/logs -v /opt/docker/nacos/conf/:/home/nacos/conf/ -v /opt/docker/files:/opt/docker/files --restart=always nacos/nacos-server:v2.1.0
}