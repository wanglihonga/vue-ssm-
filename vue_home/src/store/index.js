import Vue from 'vue'
import Vuex from 'vuex'
import app from './modules/app'
import auth from './modules/auth'
import user from './modules/user'
import tabs from './modules/tabs'
import getters from './getters'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    auth,
    app,
    user,
    tabs
  },
  getters
})

export default store
