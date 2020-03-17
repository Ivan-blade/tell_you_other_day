<template>
  <v-container fluid class="text-center">
      <v-row
        class="flex"
        justify="space-between"
      >
        <v-col cols="12" class="mt-12">
          <v-tooltip v-model="show" top>
            <template v-slot:activator="{ on }">
              <span v-on="on" class="float-none"></span>
            </template>
            <span>{{tipinfo}}</span>
          </v-tooltip>
        </v-col>
      </v-row>
    </v-container>
</template>

<script>
  export default {
    data () {
      return {
        show: false
      }
    },
    methods: {
      changeShow () {
        this.show = !this.show
      },
      isShow () {
        this.changeShow()
        this.$nextTick(() => {
            setTimeout(this.changeShow,3000)
        })
      }
    },
    props: ['tipinfo'],
    watch: {
      tipinfo () { 
        this.isShow()
      }
    }
  }
</script>