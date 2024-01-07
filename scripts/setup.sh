#!/bin/bash

cd ../
yarn install
docker compose up -d

cd admin
yarn install

cd api
mvn install

cd web
yarn install