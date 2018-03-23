import actions from './actions';
import mutations from './mutations';
import getters from './getters';

const state = {
  list: [],
  loading: false,
  error: null,
};


export default {
  state,
  mutations,
  actions,
  getters,
};

// https://github.com/kopterio/vue-auth-cognito/blob/master/src/actions.js
