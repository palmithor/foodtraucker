import { createAPIGatewayEventCallbackHandler } from '@foodtraucker/utils';
import { APIGatewayEvent } from 'aws-lambda';
import { DynamoDB } from 'aws-sdk';
import { v1 as uuid } from 'uuid';

const dynamodb = new DynamoDB();

export const promiseHandler = async (event: APIGatewayEvent) => {
  const tableName = process.env.FOODTRUCK_CHECKINS_TABLE;
  const datetime = new Date().getTime().toString();

  const body = JSON.parse(event.body as string);

  const id = uuid();
  const foodTruckId = event.pathParameters!.id;
    const item = {
    foodtruck_id: {
      S: foodTruckId,
    },
    id: {
      S: id,
    },
    checkin: {
      N: body.checkin.toString()
    },
    checkout: {
      N: body.checkin.toString()
    },
    lat: {
      N: body.lat.toString()
    },
    lon: {
      N: body.lon.toString()
    },
    updated: {
      N: datetime
    },
    created: {
      N: datetime,
    },
  };
  await dynamodb
    .putItem({
      TableName: tableName,
      Item: item,
    })
    .promise();

  return {
    statusCode: 200,
    body: JSON.stringify({
        id,
        foodtruck_id: foodTruckId,
        checkin: body.checkin,
        checkout: body.checkout,
        lat: body.lat,
        lon: body.lon,
        updated: datetime,
        created: datetime
    }),
  };
};

export const handler = createAPIGatewayEventCallbackHandler(promiseHandler);