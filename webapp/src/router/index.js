import Vue from 'vue'
import Router from 'vue-router'

import Landing from '@/components/Landing'
import Login from '@/components/auth/Login'
import SignUp from '@/components/auth/SignUp'
import About from '@/components/about/About'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Landing',
      component: Landing
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/signup',
      name: 'Sign Up',
      component: SignUp
    },
    {
      path: '/about',
      name: 'About',
      component: About
    }
  ]
})
