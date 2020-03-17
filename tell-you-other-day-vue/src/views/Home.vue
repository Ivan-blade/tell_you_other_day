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
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-spacer></v-spacer>
      <v-spacer></v-spacer>
      <v-write :date="picker"></v-write>
    </v-card-actions>
    <tip-info :tipinfo="infodata"></tip-info>
  </v-card>
</template>

<script>
  import TipInfo from '../components/TipInfo'
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
        userid: '',
        infodata: ''
      }
    },
    components: {
      'v-write': Write,
      'tip-info': TipInfo
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
        getRequest(`/article/allPublishDate/${this.userid}`).then(resp => {
          if (resp.status == 200 && resp.data) {
          let formatDate = resp.data.map(item => item.substr(0,10))
          this.arrayEvents = formatDate
          } else {
            this.TipInfo = '没有查询相关信息'
          }
        })
      },
      getInfo () {
        getRequest("/currentUserId").then(resp => {
          this.userid = resp.data;
          this.$nextTick(this.getAllPublishDate())
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