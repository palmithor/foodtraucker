/* eslint-disable no-unused-vars */
import * as types from './mutation-types';
import AuthService from '../../service/AuthService';


export default {
  getCurrentUser({ commit }) {
    AuthService.getCurrentUser()
      .then((user) => {
        commit(types.AUTHENTICATE, user);
      })
      .catch((err) => { });
  },

  login({ commit }, credentials) {
    AuthService.authenticateUser(credentials)
      .then((user) => {
        commit(types.AUTHENTICATE, user);
      }).catch((error) => {
        if (error.code === 'UserNotConfirmedException') {
          console.error('TODO: User must be confirmed');
        } else {
          commit(types.ERROR, error);
        }
      });
  },


  signUp({ commit }, credentials) {
    AuthService.signUp(credentials)
      .then((user) => {
        commit(types.SIGNED_UP, { user });
      }).catch((error) => {
        commit(types.ERROR, error);
      });
  },
  signOut({ commit }) {
    return AuthService.signOut()
      .then(() => {
        commit(types.SIGN_OUT);
      }).catch(() => {
        commit(types.SIGN_OUT);
      });
  },
/*
  confirmRegistration({ state }, payload) {
    const cognitoUser = new CognitoUser({
      Pool: cognitoUserPool,
      Username: payload.username,
    });

    return new Promise((resolve, reject) => {
      cognitoUser.confirmRegistration(payload.code, true, (err) => {
        if (!err) {
          resolve();
          return;
        }
        reject(err);
      });
    });
  },

  resendConfirmationCode({ commit }, payload) {
    const cognitoUser = new CognitoUser({
      Pool: cognitoUserPool,
      Username: payload.username,
    });

    return new Promise((resolve, reject) => {
      cognitoUser.resendConfirmationCode(
        (err) => {
          if (!err) {
            resolve();
            return;
          }
          reject(err);
        });
    });
  },

  forgotPassword({ commit }, payload) {
    const cognitoUser = new CognitoUser({
      Pool: cognitoUserPool,
      Username: payload.username,
    });

    return new Promise((resolve, reject) => cognitoUser.forgotPassword({
      onSuccess() {
        resolve();
      },
      onFailure(err) {
        reject(err);
      },
    }));
  },

  confirmPassword({ commit }, payload) {
    const cognitoUser = new CognitoUser({
      Pool: cognitoUserPool,
      Username: payload.username,
    });

    return new Promise((resolve, reject) => {
      cognitoUser.confirmPassword(payload.code, payload.newPassword, {
        onFailure(err) {
          reject(err);
        },
        onSuccess() {
          resolve();
        },
      });
    });
  },

  // Only for authenticated users
  changePassword({ state }, payload) {
    return new Promise((resolve, reject) => {
      // Make sure the user is authenticated
      if (state.user === null || (state.user && state.user.tokens === null)) {
        reject({
          message: 'User is unauthenticated',
        });
        return;
      }

      const cognitoUser = new CognitoUser({
        Pool: cognitoUserPool,
        Username: state.user.username,
      });

      // Restore session without making an additional call to API
      cognitoUser.signInUserSession = cognitoUser.getCognitoUserSession(state.user.tokens);

      cognitoUser.changePassword(payload.oldPassword, payload.newPassword,
        (err) => {
          if (!err) {
            resolve();
            return;
          }
          reject(err);
        });
    });
  },

  // Only for authenticated users
  */
};
