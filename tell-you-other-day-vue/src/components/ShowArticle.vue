<template>
  <v-row justify="center">
    <v-btn
      color="blue lighten-1"
      class="white--text"
      small
      @click.stop="dialog = true"
    >
      {{title}}
    </v-btn>

    <v-dialog
      v-model="dialog"
      max-width="290"
    >
      <v-card
        color="blue lighten-1"
        shaped
      >
        <v-card-title class="headline white--text">{{title}}</v-card-title>
        
        <v-card-subtitle class="white--text mt-1">Published at {{time}}</v-card-subtitle>
        <div>
            <mavon-editor 
            v-model="mdContent"
            :toolbarsFlag = decision
            :defaultOpen = editorMode
            :subfield = divideScreen
            placeholder=" "
            previewBackground = "#FAFAFA"
            >
            </mavon-editor>
        </div>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="white"
            text
            @click="dialog = false"
          >
            Close
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
  import { mavonEditor } from 'mavon-editor'
  import 'mavon-editor/dist/css/index.css'
  export default {
    data () {
      return {
        dialog: false,
        decision: false,
        divideScreen: false,
        editorMode: 'preview'
      }
    },
    components: {
        'mavon-editor': mavonEditor
    },
    props: ['title','mdContent','time']
  }
</script>

<style>

</style>