<template>
  <div style="padding: 10px 20px">
    <div style="border-bottom:1px solid #DCDFE6;padding-bottom: 10px;margin-bottom: 10px">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>页面管理</el-breadcrumb-item>
        <el-breadcrumb-item>热门推荐</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="tag-group" style="border-bottom: 1px solid #ccc;padding-bottom: 10px">
      <span class="tag-group__title" style="font-size: small">类型</span>
      <el-tag
          v-for="item in typeItems"
          :key="item.label"
          :type="item.type"
          effect="plain"
          style="margin-left: 5px"
          @click="changeType(item)">
        {{ item.label }}
      </el-tag>
    </div>
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="输入标题查询" style="width: 20%" size="mini" clearable></el-input>
      <el-button style="margin-left: 5px;" size="mini" @click="load">
        <el-icon style="vertical-align: middle;">
          <search />
        </el-icon>
        <span style="vertical-align: middle;"> 搜索 </span>
      </el-button>
    </div>
    <el-table :data="tableData" border stripe style="width: 1150px" size="mini"
              :header-cell-style="{'text-align':'center'}"
              :cell-style="{'text-align':'center'}">
      <el-table-column prop="id" label="序号" sortable width="100px"> </el-table-column>
      <el-table-column prop="name" label="用户id" > </el-table-column>
      <el-table-column prop="title" label="标题" > </el-table-column>
      <el-table-column prop="meg" label="简介" width="350px"> </el-table-column>
      <el-table-column prop="view" label="浏览量" sortable> </el-table-column>
      <el-table-column prop="detail" label="内容详情">
        <template #default="scope">
          <router-link :to="'/'+this.linkTo+'/'+scope.row.id">
            <el-button size="small" type="text">查看详情</el-button>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column prop="type" label="状态">
        <template #default="scope">
          <el-tag v-if="scope.row.istop===1" size="small" type="success">在推</el-tag>
          <el-tag v-if="scope.row.istop===0" size="small" type="info">未推</el-tag>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作">
        <template #default="scope">
          <el-popconfirm
              title="确定撤销该内容吗？"
              v-if="scope.row.istop===1"
              @confirm="delTop(scope.row.id)">
            <template #reference>
              <el-button size="small">撤销热门</el-button>
            </template>
          </el-popconfirm>
          <el-popconfirm
              title="确定推荐该内容吗？"
              v-if="scope.row.istop===0 && scope.row.active===1"
              @confirm="toTop(scope.row.id)">
            <template #reference>
              <el-button size="small">推荐热门</el-button>
            </template>
          </el-popconfirm>
          <el-popconfirm
              title="确定推荐该内容吗？"
              v-if="scope.row.istop===0 && scope.row.active===0">
            <template #reference>
              <el-button size="small" disabled>推荐热门</el-button>
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
  name: 'Top',
  data(){
    return{
      tableData: [],
      pageNum:1,
      pageSize:10,
      total:'',
      search: '',
      typeItem:'travels',
      linkTo:'travelItem',
      typeItems:[
        { type: 'success', label: '旅行推荐' },
        { type: '', label: '达人点评' },
      ],
    }
  },
  components: {
    Search,
  },
  mounted(){
    this.load()
  },
  methods:{
    load(){
      axios.get("/api/"+this.typeItem,{
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

    toTop(id) {
      console.log(this.typeItem)
      axios.put("/api/"+this.typeItem+"/top/"+id).then(
          res => {
            if(res.data.flag){
              this.$message({
                type: "success",
                message: "热推成功"
              })
            }else {
              this.$message({
                type: "error",
                message: "热推失败"
              })
            }
            this.load()
          }
      )
    },
    delTop(id) {
      axios.delete("/api/"+this.typeItem+"/top/"+id).then(
          res => {
            if(res.data.flag){
              this.$message({
                type: "success",
                message: "撤推成功"
              })
            }else {
              this.$message({
                type: "error",
                message: "撤推失败"
              })
            }
            this.load()
          }
      )
    },

    changeType(item){
      for (const k in this.typeItems) {
        this.typeItems[k].type=''
      }
      item.type='success'
      console.log(item.label)
      if(item.label==='旅行推荐') {
        this.typeItem = 'travels'
        this.linkTo = 'travelItem'
      }
      else {
        this.typeItem = 'articles'
        this.linkTo = 'article'
      }
      console.log(this.typeItem)
      console.log(this.linkTo)
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