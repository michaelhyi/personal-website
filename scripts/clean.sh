#!/bin/bash

cd ../
rm -rf node_modules

cd admin
rm -rf build coverage node_modules

cd ../api
mvn clean

cd ../web 
rm -rf build coverage node_modules