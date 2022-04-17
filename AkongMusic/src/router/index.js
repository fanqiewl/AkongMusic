import Vue from 'vue'
import Router from 'vue-router'
import Index from "../Index";
import Admin from "../components/admin/Admin";
import Login from "../components/admin/Login";
import AdminInfo from "../components/admin/AdminInfo";
import MusicManager from "../components/admin/MusicManager";


Vue.use(Router)

export default new Router({
  mode: "history",
  routes: [
    {
      path: '/',
      name: 'Index',
      component: Index
    },
    {
      path: '/admin',
      component: Admin,
      children: [
        {
          path: '/admin',
          name: 'AdminInfo',
          component: AdminInfo
        },
        {
          path: '/admin/music',
          name: 'MusicManager',
          component: MusicManager
        }
      ]
    },
    {
      path: '/admin/login',
      name: 'Login',
      component: Login
    }
  ]
})
