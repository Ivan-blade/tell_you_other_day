<template>
  <v-container class="px-10">
    <v-row align="center">
        <v-col
        class="my-2"
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
            class="my-10"
            ></v-text-field>
            <v-radio-group v-model="gender" :mandatory="false" row>
              <span>您的性别：</span>
              <v-spacer></v-spacer>
              <v-radio label="男" value="男"></v-radio>
              <v-radio label="女" value="女"></v-radio>
            </v-radio-group>
            <v-text-field
            v-model="userface"
            label="userface" 
            hide-details="auto"
            :rules="rules"
            class="mb-2"
            ></v-text-field>
            <v-text-field
            v-model="email"
            label="email"
            :rules="rules" 
            hide-details="auto"
            class="my-5"
            ></v-text-field>
            <v-text-field
            v-model="code"
            label="验证码"
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
                @click="Register"
                >注册</v-btn>
            </div>
        </v-col>
        <v-col class="text-center">
            <div>
                <v-btn
                text 
                color="blue lighten-1" 
                large
                tile
                @click="getCode"
                >点击获取验证码</v-btn>
            </div>
        </v-col>
    </v-row>
    </v-container>
</template>

<script>
  import { postRequest } from '../utils/api'
  export default {
    name: 'Register',
    data() {
      return {
        username: '',
        password: '',
        email: '',
        userface: '',
        code: '',
        gender: '男',
        show: false,
        rules: [
          value => !!value || 'Required.'
        ]
      }
    },
    methods: {
      goToLogin () {
        this.$router.push({
          path: '/login'
        })
      },
      Register () {
        if(this.code == "3273") {
          postRequest('/reg',{
            username: this.username,
            password: this.password,
            email: this.email,
            userface: this.userface,
            gender: this.gender
          }).then(resp=> {
            if (resp.status == 200) {
              //成功
              var json = resp.data;
              if (json.status == 'success') {
                console.log('注册成功', '成功!')
                this.$router.push({
                  path: '/login'
                })
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
        } else {
          console.log("验证码错误")
        }
      },
      getCode () {
        postRequest('/email/sendMail',{
          receiver: this.email
        }).then(resp => {
          console.log(resp)
        })
      }
    }
  }
</script>

<style>

</style>