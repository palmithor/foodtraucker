import axios from 'axios';
import AuthService from '../service/AuthService';


const instance = axios.create({
  baseURL: process.env.BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

instance.interceptors.request.use(config => AuthService.getCurrentUser()
  .then((currentUser) => {
    config.headers.Authorization = currentUser.tokens.IdToken;
    return config;
  }), err => Promise.reject(err));

export default instance;
