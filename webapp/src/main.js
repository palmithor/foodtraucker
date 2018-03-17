// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import VeeValidate from 'vee-validate';
import VueAxios from 'vue-axios';
import Vue from 'vue';

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
    this.axios.get('/users/abc')
      .then(response => console.log(response))
      .catch(err => console.log(`in error: ${err}`));
  },
});

