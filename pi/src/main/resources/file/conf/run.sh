#! /bin/bash

blogPiJar="blog-pi"

echo "启动树莓派硬件接入服务..."
nohup java -jar ${blogPiJar}.jar > /opt/docker/files/log/${blogPiJar}.log 2>&1 &


# 保持容器运行
tail -f /dev/null