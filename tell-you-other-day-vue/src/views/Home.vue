<template>
  <v-row justify="center">
    <v-date-picker
      v-model="picker"
      color="blue lighten-1"
      width="100%"
      :events="arrayEvents"
      event-color="blue lighten-1"
      :picker-date.sync="picker"
    ></v-date-picker>
    <v-write :date="picker"></v-write>
  </v-row>
</template>

<script>
  import { getRequest } from '../utils/api'
  import Write from '../components/Write'
  export default {
    name: 'Home',
    data () {
      return {
        picker: new Date().toISOString().substr(0, 10),
        arrayEvents: null,
        pickerDate: null,
        userid: ''
      }
    },
    components: {
      'v-write': Write
    },
    watch: {
      picker (val) {}
    },
    methods: {
      goToWrite() {
        this.$router.push({
          path: '/write'
        })
      },
      getAllPublishDate () {
        var _this = this
        getRequest(`/article/allPublishDate/${_this.userid}`).then(resp => {
          if (resp.status == 200 && resp.data) {
          let formatDate = resp.data.map(item => item.substr(0,10))
          _this.arrayEvents = formatDate
          } else {
            console.log('查询失败') 
          }
        })
      },
      getInfo () {
        var _this = this
        getRequest("/currentUserId").then(function (resp) {
          _this.userid = resp.data;
          _this.$nextTick(_this.getAllPublishDate())
        })
      }
      
    },
    mounted() {
      this.getInfo()
    }
  }
</script>
<style lang="less" scoped>
  .editor{
    z-index: -1;
  }
</style>