# Quick Start

Prerequisites: `node`, `npm`, `java8` and `maven`.

## Install Serverless

``` bash
npm install -g serverless
```

## Configure AWS Credentials

``` bash
 serverless config credentials --provider aws --key $KEY --secret $SECRET --profile foodtraucker
 ```

## Deployment

``` bash
serverless deploy --profile foodtraucker
```