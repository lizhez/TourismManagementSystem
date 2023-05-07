<template>
  <div style="padding: 10px 20px">
    <div style="border-bottom:1px solid #DCDFE6;padding-bottom: 10px">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>用户管理</el-breadcrumb-item>
        <el-breadcrumb-item>酒店管理</el-breadcrumb-item>
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
      <el-badge :value="addInform" max="99" class="item" style="float: right">
        <router-link to="/req">
          <el-button style="float: right;margin-right: -10px;" size="mini">酒店新增请求</el-button>
        </router-link>
      </el-badge>
    </div>
    <el-table :data="tableData" border stripe style="width: 1150px" size="mini">
      <el-table-column prop="id" label="id" sortable width="70px"> </el-table-column>
      <el-table-column prop="name" label="酒店名称" > </el-table-column>
      <el-table-column prop="pay" label="价格" sortable> </el-table-column>
      <el-table-column prop="remain" label="剩余量"> </el-table-column>
      <el-table-column prop="payNum" label="订单数量"> </el-table-column>
      <el-table-column prop="phone" label="电话" > </el-table-column>
      <el-table-column prop="email" label="邮箱" > </el-table-column>
      <el-table-column prop="ban" label="违规信息" sortable> </el-table-column>
      <el-table-column prop="detail" label="详情">
        <template #default="scope">
          <el-button @click="seeDetail(scope.row)" size="small" type="text">查看详情</el-button>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="135px">
        <template #default="scope">
          <el-button @click="handleEdit(scope.row)" size="small">编辑</el-button>
          <el-popconfirm title="确定下架该酒店吗？" @confirm="handleDelete(scope.row.id)">
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

    <el-dialog title="酒店信息" v-model="dialogVisible" width="30%">
      <el-form ref="form"
               :model="form"
               label-width="80px"
               class="demo-dynamic">
        <el-form-item label="ID">
          <el-input v-model="form.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="酒店名称">
          <el-input v-model="form.name" disabled></el-input>
        </el-form-item>
        <el-form-item label="价格">
          <el-input v-model="form.pay" type="number"></el-input>
        </el-form-item>
        <el-form-item label="剩余量">
          <el-input v-model="form.remain" type="number"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.phone" disabled></el-input>
        </el-form-item>
        <el-form-item label="违规次数">
          <el-input v-model="form.ban" type="number"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit" size="mini">确定</el-button>
          <el-button @click="dialogVisible=false" size="mini">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>

import axios from 'axios'
import { Search } from '@element-plus/icons'

export default {
  name: 'Hotel',
  data(){
    return{
      tableData: [],
      pageNum:1,
      pageSize:5,
      total:5,
      search: '',
      dialogVisible: false,
      form: {
        id:'',
        name: '',
        pay:1000,
        remain:50,
        email: "1181347754@qq.com",
        ban:0
      },
      addInform:10
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
      axios.get("/api/users",{
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
    onSubmit() {
      axios.put("/api/users",this.form).then(
          res=>{
            this.dialogVisible=false
            if(res.data.flag){
              this.$message({
                type: "success",
                message: "更新成功"
              })
            }else {
              this.$message({
                type: "error",
                message: "更新失败"
              })
            }
            this.load()
          }
      )
    },
    seeDetail(){

    },
    handleEdit(row) {
      this.isADD=false
      this.form=JSON.parse(JSON.stringify(row))
      this.dialogVisible=true
    },
    handleDelete(id) {
      console.log(id)
      axios.delete("/api/users/"+id).then(
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
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then((_) => {
            done()
          })
          .catch((_) => {})
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
.item{
  margin-right: 10px;
}
</style>