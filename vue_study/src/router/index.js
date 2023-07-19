import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'
import VuetifyjsHome from '../views/vuetifyjs2/index'
import VuefifyjsLists from '../views/vuetifyjs2/mian/lists'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  }
  ,
  {
    path: '/vtf',
    component: VuetifyjsHome,
    children:[
      {
        path:'lists',
        component:VuefifyjsLists
      },
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
