# ok en gros, le but de la partie Infra, c'est de "containerize" les microservices
# chaque microservice a besoin d'un Dockerfile, mais esti j'ai passé 30 minutes à attendre que Gradle download + compile
# parce que le Wifi est pourri, prenez votre hotspot si possible

# je comprends rien et j'ai peur
# GOD HELP YOU, désolé :'(
# Max was here 2018

FROM frolvlad/alpine-oraclejdk8:slim
EXPOSE 8080
RUN mkdir -p /app/
ADD build/libs/eureka-server-0.0.1-SNAPSHOT.jar /app/eureka.jar
ENTRYPOINT ["java", "-jar", "/app/eureka.jar"]
