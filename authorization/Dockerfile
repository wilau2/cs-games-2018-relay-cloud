FROM node:latest
COPY . /var/www
WORKDIR /var/www
EXPOSE 25985
RUN ./gradlew :authorization:bootRun -Dspring.profiles.active=native
