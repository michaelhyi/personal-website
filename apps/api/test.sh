#!/bin/bash

source ../../.env

mvn test -Dtest.arguments=" \
--AWS_ACCESS_KEY=$API_AWS_ACCESS_KEY \
--AWS_SECRET_KEY=$API_AWS_SECRET_KEY \
--SECURITY_JWT_SECRET_KEY=$API_SECURITY_JWT_SECRET_KEY
"