#!/bin/bash

cd ../ &&

rm -rf node_modules/ && 
npm install && 

cd api && 
docker compose up -d && 
mvn clean && 

cd ../web/ && 
rm -rf node_modules/ && 
npm install
