import * as types from './mutation-types';

export default {
  [types.AUTHENTICATE](state, payload) {
    state.user = payload;
    state.loading = false;
    state.error = null;
  },
  [types.SIGN_OUT](state) {
    state.user = null;
    state.loading = false;
    state.error = null;
  },
  [types.LOADING](state) {
    state.loading = true;
  },
  [types.ERROR](state, payload) {
    state.loading = false;
    state.error = payload;
  },
};
