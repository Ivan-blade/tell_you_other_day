<template>
  <v-card class="fill-height">
    <v-card v-show="isMatch == 0" class="fill-height">
      等下给您发女朋友
      <v-row>
        <v-spacer></v-spacer>
        <match-request></match-request>
        <match-list></match-list>
      </v-row>
    </v-card>
    <v-card v-show="isMatch == 1" class="fill-height">
      等下给您换女朋友
      <match-list></match-list>
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
      isMatch: 0
    }
  },
  methods: {
    getIsMatch () {
      var _this = this
      getRequest("/currentMatch").then(resp => {
        if (resp.status == 200) {
            _this.isMatch = resp.data
          } else {
            console.log('查询失败') 
          }
      })
    }
  },
  mounted() {
    this.getIsMatch()
  },
}
</script>

<style>

</style>