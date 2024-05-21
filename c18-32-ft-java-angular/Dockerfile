FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY . /app

RUN mvn clean package

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /app/target/c18-32-ft-java-angular-0.0.1-SNAPSHOT.jar /app/c18-32-ft-java-angular-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/app/c18-32-ft-java-angular-0.0.1-SNAPSHOT.jar"]