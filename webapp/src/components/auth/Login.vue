<template>
  <div>
    <form>
      <div>
        <label for="email">Email address</label>
        <input v-validate="{required: true, email: true}" v-model="credentials.username" name="Email" type="email" id="email"
               aria-describedby="emailHelp" placeholder="Enter email">
      </div>
      <div>
        <label for="password">Password</label>
        <input v-validate="{required: true}" v-model="credentials.password" name="Password" type="password"
               id="password" placeholder="Password">
      </div>
      <button @click.prevent="submit()" type="submit" class="btn btn-primary">Submit</button>
    </form>
    <p>Is loading: {{ isLoading }}</p>
    <p>Is authenticated: {{ isAuthenticated }}</p>
    <span v-show="errors.has('email')">{{ errors.first('email') }}</span>
    <p>{{ errors }}</p>
    <p>{{ serviceError }}</p>
    <p>{{ user }}</p>
  </div>
</template>

<script>
export default {
  data: () => ({
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
      if (this.$store.getters.isAuthenticated) {
        this.$router.replace(this.$route.query.redirect || '/dashboard');
      }
      return this.$store.getters.isAuthenticated;
    },
  },
  methods: {
    submit() {
      this.$validator.validateAll().then((result) => {
        if (result) {
          this.$store.dispatch('login', this.credentials);
        }
      });
    },
  },
  beforeDestroy() {

  },
};
</script>
