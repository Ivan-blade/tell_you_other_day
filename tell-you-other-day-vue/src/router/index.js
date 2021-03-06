import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import About from '../views/About.vue'
import PersonalInfo from '../views/PersonalInfo.vue'
import Messages from '../views/Messages.vue'
import Settings from '../views/Settings.vue'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import ViewAll from '../views/ViewAll.vue'
import Match from '../views/Match.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/home',
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
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/viewall',
    name: 'ViewAll',
    component: ViewAll
  },
  {
    path: '/match',
    name: 'Match',
    component: Match
  }
]

const router = new VueRouter({
  routes
})

export default router
