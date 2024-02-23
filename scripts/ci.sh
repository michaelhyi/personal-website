#!/bin/bash

cd ../
rm -rf node_modules
yarn install

cd admin
rm -rf build coverage node_modules
yarn install

cd ../api
mvn clean

cd ../web 
rm -rf build coverage node_modules
yarn install