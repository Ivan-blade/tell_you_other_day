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
    <mavon-editor></mavon-editor>
  </v-row>
</template>

<script>
  import mavonEditor from '../components/MavonEditor'
  export default {
    name: 'Home',
    data () {
      return {
        picker: new Date().toISOString().substr(0, 10),
        arrayEvents: null,
        pickerDate: null,
      }
    },
    components: {
      mavonEditor
    },
    watch: {
      picker (val) {
        console.log(val)
      }
    }
  }
</script>
<style lang="less" scoped>
  .editor{
    z-index: -1;
  }
</style>