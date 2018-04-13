<template>
  <section class="section hero foodtraucker is-fullheight">
    <div class="hero-body">
      <div class="container has-text-centered">
        <div class="column is-4 is-offset-4">
          <div class="box">
            <figure class="avatar">
              <img src="../../assets/small-logo.svg" alt="Foodtraucker">
            </figure>
            <div>
              <form v-show="!signUpSent">
                <div class="field">
                  <p class="control has-icons-left has-icons-right">
                    <span class="icon is-small is-left">
                      <i class="fas fa-envelope"></i>
                    </span>
                    <input v-validate="{required: true, email: true}" v-model="credentials.username"
                        name="email" type="email" id="email" class="input"
                        aria-describedby="emailHelp" placeholder="Enter email"
                        :class="[{'is-success' : !errors.has('email') && credentials.username.length > 0},
                          {'is-danger' : errors.has('email') && credentials.username.length > 0}]">
                  </p>
                  <p v-show="errors.has('email') && hasBeenSubmitted" class="help is-danger">
                    {{ errors.first('email') }}
                  </p>
                </div>
                <div class="field">
                  <p class="control has-icons-left has-icons-right">
                    <span class="icon is-small is-left">
                      <i class="fas fa-lock"></i>
                    </span>
                    <input v-validate="{required: true}" v-model="credentials.password"
                      name="password" type="password" id="password" placeholder="Password"
                      class="input" :class="[{'is-success' : !errors.has('password') && credentials.password.length > 0},
                        {'is-danger' : errors.has('password') && credentials.password.length > 0}]">
                  </p>
                  <p v-show="errors.has('password') && hasBeenSubmitted" class="help is-danger">
                    {{ errors.first('password') }}
                  </p>
                </div>
                <div class="field">
                  <p class="control has-icons-left has-icons-right">
                    <span class="icon is-small is-left">
                      <i class="fas fa-lock"></i>
                    </span>
                    <input v-validate="{required: true, confirmed: 'password'}"
                      name="confirm password" type="password" id="password-confirm" placeholder="Confirm password"
                      class="input" :class="[{'is-success' : !errors.has('confirm password') && credentials.password.length > 0},
                        {'is-danger' : errors.has('confirm password') && credentials.password.length > 0}]">
                  </p>
                  <p v-show="errors.has('confirm password') && hasBeenSubmitted" class="help is-danger">
                    {{ errors.first('confirm password') }}
                  </p>
                </div>
                <div class="field">
                  <p class="control">
                    <button @click.prevent="submit()" type="submit" class="button is-block is-info is-fullwidth"
                      :disabled="isLoading" :class="{'is-loading' : isLoading}">Submit</button>
                  </p>
                </div>
                <p v-show="serviceError.errorMessage && hasBeenSubmitted" class="help is-danger">
                  {{ serviceError.errorMessage }}
                </p>
              </form>
              <p v-show="signUpSent">Registration email has been sent your inbox</p>
            </div>
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
    signUpSent: false,
    credentials: {
      username: '',
      password: '',
    },
  }),
  computed: {
    serviceError() {
      return this.$store.state.auth.error ? this.$store.state.auth.error : {};
    },
    isLoading() {
      return this.$store.state.auth.loading;
    },
  },
  methods: {
    submit() {
      this.hasBeenSubmitted = true;
      this.$validator.validateAll().then((result) => {
        if (result) {
          this.$store.dispatch('signUp', this.credentials)
            .then(() => {
              this.signUpSent = true;
            });
        }
      });
    },
  },
  beforeDestroy() {},
};
</script>

<style scoped>
  .avatar {
    margin-top: -96px;
    padding-bottom: 12px;
  }
  .avatar img {
    padding: 5px;
  }
</style>
