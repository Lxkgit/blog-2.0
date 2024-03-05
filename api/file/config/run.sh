#! /bin/bash


blogGatewayJar="blog-gateway"
blogAuthJar="blog-auth"
blogUserJar="blog-user"
blogContentJar="blog-content"
blogFileJar="blog-file"



nohup java -jar ${blogGatewayJar}.jar >/opt/docker/files/log/${blogGatewayJar}.log 2>&1 &
nohup java -jar ${blogAuthJar}.jar >/opt/docker/files/log/${blogAuthJar}.log 2>&1 &
nohup java -jar ${blogUserJar}.jar >/opt/docker/files/log/${blogUserJar}.log 2>&1 &
nohup java -jar ${blogContentJar}.jar >/opt/docker/files/log/${blogContentJar}.log 2>&1 &
nohup java -jar ${blogFileJar}.jar >/opt/docker/files/log/${blogFileJar}.log 2>&1 &