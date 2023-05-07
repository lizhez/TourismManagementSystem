<template>
  <div>
    <el-form
        status-icon
        ref="rejForm"
        v-model="rejForm"
        label-width="100px"
        size="large"
      >
        <el-form-item label="用户信息">
          <el-button style="margin-left: 10px;" size="small">
            <el-icon style="vertical-align: middle;">
              <edit />
            </el-icon>
            <span style="vertical-align: middle;" @click="edit(rejForm)"> 点击修改 </span>
          </el-button>
        </el-form-item>
        <el-form-item label="用户名" prop="name" >
          <span>{{rejForm.name}}</span>
        </el-form-item>
        <el-form-item label="等级" prop="level" >
          <span>{{rejForm.level}}</span>
        </el-form-item>
        <el-form-item label="性别" prop="sex" >
          <span>{{rejForm.sex}}</span>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <span>{{rejForm.address}}</span>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <span>{{rejForm.phone}}</span>
        </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <span>{{rejForm.email}}</span>
      </el-form-item>
      <el-form-item label="账号信息"></el-form-item>
      <el-form-item label="密码" prop="pass">
        <el-button style="margin-left: 10px;" size="small" @click="changePass">
          <span style="vertical-align: middle;"> 修改密码 </span>
        </el-button>
      </el-form-item>
    </el-form>

    <el-dialog title="用户信息" v-model="dialogVisible" width="35%">
      <el-form ref="form" :model="form" label-width="80px" class="demo-dynamic">
        <el-form-item label="ID">
          <el-input v-model="form.id" disabled style="width: 90%" ></el-input>
        </el-form-item>
        <el-form-item label="用户名" prop="name"
                      :rules="{ required: true, message: '请输入用户名', trigger: 'blur' }">
          <el-input style="width: 90%" v-model="form.name" ></el-input>
        </el-form-item>
        <el-form-item label="性别">
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
<!--          <el-button v-show="!send" size="small" style="margin-left: 10px" @click="sendMes">发送</el-button>
          <el-button v-show="send" size="small" style="margin-left: 10px" disabled>重新发送</el-button>-->
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
          <el-button @click="dialogVisible=false" size="mini">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog title="修改密码" v-model="passVisible" width="35%">
      <el-form
          ref="passForm"
          :rules="rules"
          :model="passForm"
          label-width="80px">
        <el-form-item label="原密码" prop="oldpass">
          <el-input
              :type="opwd"
              v-model="passForm.oldpass"
              autocomplete="off"
              style="width: 90%"
          ></el-input>
          <img v-show="oclose"
               style="width: 15px;height: 8px;margin-left: 5px"
               @click="changPWDo"
               src="@/assets/img/close.png"/>
          <img v-show="!oclose"
               style="width: 20px;height: 15px;margin-left: 5px"
               @click="changPWDo"
               src="@/assets/img/open.png"/>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input
              :type="npwd"
              v-model="passForm.password"
              autocomplete="off"
              style="width: 90%"
          ></el-input>
          <img v-show="nclose"
               style="width: 15px;height: 8px;margin-left: 5px"
               @click="changPWDn"
               src="@/assets/img/close.png"/>
          <img v-show="!nclose"
               style="width: 20px;height: 15px;margin-left: 5px"
               @click="changPWDn"
               src="@/assets/img/open.png"/>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input
              :type="apwd"
              v-model="passForm.checkPass"
              autocomplete="off"
              style="width: 90%"
          ></el-input>
          <img v-show="aclose"
               style="width: 15px;height: 8px;margin-left: 5px"
               @click="changPWDa"
               src="@/assets/img/close.png"/>
          <img v-show="!aclose"
               style="width: 20px;height: 15px;margin-left: 5px"
               @click="changPWDa"
               src="@/assets/img/open.png"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitPass" size="mini" style="margin-left: 10px;">确定</el-button>
          <el-button @click="passVisible=false" size="mini">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>

<script>
import 'animate.css';
import pca from "@/assets/pca-code.json";
import {Edit} from "@element-plus/icons";
import axios from "axios";

export default {
  name: "Reformation",
  components:{
    Edit,
  },
  data(){

    var validatePass0 = (rule, value, callback) => {
      if(value === ''){
        callback(new Error('请输入原密码!'))
      }else {
        if(value !== this.rejForm.password){
          callback(new Error('原密码错误!'))
        }
      }
    }
    var validatePass = (rule, value, callback) => {
      console.log("value",value)
      if(value === ''){
        callback(new Error('请输入密码!'))
      }else{
        if(value.length<8 || value.length>15){
          callback(new Error('密码为 8-15 为字符!'))
        } else {
          if (this.passForm.checkPass !== '') {
            this.$refs.passForm.validateField('checkPass')
          }
          callback()
        }
      }
    }
    var validatePass2 = (rule, value, callback) => {
      if(value === ''){
        callback(new Error('请再次输入密码!'))
      }else if (value !== this.passForm.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }

    return{
      dialogVisible:false,
      form: {},
      addChange:true,

      passVisible:false,
      oclose:true,
      nclose:true,
      aclose:true,
      opwd:'password',
      npwd:'password',
      apwd:'password',
      passForm:{
        oldpass:'',
        password: '',
        checkPass: '',
      },
      rules: {
        oldpass: [{validator: validatePass0, trigger: 'blur' }],
        password: [{validator: validatePass, trigger: 'blur' }],
        checkPass: [{validator: validatePass2, trigger: 'blur' }]
      },

      emailVisible:false,
      send:false,
      checkNode0:'',
      checkNode1:'',
      emailForm:{
        oldEmail: '',
      },

      rejForm: {},

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
  mounted(){
    this.load()
  },
  methods:{
    load(){
      this.rejForm=JSON.parse(sessionStorage.getItem("user"))
    },
    changeAd(e){
      console.log("rej",this.rejForm)
      this.addChange=true
    },
    edit(forms) {
      this.form=JSON.parse(JSON.stringify(forms))
      this.dialogVisible=true
    },
    onSubmit() {
      axios.put("/api/users",this.form).then(
          res=>{
            this.dialogVisible=false
            if(res.data.flag){
              sessionStorage.setItem("user",JSON.stringify(this.form))
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
    handleAddressNode(value){    //value为数组
      this.form.address = value[0]+'/'+value[1]+'/'+value[2]
      console.log(this.form.address)
    },

    changePass(){
      this.passVisible=true
      this.passForm={oldpass:'', password: '', checkPass: '',}
    },
    submitPass(){
      axios.put("/api/users/"+this.rejForm.id+"/"+this.passForm.password).then(
          res=>{
            this.dialogVisible=false
            if(res.data.flag){
              this.$message({
                type: "success",
                message: "更新成功"
              })
              this.rejForm.password=this.passForm.password
              sessionStorage.setItem("user",JSON.stringify(this.rejForm))
            }else {
              this.$message({
                type: "error",
                message: "更新失败"
              })
            }
            this.passVisible=false
            this.load()
          }
      )
    },
    changPWDo(){
      this.oclose=!this.oclose
      if(this.oclose)
        this.opwd='password'
      else
        this.opwd='text'
    },
    changPWDn(){
      this.nclose=!this.nclose
      if(this.nclose)
        this.npwd='password'
      else
        this.npwd='text'
    },
    changPWDa(){
      this.aclose=!this.aclose
      if(this.aclose)
        this.apwd='password'
      else
        this.apwd='text'
    },

  },
}
</script>

<style scoped>

</style>