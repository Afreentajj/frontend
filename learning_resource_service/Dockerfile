FROM openjdk:17

WORKDIR /app

# Copy the Maven project files
COPY pom.xml .

# Resolve Maven dependencies
RUN mvn dependency:go-offline

# Copy the source code
COPY src src

# Build the project
RUN mvn clean package -DskipTests

# Copy the built JAR file
COPY target/*.jar app.jar

# Expose port 2222
EXPOSE 2222

# Define the command to run the Spring Boot application when the container starts
CMD ["java", "-jar", "app.jar"]


