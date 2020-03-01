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
                v-model="email"
                label="email"
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
    </v-container>
</template>

<script>
  import { postRequest } from '../utils/api'
  export default {
    name: 'Login',
    data() {
      return {
        username: '',
        password: '',
        email: '',
        show: false,
        rules: [
            value => !!value || 'Required.'
        ]
      }
    },
    methods: {
      goToLogin () {
        postRequest('/login',{
          username: this.username,
          password: this.password
        }).then(resp=> {
          if (resp.status == 200) {
            //成功
            var json = resp.data
            if (json.status == 'success') {
              console.log('登录成功','成功！')
              this.$router.replace({path: '/personalInfo'})
            } else {
              console.log('登录失败!', '失败!')
            }
          } else {
            //失败
            console.log('登录失败!', '失败!')
          }
        }, resp=> {
          console.log('找不到服务器⊙﹏⊙∥!', '失败!')
          console.log(resp)
        })
      },
      goToReg () {
        postRequest('/reg',{
          username: this.username,
          password: this.password,
          email: this.email
        }).then(resp=> {
          if (resp.status == 200) {
            //成功
            var json = resp.data;
            if (json.status == 'success') {
              console.log('注册成功', '成功!')
            } else {
              console.log('注册失败!', '失败!')
            }
          } else {
            //失败
            console.log('注册失败!', '失败!')
          }
        }, resp=> {
          console.log('找不到服务器⊙﹏⊙∥!', '失败!')
          console.log(resp)
        })
      }
    }
  }
</script>

<style lang="less" scoped>

</style>