<template>
  <div class='navbar'>
    <img class='logo' src='../../assets/logo.svg'>
    <img class='logo-small' src='../../assets/small-logo.svg'>
    <div class='menu tmp'>
      <span class='menu-item menu-item-1'>About</span>
      <span class='menu-item'>Sign In</span>
    </div>
  </div>
</template>

<script>
  export default {
    data () {
      return {}
    }
  }
</script>


<style scoped>

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

.logo-small {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translateY(-50%) translateX(-50%);

  
}

.menu {
  display: flex;
  flex-basis: row;
}

.menu-item {

}

.menu-item-1 {
  margin-right: 30px;
}

</style>