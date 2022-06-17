FROM adoptopenjdk/openjdk11:jdk-11.0.7_10-alpine-slim
ARG APP_NAME=tracking-app
ARG JAR_FILE=lib/${APP_NAME}.jar
COPY ${JAR_FILE} app.jar
# ENV spring.profiles.active=dev

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]