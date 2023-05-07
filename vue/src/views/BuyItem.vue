<template>
  <div class="blog_bg">
    <div style="padding: 20px 40px 10px">
      <img style="width: 100px;height: 80px;margin-top: 10px; border-radius: 3%" :src="payData.firstImg">
      <div style="border-left: 1px solid #DCDFE6;height: 80px;width: 600px;display:inline-block;margin-left: 10px;padding: 8px 10px">
        <h4 style="float:left; width: 100%">  {{payData.title}}</h4>
        <h5 style="float:left; width: 100%;color: #858585">  单价：￥{{payData.pay}}</h5>
        <h5 style="float:left; width: 100%;color: #858585">  数量：{{payData.num}}</h5>
        <h5 style="float:left; width: 100%;color: #858585">  实付：￥{{payData.total}}</h5>
      </div>
      <el-button size="mini" style="float: right;margin-top: 8px;" @click="goo">返回</el-button>
    </div>
    <div style="background-color: #DCDFE6;height: 1px;margin: 5px 0;"></div>
<!--    <h3 style="padding: 10px 40px">订单详情</h3>-->
    <!--文章主体-->
    <div style="padding: 5px 40px;">
      <el-descriptions
          class="margin-top"
          title="卖家信息"
          :column="4"
          direction="vertical"
          style="margin-bottom: 5px">
        <el-descriptions-item label="用户名">{{payData.fname}}</el-descriptions-item>
        <el-descriptions-item label="手机号">{{payData.fphone}}</el-descriptions-item>
        <el-descriptions-item label="所在地址" :span="2">{{payData.faddress}}</el-descriptions-item>
      </el-descriptions>
      <el-descriptions
          class="margin-top"
          title="买家信息"
          :column="4"
          direction="vertical"
          style="margin-bottom: 5px">
        <el-descriptions-item label="用户名">{{payData.toname}}</el-descriptions-item>
        <el-descriptions-item label="手机号">{{payData.tophone}}</el-descriptions-item>
        <el-descriptions-item label="下单地址" :span="2">{{payData.toaddreaa}}</el-descriptions-item>
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
  </div>
</template>

<script>

import axios from 'axios'
import Calendar from '@element-plus/icons'

export default {
  name: "BuyItem",
  components:{
    Calendar,
  },
  data () {
    return {
      payData:{},
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
      axios.get('/api/pays/' + this.id).then(res=>{
        console.log(res.data)
        this.payData = res.data.data

        /*var MardownIt = require("markdown-it")
        var md = new MardownIt()
        var result = md.render(res.data.obj.content)
        this.artData.content = result
        console.log(this.artData)*/
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