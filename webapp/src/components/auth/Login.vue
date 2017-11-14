<template>
  <div>
    <form>
      <div>
        <label for="email">Email address</label>
        <input v-validate="{required: true, email: true}" v-model="user.email" name="Email" type="email" id="email"
               aria-describedby="emailHelp" placeholder="Enter email">
      </div>
      <div>
        <label for="password">Password</label>
        <input v-validate="{required: true}" v-model="user.password" name="Password" type="password"
               id="password" placeholder="Password">
      </div>
      <button @click.prevent="submit()" type="submit" class="btn btn-primary">Submit</button>
    </form>
    <p>Is loading: {{ isLoading }}</p>
    <p>Is authenticated: {{ isAuthenticated }}</p>
    <span v-show="errors.has('email')">{{ errors.first('email') }}</span>
    <p>{{ errors }}</p>
    <p>{{ serviceError }}</p>
    <p>{{ session }}</p>
  </div>
</template>

<script>
  export default {
    data: () => ({
      user: {
        email: '',
        password: ''
      }
    }),
    computed: {
      isLoading () {
        return this.$store.state.session.isLoading
      },
      serviceError () {
        return this.$store.state.session.error
      },
      session () {
        return this.$store.state.session.session
      },
      isAuthenticated () {
        return this.$store.getters.isAuthenticated
      }
    },
    methods: {
      submit () {
        this.$validator.validateAll().then((result) => {
          if (result) {
            this.$store.dispatch('login', {email: this.user.email, password: this.user.password})
          }
        })
      }
    },
    beforeDestroy: function () {
      this.$store.dispatch('sessionCleanup')
    }
  }
</script>
