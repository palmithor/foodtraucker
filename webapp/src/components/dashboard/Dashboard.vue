<template>
  <div>
    <h1 class="title">My Foodtrucks</h1>
      <div class="columns" v-for="chunk in foodtruckChunks">
        <div class="column is-one-third" v-for="truck in chunk">
          <foodtruck-card :foodtruck="truck"/>
        </div>
    </div>
  </div>
</template>

<script>
import FoodtruckCard from './FoodtruckCard';

export default {
  components: { FoodtruckCard },
  data: () => ({
  }),
  computed: {
    foodtruckChunks() {
      return this.$store.getters.foodtruckChunks;
    },
  },
  created() {
    this.$store.dispatch('getSelfFoodtrucks');
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
