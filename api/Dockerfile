FROM openjdk:17
COPY --from=build target/*.jar app.jar  
ENTRYPOINT ["java","-jar","/app.jar"]