#!/bin/bash

cd ../
rm -rf node_modules
npm cache clean

cd admin
rm -rf build coverage node_modules
npm cache clean

cd ../api
mvn clean

cd ../web 
rm -rf build coverage node_modules
npm cache clean