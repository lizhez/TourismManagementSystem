<template>
  <div style="background:url('https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg');width: 100%;height: 100vh;">
    <transition-group
      appear
      name="animate__animated animate__bounce"
      enter-active-class="animate__flipInY"
      leave-active-class="animate__flipOutY"
    >
<!--      正常-->
      <div class="total" v-show="rejshow" key="1">

        <router-link to="/main/views">
          <div class="word">
            <span class="out">WELCOME TO ToFLY</span>
            <span class="in">WELCOME TO ToFLY</span>
          </div>
        </router-link>
        <div class="buttons">
          <el-button type="text" class="button" @click="loginshow = !loginshow">登录</el-button>
          /
          <el-button type="text" class="button" @click="rejOpen">注册</el-button>
        </div>

        <el-collapse-transition>
<!--          登录-->
          <div v-show="loginshow" class="login">
            <el-card :body-style="{ padding: '20px 20px 0px 20px' }">
              <el-form
                  :model="loginForm"
                  ref="loginForm"
                  label-width="100px"
              >
                <el-form-item
                    prop="name"
                    label="用户名"
                    :rules="[{ required: true, message: '请输入用户名', trigger: 'blur' },]">
                  <el-input v-model="loginForm.name" style="width: 380px"></el-input>
                </el-form-item>
                <el-form-item
                    prop="password"
                    label="密码"
                    :rules="[
                      { required: true, message: '请输入密码', trigger: 'blur' },
                      { type: 'string', min: 8,max: 15,message: '密码为8-15字符!', trigger: 'blur'}
                      ]">
                  <el-input v-model="loginForm.password" :type="pwd" style="width: 380px">
                  </el-input>
                  <img v-show="close"
                       style="width: 15px;height: 8px;padding-left: 2px"
                       @click="changPWD"
                       src="@/assets/img/close.png"/>
                  <img v-show="!close"
                       style="width: 20px;height: 15px;padding-left: 2px"
                       @click="changPWD"
                       src="@/assets/img/open.png"/>
                </el-form-item>
                <el-form-item >
                  <el-button type="primary" @click="login(loginForm.name,loginForm.password)">确定</el-button>
                  <el-button @click="loginshow = !loginshow" style="margin-left:260px">取消</el-button>
                </el-form-item>
              </el-form>
            </el-card>
          </div>
        </el-collapse-transition>
      </div>
<!--      注册-->
      <div v-show="!rejshow" class="rej" key="2">
        <el-card :body-style="{ padding: '20px 20px 0px 20px' }">
          <el-form
              :model="rejForm"
              status-icon
              :rules="rules"
              ref="rejForm"
              label-width="80px"
          >
            <el-form-item label="用户注册"></el-form-item>
            <el-form-item label="用户名" prop="name"
                :rules="{ required: true, message: '请输入用户名', trigger: 'blur' }">
              <el-input style="width: 430px" v-model="rejForm.name"></el-input>
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
                <el-radio label="商家"></el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="地址" prop="address">
              <el-cascader
                  v-model="rejForm.address"
                  :options="addressC.pcaOptions"
                  :props="addressC.addressProps"
                  @change="handleAddressNode"
                  :placeholder="rejForm.address"
                  filterable
              ></el-cascader>
            </el-form-item>
            <el-form-item label="密码" prop="pass">
              <el-input
                  :type="pwd"
                  v-model="rejForm.pass"
                  autocomplete="off"
                  style="width: 430px"
              ></el-input>
              <img v-show="close"
                   style="width: 15px;height: 8px;margin-left: 2px"
                   @click="changPWD"
                   src="@/assets/img/close.png"/>
              <img v-show="!close"
                   style="width: 20px;height: 15px;margin-left: 2px"
                   @click="changPWD"
                   src="@/assets/img/open.png"/>
            </el-form-item>
            <el-form-item label="确认密码" prop="checkPass">
              <el-input
                  type="password"
                  v-model="rejForm.checkPass"
                  autocomplete="off"
                  style="width: 430px"
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
              <el-input v-model="rejForm.phone" style="width: 430px"></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="email"
              :rules="[
                      { required: true, message: '请输入邮箱地址', trigger: 'blur' },
                      { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
                      ]">
              <el-input v-model="rejForm.email" style="width: 380px"></el-input>
          </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm()">确定</el-button>
              <el-button @click="rejshow = !rejshow" style="margin-left:309px">取消</el-button>
            </el-form-item>
          </el-form>

        </el-card>
      </div>
    </transition-group>
  </div>
</template>


<script>
import 'animate.css';
import pca from "@/assets/pca-code.json";
import axios from "axios";

export default {
  name: "Login",
  components:{
  },
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
      rejshow: true,
      loginshow: false,
      close:true,
      pwd:'password',
      loginForm: {},
      rejForm: {},
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
  methods:{
    login(name,password){
      if(!name){
        this.$message({
          type: "error",
          message: "用户名不能为空！"
        })
      }else if(!password){
        this.$message({
          type: "error",
          message: "密码不能为空！"
        })
      }else{
        axios.get('/api/users/'+name+"/"+password).then(
            res=>{
              console.log("12",res.data.data)
              if(res.data.flag){
                sessionStorage.setItem("user",JSON.stringify(res.data.data))
                if (res.data.data.type===1)
                  this.$router.replace("/main/index")
                else
                  this.$router.replace("/main/views")
              }else {
                this.$message({
                  type: "error",
                  message: "用户名或密码错误"
                })
              }
            }
        )
      }
    },

    rejOpen(){
      this.rejshow = false
      this.rejForm={}
    },
    handleAddressNode(value){    //value为数组
      this.rejForm.address = value[0]+'/'+value[1]+'/'+value[2]
      console.log(this.rejForm.address)
    },
    changPWD(){
      this.close=!this.close
      if(this.close)
        this.pwd='password'
      else
        this.pwd='text'
    },
    submitForm(){
      if(!this.rejForm.phone){
        this.$message({
          type: "error",
          message: "手机号不能为空！"
        })
      }else if(!this.rejForm.email){
        this.$message({
          type: "error",
          message: "邮箱不能为空！"
        })
      }else {
        if(this.rejForm.type==='游客')
          this.rejForm.type=0
        else
          this.rejForm.type=1
        console.log(this.rejForm)
        axios.post('/api/users',this.rejForm).then(
            res=>{
              console.log("12",res.data)
              if(res.data.flag){
                this.$message({
                  type: "success",
                  message: "注册成功"
                })
                this.rejshow=true
              }else {
                this.$message({
                  type: "error",
                  message: "用户名已存在"
                })
              }
            }
        )
      }
    }
  },
}
</script>

<style scoped>
.total{
  position: relative;
  width: 600px;
  left:calc( (100vw - 600px) / 2);
  top:calc( (100vh - 250px) / 2);
}
.rej{
  position: relative;
  width: 600px;
  left:calc( (100vw - 600px) / 2);
  top:calc( (100vh - 600px) / 2);
}
.word{
  position: relative;
}
.word:hover{
  left:-5px;
  top:-10px;
}

.out{
  position: absolute;
  left:10px;
  top:30px;
  font-size: 60px;
  color: rgba(255, 255, 255);
  text-decoration-line: underline;
}
.in{
  position: absolute;
  left:18px;
  top:35px;
  font-size: 60px;
  color: rgba(255, 255, 255, 0.58);

}

.buttons{
  position: absolute;
  left:calc((600px - 140px) / 2);
  top:110px;
  color: #ffffff;
}
.button{
  font-size: 18px;
  color: #ffffff;
  margin-right: 18px;
}
.button:hover{
  color: rgba(221, 225, 234, 0.88);
}

.login{
  position: absolute;
  left:10px;
  top:150px;
  width: 580px;
}
</style>