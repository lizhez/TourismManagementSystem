<template>
  <div>
    <div style="padding: 20px 40px 10px">
      <img style="width: 100px;height: 80px;margin-top: 10px; border-radius: 3%" :src="payData.firstImg">
      <div style="border-left: 1px solid #DCDFE6;height: 80px;width: 600px;display:inline-block;margin-left: 10px;padding: 8px 10px">
        <h4 style="float:left; width: 100%">  {{payData.title}}</h4>
        <h5 style="float:left; width: 100%;color: #858585">  单价：￥{{payData.pay}}</h5>
        <h5 style="float:left; width: 100%;color: #858585">  数量：{{payData.num}}</h5>
        <h5 style="float:left; width: 100%;color: #858585">  实付：￥{{payData.total}}</h5>
      </div>
      <el-button style="float: right;margin-top: -36px;" @click="goo">返回</el-button>
    </div>
    <div style="background-color: #DCDFE6;height: 1px;margin: 5px 0;"></div>
<!--    <h3 style="padding: 10px 40px">订单详情</h3>-->
    <!--文章主体-->
    <div style="padding: 5px 40px;">
      <el-descriptions
          v-if="type===0"
          class="margin-top"
          title="卖家信息"
          :column="4"
          direction="vertical"
          style="margin-bottom: 5px">
        <el-descriptions-item  :span="4">
          <el-button v-if="payData.fname!=='用户已注销'">
            <el-icon style="vertical-align: middle;">
              <chat-dot-square />
            </el-icon>
            <span style="vertical-align: middle;" @click="isTalking=!isTalking"> 联系商家 </span>
          </el-button >
          <el-button v-if="payData.fname==='用户已注销'" disabled>
            <el-icon style="vertical-align: middle;">
              <chat-dot-square />
            </el-icon>
            <span style="vertical-align: middle;" @click="isTalking=!isTalking"> 联系商家 </span>
          </el-button>
        </el-descriptions-item>
        <el-descriptions-item label="用户名">{{payData.fname}}</el-descriptions-item>
        <el-descriptions-item label="手机号">{{payData.fphone}}</el-descriptions-item>
        <el-descriptions-item label="所在地址" :span="2">{{payData.faddress}}</el-descriptions-item>
      </el-descriptions>
      <el-descriptions
          v-if="type===1"
          class="margin-top"
          title="买家信息"
          :column="4"
          direction="vertical"
          style="margin-bottom: 5px">
        <el-descriptions-item  :span="4">
          <el-button v-if="payData.toname!=='用户已注销'">
            <el-icon style="vertical-align: middle;">
              <chat-dot-square />
            </el-icon>
            <span style="vertical-align: middle;" @click="isTalking=!isTalking"> 联系买家 </span>
          </el-button>
          <el-button v-if="payData.toname==='用户已注销'" disabled>
            <el-icon style="vertical-align: middle;">
              <chat-dot-square />
            </el-icon>
            <span style="vertical-align: middle;" @click="isTalking=!isTalking"> 联系买家 </span>
          </el-button>
        </el-descriptions-item>
        <el-descriptions-item label="用户名">{{payData.toname}}</el-descriptions-item>
        <el-descriptions-item label="手机号">{{payData.tophone}}</el-descriptions-item>
        <el-descriptions-item label="所在地址" :span="2">{{payData.toaddress}}</el-descriptions-item>
      </el-descriptions>
      <el-descriptions
          class="margin-top"
          title="商品信息"
          :column="4"
          direction="vertical">
        <el-descriptions-item label="订单编号">{{payData.payid}}</el-descriptions-item>
        <el-descriptions-item label="商品名">{{payData.title}}</el-descriptions-item>
        <el-descriptions-item label="商品总价" :span="2">{{payData.pay}}</el-descriptions-item>
        <!--0未支付；1已支付；2已取消-->
        <el-descriptions-item label="创建时间"><data value="">{{payData.createtime}}</data></el-descriptions-item>
        <el-descriptions-item label="支付时间" v-if="payData.status===1||payData.status===-1"><data value="">{{payData.paytime}}</data></el-descriptions-item>
        <el-descriptions-item label="完成时间" v-if="payData.status===-1"><data value="">{{payData.finishtime}}</data></el-descriptions-item>
      </el-descriptions>
    </div>

    <el-dialog v-model="isTalking" title="聊天室" width="40%">
      <Talking :thisid="payData.toid" :toid="payData.fid" v-if="type===0"/>
      <Talking :thisid="payData.fid" :toid="payData.toid" v-if="type===1"/>
    </el-dialog>
  </div>
</template>

<script>

import axios from 'axios'
import {Calendar,ChatDotSquare} from '@element-plus/icons'
import MarkdownIt from "markdown-it";
import Talking from "@/components/Talking";

export default {
  name: "BuyItem",
  components:{
    Calendar,
    ChatDotSquare,
    Talking,
  },
  data () {
    return {
      payData:{},
      isTalking:false,
      type:'',
    }
  },
  props:['id'], //接收路由传过来的id
  mounted() {
    this.type=JSON.parse(sessionStorage.getItem("user")).type
    this.load()
  },
  methods:{
    goo(){
      this.$router.go(-1);
    },
    load(){
      axios.get('/api/pays/' + this.id).then(res=>{
        console.log("1",res.data.data)
        this.payData = res.data.data
      })
    },
  }
}
</script>

<style scoped>

</style>