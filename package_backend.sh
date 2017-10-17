#!/bin/bash

node_backend_package="./backend_node.zip"
if [ -f "$node_backend_package" ]
then
	echo "Removing previous zip file"
	rm $node_backend_package
fi

npm --prefix ./backend_node install ./backend_node

zip -j backend_node.zip backend_node/*
mvn clean package -f backend_java/pom.xml