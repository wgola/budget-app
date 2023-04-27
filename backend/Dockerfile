FROM maven:3.9.1-eclipse-temurin-17-alpine AS builder

WORKDIR /app

COPY . .

RUN mvn clean install

FROM eclipse-temurin:17-alpine

WORKDIR /app

COPY --from=builder /app/target/application-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]
