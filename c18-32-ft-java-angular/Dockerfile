FROM eclipse-temurin:17-alpine AS build
WORKDIR /app
COPY ./target/c18-32-ft-java-angular-0.0.1-SNAPSHOT.jar /app

ENV DB_HOST=$DB_HOST \
    DB_NAME=$DB_NAME \
    DB_PASS=$DB_PASS \
    DB_PORT=$DB_PORT \
    DB_USER=$DB_USER \
    ENVIRONMENT_PLATFORM=$ENVIRONMENT_PLATFORM

EXPOSE 8080
CMD ["java", "-jar", "c18-32-ft-java-angular-0.0.1-SNAPSHOT.jar"]
