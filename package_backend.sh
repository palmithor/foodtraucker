#!/bin/bash

node_backend_package="./backend_node.zip"
if [ -f "$node_backend_package" ]
then
	echo "Removing previous zip file"
	rm $node_backend_package
fi

npm --prefix ./backend_node install ./backend_node

cd backend_node; zip -r ../backend_node.zip *; cd ..

mvn clean package -f backend_java/pom.xml