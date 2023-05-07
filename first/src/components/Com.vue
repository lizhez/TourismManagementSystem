<template >
  <div class="grid-content content bg-purple blog_bg ">

    <div  style="height:3px; margin: 5px 0; border:none; border-top:3px solid  #6b6e94;"></div>

    <el-form  :model="comment" :rules="rules" ref="comment" class="demo-ruleForm">
      <el-form-item prop="context">
        <div style="width: 95%;margin: auto;padding-top: 1%;">
          <el-input
              type="textarea"
              :rows="7"
              ref="ipt"
              :placeholder="placeholder"
              v-model="comment.context">
          </el-input>
        </div>
      </el-form-item>
      <el-form-item>
        <el-col :span="2" style="margin-left: 75%;">
          <el-button size="small" type="primary" plain @click="resetForm('comment')">清空</el-button>
        </el-col>
        <el-col :span="2">
          <el-button size="small" type="primary"  @click="submitForm('comment')">发表</el-button>
        </el-col>
      </el-form-item>
    </el-form>

    <!-- 分割线 -->
    <!-- <div style="background-color: #DCDFE6;height: 1px;margin: 5px 0;"></div> -->
    <div  style="height:3px; margin: 5px 0; border:none; border-top:3px solid  #6b6e94;"></div>

    <div style="width: 95%;margin: auto;padding-top: 1%;">
      评论
      <div style="background-color: #6b6e94;height: 1px;margin: 13px 0;"></div>
    </div>

    <!-- 评论区 -->

    <div v-for="(item1,index1) in commentData" :key=index1 style="padding-bottom: 20px">
      <!-- 父评论 -->
      <div style="width: 95%;margin: auto;padding-top: 1%;">
        <el-row :gutter="15">
          <el-col :span="1" style="margin-right: 10px;">
            <el-avatar shape="square" :src="item1.avator"></el-avatar>
          </el-col>
          <el-col :span="22">
            <div style=" width: 100%;">
              <p style="font-size: 15px;line-height: 15px;font-weight:bold;">{{item1.name}}
                <span style="font-size: 6px;font-weight: normal;">  {{item1.time}}</span>
              </p>
              <p style="font-size: 12px;color:#2d3c62;line-height: 35px; ">{{item1.context}}</p>
              <p style="font-size: 10px;line-height: 5px;" @click="reply(item1)">
                <el-link style="font-size: 5px;" :underline="false">回复</el-link>
              </p>
            </div>
          </el-col>

        </el-row>
      </div>

      <!-- 子评论 -->
      <div  v-for="(item2,index2) in item1.replyConments" :key=index2
            style="width: 88%;margin-left:80px;padding-top: 1%;">
        <el-row :gutter="15">
          <el-col :span="1" style="margin-right: 10px;">
              <el-avatar shape="square" :src="item2.avator"></el-avatar>
          </el-col>
          <el-col :span="22">
            <div style=" width: 100%;">
              <p style="font-size: 15px;line-height: 15px;font-weight:bold;">{{item2.name}}
                <span style="font-size: 12px;font-weight: normal;color: rgba(120,190,248,0.68);">  @{{item2.parentname}}</span>
                <span style="font-size: 6px;font-weight: normal;">  {{item2.time}}</span>
              </p>
              <p style="font-size: 12px;color:#2d3c62;line-height: 35px;  ">{{item2.context}}</p>
              <p style="font-size: 10px;line-height: 5px; " @click="reply(item2)"><el-link style="font-size: 5px;" :underline="false">回复</el-link></p>
            </div>
          </el-col>

        </el-row>
      </div>
    </div>



  </div>

</template>

<script>
import axios from "axios";

export default {
  name: 'Com',
  data () {
   return {
     textarea: '',//评论内容
     comment: {
       context: '',
       pid: -1,
       uid:'',
       belongid:'',
       active:1,
     },
     placeholder: '请输入评论内容',
     commentData:[],
     rules:{
         context: { required: true, message: '请输入评论内容再提交', trigger: 'blur' },
     },
     loginForm:{
       username:'',
       password:''
     },
     dialogFormVisible: false,
     showLogout: false,
     user: false,
   }
  },
  props:['blogId'],
  mounted() {
    this.name=JSON.parse(sessionStorage.getItem("user")).name
    this.comment.belongid = this.blogId
    this.comment.uid=JSON.parse(sessionStorage.getItem("user")).id
    this.initComment()
  },
  watch: {
    commentData() {
      this.timer()
    }
  },
  destroyed() {
    clearTimeout(this.timer())
  },
  methods:{
    timer() {
      return setTimeout(()=>{
        this.initComment()
      },1000)
    },
    //聚焦回复评论
    reply(item){
      // console.log(item)
      this.placeholder = '@'+ item.name + '  ' + item.context
      this.comment.pid = item.id
      this.$refs.ipt.focus()
    },
    //初始化评论数据
    initComment(){
      axios.get('/api/comments/' + this.blogId).then(
          res=>{
            console.log('com----------',res.data.data)
            this.commentData = res.data.data
      })
    },
    //发表评论
    submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            axios.post('/api/comments/add',this.comment).then(res=>{
              if(res.status === 200){
                this.initComment()
                this.$message({ message: '已发表评论！', type: 'success'  });
                this.placeholder = "请输入评论内容"
              }else{
                this.$message.error('请求错误！');
              }
              this.comment.context = ''
            })
          } else {
            this.$message.error('请输入内容再提交！');
            return false;
          }
        });
      },
      //重置评论
    resetForm(formName) {
        this.$refs[formName].resetFields();
        this.placeholder = "请输入评论内容"
      },


  }
  
}
</script>

<style scoped>
    .blog_bg{
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.25);
        width: 95%;
        margin-top: 5% ;
        margin-bottom: 5% ;
        padding-bottom: 5% ;
        min-height: 500px;
        background: #f2f4f6;
    }


</style>