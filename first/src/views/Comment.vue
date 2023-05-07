<template>
  <div>
      <div style="margin: 10px 0">
        <el-input v-model="search" placeholder="输入内容查询" style="width: 20%" size="mini" clearable></el-input>
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
        <el-table-column prop="id" label="序号" sortable width="80px"> </el-table-column>
        <el-table-column prop="context" label="内容"  width="350px"> </el-table-column>
        <el-table-column prop="time" label="发布时间" sortable width="200px"> </el-table-column>
        <el-table-column prop="active" label="评论状态">
          <template #default="scope">
            <el-tag v-if="scope.row.active===1" size="small" type="success">正常</el-tag>
            <el-tag v-if="scope.row.active===0" size="small" type="info">已删除</el-tag>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作">
          <template #default="scope">
            <el-popconfirm
                title="确定删除该评论吗？"
                v-if="scope.row.active===1"
                @confirm="del(scope.row.id)">
              <template #reference>
                <el-button size="small">删除</el-button>
              </template>
            </el-popconfirm>
            <el-popconfirm
                title="确定恢复该评论吗？"
                v-if="scope.row.active===0"
                @confirm="rem(scope.row.id)">
              <template #reference>
                <el-button size="small">恢复</el-button>
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
  name: 'Comment',
  data(){
    return{
      tableData: [],
      pageNum:1,
      pageSize:8,
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
  props:['id'],
  methods:{
    load(){
      axios.get("/api/comments/user",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          search: this.search,
          id: this.id,
        }
      }).then(
          res=>{
            console.log(res.data);
            this.tableData=res.data.data.records;
            this.total=res.data.data.total
          })
    },

    rem(id) {
      console.log(id)
      axios.post("/api/comments/"+id).then(
          res => {
            if(res.data.flag){
              this.$message({
                type: "success",
                message: "恢复成功"
              })
            }else {
              this.$message({
                type: "error",
                message: "恢复失败"
              })
            }
            this.load()
          }
      )
    },
    del(id) {
      console.log(id)
      axios.delete("/api/comments/"+id).then(
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