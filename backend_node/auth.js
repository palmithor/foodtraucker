'use strict';

var aws = require('aws-sdk');
var dynamodb = new aws.DynamoDB();



//const uuid = require('uuid');
//const dynamodb = require('./dynamodb');

//const userTable = process.env.USERS_TABLE;
//const traucksTable = process.env.TRAUCKS_TABLE;


module.exports.signup = function (event, context) {
    console.log('Event: ' + JSON.stringify(event));
    console.log('NODE_ENV = ' + process.env.NODE_ENV);

    var userAuthTableName = 'foodtraucker-' + process.env.NODE_ENV + '-user-auth';
    var userTableName = 'foodtraucker-' + process.env.NODE_ENV + '-users';


    var datetime = new Date().getTime().toString();

    dynamodb.listTables().promise()
        .then(function (result) {
            console.log(JSON.stringify(result, null, '  '));
            return dynamodb.putItem({
                "TableName": userAuthTableName,
                "Item": {
                    "cognito_id": {
                        "S": event.userName
                    },
                    "date": {
                        "S": datetime
                    },
                    "email": {
                        "S": event.request.userAttributes.email
                    }
                }
            }).promise();
        }).then(function (result) {
            return dynamodb.putItem({
                "TableName": userTableName,
                "Item": {
                    "email": {
                        "S": event.request.userAttributes.email
                    },
                    "date": {
                        "S": datetime
                    }
                }
            }).promise();
        })
        .then(function (result) {
            console.log('Success YAY!')
            context.done(null, event);
        })
        .catch(function (err) {
            console.log(err);
        });
};