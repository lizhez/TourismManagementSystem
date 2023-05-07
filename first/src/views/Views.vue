<template>
  <div id="view">
    <div style="position: relative">
      <div class="img">
        <el-image :src="url" style="width: 100vw;height: 110px;"></el-image>
      </div>
      <div style="
          position: absolute;
          width: 77px;
          border-bottom: 2px solid #ffffff;
          left: 246px;
          top:-38px;
          z-index: 10;"></div>
    </div>

    <div class="search">
      <el-input v-model="search" placeholder="输入关键字查询" style="width: 300px" clearable></el-input>
      <el-button style="margin-left: 10px;" size="mini" @click="load">
        <el-icon style="vertical-align: middle;">
          <search />
        </el-icon>
        <span style="vertical-align: middle;"> 搜索 </span>
      </el-button>
    </div>

    <div class="top">
      <el-carousel :interval="6000" type="card" height="500px" v-if="topData.length>0">
        <el-carousel-item v-for="(item,i) in topData" :key="i" >
          <el-image :src="item.firstImg" style="height: 100%;width: 100%;" @click="to(item.id)"></el-image>
        </el-carousel-item>
      </el-carousel>
    </div>

    <div style="padding: 0 110px">
      <el-col>
        <div>

          <el-row :gutter="20">
            <el-col :span="12"><div style="margin: 15px 0 15px 20px;color: #464646;">项目</div></el-col>
            <el-col :span="12"><div style="float: right;margin: 10px 20px 0 0;color: #464646">
              共 <span style="font-size: 28px;color: rgb(134,137,145);">{{total}}</span> 种</div>
            </el-col>
          </el-row>

          <!-- 文章 -->
          <div class="div002" v-for="(item,index) in tableData" :key="index">
            <el-card shadow="hover" :body-style="{ padding: '15px' }" style="height: 150px;" @click="to(item.id)">
              <el-row :gutter="20">
                <el-col :span="17">
                  <div>
                    <h2>{{ item.title }}</h2>
                    <p style="font-size: 17px;line-height: 43px">{{ item.meg }}</p>
                    <div>
                      <el-avatar style="vertical-align:middle" :src="item.avator"></el-avatar>
                      <span style="margin-left: 5px;">{{item.name}}</span>
                      <span style="font-size: 16px;margin-left: 10px;">
                        <el-icon><calendar /></el-icon>
                        {{item.time}}
                      </span>
                      <span style="font-size: 16px;margin-left: 10px;">
                        <el-icon><video-camera-filled /></el-icon>
                        {{item.view}}
                      </span>
                    </div>
                  </div>
                </el-col>
                <el-col :span="7">
                  <el-image
                      style="width: 200px; height: 115px"
                      :src="item.firstImg">
                  </el-image>
                </el-col>
              </el-row>
            </el-card>
            <el-divider></el-divider>
          </div>

          <div  style="margin-left: 2%;padding-bottom: 2%;">
            <!-- 分页 -->
            <el-pagination
                background
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page.sync="pageNum"
                :page-sizes="[5,10]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
          </div>
        </div>
      </el-col>
    </div>
  </div>
</template>

<script>
import Header from "@/components/Header";
import Footer from "@/components/Footer";
import {Calendar, Search, VideoCameraFilled} from "@element-plus/icons";
import axios from "axios";
export default {
  name: 'Views',
  components: {
    Footer,
    Header,
    Search,
    Calendar,
    VideoCameraFilled,
  },
  data() {
    return {
      url:'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fp3-tt.byteimg.com%2Forigin%2Fpgc-image%2F8c97a9d8189043ceb2af93ad2e3e7153%3Ffrom%3Dpc&refer=http%3A%2F%2Fp3-tt.byteimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661775680&t=1f7af39d5d9a6f7aa96c85afead392ad',
      tableData: [],
      topData:[],
      pageNum:1,
      pageSize:5,
      total:'',
      search: '',
    }
  },
  mounted(){
    this.top()
    this.load()
  },

  methods:{
    load(){
      axios.get("/api/travels",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          search: this.search,
        }
      }).then(
          res=>{
            console.log(res.data);
            this.tableData=res.data.data.records;
            this.total=res.data.data.total
          })
    },
    scrollToTop () {
      this.$nextTick(() => {
        let ele = document.getElementById("home");
        console.log("ele: ",ele.scrollTop)
        ele.scrollTop = 0;
      })
    },
    to(id){
      this.scrollToTop()
      this.$router.push("/main/item/"+id)
    },

    top(){
      axios.get("/api/travels/top").then(
          res=>{
            console.log("top",res.data);
            this.topData=res.data.data;
          })
    },
    changeIMG(index){
      this.$refs.carousel.setActiveItem(index);
      // this.currentIndex=index
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
  top:-120px;
}

.search{
  width: 100vw;
  height: 50px;
  margin-top: 10px;
  padding: 10px 110px;
}
.top{
  width: 100vw;
  height: 500px;
  margin-top: 10px;
  margin-bottom: 10px;
  padding: 10px 110px;
}
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
</style>