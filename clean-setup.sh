#!/bin/bash

npm cache verify 

cd admin
rm -rf build coverage node_modules
npm i

cd ../api
mvn clean

cd ../web 
rm -rf build coverage node_modules
npm i