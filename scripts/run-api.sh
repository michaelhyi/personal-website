#!/bin/bash

cd ../api/monolithic/api && 
docker compose up -d && 
mvn clean package && 
java -jar target/api-1.0.1.jar