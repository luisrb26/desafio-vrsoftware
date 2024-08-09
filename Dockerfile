FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build.gradle /app/
COPY gradlew /app/
COPY gradle /app/gradle/

RUN chmod +x ./gradlew

COPY src /app/src

RUN ./gradlew build --no-daemon

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "build/libs/desafiovr-0.0.1-SNAPSHOT.jar"]