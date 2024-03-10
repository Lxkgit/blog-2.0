#! /bin/bash

blogPiJar="blog-pi"

JAVA_OPTS="-Duser.timezone=GMT+8"

echo "启动树莓派硬件接入服务..."
nohup java ${JAVA_OPTS} -jar ${blogPiJar}.jar > /opt/docker/files/log/${blogPiJar}.log 2>&1 &


# 保持容器运行
tail -f /dev/null