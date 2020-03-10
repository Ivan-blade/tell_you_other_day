<template>
    <v-card
    class="my-5 mx-auto"
    max-width="344"
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
              格言：{{currentUserface}}
            </span>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-card>
</template>

<script>
  import { getRequest } from '../utils/api'
  export default {
    name: 'PersonalInfo',
    mounted: function () {
      var _this = this
      getRequest("/currentUserName").then(function (msg) {
        _this.currentUserName = msg.data;
      }, function (msg) {
        _this.currentUserName = '游客';
        console.log(msg)
      });
      getRequest("/currentUserId").then(function (msg) {
        _this.currentUserId = msg.data;
      }, function (msg) {
        _this.currentUserId = 'unknown';
        console.log(msg)
      });
       getRequest("/currentUserEmail").then(function (msg) {
        _this.currentUserEmail = msg.data;
      }, function (msg) {
        _this.currentUserEmail = 'unknown';
        console.log(msg)
      });
       getRequest("/currentUserface").then(function (msg) {
        _this.currentUserface = msg.data;
      }, function (msg) {
        _this.currentUserface = '这个人很懒...';
        console.log(msg)
      });
    },
    data(){
      return {
        currentUserName: '',
        currentUserId: '',
        currentUserface: '',
        currentUserEmail: '',
      }
    }
  }
</script>

<style lang="less" scoped>
  
</style>