<template>
  <v-container style="max-width: 600px;">
    <v-timeline dense clipped>
      <v-timeline-item
        fill-dot
        class="white--text mb-12"
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
                <show-article :title="event.title" :mdContent="event.mdContent" :time="event.publishDate"></show-article>
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
                >
                2020-03-04
                </v-chip>
          </v-col>
        </v-row>
      </v-timeline-item>
    </v-timeline>
  </v-container>
</template>

<script>
import ShowArticle from '../components/ShowArticle'
import { getRequest } from '../utils/api'
export default {
    name: 'ViewAll',
    data: () => ({
      articles: [],
      userid: '',
      state: 1
    }),

    computed: {
      timeline () {
        return this.articles.slice()
      }
    },
    
    components: {
      'show-article': ShowArticle
    },

    methods: {
      getContent() {
        getRequest(`/article/view/${this.userid}/${this.state}`).then(resp => {
          if (resp.status == 200 && resp.data) {
            this.articles = resp.data
          } else {
            console.log('查询失败') 
          }
        })
      },
      getInfo () {
        var _this = this
        getRequest("/currentUserId").then(function (resp) {
          _this.userid = resp.data;
          _this.$nextTick(_this.getContent())
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