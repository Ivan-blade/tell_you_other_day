<template>
<!-- nav标签内没有特殊效果，该标签只是表示包含内容是导航标签，一般内容为列表 -->
  <nav>
      <v-app-bar
       text 
       class="blue lighten-1"
       >
          <v-app-bar-nav-icon @click="navShow = !navShow" class="white--text"></v-app-bar-nav-icon>
          <v-toolbar-title class="text-uppercase white--text">
              <span class="font-weight-light subtitle-1">Diary-cat 日记猫</span>
          </v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn text class="white--text" @click="logout">
              <span>sign out</span>
              <v-icon right>exit_to_app</v-icon>
          </v-btn>
      </v-app-bar>
      <v-navigation-drawer app v-model="navShow" class="blue lighten-3">
          <v-list>
            <v-subheader>菜单栏</v-subheader>
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
  import { getRequest } from '../utils/api'
  export default {
    data() {
      return {
        navShow: false,
        links: [
            { icon: 'person', text: '个人信息', route: '/personalInfo' },
            { icon: 'notes', text: '写作', route: '/home' },
            // { icon: 'message', text: '信息', route: '/messages' },
            // { icon: 'settings', text: '设置', route: '/settings' },
            { icon: 'assistant_photo', text: '关于', route: '/about' },
            { icon: 'perm_identity', text: '登录/注册', route: '/login' },
            { icon: 'view_list', text: '浏览', route: '/viewall' },
            { icon: 'bolt', text: '匹配', route: '/match' }
        ]
      }
    },
    methods: {
      logout () {
        getRequest("/logout")
        this.$router.push({path: '/'});
      }
    },
  }
</script>

<style>

</style>