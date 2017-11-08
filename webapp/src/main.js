// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import VueResource from 'vue-resource'
import VueCookie from 'vue-cookie'
import VeeValidate from 'vee-validate'

Vue.use(VeeValidate)
Vue.use(VueResource)
Vue.use(VueCookie)
Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: { App },
  created: function () {
    this.$store.dispatch('initSession')
  }
})

