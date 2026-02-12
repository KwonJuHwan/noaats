# Gradle을 사용하여 JAR 파일 생성
FROM gradle:8.10-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

RUN ./gradlew clean bootJar -x test --no-daemon


FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

COPY --from=build /home/gradle/src/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dserver.port=${PORT:8080}", "app.jar"]