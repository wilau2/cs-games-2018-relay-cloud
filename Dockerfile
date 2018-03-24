# Pull base image.
FROM gradle:4.2.1-jdk8-alpine

WORKDIR /src/app
COPY . .

RUN ./gradlew clean build -x test

CMD [ "./gradlew", "bootRun" ]

