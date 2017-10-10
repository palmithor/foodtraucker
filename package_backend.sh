#!/bin/bash

node_backend_package="./backend_node.zip"
if [ -f "$node_backend_package" ]
then
	rm $node_backend_package
fi

zip -j backend_node.zip backend_node/*
mvn clean package -f backend_java/pom.xml