/* eslint-disable max-len */
<template>
  <section class="section hero foodtraucker is-fullheight">
    <div class="hero-body">
      <div class="container">
        <div class="column is-4 is-offset-4">
          <div class="box">
            <form>
              <div class="field">
                <label class="label">Latitude</label>
                <p class="control has-icons-left has-icons-right">
                  <input v-validate="{required: true, max_value: 90, min_value: -90  }" v-model="checkin.lat" name="Latitude"
                         type="text" class="input" placeholder="Latitude"
                         :class="[{'is-success' : !errors.has('Latitude')},
                          {'is-danger' : (errors.has('Latitude') && hasBeenSubmitted)}]">
                  <span class="icon is-small is-left">
                        <i class="fas fa-compass"></i>
                    </span>
                </p>
                <p v-show="errors.has('Latitude') && hasBeenSubmitted" class="help is-danger">
                  {{ errors.first('Latitude') }}
                </p>
              </div>
              <div class="field">
                <label class="label">Longitude</label>
                <p class="control has-icons-left has-icons-right">
                  <input v-validate="{required: true, max_value: 180, min_value: -180  }" v-model="checkin.lon" name="Longitude"
                         type="text" class="input" placeholder="Longitude"
                         :class="[{'is-success' : !errors.has('Longitude')},
                          {'is-danger' : (errors.has('Longitude') && hasBeenSubmitted)}]">
                  <span class="icon is-small is-left">
                        <i class="fas fa-compass"></i>
                    </span>
                </p>
                <p v-show="errors.has('Longitude') && hasBeenSubmitted" class="help is-danger">
                  {{ errors.first('Longitude') }}
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
              <p v-show="serviceError.errorMessage && hasBeenSubmitted" class="help is-danger">
                {{ serviceError.errorMessage }}
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
  props: ['id'],
  data: () => ({
    hasBeenSubmitted: false,
    checkin: {
      lat: undefined,
      lon: undefined,
    },
    serviceError: {},
    isLoading: false,
  }),
  methods: {
    submit() {
      this.hasBeenSubmitted = true;
      this.loading = true;
      this.$validator.validateAll().then((result) => {
        if (result) {
          this.axios.post(`/foodtrucks/${this.id}/checkins`, {
            lat: this.checkin.lat,
            lon: this.checkin.lon,
            checkin: new Date().getTime(),
            checkout: new Date().getTime() + (1000 * 60 * 60 * 8),
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
