
<template>
  <v-marker :lat-lng="position" :title="title" :draggable="false">
    <v-popup :content="text"></v-popup>
  </v-marker>
</template>

<script>
import { Marker, Popup } from 'vue2-leaflet';

export default {
  name: 'MarkerPopup',
  components: {
    'v-marker': Marker,
    'v-popup': Popup,
  },
  props: ['text', 'position', 'title'],
};
</script>
