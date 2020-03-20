<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" fullscreen hide-overlay transition="dialog-bottom-transition">
      <template v-slot:activator="{ on }">
        <v-btn
         color="blue lighten-1" 
         dark
         fab
         v-on="on">
            <v-icon>list</v-icon>
         </v-btn>
      </template>
      <v-card>
        <v-toolbar dark color="blue lighten-1">
          <v-btn icon dark @click="dialog = false">
            <v-icon>close</v-icon>
          </v-btn>
          <v-toolbar-title>match list</v-toolbar-title>
        </v-toolbar>
        <v-list>
            <v-list-item
              v-for="item in listInfo"
              :key="item.id"
              >
              <v-row>
                <v-col cols="8">
                  <v-list-item-content>
                  <v-list-item-title>匹配用户id：{{item.userId}}</v-list-item-title>
                  <v-list-item-subtitle>匹配用户邮箱：{{item.userEmail}}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-col>
                <v-col cols="4" class="d-flex justify-space-around mt-2"> 
                  <v-btn 
                    icon
                    color="blue lighten-1"
                    @click="matchChange(item,1)"
                    >
                    <v-icon dark>add</v-icon>
                  </v-btn>
                  <v-btn 
                    icon
                    color="red darken-1"
                    @click="matchChange(item,2)"
                    >
                    <v-icon dark>close</v-icon>
                  </v-btn>
                </v-col>
              </v-row>
          </v-list-item>
        </v-list>
        <tip-info :tipinfo="infodata"></tip-info>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
  import TipInfo from '../components/TipInfo'
  import { getRequest, postRequest } from '../utils/api'
  export default {
    data () {
      return {
        dialog: false,
        listInfo: '',
        infodata: ''
      }
    },
    methods: {
      getListInfo () {
        var _this = this
        getRequest("/match/getMatchInfo").then(function (resp) {
          _this.listInfo = resp.data
        })
      },
      matchChange (item, temp) {
        var _this = this
        postRequest(`/match/statusChange/${temp}`,{
          id: item.id,
          userId: item.otherId,
          otherId: item.userId
        })
        _this.infodata = '操作成功'
      }
    },
    components: {
      'tip-info': TipInfo
    },
    mounted() {
      this.getListInfo()
    }
  }
</script>