import Vue from 'vue';
import Router from 'vue-router';

import Home from '@/components/Home';
import Login from '@/components/auth/Login';
import SignUp from '@/components/auth/SignUp';
import Dashboard from '@/components/Dashboard';

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home,
    },
    {
      path: '/login',
      name: 'Login',
      component: Login,
    },
    {
      path: '/signup',
      name: 'Sign Up',
      component: SignUp,
    },
    {
      path: '/dashboard',
      name: 'Dashboard',
      component: Dashboard,
    },
  ],
});
