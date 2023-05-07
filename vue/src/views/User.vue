<template>
  <div style="padding: 10px 20px">
    <div style="border-bottom:1px solid #DCDFE6;padding-bottom: 10px">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>用户管理</el-breadcrumb-item>
        <el-breadcrumb-item>用户管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="输入用户名查询" style="width: 20%" size="mini" clearable></el-input>
      <el-button style="margin-left: 5px;" size="mini" @click="load">
          <el-icon style="vertical-align: middle;">
            <search />
          </el-icon>
          <span style="vertical-align: middle;"> 搜索 </span>
      </el-button>
      <el-button style="float: right" size="mini" @click="handleAdd">添加用户</el-button>
    </div>
    <el-table :data="tableData" border stripe style="width: 1150px"
              size="mini"
              :header-cell-style="{'text-align':'center'}"
              :cell-style="{'text-align':'center'}">
      <el-table-column prop="id" label="序号" sortable width="80px"> </el-table-column>
      <el-table-column prop="name" label="用户名" > </el-table-column>
      <el-table-column prop="type" label="用户种类">
        <template #default="scope">
          <el-tag v-if="scope.row.type===1" size="small" type="success">旅行社</el-tag>
          <el-tag v-if="scope.row.type===0" size="small">游客</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="sex" label="性别"></el-table-column>
      <el-table-column prop="phone" label="电话" width="150px"> </el-table-column>
      <el-table-column prop="email" label="邮箱" width="200px"> </el-table-column>
      <el-table-column prop="address" label="地址" width="250px"> </el-table-column>
      <el-table-column fixed="right" label="操作" width="150px">
        <template #default="scope">
          <el-button @click="handleEdit(scope.row)" size="small">编辑</el-button>
          <el-popconfirm title="这是一段内容确定注销吗？" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button size="small">注销</el-button>
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

    <el-dialog title="用户信息" v-model="editVisible" width="40%">
      <el-form ref="form" :model="form" label-width="80px" class="demo-dynamic">
        <el-form-item label="ID">
          <el-input v-model="form.id" disabled style="width: 90%" ></el-input>
        </el-form-item>
        <el-form-item label="用户名" prop="name"
                      :rules="{ required: true, message: '请输入用户名', trigger: 'blur' }">
          <el-input style="width: 90%" v-model="form.name" ></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="form.sex">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机号" prop="phone"
                      :rules="[
                      { required: true, message: '请输入手机号', trigger: 'blur' },
                      {type: 'number', message: '手机号格式不正确', trigger: 'blur',
                       transform(value) {
                         var phonereg = 11 && /^((13|14|15|16|17|18|19)[0-9]{1}\d{8})$/
                         if (!phonereg.test(value)) {
                           return false
                         } else {
                           return Number(value)
                         }
                       }}
                      ]">
          <el-input v-model="form.phone" style="width: 90%"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email"
                      :rules="[
                      { required: true, message: '请输入邮箱地址', trigger: 'blur' },
                      { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
                      ]">
          <el-input v-model="form.email" style="width: 70%"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-if="addChange" prop="address"  v-model="form.address" style="width: 220px"></el-input>
          <el-button v-if="addChange" @click="addChange=!addChange" size="mini" style="margin-left: 10px;">修改</el-button>
          <el-cascader
              v-if="!addChange"
              v-model="form.address"
              :options="addressC.pcaOptions"
              :props="addressC.addressProps"
              @change="handleAddressNode"
              :placeholder="form.address"
              filterable
          ></el-cascader>
          <el-button v-if="!addChange" @click="changeAd" size="mini" style="margin-left: 10px;">确定</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit" size="mini" style="margin-left: 10px;">确定</el-button>
          <el-button @click="editVisible=false" size="mini">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog title="用户添加" v-model="addVisible" width="40%">
      <el-form
          :model="rejForm"
          status-icon
          :rules="rules"
          ref="rejForm"
          label-width="100px"
      >
        <el-form-item label="用户名" prop="name"
                      :rules="{ required: true, message: '请输入用户名', trigger: 'blur' }">
          <el-input style="width: 380px" v-model="rejForm.name"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="rejForm.sex">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-radio-group v-model="rejForm.type">
            <el-radio label="游客"></el-radio>
            <el-radio label="旅游社"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-cascader
              v-model="rejForm.address"
              :options="addressC.pcaOptions"
              :props="addressC.addressProps"
              @change="AddressNode"
              :placeholder="rejForm.address"
              filterable
          ></el-cascader>
        </el-form-item>
        <el-form-item label="密码" prop="pass">
          <el-input
              :type="pwd"
              v-model="rejForm.pass"
              autocomplete="off"
              style="width: 380px"
          ></el-input>
          <img v-show="close"
               style="width: 15px;height: 8px;margin-left: 5px"
               @click="changPWD"
               src="@/assets/img/close.png"/>
          <img v-show="!close"
               style="width: 20px;height: 15px;margin-left: 5px"
               @click="changPWD"
               src="@/assets/img/open.png"/>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input
              type="password"
              v-model="rejForm.checkPass"
              autocomplete="off"
              style="width: 380px"
          ></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone"
                      :rules="[
                      { required: true, message: '请输入手机号', trigger: 'blur' },
                      {type: 'number', message: '手机号格式不正确', trigger: 'blur',
                       transform(value) {
                         var phonereg = 11 && /^((13|14|15|16|17|18|19)[0-9]{1}\d{8})$/
                         if (!phonereg.test(value)) {
                           return false
                         } else {
                           return Number(value)
                         }
                       }}
                      ]">
          <el-input v-model="rejForm.phone" style="width: 380px"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email"
                      :rules="[
                      { required: true, message: '请输入邮箱地址', trigger: 'blur' },
                      { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
                      ]">
          <el-input v-model="rejForm.email" style="width: 380px"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit()">确定</el-button>
          <el-button @click="addVisible = !addVisible" style="margin-left:259px">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>



  </div>
</template>

<script>

import axios from 'axios'
import { Search } from '@element-plus/icons'
import request from "@/utils/request";
import Address from "@/components/Address";
const pca = require('@/assets/pca-code.json')

export default {
  name: 'User',
  data(){
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'))
        } else {
          if (this.rejForm.checkPass !== '') {
            this.$refs.rejForm.validateField('checkPass')
          }
          callback()
        }
      }
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'))
        } else if (value !== this.rejForm.pass) {
          callback(new Error('两次输入密码不一致!'))
        } else {
          callback()
        }
      }
    return{
      pageNum:1,
      pageSize:10,
      total:'',
      search: '',

      tableData: [],

      isADD:true,

      addVisible: false,
      editVisible: false,
      addChange:true,
      form: {},
      rejForm: {},
      close:true,
      pwd:'password',
      rules: {
        pass: [{validator: validatePass, trigger: 'blur' }],
        checkPass: [{validator: validatePass2, trigger: 'blur' }],
      },

      addressC:{
        pcaOptions:pca,
        addressProps:{
          label: 'name',
          children: 'children',
          value: 'name'
        }
      },
    }
  },
  components: {
    Search,
    Address,
  },
  mounted(){
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
    changeAd(e){
      this.addChange=true
    },
    handleAddressNode(value){    //value为数组
      this.form.address =''+ value[0]+'/'+value[1]+'/'+value[2]
      console.log(this.form.address)
    },
    AddressNode(value){    //value为数组
      this.rejForm.address =''+ value[0]+'/'+value[1]+'/'+value[2]
      console.log(this.form.address)
    },
    changPWD(){
      this.close=!this.close
      if(this.close)
        this.pwd='password'
      else
        this.pwd='text'
    },
    handleAdd() {
      this.isADD=true
      this.rejForm={}
      this.addVisible=true
    },
    onSubmit() {
      if(!this.isADD){
        axios.put("/api/users",this.form).then(
            res=>{
              this.editVisible=false
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
      }else{
        if(this.rejForm.type==='游客')
          this.rejForm.type=0
        else
          this.rejForm.type=1
        console.log(this.rejForm)
        axios.post("/api/users",this.rejForm).then(
            res => {
              this.addVisible=false
              if(res.data.flag){
                this.$message({
                  type: "success",
                  message: "添加成功"
                })
              }else {
                this.$message({
                  type: "error",
                  message: "添加失败"
                })
              }
              this.load()
            }
        )
      }
    },
    handleEdit(row) {
      this.isADD=false
      this.form=JSON.parse(JSON.stringify(row))
      this.editVisible=true
    },

    handleDelete(id) {
      console.log(id)
      axios.delete("/api/users/"+id).then(
          res => {
            if(res.data.flag){
              this.$message({
                type: "success",
                message: "用户已注销"
              })
            }else {
              this.$message({
                type: "error",
                message: "注销失败"
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
