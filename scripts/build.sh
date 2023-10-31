#!/bin/bash

cd ../ &&

cd api && 
mvn package && 

cd ../web/ && 
npm run build