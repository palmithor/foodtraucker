// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import VeeValidate from 'vee-validate';
import VueAxios from 'vue-axios';
import Vue from 'vue';
import 'leaflet/dist/leaflet.css';
import 'leaflet.markercluster/dist/MarkerCluster.css';
import 'leaflet.markercluster/dist/MarkerCluster.Default.css';


import App from './App';
import router from './router';
import store from './store';
import axios from './api/axios';

Vue.use(VueAxios, axios);

Vue.use(VeeValidate);
Vue.config.productionTip = false;


/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: { App },
  created() {
    store.dispatch('getCurrentUser');
  },
});

