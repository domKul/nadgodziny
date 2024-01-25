FROM gradle:7.3.3-jdk17 AS build

WORKDIR /app

COPY . .

COPY gradlew .

RUN ./gradlew build

FROM openjdk:17-alpine

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]