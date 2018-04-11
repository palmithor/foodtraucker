import Vue from 'vue';
import Vuex from 'vuex';
import VuexPersistence from 'vuex-persist';
import AuthModule from './auth';
import FoodtruckModule from './foodtrucks';
import CheckinsModule from './checkins';

const vuexLocal = new VuexPersistence({
  storage: window.localStorage,
});

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    auth: AuthModule,
    foodtrucks: FoodtruckModule,
    checkins: CheckinsModule,
  },
  plugins: [vuexLocal.plugin],
});
