<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" fullscreen hide-overlay transition="dialog-bottom-transition">
      <template v-slot:activator="{ on }">
        <v-btn
         color="blue lighten-1" 
         dark
         fab
         v-on="on">
            <v-icon>playlist_add</v-icon>
         </v-btn>
      </template>
      <v-card>
        <v-toolbar dark color="blue lighten-1">
          <v-btn icon dark @click="dialog = false">
            <v-icon>close</v-icon>
          </v-btn>
          <v-toolbar-title>match add</v-toolbar-title>
        </v-toolbar>
        <v-row align="center" class="px-10">
            <v-col
            class="my-2"
            >
                <v-text-field
                v-model="email"
                label="please input the email of target"
                :rules="rules" 
                hide-details="auto"
                class="my-5"
                ></v-text-field>
            </v-col>
        </v-row>
        <v-row align="center" class="px-10">
            <v-col class="text-center">
                <div>
                    <v-btn 
                    color="blue lighten-1 white--text" 
                    depressed
                    large
                    tile
                    @click="pullMatch"
                    >发起请求</v-btn>
                </div>
            </v-col>
        </v-row>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
  import { postRequest } from '../utils/api'
  export default {
    data () {
      return {
        dialog: false,
        rules: [
          value => !!value || 'Required.'
        ],
        email: ''
      }
    },
    methods: {
      pullMatch () {
        var _this = this
        postRequest("/match/pullMatch",{
            email: _this.email
        }).then(resp => {
            console.log("请求成功")
        })
      }
    }
  }
</script>