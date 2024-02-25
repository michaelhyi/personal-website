#!/bin/bash

cd admin
rm -rf build coverage node_modules
npm cache verify 

cd ../api
mvn clean

cd ../web 
rm -rf build coverage node_modules
npm cache verify 