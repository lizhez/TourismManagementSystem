<template>
  <div class="blog_bg">
    <div style="padding: 1%;">
      <el-avatar style="vertical-align:middle;" :src="traData.avator"></el-avatar>
      <div style="
                  display: inline-block;
                  margin-left: 15px;
                  margin-top: 12px;
                  vertical-align:middle">
        {{traData.name}}</div>
      <div style="
                  display: inline-block;
                  margin-left: 15px;
                  margin-top: 16px;
                  vertical-align:middle;
                  font-size: 10px;color: #626262">
        发布时间：{{traData.time}}
      </div>
      <el-button size="mini" style="float: right;margin-top: 8px;" @click="goo">
<!--        <router-link to="/travel">返回</router-link>-->
        返回
      </el-button>
    </div>
    <div style="background-color: #DCDFE6;height: 1px;margin: 5px 0;"></div>
    <div style="padding: 5px 40px">
      <img style="width: 300px;height: 150px;margin-top: 10px;" :src="traData.firstImg">
      <div style="border-left: 1px solid #DCDFE6;height: 150px;width: 600px;display:inline-block;margin-left: 10px;padding: 10px">
        <!--        <span>标题：{{artData.title}}</span>-->
        <h3 style="float:left;margin-top: 50px; width: 100%">  标题：{{traData.title}}</h3>
        <h3 style="float:left;margin-top: 5px; width: 100%;color: #858585">  价格：每人 ￥{{traData.pay}}</h3>
        <h3 style="float:left;margin-top: 5px; width: 100%;color: #626262">  简述：{{traData.meg}}</h3>
      </div>
    </div>

    <!--文章主体-->
    <div class="markdown-body" v-html="traData.context" style="padding: 10px 40px;"></div>
  </div>
</template>

<script>

import axios from 'axios'
import Calendar from '@element-plus/icons'
import MardownIt from "markdown-it";

export default {
  name: "TravelItem",
  components:{
    Calendar,
  },
  props:['id'],
  data () {
    return {
      traData:'',
    }
  },
   //接收路由传过来的id
  mounted() {
    this.load()
  },
  methods:{
    goo(){
      this.$router.go(-1);
    },
    load(){
      console.log(this.id)
      axios.get('/api/travels/' + this.id).then(res=>{
        this.traData = res.data.data;
        console.log(this.traData.avator)
        console.log(this.traData.firstImg)
        var MardownIt = require("markdown-it")
        var md = new MardownIt()
        this.traData.context = md.render(res.data.data.context)
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