<template>
  <v-app>
    <Navbar />
    <v-content>
      <router-view></router-view>
    </v-content>
  </v-app>
</template>

<script>
  import Navbar from './components/Navbar'

  export default {
    components: { Navbar },
    name: 'App'
  }
</script>