#! /bin/bash
# 导出博客数据为blog.zip

# 导出mysql
sudo docker exec mysql bash /opt/docker/files/shell/exportSql.sh

# 导出博客文件数据
rm -rf /opt/files/files.zip
cd /opt/files
zip -r files.zip /opt/files/*

# 创建临时目录
mkdir -p /opt/temp/blog
mkdir -p /opt/temp/blog/sql
mkdir -p /opt/temp/blog/files

# 移动文件
mv /opt/docker/files/sql/* /opt/temp/blog/sql
mv /opt/files/files.zip /opt/temp/blog/files

# 压缩文件
cd /opt/temp/blog
zip -r blog.zip /opt/temp/blog/*

# 导出文件移至ftp system用户目录
mkdir -p /opt/docker/ftp/system
mv /opt/temp/blog/blog.zip /opt/docker/ftp/system
# 临时文件删除
rm -rf /opt/temp/blog/*



