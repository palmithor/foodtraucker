import Vue from 'vue'
import AuthService from './../../services/AuthService'

const state = {
  session: {},
  isLoading: false,
  error: null
}

const actions = {
  initSession (context) {
    const sessionCookie = Vue.cookie.get('session')
    const session = sessionCookie ? JSON.parse(sessionCookie) : null
    context.commit('initSession', session)
  },
  clearSession (context) {
    context.commit('clearSession')
  },
  sessionCleanup (context) {
    context.commit('sessionCleanup')
  },
  login (context, data) {
    context.commit('setLoading')
    AuthService.login(data.email, data.password)
      .then((result) => {
        context.commit('initSession', result)
      })
      .catch((err) => {
        context.commit('setError', err)
      })
  },
  signUp (context, data) {
    context.commit('setLoading')
    AuthService.signUp(data.email, data.password)
      .then((result) => {
        // TODO show message that an email has been sent
      })
      .catch((err) => {
        context.commit('setError', err)
      })
  }
}

const mutations = {
  initSession (state, data) {
    state.isLoading = false
    if (data) {
      Vue.cookie.set('session', JSON.stringify(data))
      state.session = {
        accessToken: data.accessToken.jwtToken,
        refreshToken: data.refreshToken.token,
        idToken: data.idToken.jwtToken
      }
    } else {
      state.session = {}
      Vue.cookie.delete('session')
    }
  },
  clearSession (state) {
    state.session = {}
    Vue.cookie.delete('session')
  },
  sessionCleanup (state) {
    state.isLoading = false
    state.error = null
  },
  setError (state, err) {
    state.isLoading = false
    state.error = err.message
  },
  setLoading (state) {
    state.isLoading = true
  }
}

const getters = {
  isAuthenticated: state => {
    return !!state.session.refreshToken
  }
}

export default {
  state,
  actions,
  mutations,
  getters
}
