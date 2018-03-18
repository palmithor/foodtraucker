import { createAPIGatewayEventCallbackHandler } from '@foodtraucker/utils';
import { APIGatewayEvent } from 'aws-lambda';
import { DynamoDB } from 'aws-sdk';

const dbClient = new DynamoDB.DocumentClient();

export const promiseHandler = async (event: APIGatewayEvent) => {
  const foodtruckUserTable = process.env.FOODTRUCK_USERS_TABLE;
  const foodtrucksTable = process.env.TRUCKS_TABLE;

  const userFoodtrucksResult = await dbClient
    .query({
      TableName: foodtruckUserTable,
      KeyConditionExpression: '#cognitoId = :cognitoId',
      ExpressionAttributeNames: {
        '#cognitoId': 'cognito_id',
      },
      ExpressionAttributeValues: {
        ':cognitoId': event.requestContext.authorizer!.claims.sub,
      },
    })
    .promise();

  if (userFoodtrucksResult.Count && userFoodtrucksResult.Count > 0) {
    const foodtruckListResult: any[] = await Promise.all(
      userFoodtrucksResult!.Items!.map(async foodtruckUser => {
        return dbClient
          .query({
            TableName: foodtrucksTable,
            KeyConditionExpression: '#foodtruckId = :foodtruckId',
            ExpressionAttributeNames: {
              '#foodtruckId': 'foodtruck_id',
            },
            ExpressionAttributeValues: {
              ':foodtruckId': foodtruckUser.foodtruck_id,
            },
          })
          .promise();
      }),
    );

    return {
      statusCode: 200,
      headers: {
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Methods": "GET,HEAD,OPTIONS,POST,PUT",
        "Access-Control-Allow-Headers": "Origin, X-Requested-With, Content-Type, Accept, Authorization"
      },
      body: JSON.stringify([].concat(...foodtruckListResult.map(arr => arr.Items))),
    };
  }

  return {
    statusCode: 200,
    headers: {
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Methods": "GET,HEAD,OPTIONS,POST,PUT",
        "Access-Control-Allow-Headers": "Origin, X-Requested-With, Content-Type, Accept, Authorization"
    },
    body: JSON.stringify([]),
  };
};

export const handler = createAPIGatewayEventCallbackHandler(promiseHandler);
