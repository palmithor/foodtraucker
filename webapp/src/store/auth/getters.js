export default {
  isAuthenticated: (state) => {
    if (state.user !== null &&
      state.user !== undefined &&
      state.user.isConfirmed) {
      return true;
    }
    return false;
  },
};
