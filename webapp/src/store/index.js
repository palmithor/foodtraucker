import Vue from 'vue';
import Vuex from 'vuex';
import AuthModule from './auth';
import FoodtruckModule from './foodtrucks';
import CheckinsModule from './checkins';

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    auth: AuthModule,
    foodtrucks: FoodtruckModule,
    checkins: CheckinsModule,
  },
});
