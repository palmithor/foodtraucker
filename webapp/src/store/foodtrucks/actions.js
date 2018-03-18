/* eslint-disable no-unused-vars */
import * as types from './mutation-types';
import FoodtruckService from '../../service/FoodtruckService';


export default {
  getSelfFoodtrucks({ commit }) {
    return FoodtruckService.getSelfFoodtrucks()
      .then((response) => {
        const list = response.data.map(foodtruck => ({
          id: foodtruck.foodtruck_id,
          name: foodtruck.name,
          created: new Date(foodtruck.created).toDateString(),
          updated: new Date(foodtruck.updated).toDateString(),
        }));
        commit(types.FOODTRUCK_LIST, list);
      })
      .catch((err) => {

      });
  },
};
