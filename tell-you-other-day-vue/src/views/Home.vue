<template>
  <v-row justify="center">
    <v-date-picker
      v-model="picker"
      color="blue lighten-1"
      width="100%"
      :events="arrayEvents"
      event-color="green lighten-1"
      :picker-date.sync="picker"
    ></v-date-picker>
    <v-btn
     class="my-2" 
     fab 
     dark
     large 
     color="cyan"
     @click="goToWrite">
      <v-icon dark>mdi-pencil</v-icon>
    </v-btn>
  </v-row>
</template>

<script>
  export default {
    name: 'Home',
    data () {
      return {
        picker: new Date().toISOString().substr(0, 10),
        arrayEvents: null,
        pickerDate: null,
      }
    },
    watch: {
      picker (val) {
        console.log(val)
      }
    },
    methods: {
      goToWrite() {
        this.$router.push({
          path: '/write'
        })
      }
    }
  }
</script>
<style lang="less" scoped>
  .editor{
    z-index: -1;
  }
</style>