FROM openjdk:8

COPY build/libs/config-server-0.0.1-SNAPSHOT.jar /usr/src/config.jar
WORKDIR /usr/src/

CMD ["java", "-jar", "config.jar"]