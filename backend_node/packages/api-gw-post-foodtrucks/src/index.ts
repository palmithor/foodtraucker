import {createAPIGatewayEventCallbackHandler} from '@foodtraucker/utils';
import {APIGatewayEvent} from 'aws-lambda';
import {DynamoDB} from 'aws-sdk';
import {v1 as uuid} from 'uuid';

const dynamodb = new DynamoDB();

export const promiseHandler = async (event: APIGatewayEvent) => {
    const foodtruckUserTable = process.env.FOODTRUCK_USERS_TABLE;
    const trucksTable = process.env.TRUCKS_TABLE;
    const datetime = new Date().getTime().toString();

    const body = JSON.parse(event.body as string);
    const foodTruckId = uuid();

    const scanResult = await dynamodb.scan({
        TableName: trucksTable,
        ProjectionExpression: 'foodtruck_id',
        FilterExpression: '#foodtruckName = :foodtruckName',
        ExpressionAttributeNames: {
            '#foodtruckName': 'name'
        },
        ExpressionAttributeValues: {
            ':foodtruckName': {S: body.name}
        }
    }).promise();

    if (scanResult.Items!.length > 0) {
        return {
            statusCode: 409,
            body: JSON.stringify({errorMessage: 'Foodtruck name already exists'}),
        };
    }

    const foodTruckUser = {
        foodtruck_id: {
            S: foodTruckId
        },
        cognito_id: {
            S: event.requestContext.authorizer!.claims.sub
        },
        updated: {
            N: datetime
        },
        created: {
            N: datetime,
        },
    };
    const foodTruck = {
        foodtruck_id: {
            S: foodTruckId
        },
        name: {
            S: body.name
        },
        updated: {
            N: datetime
        },
        created: {
            N: datetime,
        },
    };

    await Promise.all([
        dynamodb.putItem({
            TableName: foodtruckUserTable,
            Item: foodTruckUser,
        }).promise(),
        dynamodb.putItem({
            TableName: trucksTable,
            Item: foodTruck,
        }).promise(),
    ]);

    return {
        statusCode: 200,
        body: JSON.stringify({
            foodtruck_id: foodTruckId,
            name: body.name,
            updated: datetime,
            created: datetime,
        }),
    };
};

export const handler = createAPIGatewayEventCallbackHandler(promiseHandler);