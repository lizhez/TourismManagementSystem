<template>
  <div style="padding: 10px 20px" class="blog_bg">
    <div style="border-bottom:1px solid #DCDFE6;padding-bottom: 10px;margin-bottom: 10px">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>页面管理</el-breadcrumb-item>
        <el-breadcrumb-item>文章审查</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="输入文章标题查询" style="width: 20%" size="mini" clearable></el-input>
      <el-button style="margin-left: 5px;" size="mini" @click="load">
        <el-icon style="vertical-align: middle;">
          <search />
        </el-icon>
        <span style="vertical-align: middle;"> 搜索 </span>
      </el-button>
    </div>
    <el-table
        :data="tableData"
        border stripe
        style="width: 1150px"
        size="mini"
        :header-cell-style="{'text-align':'center'}"
        :cell-style="{'text-align':'center'}">
      <el-table-column prop="id" label="序号" sortable width="80px"> </el-table-column>
      <el-table-column prop="name" label="用户名" > </el-table-column>
      <el-table-column prop="active" label="文章状态" width="100px">
        <template #default="scope" >
          <el-tag v-if="scope.row.active===1" type="success" size="small">已发布</el-tag>
          <el-tag v-if="scope.row.active===0" type="info" size="small">未发布</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="title" label="标题" > </el-table-column>
      <el-table-column prop="meg" label="简述" > </el-table-column>
      <el-table-column prop="time" label="发布时间" > </el-table-column>
      <el-table-column prop="detail" label="内容详情" width="100px">
        <template #default="scope">
          <router-link :to="'/article/'+scope.row.id">
          <el-button size="small" type="text">查看详情</el-button>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作">
        <template #default="scope">
          <el-popconfirm
              title="确定发布该文章吗？"
              @confirm="publicIt(scope.row.id)"
              v-if="scope.row.active===0">
            <template #reference>
              <el-button size="small">发布</el-button>
            </template>
          </el-popconfirm>
          <el-popconfirm
              title="确定拒绝此文章发布吗？"
              @confirm="unpublicIt(scope.row.id)"
              v-if="scope.row.active===0">
            <template #reference>
              <el-button size="small">拒绝</el-button>
            </template>
          </el-popconfirm>
          <el-popconfirm
              title="确定取消此文章发布吗？"
              @confirm="delPub(scope.row.id)"
              v-if="scope.row.active===1">
            <template #reference>
              <el-button size="small">取消</el-button>
            </template>
          </el-popconfirm>
          <el-popconfirm
              title="确定删除此文章吗？"
              @confirm="del(scope.row.id)"
              v-if="scope.row.active===1">
            <template #reference>
              <el-button size="small">删除</el-button>
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
  name: 'Text',
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
    //this.getAll();
    this.load()
  },
  methods:{
    load(){
      axios.get("/api/articles",{
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

    publicIt(id) {
      axios.post("/api/articles/"+id).then(
          res => {
            if(res.data.flag){
              this.$message({
                type: "success",
                message: "发布成功"
              })
            }else {
              this.$message({
                type: "error",
                message: "发布失败"
              })
            }
            this.load()
          }
      )
    },
    unpublicIt(id){
      axios.delete("/api/articles/"+id).then(
          res => {
            if(res.data.flag){
              this.$message({
                type: "success",
                message: "拒绝文章发布成功"
              })
            }else {
              this.$message({
                type: "error",
                message: "拒绝文章发布失败"
              })
            }
            this.load()
          }
      )
    },
    del(id){
      axios.put("/api/articles/"+id).then(
          res => {
            if(res.data.flag){
              this.$message({
                type: "success",
                message: "删除成功"
              })
            }else {
              this.$message({
                type: "error",
                message: "删除失败"
              })
            }
            this.load()
          }
      )
    },
    delPub(id){
      axios.delete("/api/articles/delPub/"+id).then(
          res => {
            if(res.data.flag){
              this.$message({
                type: "success",
                message: "取消成功"
              })
            }else {
              this.$message({
                type: "error",
                message: "取消失败"
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

</style>