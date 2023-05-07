<template>
  <div>
    <div style="position: relative">
      <div class="img">
        <el-image :src="url" style="width: 100vw;height: 110px;"></el-image>
      </div>
    </div>

    <div style="padding: 10px 110px">
      <div style="padding: 1%;">
        <el-avatar style="vertical-align:middle" :src="articleData.avator"></el-avatar>
        <span style="margin-left: 5px;">{{articleData.name}}</span>
        <span style="font-size: 16px;margin-left: 10px;">
          <el-icon><calendar /></el-icon>
          {{articleData.time}}
        </span>
        <span style="font-size: 16px;margin-left: 10px;">
          <el-icon><video-camera-filled /></el-icon>
          {{articleData.view}}
        </span>
        <el-button size="mini" style="float: right;margin-top: 8px;" @click="goo">返回
          <el-icon>
            <d-arrow-right />
          </el-icon>
        </el-button>
      </div>
      <div style="background-color: #DCDFE6;height: 1px;margin: 5px 0;"></div>
      <div>
        <img style="width: 96%;height: 400px;margin: 2%;" :src="articleData.firstImg">
      </div>
      <div style="text-align: center;">
        <h4 >{{articleData.title}}</h4>
      </div>
      <div class="markdown-body" v-html="articleData.context" style="padding: 40px;"></div>
    </div>

  </div>

</template>


<script>
import Com from "@/components/Com";
import axios from "axios";
import {Calendar, DArrowRight,VideoCameraFilled} from "@element-plus/icons"

export default {
  name: "Article",
  components:{
    Com,
    Calendar,
    VideoCameraFilled,
    DArrowRight,
  },
  props:['id'], //接收路由传过来的id
  mounted() {
    this.load()
  },
  data(){
    return{
      url:'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fp3-tt.byteimg.com%2Forigin%2Fpgc-image%2F8c97a9d8189043ceb2af93ad2e3e7153%3Ffrom%3Dpc&refer=http%3A%2F%2Fp3-tt.byteimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661775680&t=1f7af39d5d9a6f7aa96c85afead392ad',
      articleData:{}
    }
  },
  methods:{
    goo(){
      this.$router.go(-1);
    },
    load(){
      console.log(this.id)
      axios.get('/api/articles/' + this.id).then(res=>{
        this.articleData = res.data.data;
        var MardownIt = require("markdown-it")
        var md = new MardownIt()
        this.articleData.context = md.render(res.data.data.context)
      })
    }
  }
}
</script>

<style scoped>
.img{
  position: absolute;
  z-index: -1;
  left: 0;
  top:-120px;
}
</style>