import Vue from 'vue';
import Router from 'vue-router';

import Home from '@/components/Home';
import Login from '@/components/auth/Login';
import SignUp from '@/components/auth/SignUp';
import Dashboard from '@/components/dashboard/Dashboard';

Vue.use(Router);

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
    },
    {
      path: '/foodtrucks/:id',
      name: 'foodtruck',
      component: Home,
    },
    {
      path: '/foodtrucks/:id/checkins',
      name: 'foodtruck-checkin',
      component: Home,
    },
  ],
});
