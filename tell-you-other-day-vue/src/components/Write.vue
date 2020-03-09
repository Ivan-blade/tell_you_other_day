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
            @click="articleSave">
            Save
            </v-btn>
          </v-toolbar-items>
        </v-toolbar>
        <v-card>
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
              </div>
            </v-tab-item>
          </v-tabs-items>
        </v-card>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
  import { mavonEditor } from 'mavon-editor'
  import 'mavon-editor/dist/css/index.css'
  import { postRequest } from '../utils/api'
  import { getRequest } from '../utils/api'
  import {isNotNullORBlank} from '../utils/utils'
  export default {
    data () {
      return {
        dialog: false,
        tab: null,
        state: 1,
        decision: false,
        divideScreen: false,
        editorMode: 'edit',
        items: [
          { tab: 'Diary', state: 1 },
          { tab: 'Secret', state: 2},
        ],
        article: {
          id: '-1',
          title: '',
          mdContent: ''
        }
      }
    },
    watch: {
      state: function() {
        if(this.state) {
          this.getArticle()
        }
      }
    },
    methods: {
      stateEdit(state) {
        this.state = state
      },
      articleSave () {
        if (!(isNotNullORBlank(this.article.title, this.article.mdContent))) {
          console.log({type: 'error', message: '数据不能为空!'});
          return;
        }
        var _this = this;
        postRequest("/article/", {
          id: _this.article.id,
          title: _this.article.title,
          mdContent: _this.article.mdContent,
          htmlContent: _this.$refs.md.d_render,
          state: _this.state,
          publishDate: _this.date
        }).then(resp=> {
          if (resp.status == 200 && resp.data.status == 'success') {
            _this.article.id = resp.data.msg;
            console.log({type: 'success', message: '保存成功！'});
          }
        }, resp=> {
          console.log({type: 'error', message: resp});
        })
      },
      getArticle() {
        var _this = this;
        getRequest(`/article/${_this.date}/${_this.state}`).then(resp=> {
          if (resp.status == 200 && resp.data) {
          _this.article = resp.data;
          console.log(resp)
          } else {
            _this.article.id = -1
            _this.article.title = ''
            _this.article.mdContent = '' 
          }
        })
      }
    },
    components: {
      'mavon-editor': mavonEditor
    },
    props: ['date']
  }
</script>

<style>
#editor {
    margin: auto;
    width: 100%;
}
</style>