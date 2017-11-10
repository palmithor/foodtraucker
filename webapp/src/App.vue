<template>
  <div class='page'>
    <app-nav-bar/>
    <div>
      <router-view/>
    </div>
  </div>
</template>

<script>
  import NavBar from '@/components/shared/NavBar'

  export default {
    components: {
      appNavBar: NavBar
    }
  }
</script>

<style>
  body {
    margin: 0;
  }
  .page {
    height: 100vh;
    width: 100vw;
    background: url(assets/background.png) no-repeat;
    background-size: 100% 100%;
  }
</style>
