import * as types from './mutation-types';

export default {
  [types.CHECKINS_LIST](state, payload) {
    state.list = payload;
    state.loading = false;
    state.error = null;
  },
  [types.LOADING](state) {
    state.loading = true;
  },
  [types.ERROR](state, payload) {
    state.error = payload;
  },
};
