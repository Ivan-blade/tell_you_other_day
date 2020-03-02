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
    <v-write></v-write>
  </v-row>
</template>

<script>
  import Write from '../components/Write'
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
      'v-write': Write
    },
    watch: {
      picker (val) {
        console.log(typeof(val))
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