FROM gradle:alpine

COPY ./ /app

RUN /app/gradlew :config-server:bootRun -Dspring.profiles.active=native
