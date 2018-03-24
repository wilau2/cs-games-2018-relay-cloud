# Pull base image.
FROM gradle:4.2.1-jdk8-alpine

WORKDIR /app

EXPOSE 8000 -p 8000

RUN ./gradlew clean build -x test
