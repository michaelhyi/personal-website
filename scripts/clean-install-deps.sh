#!/bin/bash

cd ../
rm -rf node_modules
npm i

cd admin
rm -rf build coverage node_modules
npm i

cd ../api
mvn clean

cd ../web 
rm -rf build coverage node_modules
npm i