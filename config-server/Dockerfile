FROM gradle:alpine

COPY ./ /app

WORKDIR /app
RUN gradle build
# RUN /app/gradlew :config-server:bootRun -Dspring.profiles.active=native
