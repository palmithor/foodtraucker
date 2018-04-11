<template>
  <div class="card">
    <div class="card-content">
      <div class="media">
        <div class="media-left">
          <figure class="image is-48x48">
            <img src="../../assets/small-logo.svg" alt="Placeholder image">
          </figure>
        </div>
        <div class="media-content">
          <p class="title is-4">{{ foodtruck.name }}</p>
          <p class="subtitle is-6">Available since {{ foodtruck.created }}</p>
        </div>
      </div>
    </div>
    <footer class="card-footer">
      <router-link  :to="{ name: 'foodtruck', params: {id : foodtruck.id} }"
                    class="card-footer-item">View</router-link>
      <router-link  :to="{ name: 'checkin-create', params: {id : foodtruck.id} }"
                    class="card-footer-item">Checkin</router-link>
    </footer>
  </div>
</template>

<script>
export default {
  props: {
    foodtruck: Object,
  },
  data: () => ({
  }),
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
