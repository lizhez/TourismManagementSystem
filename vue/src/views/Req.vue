<template>
  <div style="padding: 10px 20px">
    <div style="border-bottom:1px solid #DCDFE6;padding-bottom: 10px;margin-bottom: 10px">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/travel' }">旅游项目管理</el-breadcrumb-item>
        <el-breadcrumb-item>请求处理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="输入旅行社名称查询" style="width: 20%" size="mini" clearable></el-input>
      <el-button style="margin-left: 5px;" size="mini" @click="load">
        <el-icon style="vertical-align: middle;">
          <search />
        </el-icon>
        <span style="vertical-align: middle;"> 搜索 </span>
      </el-button>
      <el-button size="mini" style="float: right;">
        <router-link to="/travel">返回</router-link>
      </el-button>
    </div>
    <el-table :data="tableData" style="width: 1150px">
        <el-table-column class="ti" label="请求列表">
          <template  #default="scope">
            <el-card>
              <div >
                <div style=" font-size: 18px;">
                  {{scope.row.name}}
                </div>
                <div style="margin-top: 10px;">
                  <i style="margin-right: 20px;" >
                    <el-icon><calendar /></el-icon>
                    {{reqTime}}</i>
<!--                  <router-link :to=" '/travelItem' + scope.row.id ">-->
                  <el-popconfirm title="确定拒绝该旅游项目上架吗？" @confirm="deleteReq(scope.row.id)">
                    <template #reference>
                      <el-button size="small" type="danger" style="float: right;">拒绝</el-button>
                    </template>
                  </el-popconfirm>
                  <el-button  style="float: right;margin-right: 10px;" size="small" @click="addReq(scope.row.id)">允许</el-button>
                  <router-link :to="'/travelItem/'+scope.row.id">
                    <el-button  style="float: right; "  size="small">
                      <span>查看详情</span>
                    </el-button>
                  </router-link>
                </div>
              </div>
            </el-card>
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
import { Calendar } from '@element-plus/icons'

export default {
  name: 'Req',
  data(){
    return{
      tableData: [],
      pageNum:1,
      pageSize:3,
      total:5,
      search: '',
      reqTime:'2020-05-09 12:14:02'
    }
  },
  components: {
    Search,
    Top,
    Calendar,
  },
  mounted(){
    //this.getAll();
    this.load()
  },
  methods:{
    load(){
      axios.get("/api/travels",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          search: this.search,
          req:"req",
        }
      }).then(
          res=>{
            console.log(res.data);
            this.tableData=res.data.data.records;
            this.total=res.data.data.total
          })
    },
    addReq(id){
      console.log(id)
      axios.post("/api/travels/"+id).then(
          res => {
            if(res.data.flag){
              this.$message({
                type: "success",
                message: "上架成功"
              })
            }else {
              this.$message({
                type: "error",
                message: "上架失败"
              })
            }
            this.load()
          }
      )
    },
    deleteReq(id){
      axios.delete("/api/travels/"+id).then(
          res => {
            if(res.data.flag){
              this.$message({
                type: "success",
                message: "拒绝成功"
              })
            }else {
              this.$message({
                type: "error",
                message: "拒绝失败"
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