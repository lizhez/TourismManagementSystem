<template>
<div class="header">
  <div class="pad">
    <span style="color:#DCDFE6;">{{time}}</span>
    <router-link to="/">
      <el-button type="text" class="button">返回主页</el-button>
    </router-link>
  </div>
  <div class="theLine"></div>
  <div class="pad">
    <span style="color:#ffffff;display:inline-block;float:left;width: 140px;height: 60px;font-size: 30px;padding:15px 0">toFLY</span>
    <ul>
      <router-link to="/main/index" class="ccc">
        <li v-if="type===1">首页</li>
      </router-link>
      <router-link to="/main/views">
        <li v-if="type===0">景点&活动</li>
      </router-link>
      <router-link to="/main/vlog">
        <li v-if="type===0">达人日记</li>
      </router-link>
    </ul>
    <div v-if="hasUser">
      <router-link to="/main/user/ref">
        <div style="float: right;color:#ffffff;display: inline-block;width: 100px;height: 30px;margin: 31px -30px 0 20px">
          <el-icon color="#fff" style="vertical-align: middle;">
            <user />
          </el-icon>
          <span style="vertical-align: middle;font-size: 10px"> 个人中心 </span>
        </div>
      </router-link>
    </div>

  </div>
</div>
</template>

<script>

import {ChatDotRound, User} from "@element-plus/icons"

export default {
  name: "Header",
  data () {
    return {
      type:0,
      time: '', //现在时间
      timer: '', //接收定时器'
      hasUser:false
    }
  },
  components:{
    User,
    ChatDotRound,
  },
  mounted() {
    let user=JSON.parse(sessionStorage.getItem("user"))
    console.log('user',user)
    if(user!==null){
      this.type=user.type
      this.hasUser=true
    }
  },
  methods:{
    myTimer() {
      let d = new Date();
      this.time = d.toLocaleString();
    },
  },
  created() {
    this.timer= setInterval(this.myTimer, 1000); //第一个参数不可以写成this.myTimer()
  },
  destroyed() {
    //清除定时器
    clearInterval(this.timer);
  },
}
</script>

<style scoped>
.header{
  width: 100%;
  height: 110px;
  padding-top: 2px;
}
.pad{
  padding: 0 110px;
}
.pad ul li{
  float: left;
  display: inline-block;
  margin-right: 40px;
  font-size: 14px;
  color: #ffffff;
  margin-top: 29px;
}
.pad ul li:hover{
  color: #dcdcdc;
}

.theLine{
  background-color: #DCDFE6;
  height: 1px;
  margin: 5px 0;
}
.button{
  float: right;
  color: #d2d2d2;
  margin-left: 10px;
}
</style>