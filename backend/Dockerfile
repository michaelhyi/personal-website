FROM openjdk:17
WORKDIR /usr/src/app
COPY build/libs/*.jar app.jar  
ENTRYPOINT ["java", "-jar", "app.jar"]
