import Vue from 'vue';
import Vuex from 'vuex';
import AuthModule from './auth';
import FoodtruckModule from './foodtrucks';

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    auth: AuthModule,
    foodtrucks: FoodtruckModule,
  },
});
