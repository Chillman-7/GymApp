# === Build stage ===
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom and sources
COPY pom.xml .
COPY src ./src

# Build the jar
RUN mvn clean package -DskipTests

# === Run stage ===
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy jar from build stage (the *.jar in target/)
COPY --from=build /app/target/*.jar app.jar

# Railway will set PORT env; Spring should read it via server.port=${PORT:8080}
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
