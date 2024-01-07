#!/bin/bash

cd ../
prettier --check .

cd admin 
yarn lint

cd ../api
mvn validate

cd ../web
yarn lint