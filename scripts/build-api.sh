#!/bin/bash

echo What should the version be?
read VERSION

cd ../api && 
docker build -t michaelyi/personal-website-api:$VERSION . &&
docker push michaelyi/personal-website-api:$VERSION