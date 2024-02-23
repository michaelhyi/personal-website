#!/bin/bash

cd ../
rm -rf node_modules
npm cache clean
npm i

cd admin
rm -rf build coverage node_modules
npm cache clean
npm i

cd ../api
mvn clean

cd ../web 
rm -rf build coverage node_modules
npm cache clean
npm i