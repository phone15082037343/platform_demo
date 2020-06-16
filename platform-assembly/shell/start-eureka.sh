#!/bin/bash

java -jar platform-eureka.jar \
    --spring.profiles.active=test \
    > platform-eureka.log 2>&1 &
