import axios from 'axios';
import store from '../store';


const instance = axios.create({
  baseURL: process.env.BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

instance.interceptors.request.use((config) => {
  if (store.getters.isAuthenticated) {
    config.headers.Authorization = `${store.state.auth.user.tokens.IdToken}`;
    store.dispatch('getCurrentUser');
  }
  return config;
}, (err) => {
  Promise.reject(err);
});


/*

 */

export default instance;
