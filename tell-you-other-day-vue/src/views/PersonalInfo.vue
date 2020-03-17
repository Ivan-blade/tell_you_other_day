<template>
    <v-card
    class="mx-auto fill-height"
    outlined
    >
      <v-list>
        <v-subheader>个人信息</v-subheader>
        <v-list-item-group>
          <v-list-item>
            <span>
              id: {{currentUserId}}
            </span>
          </v-list-item>
          <v-list-item>
            <span>
              邮箱：{{currentUserEmail}}
            </span>
          </v-list-item>
          <v-list-item>
            <span>
              昵称：{{currentUserName}}
            </span>
          </v-list-item>
          <v-list-item>
            <span>
              性别：{{currentGender}}
            </span>
          </v-list-item>
          <v-list-item>
            <span>
              格言：{{currentUserface}}
            </span>
          </v-list-item>
        </v-list-item-group>
      </v-list>
      <tip-info :tipinfo="infodata"></tip-info>
    </v-card>
</template>

<script>
  import TipInfo from '../components/TipInfo'
  import { getRequest } from '../utils/api'
  export default {
    name: 'PersonalInfo',
    components: {
      'tip-info': TipInfo
    },
    mounted: function () {
      getRequest("/currentUserName").then(msg => {
        this.currentUserName = msg.data;
      }, msg => {
        this.currentUserName = '游客'
        this.infodata = '您还未登录'
      });
      getRequest("/currentGender").then(msg => {
        this.currentGender = msg.data
      }, msg => {
        this.currentUserName = '保密'
      });
      getRequest("/currentUserId").then(msg => {
        this.currentUserId = msg.data
      }, msg => {
        this.currentUserId = 'unknown'
      });
       getRequest("/currentUserEmail").then(msg => {
        this.currentUserEmail = msg.data
      }, msg => {
        this.currentUserEmail = 'unknown'
      });
       getRequest("/currentUserface").then(msg => {
        this.currentUserface = msg.data
      }, msg => {
        this.currentUserface = '这个人很懒...'
      });
    },
    data(){
      return {
        currentUserName: '',
        currentUserId: '',
        currentUserface: '',
        currentUserEmail: '',
        currentGender: '保密',
        infodata: ''
      }
    }
  }
</script>

<style lang="less" scoped>
  
</style>