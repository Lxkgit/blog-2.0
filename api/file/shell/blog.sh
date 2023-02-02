#! /bin/bash
# 脚本安装博客
# 文件下载服务器ip
serviceIp="http://121.4.68.246/file"
# MySQL登陆密码
mysqlPassword="MySql@Admin123*."
# redis登陆密码
redisPassword="redis-960"

# 组件下载原地址
# jdkUrl="https://download.oracle.com/java/17/latest/jdk-17_linux-x64_bin.tar.gz"
# nginxUrl="http://nginx.org/download/nginx-1.20.2.tar.gz"
# redisUrl="https://download.redis.io/releases/redis-6.2.5.tar.gz"
# tomcatUrl="http://archive.apache.org/dist/tomcat/tomcat-9/v9.0.63/bin/apache-tomcat-9.0.63.tar.gz"
# nacosUrl="https://github.com/alibaba/nacos/releases/download/2.1.0/nacos-server-2.1.0.tar.gz"
# boostUrl="https://sourceforge.net/projects/boost/files/boost/1.59.0/boost_1_59_0.tar.gz"
# cmakeUrl="https://cmake.org/files/v3.22/cmake-3.22.1.tar.gz"
# mysqlBuildUrl="https://cdn.mysql.com//Downloads/MySQL-5.7/mysql-5.7.37.tar.gz"

# 组件下载地址
jdkUrl="${serviceIp}/package/jdk-17_linux-x64_bin.tar.gz"
nginxUrl="${serviceIp}/package/nginx-1.20.2.tar.gz"
redisUrl="${serviceIp}/package/redis-6.2.5.tar.gz"
tomcatUrl="${serviceIp}/package/apache-tomcat-9.0.63.tar.gz"
nacosUrl="${serviceIp}/package/nacos-server-2.1.0.tar.gz"
boostUrl="${serviceIp}/package/boost_1_59_0.tar.gz"
cmakeUrl="${serviceIp}/package/cmake-3.22.1.tar.gz"
mysqlBuildUrl="${serviceIp}/package/mysql-5.7.37.tar.gz"

# 配置文件
nginxConfig="${serviceIp}/config/nginx.conf"

# jar包名字
blogAuthJar="blog-auth"
blogContentJar="blog-content"
blogGatewayJar="blog-gateway"
blogUserJar="blog-user"
blogFileJar="blog-file"

# 微服务jar包下载地址
blogAuthJarUrl="${serviceIp}/jar/${blogAuthJar}.jar"
blogContentJarUrl="${serviceIp}/jar/${blogContentJar}.jar"
blogGatewayJarUrl="${serviceIp}/jar/${blogGatewayJar}.jar"
blogUserJarUrl="${serviceIp}/jar/${blogUserJar}.jar"
blogFileJarUrl="${serviceIp}/jar/${blogFileJar}.jar"

# 数据库名字
blogAuthSql="blog_auth"
blogContentSql="blog_content"
blogUserSql="blog_user"
blogFileSql="blog_file"
nacosSql="nacos"

# 数据库下载文件
blogAuthSqlUrl="${serviceIp}/sql/${blogAuthSql}.sql"
blogContentSqlUrl="${serviceIp}/sql/${blogContentSql}.sql"
blogUserSqlUrl="${serviceIp}/sql/${blogUserSql}.sql"
blogFileSqlUrl="${serviceIp}/sql/${blogFileSql}.sql"
nacosConfigSqlUrl="${serviceIp}/sql/${nacosSql}.sql"

createDir() {
	cd /opt
	# 组件安装包目录
	mkdir package
	# sql文件存放目录
	mkdir sql
	# 博客文件服务器目录
	mkdir -p /opt/file/{img,video}
	# 博客微服务目录
	mkdir -p /opt/blog/{blog-auth,blog-content,blog-gateway,blog-user,blog-file}
	# 博客微服务日志目录
	mkdir -p /opt/log/{blog-auth,blog-content,blog-gateway,blog-user,blog-file}
	
}

util(){
	echo "服务器环境所需依赖..."
	yum -y install lrzsz
    yum -y install php-devel php-pear httpd-devel
    yum -y install libaio
    yum -y install perl
    yum -y install net-tools
	yum -y install gcc gcc-c++ make glibc automake autoconf libtool  libssl-dev openssl openssl-devel
	yum -y install tree
}

jdk() {
	cd /opt/package
	echo "正在下载jdk ... "
	wget $jdkUrl
	echo "jdk下载完成,正在解压中..."
	tar -zxvf jdk-17_linux-x64_bin.tar.gz -C /opt
	cd /opt
	mv jdk-17.0.3.1/ jdk
	cd /etc/profile.d/
	touch jdk.sh
	chmod 644 jdk.sh
	echo "# jdk 环境变量配置 ... "  >> jdk.sh
	sed -i '$aexport JAVA_HOME=/opt/jdk' jdk.sh
	sed -i '$aexport PATH=$PATH:$JAVA_HOME/bin;' jdk.sh
	sed -i '$aexport CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar' jdk.sh
	source /etc/profile
}

nginx(){
	cd /opt/package
	echo "正在安装zlib..."
	yum install -y zlib zlib-devel
	echo "正在安装openssl..."
	yum install -y openssl openssl-devel
	echo "正在下载nginx安装包 nginx版本为1.20.2 ..."
	wget $nginxUrl
	echo "nginx安装包下载完成,正在解压中 ..."
	tar -zxvf nginx-1.20.2.tar.gz -C /opt
	echo "编译安装nginx中..."
	cd /opt/nginx-1.20.2
	./configure
	make
	make install
	echo "nginx安装完成,nginx启动目录在 /usr/local/nginx/sbin"
}

tomcat() {
	cd /opt/package
	echo "正在下载tomcat安装包..."
	wget $tomcatUrl
	echo "tomcat安装包下载完成,正在解压..."
	tar -zxvf apache-tomcat-9.0.63.tar.gz -C /opt
}

nacos() {
	cd /opt/package
	wget $nacosUrl
	tar -zxvf nacos-server-2.1.0.tar.gz -C /opt
	echo "nacos 解压完成 ... "
}

redis() {
	cd /opt/package
	echo "正在下载redis..."
	wget $redisUrl

	echo "redis下载完成，正在解压..."
	tar -zxvf redis-6.2.5.tar.gz -C /opt

	cd /opt/redis-6.2.5
	echo "正执行make命令..."
	make
	sed -i 's/daemonize no/daemonize yes/g' redis.conf
	echo "修改redis配置之后启动redis"
	echo "配置文件位置:  /opt/redis-6.2.5/redis.conf"
	echo "修改 daemonize 选项 no 改为 yes"
	echo "启动命令:"
	cd src/
	./redis-server /opt/redis-6.2.5/redis.conf
	./redis-cli <<EOF
config set requirepass ${redisPassword}
exit
EOF
}

buildMysql() {
	mariadb=`rpm -qa | grep mariadb`
	for del in $mariadb
	do
		echo $del
		rpm -e --nodeps $del
	done

	mysql=`rpm -qa | grep mysql`
	for del in $mysql
	do
		echo $del
		rpm -e --nodeps $del
	done
	
	cd /opt/package
	wget $boostUrl
	wget $cmakeUrl
	wget $mysqlBuildUrl
	echo "cmake 开始安装 ... "
	tar -zxvf cmake-3.22.1.tar.gz -C /opt
	cd /opt/cmake-3.22.1
	./bootstrap
	gmake
	gmake install
	cd /opt/package
	echo "boost 开始安装 ... "
	tar -zxvf boost_1_59_0.tar.gz -C  /opt
	cd /opt
	mv boost_1_59_0/  boost
	cd boost
	yum install -y ncurses-devel  gcc gcc-c++ ncurses bison make
	mkdir -p /opt/mysql/{data,tmp,binlog,logs}
	useradd mysql
	useradd mysql -s /sbin/nologin -M -g mysql
	id mysql
	cd /opt/package
	echo "mysql 开始安装 ... "
	tar -zxvf mysql-5.7.37.tar.gz -C /opt
	cd /opt/mysql-5.7.37
	cmake . -DCMAKE_INSTALL_PREFIX=/opt/mysql -DMYSQL_DATADIR=/opt/mysql/data -DMYSQL_UNIX_ADDR=/opt/mysql/tmp/mysql.sock -DDEFAULT_CHARSET=utf8 -DDEFAULT_COLLATION=utf8_general_ci -DEXTRA_CHARSETS=gbk,gb2312,utf8,ascii -DENABLED_LOCAL_INFILE=ON -DWITH_INNOBASE_STORAGE_ENGINE=1 -DWITH_FEDERATED_STORAGE_ENGINE=1 -DWITH_BLACKHOLE_STORAGE_ENGINE=1 -DWITHOUT_EXAMPLE_STORAGE_ENGINE=1 -DWITHOUT_PARTITION_STORAGE_ENGINE=1 -DWITH_FAST_MUTEXES=1 -DWITH_ZLIB=bundled -DENABLED_LOCAL_INFILE=1 -DWITH_READLINE=1 -DWITH_EMBEDDED_SERVER=1 -DWITH_DEBUG=0 -DDOWNLOAD_BOOST=1 -DWITH_BOOST=/opt/boost
	make install
	cd /etc/profile.d/
	touch mysql.sh
	chmod 644 mysql.sh
	echo 'export PATH=/opt/mysql/bin:$PATH' >> mysql.sh         
	source /etc/profile
	mv /etc/my.cnf  /etc/my.cnf.bak
	
	cd /etc
	touch my.cnf
	chmod 644 my.cnf
	echo "[client]"  >> /etc/my.cnf
	echo "port = 3306"  >> /etc/my.cnf
	echo "socket = /opt/mysql/mysql.sock"  >> /etc/my.cnf
	echo "default-character-set = utf8"  >> /etc/my.cnf
	echo "[mysqld]"  >> /etc/my.cnf
	echo "port = 3306"  >> /etc/my.cnf
	echo "server_id = 1"  >> /etc/my.cnf
	echo "user = mysql"  >> /etc/my.cnf
	echo "basedir = /opt/mysql"  >> /etc/my.cnf
	echo "datadir = /opt/mysql/data"  >> /etc/my.cnf
	echo "tmpdir = /opt/mysql/tmp"  >> /etc/my.cnf
	echo "socket = /opt/mysql/mysql.sock"  >> /etc/my.cnf
	echo "pid_file = /opt/mysql/mysql.pid"  >> /etc/my.cnf
	echo "character_set_server = utf8"  >> /etc/my.cnf
	echo "max_connections = 100"  >> /etc/my.cnf
	echo "max_connect_errors = 10"  >> /etc/my.cnf
	echo "log_error = /opt/mysql/logs/mysql_5_7_37.err"  >> /etc/my.cnf
	echo "log_bin = /opt/mysql/binlog/mysql-bin"  >> /etc/my.cnf
	echo "max_allowed_packet = 10M"  >> /etc/my.cnf
	echo "[mysqldump]"  >> /etc/my.cnf
	echo "user=root"  >> /etc/my.cnf
	echo "password=${mysqlPassword}"  >> /etc/my.cnf
	
	chown  -R  mysql:mysql  /opt/mysql/
	chown mysql:mysql  /etc/my.cnf
	
	cd /opt/mysql/
	./bin/mysqld --initialize-insecure --user=mysql --basedir=/opt/mysql --datadir=/opt/mysql/data
	cd /opt/mysql/data/
	chmod +r server-key.pem
	cd /opt/mysql/
	cp support-files/mysql.server  /etc/init.d/mysql
	/etc/init.d/mysql start
	
	mysql <<EOF
select user,host from mysql.user;
grant all privileges on *.* to 'root'@'%' identified by '${mysqlPassword}' with grant option;
set password for 'root'@'localhost' = password('${mysqlPassword}');
flush privileges;
exit
EOF

}

firewall(){
	
	firewall-cmd --zone=public --add-port=80/tcp --permanent
	firewall-cmd --zone=public --add-port=3306/tcp --permanent
	firewall-cmd --zone=public --add-port=8080/tcp --permanent
	firewall-cmd --zone=public --add-port=8848/tcp --permanent
	firewall-cmd --zone=public --add-port=9527/tcp --permanent
	# 重新加载防火墙
	echo "重新加载防火墙 ... "
	firewall-cmd --reload
	# 查看当前开放的端口
	echo "当前开放端口列表如下:"
	firewall-cmd --list-ports

}

nginxConf() {
	cd /usr/local/nginx/conf
	mv nginx.conf nginx.conf.bk
	wget $nginxConfig
}

nacosConf() {
	echo "正在修改nacos配置文件 ... "
	cd /opt/nacos/conf
	echo "spring.datasource.platform=mysql"  >> /opt/nacos/conf/application.properties
	echo "db.num=1"  >> /opt/nacos/conf/application.properties
	echo "db.url.0=jdbc:mysql://127.0.0.1:3306/nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC"  >> /opt/nacos/conf/application.properties
	echo "db.user.0=root"  >> /opt/nacos/conf/application.properties
	echo "db.password.0=${mysqlPassword}"  >> /opt/nacos/conf/application.properties
}

importSql(){
	cd /opt/sql
	wget $blogAuthSqlUrl
	wget $blogContentSqlUrl
	wget $blogUserSqlUrl
	wget $blogFileSqlUrl
	wget $nacosConfigSqlUrl
	
	mysql -uroot -p${mysqlPassword} <<EOF
drop database if exists ${nacosSql};
CREATE DATABASE  ${nacosSql} DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
use ${nacosSql};
source /opt/sql/${nacosSql}.sql;
drop database if exists ${blogAuthSql};
CREATE DATABASE  ${blogAuthSql} DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
use ${blogAuthSql};
source /opt/sql/${blogAuthSql}.sql;
drop database if exists ${blogContentSql};
CREATE DATABASE  ${blogContentSql} DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
use ${blogContentSql};
source /opt/sql/${blogContentSql}.sql;
drop database if exists ${blogUserSql};
CREATE DATABASE  ${blogUserSql} DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
use ${blogUserSql};
source /opt/sql/${blogUserSql}.sql;
drop database if exists ${blogFileSql};
CREATE DATABASE  ${blogFileSql} DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
use ${blogFileSql};
source /opt/sql/${blogFileSql}.sql;
exit
EOF
}

serviceStart(){
	echo "启动nigix ... "
	cd /usr/local/nginx/sbin
	./nginx
	
	echo "启动tomcat ... "
	cd /opt/apache-tomcat-9.0.63/bin
	./startup.sh
	
	echo "启动nacos ... "
	cd /opt/nacos/bin
	./startup.sh -m standalone
}

blogJar() {

	sleep 5m
	cd /opt/blog/blog-auth
	wget $blogAuthJarUrl
	mkdir bin
	
	cd /opt/blog/blog-content
	wget $blogContentJarUrl
	mkdir bin
		
	cd /opt/blog/blog-gateway
	wget $blogGatewayJarUrl
	mkdir bin
	
	cd /opt/blog/blog-user
	wget $blogUserJarUrl
	mkdir bin

	cd /opt/blog/blog-file
	wget $blogFileJarUrl
	mkdir bin
}

blogShell() {
	cd /opt/blog/blog-auth/bin
	touch start.sh
	touch stop.sh
	chmod 777 start.sh
	chmod 777 stop.sh
 	cat > start.sh <<EOF
#! /bin/bash

nohup java -jar --add-opens java.base/java.lang=ALL-UNNAMED ../${blogAuthJar}.jar >/opt/log/blog-auth/blog-auth.log 2>&1 &
echo \$! > project.pid
EOF
	cat > stop.sh <<EOF
#! /bin/bash

PID=\`cat project.pid\`
kill -9 \${PID}
EOF
	./start.sh

	cd /opt/blog/blog-content/bin
	touch start.sh
	touch stop.sh
	chmod 777 start.sh
	chmod 777 stop.sh
 	cat > start.sh <<EOF
#! /bin/bash

nohup java -jar --add-opens java.base/java.lang=ALL-UNNAMED ../${blogContentJar}.jar >/opt/log/blog-content/blog-content.log 2>&1 &
echo \$! > project.pid
EOF
	cat > stop.sh <<EOF
#! /bin/bash

PID=\`cat project.pid\`
kill -9 \${PID}
EOF
	./start.sh

	cd /opt/blog/blog-gateway/bin
	touch start.sh
	touch stop.sh
	chmod 777 start.sh
	chmod 777 stop.sh
 	cat > start.sh <<EOF
#! /bin/bash

nohup java -jar --add-opens java.base/java.lang=ALL-UNNAMED ../${blogGatewayJar}.jar >/opt/log/blog-gateway/blog-gateway.log 2>&1 &
echo \$! > project.pid
EOF
	cat > stop.sh <<EOF
#! /bin/bash

PID=\`cat project.pid\`
kill -9 \${PID}
EOF
	./start.sh

	cd /opt/blog/blog-user/bin
	touch start.sh
	touch stop.sh
	chmod 777 start.sh
	chmod 777 stop.sh
 	cat > start.sh <<EOF
#! /bin/bash

nohup java -jar --add-opens java.base/java.lang=ALL-UNNAMED ../${blogUserJar}.jar >/opt/log/blog-user/blog-user.log 2>&1 &
echo \$! > project.pid
EOF
	cat > stop.sh <<EOF
#! /bin/bash

PID=\`cat project.pid\`
kill -9 \${PID}
EOF
	./start.sh

	cd /opt/blog/blog-file/bin
	touch start.sh
	touch stop.sh
	chmod 777 start.sh
	chmod 777 stop.sh
 	cat > start.sh <<EOF
#! /bin/bash

nohup java -jar --add-opens java.base/java.lang=ALL-UNNAMED ../${blogFileJar}.jar >/opt/log/blog-file/blog-file.log 2>&1 &
echo \$! > project.pid
EOF
	cat > stop.sh <<EOF
#! /bin/bash

PID=\`cat project.pid\`
kill -9 \${PID}
EOF
	./start.sh
}
echo "正在创建服务器目录 ... "
createDir
echo "开始安装服务器所需依赖 ... "
util
echo "开始安装jdk ... "
jdk
echo "开始安装nginx ... "
nginx
echo "开始安装tomcat ... "
tomcat
echo "开始安装nacos ... "
nacos
echo "配置nacos中 ... "
nacosConf
echo "配置nginx中 ... "
nginxConf
echo "开始安装redis ... "
redis
echo "开始安装MySQL ... "
buildMysql
echo "加载防火墙端口 ... "
firewall
echo "正在导入sql文件 ... "
importSql
echo "服务启动中 ... "
serviceStart
echo "博客服务启动中 ... "
blogJar
blogShell


# 脚本执行方法
# nohup sh blog.sh >my.log 2>&1 &

# java -jar --add-opens java.base/java.lang=ALL-UNNAMED user-center-1.0-SNAPSHOT.jar

