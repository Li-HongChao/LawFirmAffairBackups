import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/global.css'
import 'default-passive-events'
import axios from "axios";
import vueToPdf from 'vue-to-pdf';

Vue.prototype.$axios = axios
//设置基址
axios.defaults.baseURL='http://localhost:8082';
Vue.use(vueToPdf);
Vue.use(ElementUI);
Vue.config.productionTip = false
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
