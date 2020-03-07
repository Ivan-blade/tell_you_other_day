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
        // test: '2020-03-01 18:09:22',
        arrayEvents: null,
        pickerDate: null,
        allPublishDate: '',
        userid: 1
      }
    },
    components: {
      'v-write': Write
    },
    watch: {
      picker (val) {
        console.log(val)
        // 测试日期格式化代码
        // let formattest = this.test.substr(0, 10)
        // console.log(formattest)
      }
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
          _this.allPublishDate = resp.data;
          } else {
            console.log('查询失败') 
          }
        })
      }
    },
    mounted() {
      this.getAllPublishDate()
    }
  }
</script>
<style lang="less" scoped>
  .editor{
    z-index: -1;
  }
</style>