<template>
  <div id="app">
    <section>
      <app-nav-bar/>
    </section>
    <section class="hero foodtraucker is-fullheight">
      <router-view/>
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

  .hero.foodtraucker {
    background: #F2F6FA;
  }
  .hero .nav, .hero.is-success .nav {
    -webkit-box-shadow: none;
    box-shadow: none;
  }
</style>
