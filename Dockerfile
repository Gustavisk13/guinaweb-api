FROM openjdk:18.0.2-jdk
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]