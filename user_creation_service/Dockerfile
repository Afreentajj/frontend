# Use a Maven base image with JDK and Maven installed
FROM maven:3.8.4-openjdk-17-slim
 
# Set the working directory in the container
WORKDIR /app
 
COPY target/*.jar app.jar
 
 
# Expose the port your app runs on
EXPOSE 4321
 
# Command to run the application
CMD ["java", "-jar", "app.jar"]
