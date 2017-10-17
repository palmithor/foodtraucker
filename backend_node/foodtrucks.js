'use strict';

const uuidv1 = require('uuid/v1');

var aws = require('aws-sdk');
var dynamodb = new aws.DynamoDB();


const checkinsTableName = process.env.TRUCK_CHECKINS_TABLE;
const trucksTableName = process.env.TRUCKS_TABLE;

module.exports.checkin = function (event, context) {

    var requestBody = JSON.parse(event.body);

    var foodtruckId = event.pathParameters.id;

    var datetime = new Date();
    var checkinId = uuidv1();
    // TODO validate event data and that the check in time is available
    return dynamodb.query({
            ExpressionAttributeValues: {
                ":v1": {
                    S: foodtruckId
                }
            },
            KeyConditionExpression: "id = :v1",
            ProjectionExpression: "id",
            TableName: trucksTableName
        }).promise()
        .then(function (findResult) {
            if (findResult.Count < 1) {
                return Promise.reject({
                    statusCode: 404,
                    headers: {},
                    isBase64Encoded: false,
                    body: JSON.stringify({
                        message: "Foodtruck not found"
                    })
                });
            }

            return dynamodb.putItem({
                    "TableName": checkinsTableName,
                    "Item": {
                        "foodtruck_id": {
                            "S": foodtruckId
                        },
                        "id": {
                            "S": checkinId
                        },
                        "lat": {
                            "N": requestBody.lat.toString()
                        },
                        "lon": {
                            "N": requestBody.lon.toString()
                        },
                        "checkin": {
                            "S": requestBody.checkin.toString()
                        },
                        "checkout": {
                            "S": requestBody.checkout.toString()
                        },
                        "created": {
                            "S": datetime.getTime().toString()
                        },
                        "updated": {
                            "S": datetime.getTime().toString()
                        }
                    }
                })
                .promise()
        })
        .then(function (result) {
            context.done(null, {
                statusCode: 200,
                headers: {},
                isBase64Encoded: false,
                body: JSON.stringify({
                    foodtruck_id: foodtruckId,
                    id: checkinId,
                    lat: requestBody.lat,
                    lon: requestBody.lon,
                    checkin: requestBody.checkin,
                    checkout: requestBody.checkout,
                    created: datetime.getTime(),
                    updated: datetime.getTime()
                })
            })
        })
        .catch(function (err) {
            console.log('err: ' + JSON.stringify(err));
            if (err.statusCode) {
                context.done(null, err);
            } else {
                context.done(err);
            }
        });
};