FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy source code
COPY . .

# Build the Spring Boot jar
RUN ./mvnw clean package -DskipTests

# Render uses PORT env variable (8080 fallback)
EXPOSE 8080

# Run the exact jar
CMD ["java", "-jar", "target/gymapp-0.0.1-SNAPSHOT.jar"]
