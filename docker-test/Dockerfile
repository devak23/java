FROM openjdk:8
ARG JAR_FILE=target/hello-0.0.1.jar
COPY $JAR_FILE app.jar

EXPOSE 8085
ENTRYPOINT ["java" , "-jar", "app.jar"]