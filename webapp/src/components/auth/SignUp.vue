<template>
  <div>
    <form>
      <div>
        <label for="email">Email address</label>
        <input v-validate="{required: true, email: true}" v-model="user.email" name="email" type="email" id="email"
               aria-describedby="emailHelp" placeholder="Enter email">
      </div>
      <div>
        <label for="password">Password</label>
        <input v-validate="{required: true}" v-model="user.password" name="password" type="password"
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
      }
    },
    methods: {
      submit () {
        console.log('before validate')
        this.$validator.validateAll().then((result) => {
          if (result) {
            this.$store.dispatch('signUp', {email: this.user.email, password: this.user.password})
            console.log('before validate')
          }
        })
      }
    },
    beforeDestroy: function () {
      this.$store.dispatch('sessionCleanup')
    }
  }
</script>
