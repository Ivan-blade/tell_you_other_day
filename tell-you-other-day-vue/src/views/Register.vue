<template>
  <v-container class="px-10">
    <v-row align="center">
        <v-col>
            <v-text-field
            v-model="username"
            label="username"
            :rules="rules" 
            hide-details="auto"
            class="mt-2"
            ></v-text-field>
            <v-text-field
            v-model="password"
            :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
            :type="show ? 'text' : 'password'"
            label="password"
            @click:append="show = !show"
            :rules="rules" 
            hide-details="auto"
            class="my-7"
            ></v-text-field>
            <v-radio-group v-model="gender" :mandatory="false" row>
              <span>您的性别：</span>
              <v-spacer></v-spacer>
              <v-radio label="男" value="男"></v-radio>
              <v-radio label="女" value="女"></v-radio>
            </v-radio-group>
            <v-text-field
            v-model="userface"
            label="个人签名" 
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
            class="mt-5"
            ></v-text-field>
        </v-col>
    </v-row>
    <tip-info :tipinfo="infodata"></tip-info>
    <v-row align="center" class="sty-btn">
        <v-col class="text-center">
            <div>
                <v-btn 
                text 
                color="blue lighten-1"
                large
                tile
                @click="goToLogin"
                >登录</v-btn>
            </div>
        </v-col>
        <v-col class="text-center">
            <div>
                <v-btn
                color="blue lighten-1 white--text" 
                depressed
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
  import TipInfo from '../components/TipInfo'
  import { postRequest } from '../utils/api'
  import { isNotNullORBlank } from '../utils/utils'
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
        infodata: '',
        rules: [
          value => !!value || 'Required.'
        ]
      }
    },
    components: {
      'tip-info': TipInfo
    },
    methods: {
      goToLogin () {
        this.$router.push({
          path: '/login'
        })
      },
      Register () {
        if(this.code == "3273") {
          if (!(isNotNullORBlank(this.username,this.password,this.email,this.userface))) {
            this.infodata = '数据不能为空'
            return;
          }
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
                this.infodata = '注册成功'
                this.$router.push({
                  path: '/login'
                })
              } else {
                this.infodata = '注册失败'
              }
            } else {
              //失败
              this.infodata = '注册失败'
            }
          }, resp=> {
            this.infodata = '服务器未响应'
          })
        } else {
          this.infodata = '验证码错误'
        }
      },
      getCode () {
        postRequest('/email/sendMail',{
          receiver: this.email
        }).then(resp => {
          if (resp.data == 'success') {
            this.infodata = `验证码已发送至${this.email}`
          } else {
            this.infodata = '验证码发送失败请验证邮箱！'
          }
        },resp => {
          this.infodata = '该邮件地址不存在！请重新输入！'
        })
      }
    }
  }
</script>

<style>

</style>