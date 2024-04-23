FROM openjdk:17-oracle
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 2222
CMD ["java" , "-jar" , "app.jar"]
