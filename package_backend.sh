#!/bin/bash

cd backend_node; yarn clean; yarn install; yarn build; yarn package; cd ..

mvn clean package -f backend_java/pom.xml