<template>
<!-- nav标签内没有特殊效果，该标签只是表示包含内容是导航标签，一般内容为列表 -->
  <nav>
      <v-app-bar text class="blue lighten-1">
          <v-app-bar-nav-icon @click="navShow = !navShow" class="white--text"></v-app-bar-nav-icon>
          <v-toolbar-title class="text-uppercase white--text">
              <span class="font-weight-light">Ivan-blade</span>
          </v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn text class="white--text">
              <span>sign out</span>
              <v-icon right>exit_to_app</v-icon>
          </v-btn>
      </v-app-bar>
      <v-navigation-drawer app v-model="navShow" class="blue lighten-3">
          <v-list>
            <v-subheader>Here is Menu</v-subheader>
            <v-list-item-group>
                <v-list-item
                v-for="(link, i) in links"
                :key="i"
                router :to="link.route"
                >
                <v-list-item-icon>
                    <v-icon v-text="link.icon" class="text--white"></v-icon>
                </v-list-item-icon>
                    <v-list-item-content>
                        <v-list-item-title v-text="link.text" class="text--white"></v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
            </v-list-item-group>
          </v-list>
      </v-navigation-drawer>
  </nav>
</template>

<script>
  export default {
    data() {
      return {
        navShow: false,
        links: [
            { icon: 'dashboard', text: 'Personal Info', route: '/personalInfo' },
            { icon: 'folder', text: 'Diarys', route: '/' },
            { icon: 'person', text: 'Messages', route: '/messages' },
            { icon: 'dashboard', text: 'Settings', route: '/settings' },
            { icon: 'folder', text: 'About', route: '/about' }
        ]
      }
    }
  }
</script>

<style>

</style>