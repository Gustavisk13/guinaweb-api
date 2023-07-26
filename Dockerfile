FROM openjdk:18.0.2-jdk
ARG JAR_FILE=target/**.jar
RUN echo "JAR_FILE: ${JAR_FILE}"
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]