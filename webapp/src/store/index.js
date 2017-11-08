import Vue from 'vue'
import Vuex from 'vuex'
import SessionModule from './modules/session'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    session: SessionModule
  }
})
