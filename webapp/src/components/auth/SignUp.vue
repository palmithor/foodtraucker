<template>
  <div>
    <form>
      <div>
        <label for="email">Email address</label>
        <input v-validate="{required: true, email: true}" v-model="credentials.username"
               name="email" type="email" id="email"
               aria-describedby="emailHelp" placeholder="Enter email">
      </div>
      <div>
        <label for="password">Password</label>
        <input v-validate="{required: true}" v-model="credentials.password"
               name="password" type="password"
               id="password" placeholder="Password">
      </div>
      <div>
        <label for="password-confirm">Confirm Password</label>
        <input v-validate="{required: true, confirmed: 'password'}"
               name="Confirm Password" type="password"
               id="password-confirm" placeholder="Password">
      </div>
      <button @click.prevent="submit()" type="submit" class="btn btn-primary">Submit</button>
    </form>
    <p>{{ errors }}</p>
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
  },
  methods: {
    submit() {
      this.$validator.validateAll().then((result) => {
        if (result) {
          this.$store.dispatch('signUp', this.credentials);
        }
      });
    },
  },
  beforeDestroy() {
  },
};
</script>
