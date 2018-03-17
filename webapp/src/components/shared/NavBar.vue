<template>
  <div>
    <li v-for="route in routes">
      <router-link :to="route">{{ route.name }}</router-link>
    </li>
    <button v-show="isAuthenticated" v-on:click="signOut">Sign Out</button>
  </div>
</template>

<script>
export default {
  data: () => ({

  }),
  methods: {
    signOut() {
      this.$store.dispatch('signOut');
    },
  },
  computed: {
    isAuthenticated() {
      return this.$store.getters.isAuthenticated;
    },
    routes() {
      const routes = [{
        path: '/',
        name: 'Home',
      }];
      if (this.isAuthenticated) {
        routes.push({
          path: '/dashboard',
          name: 'Dashboard',
        });
      } else {
        routes.push({
          path: '/login',
          name: 'Login',
        });
        routes.push({
          path: '/signup',
          name: 'Sign Up',
        });
      }
      return routes;
    },
  },
};
</script>


<style scoped>
</style>
