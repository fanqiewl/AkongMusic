// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import axios from './api/http';
import VueAxios from "vue-axios"
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css'

Vue.use(ElementUI)
Vue.use(VueAxios, axios)
Vue.config.productionTip = false

/* eslint-disable no-new */
window.wv = new Vue({
  router,
  data: {
    downloadList: [],
    playList: []
  },
  components: {App},
  template: '<App/>'
}).$mount('#app');
