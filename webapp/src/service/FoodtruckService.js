import Vue from 'vue';

export default {
  getSelfFoodtrucks() {
    return Vue.axios.get('/self/foodtrucks');
  },
};
