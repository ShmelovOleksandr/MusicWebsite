#FROM ubuntu:latest
FROM openjdk:17
LABEL authors="Oleksandr Shmelov"
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=build/libs/*.jar
#ARG JAR_FILE=
ADD ${JAR_FILE} musicwebsite.jar
ENTRYPOINT ["java","-jar","/musicwebsite.jar"]