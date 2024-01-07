#!/bin/bash

cd ../
rm -rf node_modules

cd admin
rm -rf .next node_modules

cd api
mvn clean

cd web 
rm -rf .next node_modules