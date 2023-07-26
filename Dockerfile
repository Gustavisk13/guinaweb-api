FROM openjdk:18.0.2-jdk
RUN addgroup -S guina-api && adduser -S guina-api -G guina-api
USER guina-api:guina-api
ARG JAR_FILE=target/**.jar
COPY ${JAR_FILE} app.jar
COPY --chown=guina-api:guina-api ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]