<template>
  <section class="hero">
    <div class="hero-body">
      <div class="container is-fluid">
        <div class="columns">
          <h1 class="title column is-3">My Foodtrucks</h1>
          <div class="column is-offset-7">
            <button @click="navigateCreate()" class="button is-info">Create a Foodtruck</button>
          </div>
        </div>
        <div class="columns" v-for="chunk in foodtruckChunks">
          <div class="column is-one-third" v-for="truck in chunk">
            <foodtruck-card :foodtruck="truck"/>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import FoodtruckCard from './FoodtruckCard';

export default {
  components: { FoodtruckCard },
  data: () => ({}),
  computed: {
    foodtruckChunks() {
      return this.$store.getters.foodtruckChunks;
    },
  },
  methods: {
    navigateCreate() {
      this.$router.push({ name: 'foodtruck-create' });
    },
  },
  created() {
    this.$store.dispatch('loadSelfFoodtrucks');
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .button {
    margin-left: 20px;
  }
</style>
