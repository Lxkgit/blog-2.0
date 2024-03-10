# 指定基础镜像
FROM openjdk:8

MAINTAINER gszero

# 指定工作目录
WORKDIR /opt

# 拷贝jar包并重命名为demo.jar
ADD run.sh run.sh
ADD blog-pi.jar blog-pi.jar

#  expose  该容器暴露的端口是多少，就是jar在容器中以多少端口运行
EXPOSE 10201

# 赋予启动脚本可执行权限
RUN chmod +x run.sh

CMD ["./run.sh"]