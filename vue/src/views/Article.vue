<template>
  <div class="blog_bg">
    <div style="padding: 1%;">
      <el-avatar style="vertical-align:middle;" :src="artData.avator"></el-avatar>
      <div style="
                  display: inline-block;
                  margin-left: 15px;
                  margin-top: 12px;
                  vertical-align:middle">
        {{artData.name}}</div>
      <div style="
                  display: inline-block;
                  margin-left: 15px;
                  margin-top: 16px;
                  vertical-align:middle;
                  font-size: 10px;color: #626262">
        提交时间：{{artData.time}}
      </div>
      <el-button size="mini" style="float: right;margin-top: 8px;" @click="goo">返回</el-button>
    </div>
    <div style="background-color: #DCDFE6;height: 1px;margin: 5px 0;"></div>
    <div style="padding: 5px 40px">
      <img style="width: 300px;height: 150px;margin-top: 10px;" :src="artData.firstImg">
      <div style="border-left: 1px solid #DCDFE6;height: 150px;width: 600px;display:inline-block;margin-left: 10px;padding: 10px">
        <h3 style="float:left;margin-top: 70px; width: 100%">  标题：{{artData.title}}</h3>
        <h3 style="float:left;margin-top: 5px; width: 100%;color: #626262">  简述：{{artData.meg}}</h3>
      </div>
    </div>

    <!--文章主体-->
    <div class="markdown-body" v-html="artData.context" style="padding: 10px 40px;"></div>
  </div>
</template>

<script>

import axios from 'axios'
import Calendar from '@element-plus/icons'

export default {
  name: "Article",
  components:{
    Calendar,
  },
  data () {
    return {
      artData:{},
    }
  },
  props:['id'], //接收路由传过来的id
  mounted() {
    this.load()
  },
  methods:{
    goo(){
      this.$router.go(-1);
    },
    load(){
      console.log(this.id)
      axios.get('/api/articles/' + this.id).then(res=>{
        this.artData = res.data.data;
        console.log(this.artData.avator)
        console.log(this.artData.firstImg)
        var MardownIt = require("markdown-it")
        var md = new MardownIt()
        this.artData.context = md.render(res.data.data.context)
        console.log(this.artData)
      })
    }
  }
}
</script>

<style scoped>
.blog_bg{
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  width: 80%;
  margin: auto;
  min-height:calc(100vh - 80px);
  border-radius: 3%;
  background: rgba(250, 247, 247, 0.65);
}
</style>