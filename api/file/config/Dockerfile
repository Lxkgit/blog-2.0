# 指定基础镜像
FROM openjdk:8

MAINTAINER gszero

# 指定工作目录
WORKDIR /opt

# 拷贝jar包并重命名为demo.jar
ADD run.sh run.sh
ADD blog-gateway.jar blog-gateway.jar
ADD blog-user.jar blog-user.jar
ADD blog-auth.jar blog-auth.jar
ADD blog-content.jar blog-content.jar
ADD blog-file.jar blog-file.jar

#  expose  该容器暴露的端口是多少，就是jar在容器中以多少端口运行
EXPOSE 9527

# 赋予启动脚本可执行权限
RUN chmod +x run.sh

CMD ["./run.sh"]

#CMD ["java", "-jar", "blog-gateway.jar"]
#CMD ["java", "-jar", "blog-user.jar"]
#CMD ["java", "-jar", "blog-auth.jar"]
#CMD ["java", "-jar", "blog-content.jar"]
#CMD ["java", "-jar", "blog-file.jar"]