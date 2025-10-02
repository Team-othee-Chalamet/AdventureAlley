# Build stage
# Using the maven base img and specifying maven and JDK version
FROM maven:3.9.11-eclipse-temurin-21
# Select /app as the directory and copies pom and src into it
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Builds the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# extracts the JAR file from the directory and sets it as
# the entrypoint for the docker container
COPY --from=build /app/target/*.jar app.jar
# Tells Docker to run the java -jar app.jar command when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]

