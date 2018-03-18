/* eslint-disable max-len */
<template>
  <section class="section hero foodtraucker is-fullheight">
    <div class="hero-body">
      <div class="container has-text-centered">
        <div class="column is-4 is-offset-4">
          <div class="box">
            <figure class="avatar">
              <img src="../../assets/small-logo.svg">
            </figure>
            <form>
              <div class="field">
                <p class="control has-icons-left has-icons-right">
                  <input v-validate="{required: true, email: true}" v-model="credentials.username"
                         name="email" type="email" id="email" class="input"
                         placeholder="Email"
                         :class="[{'is-success' : !errors.has('email') && credentials.username.length > 0},
                          {'is-danger' : errors.has('email') && credentials.username.length > 0}]">
                  <span class="icon is-small is-left">
                        <i class="fas fa-envelope"></i>
                    </span>
                </p>
                <p v-show="errors.has('email') && hasBeenSubmitted" class="help is-danger">
                  {{ errors.first('email') }}
                </p>
              </div>
              <div class="field">
                <p class="control has-icons-left has-icons-right">
                  <input v-validate="{required: true, min: 6}" v-model="credentials.password"
                         name="password" type="password" id="password" placeholder="Password" class="input"
                         :class="[{'is-success' : !errors.has('password') && credentials.password.length > 0},
                          {'is-danger' : errors.has('password') && credentials.password.length > 0}]">
                  <span class="icon is-small is-left">
                        <i class="fas fa-lock"></i>
                    </span>
                </p>
                <p v-show="errors.has('password') && hasBeenSubmitted" class="help is-danger">
                  {{ errors.first('password') }}
                </p>
              </div>
              <div class="field">
                <p class="control">
                  <button @click.prevent="submit()" type="submit"
                          class="button is-block is-info is-fullwidth"
                          :disabled="isLoading"
                          :class="{'is-loading' : isLoading}">Login</button>
                </p>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>


<script>
export default {
  data: () => ({
    hasBeenSubmitted: false,
    credentials: {
      username: '',
      password: '',
    },
  }),
  computed: {
    serviceError() {
      return this.$store.state.auth.error;
    },
    isLoading() {
      return this.$store.state.auth.loading;
    },
    user() {
      return this.$store.state.auth.user;
    },
    isAuthenticated() {
      return this.$store.getters.isAuthenticated;
    },
  },
  methods: {
    submit() {
      this.hasBeenSubmitted = true;
      this.$validator.validateAll().then((result) => {
        if (result) {
          this.$store.dispatch('login', this.credentials)
            .then(() => this.$router.replace(this.$route.query.redirect || '/dashboard'));
        }
      });
    },
  },
  beforeDestroy() {

  },
};
</script>

<style scoped>
  .box {
    margin-top: 5rem;
  }
  .avatar {
    margin-top: -96px;
    padding-bottom: 12px;
  }
  .avatar img {
    padding: 5px;
  }
</style>
