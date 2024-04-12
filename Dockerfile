FROM openjdk:21-jdk-slim
COPY *.jar app.jar
COPY src/main/webapp/assets/ assets/
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=render","/app.jar"]
