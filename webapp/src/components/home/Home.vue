<template>
  <section class="hero">
    <div class="hero-body">
      <div class="container is-fluid">
        <div class="columns">
          <v-map class="column mobileMap" ref="map" @l-moveend="onMoveEnd" :zoom="zoom" :center="center">
            <v-tile-layer :url="url" :attribution="attribution"></v-tile-layer>
            <div v-for="marker in markers">
              <v-marker :lat-lng="marker.latLng"></v-marker>
              <v-marker-popup :position="marker.latLng" :text="marker.text" :title="marker.title"></v-marker-popup>
            </div>
          </v-map>
        </div>
      </div>
    </div>
  </section>
</template>

// this.$refs.map.mapObject

<script>
import { Map, TileLayer, Marker } from 'vue2-leaflet';
import MarkerPopup from './MarkerPopup';

export default {
  components: {
    'v-map': Map,
    'v-tile-layer': TileLayer,
    'v-marker': Marker,
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
      this.$store.dispatch('loadCheckins', this.$refs.map.mapObject.getBounds());
    },
  },
  computed: {
    markers() {
      return [{
        latLng: L.latLng(57.7089, 11.9746),
        text: 'hej',
        title: 'Hej yo',
      }];
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

.mobileMap {
  height: 80vh;
}
</style>
