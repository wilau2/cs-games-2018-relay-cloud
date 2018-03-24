FROM openjdk:8

COPY build/libs/zuul-server-0.0.1-SNAPSHOT.jar /usr/src/zuul.jar
WORKDIR /usr/src/

CMD ["java", "-jar", "zuul.jar"]