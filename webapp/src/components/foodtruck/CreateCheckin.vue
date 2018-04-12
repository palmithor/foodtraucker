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
                <p class="control has-icons-left">
                  <input v-validate="{required: true, max_value: 90, min_value: -90  }" v-model="checkin.lat" name="Latitude"
                         type="text" class="input" placeholder="Latitude"
                         :class="[{'is-success' : !errors.has('Latitude') && checkin.lat.length > 0},
                          {'is-danger' : (errors.has('Latitude') && checkin.lat.length > 0) || (checkin.lat.length === 0 && hasBeenSubmitted)}]">
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
                <p class="control has-icons-left">
                  <input v-validate="{required: true, max_value: 180, min_value: -180  }" v-model="checkin.lon" name="Longitude"
                         type="text" class="input" placeholder="Longitude"
                         :class="[{'is-success' : !errors.has('Longitude') && checkin.lon.length > 0},
                          {'is-danger' : (errors.has('Longitude') && checkin.lon.length > 0) || (checkin.lon.length === 0 && hasBeenSubmitted)}]">
                  <span class="icon is-small is-left">
                        <i class="fas fa-compass"></i>
                    </span>
                </p>
                <p v-show="errors.has('Longitude') && hasBeenSubmitted" class="help is-danger">
                  {{ errors.first('Longitude') }}
                </p>
              </div>
              <!-- Checkin time -->
              <div class="field">
                <label class="label">Checkin</label>
                <p class="control">
                <date-picker class="control" placeholder="Select checkin and checkout time" range
                             :shortcuts="shortcuts"
                             v-model="checkin.range" :not-before="now" input-class="input"
                             type="datetime" format="yyyy-MM-dd hh:mm"
                             :time-picker-options="{start: '00:00',step: '00:30',end: '23:30'}"
                             lang="en"></date-picker>
                </p>
                <p v-show="errors.has('checkin') && hasBeenSubmitted" class="help is-danger">
                  {{ errors.first('Checkin') }}
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
import DatePicker from 'vue2-datepicker';

export default {
  components: {
    DatePicker,
  },
  props: ['id'],
  data: () => ({
    hasBeenSubmitted: false,
    now: new Date(),
    checkin: {
      lat: '',
      lon: '',
      range: [],
    },
    serviceError: {},
    isLoading: false,
    shortcuts: [],
  }),
  methods: {
    submit() {
      this.hasBeenSubmitted = true;
      this.loading = true;
      console.log(this.checkin.range)
      this.$validator.validateAll().then((result) => {
        if (result) {
          const request = {
            lat: Number(this.checkin.lat),
            lon: Number(this.checkin.lon),
            checkin: this.checkin.range[0].getTime(),
            checkout: this.checkin.range[1].getTime(),
          };
          this.axios.post(`/foodtrucks/${this.id}/checkins`, request).then(() => {
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
