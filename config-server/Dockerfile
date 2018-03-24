# Pull base image.
FROM gradle:4.2.1-jdk8-alpine

WORKDIR /app

RUN ./gradlew clean build -x test
