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
              性别：{{otherInfo.gender}}
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
      <tip-info :tipinfo="infodata"></tip-info>
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
  import TipInfo from '../components/TipInfo'
  import { getRequest } from '../utils/api'
  import MatchList from '../components/MatchList'
  import MatchRequest from '../components/MatchRequest'
  export default {
    name: 'Match',
    components: {
      'match-list': MatchList,
      'match-request': MatchRequest,
      'tip-info': TipInfo
    },
    data () {
      return {
        matchId: 0,
        otherInfo: '',
        infodata: ''
      }
    },
    methods: {
      getMatchId () {
        getRequest("/currentMatchId").then(resp => {
          if (resp.status == 200 && resp.data) {
              this.matchId = resp.data
              this.$nextTick(this.getMatchInfo())
            } else {
              this.infodata = '当前没有匹配用户'
            }
        })
      },
      getMatchInfo () {
        getRequest(`/currentMatchInfo/?id=${this.matchId}`).then(resp => {
          if(resp.status == 200 && resp.data) {
            this.otherInfo = resp.data
          }
        })
      }
    },
    mounted() {
      this.getMatchId()
    }
  }
</script>

<style>

</style>