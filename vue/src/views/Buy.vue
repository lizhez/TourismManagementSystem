<template>
  <div style="padding: 10px 20px">
    <div style="border-bottom:1px solid #DCDFE6;padding-bottom: 10px;margin-bottom: 10px">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>订单管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div style="border-bottom: 1px solid #ccc;padding-bottom: 10px">
      <div class="tag-group" style="margin-top: 10px">
        <span class="tag-group__title" style="font-size: small">支付状态</span>
        <el-tag
            v-for="item in payItems"
            :key="item.label"
            :type="item.type"
            effect="plain"
            style="margin-left: 5px"
            @click="changePay(item)"
        >
          {{ item.label }}
        </el-tag>
      </div>
    </div>
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="输入订单编号查询" style="width: 20%" size="mini" clearable></el-input>
      <el-button style="margin-left: 5px;" size="mini" @click="load">
        <el-icon style="vertical-align: middle;">
          <search />
        </el-icon>
        <span style="vertical-align: middle;"> 搜索 </span>
      </el-button>
      <router-link to="/finishBuy">
        <el-button style="float: right;" size="mini" @click="load">
          <span style="vertical-align: middle;"> 已完成订单 </span>
        </el-button>
      </router-link>
    </div>
    <el-table :data="tableData" border stripe style="width: 1150px" size="mini"
              :header-cell-style="{'text-align':'center'}"
              :cell-style="{'text-align':'center'}">
      <el-table-column prop="id" label="序号" sortable> </el-table-column>
      <el-table-column prop="payid" label="订单编号"> </el-table-column>
      <el-table-column prop="pay" label="价格" sortable> </el-table-column>
      <el-table-column prop="total" label="总价" sortable> </el-table-column>
      <el-table-column prop="num" label="数量" sortable> </el-table-column>
      <el-table-column prop="status" label="支付状态">
        <template #default="scope">
          <el-tag v-if="scope.row.status===0" size="small" type="info">未支付</el-tag>
          <el-tag v-if="scope.row.status===1" size="small" >已支付</el-tag>
          <el-tag v-if="scope.row.status===2" size="small" type="danger">已取消</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="fid" label="卖家id"> </el-table-column>
      <el-table-column prop="toid" label="买家id" > </el-table-column>
      <el-table-column prop="createtime" label="订单时间" width="200px" sortable> </el-table-column>
      <el-table-column prop="detail" label="详情" width="100px">
        <template #default="scope">
          <router-link :to="'/buyItem/'+scope.row.id">
           <el-button @click="seeDetail(scope.row)" size="small" type="text">查看详情</el-button>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="100px">
        <template #default="scope">
          <el-popconfirm title="确定该订单已经完成了吗？" @confirm="finish(scope.row.id)">
            <template #reference>
              <el-button v-if="scope.row.status===1" size="small">交易完成</el-button>
              <el-button v-if="scope.row.status===0" size="small" disabled>交易完成</el-button>
            </template>
          </el-popconfirm>
          <el-popconfirm title="确定删除该订单吗？" @confirm="del(scope.row.id)">
            <template #reference>
              <el-button v-if="scope.row.status===2" size="small">删除交易</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[5,8,10]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        style="margin-top: 10px"
    >
    </el-pagination>
  </div>
</template>

<script>

import axios from 'axios'
import { Search } from '@element-plus/icons'

export default {
  name: 'Buy',
  data(){
    return{
      tableData: [],
      pageNum:1,
      pageSize:8,
      total:'',
      search: '',
      payItem:'',
      payItems:[
        { type: 'success', label: '全部' },
        { type: '', label: '未支付' },
        { type: '', label: '已支付' },
        { type: '', label: '已取消' },
      ],
    }
  },
  components: {
    Search,
  },
  mounted(){
    //this.getAll();
    this.load()
  },
  methods:{
    load(){
      axios.get("/api/pays",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          search: this.search,
          req:this.payItem,
        }
      }).then(
          res=>{
            console.log(res.data);
            this.tableData=res.data.data.records;
            this.total=res.data.data.total
          })
    },

    finish(id){
      axios.post("/api/pays/"+id).then(
          res => {
            if(res.data.flag){
              this.$message({
                type: "success",
                message: "交易已完成"
              })
            }else {
              this.$message({
                type: "error",
                message: "交易失败"
              })
            }
            this.load()
          }
      )
    },

    del(id) {
      axios.delete("/api/pays/"+id).then(
          res => {
            if(res.data.flag){
              this.$message({
                type: "success",
                message: "订单已删除"
              })
            }else {
              this.$message({
                type: "error",
                message: "订单删除失败"
              })
            }
            this.load()
          }
      )
    },

    changePay(item){
      for (const k in this.payItems) {
        this.payItems[k].type=''
      }
      item.type='success'
      if(item.label==='全部')
        this.payItem=''
      else if(item.label==='未支付')
        this.payItem=0
      else if(item.label==='已支付')
        this.payItem=1
      else
        this.payItem=2
      console.log(this.payItem)
      this.load()
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

</style>