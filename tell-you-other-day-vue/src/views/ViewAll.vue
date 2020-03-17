<template>
  <v-container style="max-width: 600px">
    <v-timeline dense clipped>
      <v-timeline-item
        fill-dot
        class="white--text mb-4"
        color="orange"
        large
      >
        <template v-slot:icon>
          <span>To</span>
        </template>
        <v-row justify="space-between">
          <v-col>
            <v-chip
              class="white--text ml-0"
              color="blue lighten-1"
              label
            >
              Witness your past and future
            </v-chip>
          </v-col>
        </v-row>
      </v-timeline-item>
      <v-timeline-item
        class="mb-4 text--white"
        color="blue"
      >
        <v-row justify="space-between">
          <v-col cols="9">
            <v-chip
              class="white--text ml-0"
              color="blue lighten-1"
              label
              @click="changeUser"
            >
              Click to change user view
            </v-chip>
          </v-col>
          <v-col cols="3">
            <v-chip
              class="white--text ml-0"
              color="cyan lighten-1"
              label
              small
              >
              {{this.userid}}
            </v-chip>
          </v-col>
        </v-row>
      </v-timeline-item>

      <v-timeline-item
        class="mb-4 text-white"
        color="blue"
      >
        <v-row justify="space-between">
          <v-col cols="9">
            <v-chip
              class="white--text ml-0"
              color="blue lighten-1"
              label
              @click="changeState(state)"
            >
              Click to change article type
            </v-chip>
          </v-col>
          <v-col cols="3">
            <v-chip
              class="white--text ml-0"
              color="cyan lighten-1"
              label
              small
              >
              {{this.state}}
            </v-chip>
          </v-col>
        </v-row>
      </v-timeline-item>

      <v-slide-x-transition
        group
      >
        <v-timeline-item
          v-for="event in timeline"
          :key="event.id"
          class="mb-4"
          color="blue"
          small
        >
          <v-row justify="space-between">
            <v-col cols="6">
                <show-article v-if="event.title" :title="event.title.substr(0,10)" :mdContent="event.mdContent" :time="event.publishDate"></show-article>
            </v-col>
            <v-col class="text-right" cols="6">
                <v-chip
                class="white--text ml-0"
                color="cyan lighten-1"
                label
                small
                >
                {{event.publishDate}}
                </v-chip>
            </v-col>
          </v-row>
        </v-timeline-item>
      </v-slide-x-transition>

      <v-timeline-item
        class="mb-4"
        small
      >
        <v-row justify="space-between">
          <v-col cols="7">
            <v-chip
              class="white--text ml-0"
              color="purple"
              label
              small
            >
              Start Here!
            </v-chip>
          </v-col>
          <v-col class="text-right" cols="5">
              <v-chip
                class="white--text ml-0"
                color="cyan lighten-1"
                label
                small
                v-if="userinfo.regTime"
                >
                {{userinfo.regTime.substr(0,10)}}
              </v-chip>
          </v-col>
        </v-row>
      </v-timeline-item>
    </v-timeline>
    <tip-info :tipinfo="infodata"></tip-info>
  </v-container>
</template>

<script>
import TipInfo from '../components/TipInfo'
import ShowArticle from '../components/ShowArticle'
import { getRequest } from '../utils/api'
export default {
    name: 'ViewAll',
    data: () => ({
      articles: [],
      userid: '',
      state: 1,
      userinfo: '',
      infodata: ''
    }),

    watch: {
      state() {
        this.getContent()
      },
      userid() {
        this.getContent()
      }
    },
    computed: {
      timeline () {
        return this.articles.slice()
      }
    },
    
    components: {
      'show-article': ShowArticle,
      'tip-info': TipInfo
    },

    methods: {
      getContent() {
        getRequest(`/article/view/${this.userid}/${this.state}`).then(resp => {
          if (resp.status == 200 && resp.data) {
            this.articles = resp.data
          } else {
            this.infodata = '查询失败或者数据为空！' 
          }
        }, resp => {
          this.infodata = '未查询到该用户信息'
        })
      },
      changeState (state) {
        return this.state = state == 1 ? 2 : 1
      },
      changeUser () {
        if(this.userinfo.matchId) {
          return this.userid = this.userid == this.userinfo.id ? this.userinfo.matchId : this.userinfo.id
        } else {
          this.infodata = '您还未进行匹配！'
        }
      },
      getInfo () {
        getRequest("/currentUserInfo").then(resp => {
          if (resp.status == 200 && resp.data) {
          this.userinfo = resp.data
          this.userid = resp.data.id
          this.$nextTick(this.getContent())
          } else {
            this.infodata = '未查询到数据记录'
          }
        }, resp => {
          this.infodata = '未查询到该用户信息'
        })
      }
    },

    mounted() {
      this.getInfo()
    }
  }
</script>

<style>

</style>