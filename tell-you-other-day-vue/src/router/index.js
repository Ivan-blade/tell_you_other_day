import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import About from '../views/About.vue'
import PersonalInfo from '../views/PersonalInfo.vue'
import Messages from '../views/Messages.vue'
import Settings from '../views/Settings.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    component: About
  },
  {
    path: '/personalInfo',
    name: 'PersonalInfo',
    component: PersonalInfo
  },
  {
    path: '/messages',
    name: 'Messages',
    component: Messages
  },
  {
    path: '/settings',
    name: 'Settings',
    component: Settings
  }
]

const router = new VueRouter({
  routes
})

export default router
