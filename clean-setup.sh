#!/bin/bash

npm cache verify 

cd frontend/admin
rm -rf build coverage node_modules
npm i

cd ../backend
mvn clean

cd ../frontend/web
rm -rf build coverage node_modules
npm i
