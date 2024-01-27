#!/bin/bash

cd ../
rm -rf node_modules
docker compose up -d
yarn install

cd admin
rm -rf .next node_modules
yarn install

cd ../api
mvn clean

cd ../web 
rm -rf .next node_modules
yarn install