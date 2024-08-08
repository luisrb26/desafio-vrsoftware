FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build.gradle /app/
COPY src /app/src

RUN ./gradlew build --no-daemon

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "build/libs/desafiovr-0.0.1-SNAPSHOT.jar"]