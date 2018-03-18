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
    const foodtruckListResult = await Promise.all(
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
    /*
    [
{
    "Items": [
        {
            "foodtruck_id": "51da67b0-293a-11e8-9957-539ba287ff1f",
            "created": 1521219109291,
            "updated": 1521219109291,
            "name": "Foodtraucker"
        }
    ],
    "Count": 1,
    "ScannedCount": 1
}
]*/
    let result: any[] = [];
    if(foodtruckListResult.length > 0) {
      result = foodtruckListResult[0].Items!.concat(
        foodtruckListResult.splice(1).map(i => i.Items))
    }
    return {
      statusCode: 200,
      body: JSON.stringify(result),
    };
  }

  return {
    statusCode: 200,
    body: JSON.stringify([]),
  };
};

export const handler = createAPIGatewayEventCallbackHandler(promiseHandler);
