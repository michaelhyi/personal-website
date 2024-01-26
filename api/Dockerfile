FROM maven:3.8.3-openjdk-17 AS build  
COPY . /usr/src/app
RUN mvn -f /usr/src/app/pom.xml -ntp -B clean verify 

FROM openjdk:17
COPY --from=build /usr/src/app/target/*.jar app.jar  
ENTRYPOINT ["java","-jar","/app.jar"]