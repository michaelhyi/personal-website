#!/bin/bash

cd ../ &&

cd api && 
mvn package && 

cd ../web/ && 
bun run build