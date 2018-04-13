import actions from './actions';
import mutations from './mutations';
import getters from './getters';

const state = {
  user: undefined,
  loading: false,
  error: undefined,
};


export default {
  state,
  mutations,
  actions,
  getters,
};

// https://github.com/kopterio/vue-auth-cognito/blob/master/src/actions.js
