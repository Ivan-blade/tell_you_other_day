<template>
    <v-container class="px-10">
        <v-row align="center">
            <v-col
            class="my-5"
            >
                <v-text-field
                v-model="username"
                label="username"
                :rules="rules" 
                hide-details="auto"
                class="my-5"
                ></v-text-field>
                <v-text-field
                v-model="password"
                :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
                :type="show ? 'text' : 'password'"
                label="password"
                @click:append="show = !show"
                :rules="rules" 
                hide-details="auto"
                class="my-5"
                ></v-text-field>
            </v-col>
        </v-row>
        <v-row align="center" class="sty-btn">
            <v-col class="text-center">
                <div>
                    <v-btn 
                    color="blue lighten-1 white--text" 
                    depressed
                    large
                    tile
                    @click="goToLogin"
                    >登录</v-btn>
                </div>
            </v-col>
            <v-col class="text-center">
                <div>
                    <v-btn
                    text 
                    color="blue lighten-1" 
                    large
                    tile
                    @click="goToReg"
                    >注册</v-btn>
                </div>
            </v-col>
        </v-row>
        <tip-info :tipinfo="infodata"></tip-info>
    </v-container>
</template>

<script>
  import TipInfo from '../components/TipInfo'
  import { postRequest,getRequest } from '../utils/api'
  import { isNotNullORBlank } from '../utils/utils'
  export default {
    name: 'Login',
    data() {
      return {
        username: '',
        password: '',
        show: false,
        rules: [
          value => !!value || 'Required.'
        ],
        infodata: ''
      }
    },
    components: {
      'tip-info': TipInfo
    },
    methods: {
      goToLogin () {
        postRequest('/login',{
          username: this.username,
          password: this.password
        }).then(resp=> {
          if (resp.status == 200) {
            var json = resp.data
            if (json.status == 'success') {
              this.infodata = '登录成功'
              this.$router.push({path: '/personalInfo'})
            } else {
              this.infodata = '登录失败'
            }
          } else {
            this.infodata = '登录失败'
          }
        }, resp=> {
          this.infodata = '服务器未响应'
        })
      },
      goToReg () {
        this.$router.push({
          path: '/register'
        })
      }
    },
    mounted () {
      getRequest("/currentUserName").then(msg => {
        if(msg.status == '200' && isNotNullORBlank(msg.data)) {
          this.$router.push({ path: '/home'})
        }
      })
    }
  }
</script>

<style lang="less" scoped>

</style>