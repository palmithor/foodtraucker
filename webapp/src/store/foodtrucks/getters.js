export default {
  foodtruckChunks: (state) => {
    const results = [];
    const size = 3;
    while (state.list.length) {
      results.push(state.list.splice(0, size));
    }
    return results;
  },
};
