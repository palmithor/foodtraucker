#!/bin/bash

zip -j backend_node.zip backend_node/*
mvn clean package -f backend_java/pom.xml