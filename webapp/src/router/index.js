import Vue from 'vue';
import Router from 'vue-router';

import Home from '@/components/home/Home';
import Login from '@/components/auth/Login';
import SignUp from '@/components/auth/SignUp';
import Dashboard from '@/components/dashboard/Dashboard';
import CreateCheckin from '@/components/foodtruck/CreateCheckin';
import Foodtruck from '@/components/foodtruck/Foodtruck';
import CreateFoodtruck from '@/components/foodtruck/CreateFoodtruck';

import store from '../store';

Vue.use(Router);


const requireAuth = (to, from, next) => {
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
      path: '/foodtrucks/create',
      name: 'foodtruck-create',
      component: CreateFoodtruck,
      beforeEnter: requireAuth,
    },
    {
      path: '/foodtrucks/:id',
      name: 'foodtruck',
      component: Foodtruck,
      beforeEnter: requireAuth,
    },
    {
      path: '/foodtrucks/:id/checkins/create',
      name: 'checkin-create',
      props: true,
      component: CreateCheckin,
      beforeEnter: requireAuth,
    },
    {
      path: '*',
      redirect: '/',
    },
  ],
});
