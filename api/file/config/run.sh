#! /bin/bash

blogGatewayJar="blog-gateway"
blogAuthJar="blog-auth"
blogUserJar="blog-user"
blogContentJar="blog-content"
blogFileJar="blog-file"

JAVA_OPTS="-Duser.timezone=GMT+8"

echo "启动网关服务..."
nohup java ${JAVA_OPTS} -jar ${blogGatewayJar}.jar > /opt/docker/files/log/${blogGatewayJar}.log 2>&1 &
echo "启动鉴权服务..."
nohup java ${JAVA_OPTS} -jar ${blogAuthJar}.jar > /opt/docker/files/log/${blogAuthJar}.log 2>&1 &
echo "启动用户服务..."
nohup java ${JAVA_OPTS} -jar ${blogUserJar}.jar > /opt/docker/files/log/${blogUserJar}.log 2>&1 &
echo "启动内容服务..."
nohup java ${JAVA_OPTS} -jar ${blogContentJar}.jar > /opt/docker/files/log/${blogContentJar}.log 2>&1 &
echo "启动文件服务..."
nohup java ${JAVA_OPTS} -jar ${blogFileJar}.jar > /opt/docker/files/log/${blogFileJar}.log 2>&1 &

# 保持容器运行
tail -f /dev/null