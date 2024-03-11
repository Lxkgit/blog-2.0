#! /bin/bash
# 导出mysql数据

nacosSql="nacos"
blogAuthSql="blog_auth"
blogContentSql="blog_content"
blogUserSql="blog_user"
blogFileSql="blog_file"
blogGatewaySql="blog_gateway"

mysqldump --defaults-extra-file=/etc/mysql/my.cnf ${nacosSql} > /opt/docker/files/sql/${nacosSql}.sql
mysqldump --defaults-extra-file=/etc/mysql/my.cnf ${blogAuthSql} > /opt/docker/files/sql/${blogAuthSql}.sql
mysqldump --defaults-extra-file=/etc/mysql/my.cnf ${blogContentSql} > /opt/docker/files/sql/${blogContentSql}.sql
mysqldump --defaults-extra-file=/etc/mysql/my.cnf ${blogUserSql} > /opt/docker/files/sql/${blogUserSql}.sql
mysqldump --defaults-extra-file=/etc/mysql/my.cnf ${blogFileSql} > /opt/docker/files/sql/${blogFileSql}.sql
mysqldump --defaults-extra-file=/etc/mysql/my.cnf ${blogGatewaySql} > /opt/docker/files/sql/${blogGatewaySql}.sql