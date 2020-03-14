<template>
  <v-card class="fill-height">
    <v-card v-if="matchId == 0" tile class="fill-height d-flex flex-column">
      <v-row class="mx-5 my-5" style="height: 520px">
        您还未匹配用户
      </v-row>
      <v-row class="mx-3">
        <v-spacer></v-spacer>
        <v-spacer></v-spacer>
        <v-spacer></v-spacer>
        <v-spacer></v-spacer>
        <match-request></match-request>
        <match-list></match-list>
      </v-row>
    </v-card>
    <v-card v-if="matchId != 0" tile class="fill-height">
      <v-list>
        <v-subheader>匹配用户信息</v-subheader>
        <v-list-item-group>
          <v-list-item>
            <span>
              id: {{otherInfo.id}}
            </span>
          </v-list-item>
          <v-list-item>
            <span>
              邮箱：{{otherInfo.email}}
            </span>
          </v-list-item>
          <v-list-item>
            <span>
              昵称：{{otherInfo.username}}
            </span>
          </v-list-item>
          <v-list-item>
            <span>
              格言：{{otherInfo.userface}}
            </span>
          </v-list-item>
        </v-list-item-group>
      </v-list>
      <v-divider></v-divider>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-spacer></v-spacer>
        <v-spacer></v-spacer>
        <v-spacer></v-spacer>
        <match-list></match-list>
      </v-card-actions>
    </v-card>
  </v-card>
</template>

<script>
import { getRequest } from '../utils/api'
import MatchList from '../components/MatchList'
import MatchRequest from '../components/MatchRequest'
export default {
  name: 'Match',
  components: {
    'match-list': MatchList,
    'match-request': MatchRequest
  },
  data () {
    return {
      matchId: '',
      otherInfo: ''
    }
  },
  methods: {
    getMatchId () {
      var _this = this
      getRequest("/currentMatchId").then(resp => {
        if (resp.status == 200 && resp.data) {
            _this.matchId = resp.data
            _this.$nextTick(_this.getMatchInfo())
          } else {
            console.log('查询失败') 
            _this.matchId = 0
          }
      })
    },
    getMatchInfo () {
      var _this = this
      getRequest(`/currentMatchInfo/?id=${_this.matchId}`).then(resp => {
        if(resp.status == 200 && resp.data) {
          _this.otherInfo = resp.data
        } else {
          console.log("请求失败")
        }
      })
    }
  },
  mounted() {
    this.getMatchId()
  },
}
</script>

<style>

</style>