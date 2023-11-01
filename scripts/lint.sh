#!/bin/bash

cd ../ &&


prettier --check .

cd api && 
mvn validate && 

cd ../web && 
bun lint