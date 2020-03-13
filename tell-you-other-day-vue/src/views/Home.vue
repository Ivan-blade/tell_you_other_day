<template>
  <v-card class="fill-height">
    <v-date-picker
      v-model="picker"
      color="blue lighten-1"
      :full-width="fullWidth"
      :events="arrayEvents"
      event-color="blue lighten-1"
      :picker-date.sync="picker"
    ></v-date-picker>
    <v-spacer></v-spacer>
    <v-write :date="picker"></v-write>
  </v-card>
</template>

<script>
  import { getRequest } from '../utils/api'
  import Write from '../components/Write'
  export default {
    name: 'Home',
    data () {
      return {
        picker: new Date().toISOString().substr(0, 10),
        fullWidth: true,
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

</style>