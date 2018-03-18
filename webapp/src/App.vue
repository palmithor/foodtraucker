<template>
  <div id="app">
    <app-nav-bar/>
    <div>
      <router-view/>
    </div>
  </div>
</template>

<script>
import NavBar from '@/components/shared/NavBar';

export default {
  components: {
    appNavBar: NavBar,
  },
};
</script>

<style>
</style>
