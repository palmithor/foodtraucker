import Vue from 'vue';
import Router from 'vue-router';

import Home from '@/components/home/Home';
import Login from '@/components/auth/Login';
import SignUp from '@/components/auth/SignUp';
import Dashboard from '@/components/dashboard/Dashboard';
import Checkin from '@/components/foodtruck/Checkin';
import Foodtruck from '@/components/foodtruck/Foodtruck';

import store from '../store';

Vue.use(Router);


const requireAuth = function (to, from, next) {
  if (!store.getters.isAuthenticated) {
    next({
      path: '/',
    });
  } else {
    next();
  }
};


export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
    },
    {
      path: '/login',
      name: 'login',
      component: Login,
    },
    {
      path: '/signup',
      name: 'signup',
      component: SignUp,
    },
    {
      path: '/dashboard',
      name: 'dashboard',
      component: Dashboard,
      beforeEnter: requireAuth,
    },
    {
      path: '/foodtrucks/:id',
      name: 'foodtruck',
      component: Foodtruck,
      beforeEnter: requireAuth,
    },
    {
      path: '/foodtrucks/:id/checkins',
      name: 'foodtruck-checkin',
      component: Checkin,
      beforeEnter: requireAuth,
    },
  ],
});
