/* eslint-disable no-unused-vars */
import { Client } from 'elasticsearch';
import * as types from './mutation-types';


const esClient = new Client({
  host: 'https://search-foodtraucker-nscubf57faz4unxvtmsuthfphy.eu-central-1.es.amazonaws.com',
  log: 'trace',
});

const checkinIndex = process.env.ES_CHECKIN_INDEX;


export default {
  loadCheckins({ commit }, boundingBox) {
    return esClient.search({
      index: `${checkinIndex}`,
      type: ['checkins'],
      body: {
        query: {
          bool: {
            must: {
              match_all: {},
            },
            filter: {
              geo_bounding_box: {
                coordinate: {
                  top_left: {
                    lat: boundingBox._northEast.lat,
                    lon: boundingBox._northEast.lng,
                  },
                  bottom_right: {
                    lat: boundingBox._southWest.lat,
                    lon: boundingBox._southWest.lng,
                  },
                },
              },
            },
          },
        },
      },
    }).then((resp) => {
      const hits = resp.hits.hits;
      console.log(hits);
    }).catch((err) => {
      console.log(err);
    });
  },
};
