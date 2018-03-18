<template>
  <div id="app">
    <section>
      <app-nav-bar/>
    </section>
    <section class="section">
      <div class="container">
        <router-view/>
      </div>
    </section>
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

<style lang="scss">
  @import "assets/sass/main";
</style>
