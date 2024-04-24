# Use a Maven base image with JDK and Maven installed
FROM maven:3.8.4-openjdk-17-slim

# Set the working directory in the container
WORKDIR /app

# Copy the entire project (source code)
COPY . .

# Build the application
RUN mvn -DskipTests clean package

# Set the working directory in the container for the runtime
WORKDIR /app

# Expose the port your app runs on
EXPOSE 8761

# Command to run the application
CMD ["java", "-jar", "/app/target/*.jar"]

