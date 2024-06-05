FROM openjdk:21-jdk-slim
COPY target/*.jar app.jar
ENTRYPOINT ["java","-Xmx3072m","-jar","app.jar"]
