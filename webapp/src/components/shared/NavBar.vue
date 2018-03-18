<template>
  <nav class="navbar is-transparent">
    <div class="navbar-brand">
      <a class="navbar-item">
        <img src="../../assets/small-logo.svg" alt="Foodtraucker">
      </a>
      <button class="navbar-burger burger" data-target="navbarExampleTransparentExample"
              :class="{'is-active' : isMenuShowing}"
              @click="isMenuShowing = !isMenuShowing">
        <span></span>
        <span></span>
        <span></span>
      </button>
    </div>

    <div id="navbarExampleTransparentExample" class="navbar-menu" :class="{'is-active' : isMenuShowing}">
      <div class="navbar-start">
        <router-link
          to="/"
          class="navbar-item">
            Home
        </router-link>
        <router-link
          v-if="isAuthenticated"
          to="/dashboard"
          class="navbar-item">
            Dashboard
        </router-link>
      </div>

      <div class="navbar-end">
        <div class="navbar-item">
          <div v-if="!isAuthenticated" class="field is-grouped">
            <p class="control">
              <router-link
                to="login"
                v-show="!isAuthenticated"
                class="d-tw-button button"
              >
                Login
              </router-link>
            <p class="control">
              <router-link
                to="signup"
                class="button is-info"
              >
                Sign Up
              </router-link>
            </p>
          </div>
          <div v-else class="field is-grouped">
            <p class="control">
              <button
                class="button is-info"
                @click="signOut()"
              >
                Sign Out
              </button>
            </p>
          </div>
        </div>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  data: () => ({
    isMenuShowing: false,
  }),
  methods: {
    signOut() {
      this.$store.dispatch('signOut')
        .then(() => this.$router.replace(this.$route.query.redirect || '/'));
    },
  },
  computed: {
    isAuthenticated() {
      return this.$store.getters.isAuthenticated;
    },
  },
};
</script>


<style scoped>

</style>
