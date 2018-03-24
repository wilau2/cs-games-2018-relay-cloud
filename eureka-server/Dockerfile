FROM openjdk:8

COPY build/libs/eureka-server-0.0.1-SNAPSHOT.jar /usr/src/eureka.jar
WORKDIR /usr/src/

CMD ["java", "-jar", "eureka.jar"]