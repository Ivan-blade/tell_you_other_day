<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" fullscreen hide-overlay transition="dialog-bottom-transition">
      <template v-slot:activator="{ on }">
        <v-btn
        class="my-2" 
        fab 
        dark 
        color="blue lighten-1"
        @click="getArticle"
        v-on="on">
          <v-icon dark>mdi-pencil</v-icon>
        </v-btn>
      </template>
      <v-card>
        <v-toolbar dark color="blue lighten-1">
          <v-btn icon dark @click="dialog = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
          <v-toolbar-title>write your words</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-toolbar-items>
            <v-btn
            class="mx-4" 
            dark 
            text 
            :loading="loading"
            :disabled="loading"
            @click="articleSave">
            Save
            </v-btn>
          </v-toolbar-items>
        </v-toolbar>
        
          <v-tabs
            v-model="tab"
            background-color="blue lighten-1"
            dark
            grow
          >
            <v-tab
              v-for="item in items"
              :key="item.tab"
              @click="stateEdit(item.state)"
            >
              {{ item.tab }}
            </v-tab>
          </v-tabs>

          <v-tabs-items v-model="tab">
            <v-tab-item
              v-for="item in items"
              :key="item.tab"
            >
              <div class="mx-4 mr-10">
                <v-text-field 
                label="Title" 
                v-model="article.title"
                class="mt-5"
                solo
                >
                </v-text-field>
                <div id="editor">
                  <mavon-editor 
                    ref=md 
                    v-model="article.mdContent"
                    :toolbarsFlag = decision
                    :defaultOpen = editorMode
                    :subfield = divideScreen
                    placeholder=" ">
                  </mavon-editor>
                </div>
                <tip-info :tipinfo="infodata"></tip-info>
                <div v-if="item.state == 2">
                  <v-text-field 
                  label="format: 2020-03-03 01:01:01" 
                  v-model="showTime"
                  class="mt-5"
                  solo
                  >
                  </v-text-field>
                </div>
              </div>
            </v-tab-item>
          </v-tabs-items>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
  import TipInfo from '../components/TipInfo'
  import { mavonEditor } from 'mavon-editor'
  import 'mavon-editor/dist/css/index.css'
  import { postRequest } from '../utils/api'
  import { getRequest } from '../utils/api'
  import { isNotNullORBlank } from '../utils/utils'
  export default {
    data () {
      return {
        dialog: false,
        loader: null,
        loading: false,
        tab: 'Diary',
        state: 1,
        decision: false,
        divideScreen: false,
        editorMode: 'edit',
        infodata: '',
        items: [
          { tab: 'Diary', state: 1 },
          { tab: 'Secret', state: 2},
        ],
        article: {
          id: '-1',
          title: '',
          mdContent: ''
        },
        showTime: null
      }
    },
    watch: {
      state: function() {
        if(this.state) {
          this.getArticle()
        }
      },
      loader () {
        const temp = this.loader
        this[temp] = !this[temp]
        setTimeout(() => (this[temp] = false), 3000)
        this.loader = null
      }
    },
    methods: {
      stateEdit(state) {
        this.state = state
      },
      articleSave () {
        if (!(isNotNullORBlank(this.article.title, this.article.mdContent))) {
          this.infodata = '数据不能为空'
          return;
        }
        this.loader = 'loading'
        postRequest("/article/", {
          id: this.article.id,
          title: this.article.title,
          mdContent: this.article.mdContent,
          state: this.state,
          publishDate: this.date,
          showTime: this.showTime
        }).then(resp=> {
          if (resp.status == 200 && resp.data.status == 'success') {
            this.article.id = resp.data.msg;
            this.infodata = '保存成功'
          }
        }, resp=> {
          this.infodata = `保存失败，报错信息：${resp}`
        })
        
      },
      getArticle() {
        getRequest(`/article/${this.date}/${this.state}`).then(resp=> {
          if (resp.status == 200 && resp.data) {
          this.article = resp.data;
          } else {
            this.article.id = -1
            this.article.title = ''
            this.article.mdContent = '' 
          }
        })
      }
    },
    components: {
      'mavon-editor': mavonEditor,
      'tip-info': TipInfo
    },
    props: ['date']
  }
</script>

<style>
#editor {
    margin: auto;
    width: 100%;
}
.custom-loader {
  animation: loader 1s infinite;
  display: flex;
}
@keyframes loader {
  from {
    transform: rotate(0);
  }
  to {
    transform: rotate(360deg);
  }
}
</style>