<template>
  <div style="padding: 10px 20px">
    <div style="border-bottom:1px solid #DCDFE6;padding-bottom: 10px;margin-bottom: 10px">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/buy' }">订单管理</el-breadcrumb-item>
        <el-breadcrumb-item>已完成订单</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="输入关键字查询" style="width: 20%" size="mini" clearable></el-input>
      <el-button style="margin-left: 5px;" size="mini" @click="load">
        <el-icon style="vertical-align: middle;">
          <search />
        </el-icon>
        <span style="vertical-align: middle;"> 搜索 </span>
      </el-button>
      <el-button size="mini" style="float: right;">
        <router-link to="/buy">返回</router-link>
      </el-button>
    </div>
    <el-table :data="tableData" border stripe style="width: 1150px" size="mini"
              :header-cell-style="{'text-align':'center'}"
              :cell-style="{'text-align':'center'}">
      <el-table-column prop="id" label="序号" sortable> </el-table-column>
      <el-table-column prop="payid" label="订单编号"> </el-table-column>
      <el-table-column prop="pay" label="价格" sortable> </el-table-column>
      <el-table-column prop="fid" label="卖家id"> </el-table-column>
      <el-table-column prop="toid" label="买家id" > </el-table-column>
      <el-table-column prop="finishtime" label="订单时间" width="200px" sortable> </el-table-column>
      <el-table-column prop="detail" label="详情" width="100px">
        <template #default="scope">
          <router-link :to="'/buyItem/'+scope.row.id">
            <el-button @click="seeDetail(scope.row)" size="small" type="text">查看详情</el-button>
          </router-link>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[5,10]"
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
import { Top } from '@element-plus/icons'

export default {
  name: 'FinishBuy',
  data(){
    return{
      tableData: [],
      pageNum:1,
      pageSize:5,
      total:5,
      search: '',
    }
  },
  components: {
    Search,
    Top,
  },
  mounted(){
    //this.getAll();
    this.load()
  },
  methods:{
    load(){
      axios.get("/api/pays/finish",{
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
    seeDetail(){

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