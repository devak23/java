FROM openjdk:8
ADD target/docker-test.jar ./docker-test.jar
EXPOSE 8085
ENTRYPOINT ["java" , "-jar", "docker-test.jar"]