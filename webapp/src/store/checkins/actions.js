/* eslint-disable no-unused-vars */
import { Client } from 'elasticsearch';
import * as types from './mutation-types';


const esClient = new Client({
  host: 'https://search-foodtraucker-nscubf57faz4unxvtmsuthfphy.eu-central-1.es.amazonaws.com',
  log: 'warning',
});

const checkinIndex = process.env.ES_CHECKIN_INDEX;


export default {
  loadCheckins({ commit }, boundingBox) {
    commit(types.LOADING);
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
                    lat: Math.max(boundingBox._northEast.lat, boundingBox._southWest.lat),
                    lon: Math.min(boundingBox._northEast.lng, boundingBox._southWest.lng),
                  },
                  bottom_right: {
                    lat: Math.min(boundingBox._northEast.lat, boundingBox._southWest.lat),
                    lon: Math.max(boundingBox._northEast.lng, boundingBox._southWest.lng),
                  },
                },
              },
            },
          },
        },
      },
    }).then((resp) => {
      const checkins = resp.hits.hits.filter(hit => hit._source.foodtruck_id)
        .map(hit => ({
          foodtruck_id: hit._source.foodtruck_id,
          checkin: new Date(Number(hit._source.checkin)),
          checkout: new Date(Number(hit._source.checkout)),
          latLng: [hit._source.coordinate.lat, hit._source.coordinate.lon],
        }));
      commit(types.CHECKINS_LIST, checkins);
      return checkins;
    }).catch((err) => {
    });
  },
};
