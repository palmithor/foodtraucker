<template>
  <div class='navbar'>
    <img class='logo' src='../../assets/logo.svg'>
    <img class='logo-small' src='../../assets/small-logo.svg' @click.prevent="onLogo()">
    <div class='menu'>
      <router-link to="About" class='menu-item menu-item-1'>About</router-link>
      <router-link to="Login" class='menu-item'>Login</router-link>
    </div>
  </div>
</template>

<script>
  export default {
    data () {
      return {}
    },
    methods: {
      onLogo () {
        this.$router.push('/')
      }
    }
  }
</script>


<style scoped>

@font-face {
    font-family: lobster;
    src: url(../../assets/fonts/Lobster-Regular.ttf);
}

.navbar {
  position: absolute;
  height: 120px;
  width: 100%;
  margin-top: 50px;
  background-color: #B7130F;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  padding: 0 70px 0 70px
}

.logo {
  width: 25vw;
}

.logo-small {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translateY(-50%) translateX(-50%);
}

.logo-small:hover {
  cursor: pointer;
}

@media screen and (max-width: 650px) {
  .logo-small {
    position: absolute; 
    left: 0%;
    top: 50%;
    transform: translateY(-50%);
    margin-left: 30px;
  }
  .logo {
    display: none
  }
  .menu {
    position: absolute;
    flex: none;
    right: 0;
    margin-right: 30px;
  }
}

.menu {
  display: flex;
  align-items: flex-end;
}

.menu-item {
  font-family: lobster;
  font-size: 24px;
  color: #fff;
  text-decoration: none
}

.menu-item:hover {
  text-decoration: underline
}

.menu-item-1 {
  margin-right: 40px;
}

</style>