'use strict';

var aws = require('aws-sdk');
var dynamodb = new aws.DynamoDB();

const userTableName = process.env.USERS_TABLE;

module.exports.signup = function (event, context) {
    console.log('Event: ' + JSON.stringify(event));

    var datetime = new Date().getTime().toString();

    dynamodb.listTables().promise()
        .then(function (result) {
            console.log(JSON.stringify(result, null, '  '));
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