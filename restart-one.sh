#!/bin/sh
# $0 is the script name, $1 id the first ARG, $2 is second...
NAME="$1"
gradle build
docker-compose build $NAME
docker-compose up -d --no-deps $NAME