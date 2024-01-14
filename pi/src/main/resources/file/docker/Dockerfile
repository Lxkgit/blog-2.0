# 指定基础镜像
FROM openjdk:8

MAINTAINER gszero

# 拷贝jar包并重命名为demo.jar
ADD blog-pi.jar pi.jar

#  expose  该容器暴露的端口是多少，就是jar在容器中以多少端口运行
EXPOSE 10201

# entrypoint 容器启动之后执行的命令，java -jar demo.jar  即启动jar
ENTRYPOINT ["java","-jar","pi.jar"]