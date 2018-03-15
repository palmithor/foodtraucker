import { createStreamEventCallbackHandler } from '@foodtraucker/utils';
import { DynamoDBStreamEvent } from 'aws-lambda';
import { Client } from 'elasticsearch';

const esClient = new Client({
  host: 'https://search-foodtraucker-nscubf57faz4unxvtmsuthfphy.eu-central-1.es.amazonaws.com',
  connectionClass: require('http-aws-es'),
  log: 'trace', // todo trace
});

const index = `foodtraucker-${process.env.NODE_ENV}-foodtrucks`;

export const promiseHandler = async (event: DynamoDBStreamEvent) => {
  try {
    await esClient.indices.getMapping({ index });
  } catch (e) {
    await createIndex();
  }
  await Promise.all(
    event.Records.map(async record => {
      if (record) {
        if (record.eventName === 'INSERT') {
          const esResult = await esClient.index({
            index,
            type: 'checkins',
            id: record.dynamodb!.NewImage!.id.S,
            body: {
              foodtruck_id: record.dynamodb!.NewImage!.foodtruck_id.S,
              checkin: record.dynamodb!.NewImage!.checkin.N,
              checkout: record.dynamodb!.NewImage!.checkout.N,
              coordinate: {
                lat: record.dynamodb!.NewImage!.lat.N,
                lon: record.dynamodb!.NewImage!.lon.N,
              },
            },
          });
          return esResult;
        } else if (record.eventName === 'REMOVE') {
          console.log('remove record');
        } else if (record.eventName === 'MODIFY') {
          console.log('modify record');
        }
        return undefined;
      }
    }),
  );

  return event;
};

async function createIndex() {
  await esClient.indices.create({
    index,
    body: {
      mappings: {
        checkins: {
          properties: {
            coordinate: {
              type: 'geo_point',
            },
            checkin: {
              type: 'date',
              format: 'epoch_millis',
            },
            checkout: {
              type: 'date',
              format: 'epoch_millis',
            },
            foodtruck_id: {
              type: 'string',
            },
          },
        },
      },
    },
  });
}

export const handler = createStreamEventCallbackHandler(promiseHandler);
