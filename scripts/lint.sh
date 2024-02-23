#!/bin/bash

cd ../
# prettier --check .

cd admin 
npm run lint

cd ../api
mvn validate

cd ../web
npm run lint