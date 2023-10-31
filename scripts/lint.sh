#!/bin/bash

cd ../ &&
prettier --check .

cd api && 
mvn validate && 

cd ../web && 
npm run lint