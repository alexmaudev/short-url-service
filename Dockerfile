FROM adoptopenjdk:11.0.7_10-jdk-hotspot-bionic

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]