/* eslint-disable max-len */
<template>
  <section class="section hero foodtraucker is-fullheight">
    <div class="hero-body">
      <div class="container has-text-centered">
        <div class="column is-4 is-offset-4">
          <div class="box">
            <form>
              <div class="field">
                <p class="control has-icons-left has-icons-right">
                  <input v-validate="{required: true }" v-model="foodtruckName" name="Foodtruck name"
                         type="text" class="input" placeholder="Foodtruck name"
                         :class="[{'is-success' : !errors.has('Foodtruck name') && foodtruckName.length > 0},
                          {'is-danger' : (errors.has('Foodtruck name') && foodtruckName.length > 0) || (foodtruckName.length === 0 && hasBeenSubmitted)}]">
                  <span class="icon is-small is-left">
                        <i class="fas fa-truck"></i>
                    </span>
                </p>
                <p v-show="serviceError.errorMessage && hasBeenSubmitted" class="help is-danger">
                  {{ serviceError.errorMessage }}
                </p>
              </div>
              <div class="field">
                <p class="control">
                  <button @click.prevent="submit()" type="submit"
                          class="button is-block is-info is-fullwidth"
                          :disabled="isLoading"
                          :class="{'is-loading' : isLoading}">Create
                  </button>
                </p>
              </div>
              <p v-show="errors.has('Foodtruck name') && hasBeenSubmitted" class="help is-danger">
                {{ errors.first('Foodtruck name') }}
              </p>
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
    foodtruckName: '',
    serviceError: {},
    isLoading: false,
  }),
  methods: {
    submit() {
      this.hasBeenSubmitted = true;
      this.loading = true;
      this.$validator.validateAll().then((result) => {
        if (result) {
          this.axios.post('/foodtrucks', {
            name: this.foodtruckName,
          }).then(() => {
            this.loading = false;
            this.$router.push({ name: 'dashboard' });
          }).catch((err) => {
            this.loading = false;
            this.serviceError = err.response.data;
          });
        }
      });
    },
  },
  beforeDestroy() {

  },
};
</script>

<style scoped>

</style>
