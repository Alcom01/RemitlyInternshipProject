FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app
# Copy your JAR file into the container
COPY target/SwiftCodes-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]

