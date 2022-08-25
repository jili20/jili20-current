# 指定基础镜像
# 你的开发环境是jdk8,就使用下面命令
FROM openjdk:8-jdk-alpine
# openjdk9 在18年3月分开始不支持了, 支持从11开始，如果说你使用jdk1.9，则使用jdk14
# FROM openjdk:14.0-jdk

# 数据卷，宿主机文件夹挂载到容器
VOLUME /tmp

# 环境变量
# 在创建容器时使用 -e 可改变值，如 -e NACOS_SERVER_ADDR=120.78.209.73:8848 -e SPRING_PROFILES_ACTIVE=dev
# nacos服务器地址
ENV NACOS_SERVER_ADDR=120.78.209.73:8848
# 项目环境变量
ENV SPRING_PROFILES_ACTIVE=prod

# 复制文件
ADD jili20-*.jar app.jar

# Docker容器启动时执行的命令，注意：是 -D 开头。如果有换行，后面要加上英文状态的 \ 进行连接
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar", \
    "-Dspring.cloud.nacos.discovery.serverAddr=${NACOS_SERVER_ADDR}", \
    "-Dspring.cloud.nacos.config.serverAddr=${NACOS_SERVER_ADDR}", \
    "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "/app.jar"]