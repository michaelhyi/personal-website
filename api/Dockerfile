FROM maven:3.8.3-openjdk-17 AS build  
COPY src .  
COPY pom.xml . 
COPY checkstyle-suppressions.xml . 
RUN mvn -f pom.xml clean validate verify package

FROM openjdk:17
COPY --from=build target/*.jar app.jar  
ENTRYPOINT ["java","-jar","/app.jar"]