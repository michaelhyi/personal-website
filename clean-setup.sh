#!/bin/bash

npm cache verify 

cd frontend/admin
rm -rf build coverage node_modules
npm i

cd ../../backend
rm -rf .gradle .idea .settings bin
./gradlew clean

cd ../frontend/main
rm -rf build coverage node_modules
npm i
