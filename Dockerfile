FROM openjdk:21-jdk-slim
WORKDIR target
COPY *.jar app.jar
COPY ../src/main/webapp/assets/ assets/
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=render","app.jar"]
