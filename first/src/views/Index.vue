<template>
  <div>
    <div style="position: relative">
      <div class="img">
        <el-image :src="url" style="width: 100vw;height: 450px;"></el-image>
        <span class="title">
        来一场说走就走的旅行
        </span>
      </div>
      <div style="
          position: absolute;
          width: 36px;
          border-bottom: 2px solid #ffffff;
          left: 246px;
          top:-384px;
          z-index: 10;"></div>
    </div>

    <div class="users">
      <h2 style="color:#4253b9;margin-bottom: 10px">图影推荐</h2>
      <el-card :body-style="{ padding: '0px' }">
        <el-carousel height="385px" v-if="ImgData.length>0" >
          <el-carousel-item v-for="(item,i) in ImgData" :key="i" >
            <img :src="item.url" style="height: 100%;width: 100%;" v-if="item.url.length>50"/>
            <img :src="require('@/assets/img/'+item.url)" style="height: 100%;width: 100%;" v-if="item.url.length<50"/>
          </el-carousel-item>
        </el-carousel>
      </el-card>
    </div>

    <div class="views">
      <el-col>
        <div>
          <h2 style="color:#4253b9;margin-bottom: 10px">景点&活动</h2>
          <div style="width: 1200px; min-height: 730px;">
            <el-card class="cards" v-for="(item,index) in topData" :body-style="{ padding: '0px' }" shadow="hover" @click="to(item.id)">
              <el-image :src="item.firstImg" style="height: 300px"/>
              <div style="padding: 14px;">
                <span>{{ item.title }}</span>
                <span style="font-size: 16px;margin-left: 10px;"><el-icon color="black"><video-camera-filled /></el-icon>
                  {{item.view}}
                </span>
              </div>
            </el-card>
          </div>
          <div  style="width:1000px;padding: 2% 0;">
            <!-- 分页 -->
            <el-pagination
                background
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page.sync="pageNum"
                :page-size="pageSize"
                layout="total, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
          </div>
        </div>
      </el-col>

    </div>
  </div>
</template>

<script>
import axios from "axios";
import {VideoCameraFilled} from "@element-plus/icons";

export default {
  name: 'Index',
  components: {
    VideoCameraFilled,
  },
  data() {
    return {
      url:'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fp3-tt.byteimg.com%2Forigin%2Fpgc-image%2F8c97a9d8189043ceb2af93ad2e3e7153%3Ffrom%3Dpc&refer=http%3A%2F%2Fp3-tt.byteimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661775680&t=1f7af39d5d9a6f7aa96c85afead392ad',
      ImgData: [],
      topData:[],
      pageNum:1,
      pageSize:10,
    }
  },
  mounted() {
    this.load()
  },
  watch: {
    topData() {
      this.timer()
    }
  },
  destroyed() {
    clearTimeout(this.timer())
  },
  methods:{
    timer() {
      return setTimeout(()=>{
        this.load()
      },100000)
    },
    load(){
      axios.get("/api/travels",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          search: '',
        }
      }).then(
          res=>{
            console.log(res.data);
            this.topData=res.data.data.records;
            this.total=res.data.data.total
            this.Img()
          })
    },
    Img(){
      axios.get("/api/imgs").then(
          res => {
            console.log('index',res.data.data)
            this.ImgData=res.data.data
          }
      )
    },
    scrollToTop () {
      this.$nextTick(() => {
        let ele = document.getElementById('home');
        console.log("ele: ",ele.scrollTop)
        ele.scrollTop = 0;
      })
    },
    to(id){
      this.scrollToTop()
      this.$router.push("/main/item/"+id)
    },
    handleSizeChange(pageSize) {
      this.pageSize=pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.pageNum=pageNum
      this.load()
    },
  }
}
</script>

<style scoped>
.img{
  position: absolute;
  z-index: -1;
  left: 0;
  top:-465px;
}
.title{
  position: absolute;
  z-index: 0;
  left: 110px;
  top:200px;
  font-size: 60px;
  color: #ffffff;
}

.views{
  width: 100%;
  margin-top: 10px;
  padding:0 110px ;
}
.views .cards{
  display:inline-block;
  float:left;
  width: 216px;
  height: 350px;
  margin: 0 20px 20px 0 ;
}

.users{
  width: 100%;
  height: 430px;
  margin-top: 355px;
  padding: 0 110px;
}
.users .cards{
  display:inline-block;
  float:left;
  width: 100%;
  height: 385px;
  margin-right: 30px;
}

.image {
  display: block;
}
</style>