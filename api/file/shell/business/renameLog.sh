#! /bin/bash

# 定时重命名博客日志文件

timer_start=`date -d last-day "+%Y-%m-%d"`

mv /opt/docker/files/log/blog-file.log /opt/docker/files/log/blog-file_11.log