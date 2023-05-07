<template>
  <div style="padding: 10px 20px">

    <div style="border-bottom:1px solid #DCDFE6;padding-bottom: 10px">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>用户管理</el-breadcrumb-item>
        <el-breadcrumb-item>旅游项目管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div style="margin: 10px 0">

      <el-input v-model="search" placeholder="输入旅行项目查询" style="width: 20%" size="mini" clearable></el-input>
      <el-button style="margin-left: 5px;" size="mini" @click="load">
        <el-icon style="vertical-align: middle;">
          <search />
        </el-icon>
        <span style="vertical-align: middle;"> 搜索 </span>
      </el-button>

      <router-link to="/req">
        <el-button style="float: right;" size="mini">旅游项目新增请求</el-button>
      </router-link>

    </div>

    <el-table :data="tableData" border stripe style="width: 1150px" size="mini"
              :header-cell-style="{'text-align':'center'}"
              :cell-style="{'text-align':'center'}">
      <el-table-column prop="name" label="旅游社" > </el-table-column>
      <el-table-column prop="title" label="旅游项目" > </el-table-column>
      <el-table-column prop="pay" label="价格" sortable> </el-table-column>
      <el-table-column prop="leaves" label="剩余量"> </el-table-column>
      <el-table-column prop="finishes" label="订单数量"> </el-table-column>
      <el-table-column prop="phone" label="电话" > </el-table-column>
      <el-table-column prop="email" label="邮箱" width="200px"> </el-table-column>
      <el-table-column prop="detail" label="详情">
        <template #default="scope">
          <router-link :to="'/travelItem/'+scope.row.id">
            <el-button size="small" type="text" @click="aa(scope.row.id)">查看详情</el-button>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作">
        <template #default="scope">
          <el-popconfirm title="确定下架该旅游项目吗？" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button size="small">下架</el-button>
            </template>
          </el-popconfirm>
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

export default {
  name: 'Travle',
  data(){
    return{
      tableData: [],
      pageNum:1,
      pageSize:10,
      total:'',
      search: '',
    }
  },
  components: {
    Search,
  },
  mounted(){
    this.load()
  },
  methods:{
    aa(id){
      console.log(id)
    },
    load(){
      axios.get("/api/travels",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          search: this.search,
          req:"",
        }
      }).then(
          res=>{
            console.log(res.data);
            this.tableData=res.data.data.records;
            this.total=res.data.data.total
          })
    },

    handleDelete(id) {
      console.log(id)
      axios.put("/api/travels/"+id).then(
          res => {
            if(res.data.flag){
              this.$message({
                type: "success",
                message: "下架成功"
              })
            }else {
              this.$message({
                type: "error",
                message: "下架失败"
              })
            }
            this.load()
          }
      )
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
.item {
  margin-right: 10px;
}
</style>