<template>
  <section class="hero">
    <div class="hero-body">
      <div class="container is-fluid">
        <div class="columns">
          <v-map class="column mobileMap" ref="map" @l-moveend="onMoveEnd" :zoom="zoom" :center="initialLocation">
            <v-tile-layer :url="url" :attribution="attribution"/>
            <v-marker-cluster ref="cluster" :bare="true" @updated="isFetching"
                              :options="{chunkedLoading: false, maxClusterRadius: 200}"/>
          </v-map>
        </div>
      </div>
    </div>
  </section>
</template>

// this.$refs.map.mapObject

<script>
import { Map, Tile, MarkerCluster } from 'mapa/src/mapa';
import iconUrl from 'leaflet/dist/images/marker-icon.png';
import shadowUrl from 'leaflet/dist/images/marker-shadow.png';

export default {


  components: {
    'v-map': Map,
    'v-tile-layer': Tile,
    'v-marker-cluster': MarkerCluster,
  },
  data: () => ({
    zoom: 12,
    initialLocation: [57.7089, 11.9746],
    url: 'http://{s}.tile.osm.org/{z}/{x}/{y}.png',
    attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
    icon: window.L.icon({ iconUrl, shadowUrl }),
    locations: [],
    isFetching: false,
  }),
  methods: {
    onMoveEnd() {
      if (this.isFetching) {
        return;
      }
      this.isFetching = true;
      this.$store.dispatch('loadCheckins', this.$refs.map.mapa.getBounds())
        .then((checkins) => {
          this.locations = [];
          checkins.forEach((checkin) => {
            const marker = window.L.marker(checkin.latLng, { icon: this.icon });
            this.locations.push(marker);
          });
          this.$refs.cluster.update(this.locations);
          this.isFetching = false;
        }).catch(() => { this.isFetching = false; });
    },
  },
  computed: {
  },
  mounted() {
    this.$refs.cluster.add(this.$refs.map.mapa);
    this.$refs.cluster.update(this.locations);
    this.onMoveEnd();
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

  .mobileMap {
    height: 80vh;
  }
</style>
