#! /bin/bash
# 导出博客数据为blog.zip

# 导出mysql
sudo docker exec mysql bash /opt/docker/files/shell/exportSql.sh

# 导出博客文件数据
rm -rf /opt/files/files.zip
cd /opt/files
zip -r files.zip /opt/files/*

# 移动文件
mv /opt/docker/files/sql/* /opt/docker/files/sync/
mv /opt/files/files.zip /opt/docker/files/sync/

# 压缩文件
cd /opt/docker/files/sync/
zip -r blog.zip /opt/docker/files/sync/*

# 导出文件移至ftp system用户目录
mkdir -p /opt/docker/ftp/system
mv /opt/docker/files/sync/blog.zip /opt/docker/files/ftp/system

# 临时文件删除
rm -rf /opt/docker/files/sync/*



