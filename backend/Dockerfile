FROM openjdk:17
WORKDIR /usr/src/app
COPY target/*.jar app.jar  
ENTRYPOINT ["java", "-jar", "app.jar"]