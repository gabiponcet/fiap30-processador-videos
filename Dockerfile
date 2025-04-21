FROM gradle:7.5 AS build

WORKDIR /app

COPY . .

RUN gradle bootJar

FROM openjdk:17.0.2-slim-bullseye

COPY --from=build /app/build/libs/conversor-application.jar app.jar

RUN apt-get update && apt-get install -y curl

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]