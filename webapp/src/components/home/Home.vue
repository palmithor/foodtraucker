<template>
  <section class="hero">
    <div class="hero-body">
      <div class="container is-fluid">
        <div class="columns">
          <v-map class="column mobileMap" ref="map" @l-moveend="onMoveEnd" :zoom="zoom" :center="center">
            <v-tile-layer :url="url" :attribution="attribution"/>
            <v-marker-cluster ref="cluster" :bare="true"
                              :options="{chunkedLoading: true, maxClusterRadius: 200}"/>
          </v-map>
        </div>
      </div>
    </div>
  </section>
</template>

// this.$refs.map.mapObject

<script>
import { Map, Tile, MarkerCluster } from 'mapa/src/mapa';
import MarkerPopup from './MarkerPopup';

export default {
  components: {
    'v-map': Map,
    'v-tile-layer': Tile,
    'v-marker-cluster': MarkerCluster,
    'v-marker-popup': MarkerPopup,
  },
  data: () => ({
    zoom: 12,
    center: [57.7089, 11.9746],
    url: 'http://{s}.tile.osm.org/{z}/{x}/{y}.png',
    attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
  }),
  methods: {
    onMoveEnd() {
      this.$store.dispatch('loadCheckins', this.$refs.map.mapa.getBounds());
    },
  },
  computed: {
    locations() {
      const l = this.$store.state.checkins.list.map((checkin) => {
        console.log(checkin)
        const marker = window.L.marker(checkin.latLng);
        marker.bindTooltip(`Hello ${checkin.foodtruck_id}`);
        return marker;
      });
      this.$refs.cluster.update(l);
      console.log('updated');
      return l;
    },
  },
  mounted() {
    this.$refs.cluster.add(this.$refs.map.mapa);
    this.$refs.cluster.update(this.locations);
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

.mobileMap {
  height: 80vh;
}
</style>
