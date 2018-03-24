#!/bin/sh

docker-compose down
./gradlew clean build
docker-compose build
docker-compose up -d