# Quick Start
Prerequisites: Node, npm, java 8 and maven.

## Install Serverless
npm install -g serverless

## Configure Project
 serverless config credentials --provider aws --key $KEY --secret $SECRET --profile foodtraucker

# Deploy : 
serverless deploy --profile foodtraucker