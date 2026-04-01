# Stage 1: Build using Maven
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
# Copy your pom.xml and source code
COPY . .
# Run the build and skip tests to save time
RUN mvn clean package -DskipTests

# Stage 2: Run using Java
FROM openjdk:17-jdk-slim
WORKDIR /app
# Copy only the built JAR file from the first stage
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]