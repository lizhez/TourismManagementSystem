<template>
  <div ref="item">
    <div style="position: relative">
      <div class="img">
        <el-image :src="url" style="width: 100vw;height: 110px;"></el-image>
      </div>
    </div>

    <div style="padding: 10px 110px">

<!--      用户信息-->
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

<!--      分割线-->
      <div style="background-color: #DCDFE6;height: 1px;margin: 5px 0;"></div>

<!--      基本信息-->
      <div>
        <el-image style="display:inline-block;width: 56%;height: 400px;margin: 2%;" :src="articleData.firstImg"/>
        <div style="
            position: relative;
            display:inline-block;
            width: 40%;height: 400px;
            margin: 2% 0;padding: 264px 0 0 2%;
            border-left: 1px solid #DCDFE6">
          <div style="position: absolute;top:210px;left: 23px">
            <h2 >{{articleData.title}}</h2>
            <el-tag v-if="articleData.istop===1" style="margin:10px 10px 10px 0;" type="error">热门</el-tag>
            <el-tag style="margin:10px 10px 10px 0;">畅玩</el-tag>
            <div>￥ {{ articleData.pay }}</div>
            <el-input-number
                v-if="type===0"
                v-model="payData.num"
                :min="1"
                :max="articleData.leaves"
                size="small"
                style="margin: 10px 0"
            ></el-input-number>
            <div v-if="type===0">
              <el-button size="small" @click="openPay" v-if="articleData.name!=='用户已注销'&&articleData.leaves>0">下单</el-button>
              <el-button size="small" @click="openPay" v-if="articleData.name==='用户已注销'||articleData.leaves<=0" disabled>下单</el-button>
              <el-tag style="margin:10px;" v-if="articleData.leaves<=0" type="info">已售罄</el-tag>
            </div>
          </div>
        </div>
      </div>

<!--      详细信息-->
      <div class="markdown-body" v-html="articleData.context" style="padding: 40px;"></div>

<!--       评论 -->
      <Com :blogId=id style="padding-bottom: 3px;margin: 2%;" v-if="hasUser"></Com>
    </div>

<!--    下单-->
    <el-dialog title="下单" v-model="addVisible" width="30%">
      <el-form ref="form" :model="payData" label-width="80px" class="demo-dynamic">
        <el-form-item label="名称" prop="title">
          <el-input :placeholder="articleData.title" disabled></el-input>
        </el-form-item>
        <el-form-item label="单价" prop="pay">
          <el-input :placeholder="articleData.pay" disabled></el-input>
        </el-form-item>
        <el-form-item label="个数" prop="num">
          <el-input-number
              v-model="payData.num"
              :min="1"
              :max="articleData.leaves"
              size="small"
              style="margin: 10px 0"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="总价" prop="total">
          <el-input v-model="addTotal" disabled></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="createPay" size="mini">确定</el-button>
          <el-button @click="addVisible=false" size="mini">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>


<script>
import Com from "@/components/Com";
import {Calendar, ChatDotSquare, DArrowRight, VideoCameraFilled} from "@element-plus/icons";
import axios from "axios";

export default {

  name: "Item",
  components:{
    Com,
    Calendar,
    VideoCameraFilled,
    ChatDotSquare,
    DArrowRight,
  },
  props:['id'],
  data(){
    return{
      hasUser:false,

      isTalking:false,
      url:'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fp3-tt.byteimg.com%2Forigin%2Fpgc-image%2F8c97a9d8189043ceb2af93ad2e3e7153%3Ffrom%3Dpc&refer=http%3A%2F%2Fp3-tt.byteimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661775680&t=1f7af39d5d9a6f7aa96c85afead392ad',
      articleData:{
        leaves:0,
      },
      payData:{
        num: 1,
        fid:'',
        toid:'',
        tid:'',
      },
      type:'',
      addVisible:false,
      addform:{},
    }
  },
  computed:{
    addTotal() {
      const numbers=parseFloat(this.payData.num * this.articleData.pay).toFixed(2)
      this.payData.total=numbers
      return numbers
    },
  },
  mounted() {
    let user=JSON.parse(sessionStorage.getItem("user"))
    console.log('user',user)
    if(user!==null){
      this.type=user.type
      this.payData.toid=user.id
      this.hasUser=true
    }
    this.load()
  },
  methods:{
    goo(){
      this.$router.go(-1);
    },
    load(){
      axios.get('/api/travels/' + this.id).then(res=>{
        console.log('data-----',res.data.data)
        console.log('type',this.type)
        this.articleData = res.data.data;
        this.payData.fid=res.data.data.uid;
        this.payData.tid=res.data.data.id;
        console.log("pay",this.payData)
        let MarkdownIt = require("markdown-it")
        let md = new MarkdownIt()
        this.articleData.context = md.render(res.data.data.context)
      })
    },
    openPay(){
      this.addVisible=true
    },
    createPay(){
      console.log(this.payData)
      axios.post('/api/pays/add',this.payData).then(
          res=>{
            if(res.data.flag){
              this.$message({
                type: "success",
                message: "下单成功,请尽快前往我的订单进行付款!"
              })
            }else {
              this.$message({
                type: "error",
                message: "下单失败"
              })
            }
            this.load()
            this.addVisible=false
          }
      )
    },

  }
}
</script>

<style scoped>
.img{
  position: absolute;
  z-index: -1;
  left: 0;
  top:-110px;
}
</style>