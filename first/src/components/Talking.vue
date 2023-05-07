<template>
<div>
  <el-card :body-style="{ padding: '0px' }" style="height: 510px;margin-top: -30px" shadow="never">

<!--    对话显示-->
    <div id="talkArea" style="height: 350px;background-color: rgba(245,245,245,0.76) ;overflow-x: hidden;" >
      <div v-for="(item,i) in talkData" :key=i>

        <!--      对方对话框-->
        <div v-if="item.fid === this.toid"
             style="padding-top: 1%;">
          <el-row>
            <div style="width: 100%">
              <div style="float: left;margin: 10px 10px 0 10px;">
                <el-avatar shape="square" :src="item.favator" :size="30" style="float: left"></el-avatar>
              </div>
              <div style="float:left;margin: 10px 10px 0 0;">
                <el-card :body-style="{padding: '0px 5px 0px 5px'}"
                         style="max-width:150px;min-height: 30px;"
                         shadow="never">
                  <p style="line-height: 30px">
                    {{item.content}}
                  </p>
                </el-card>
              </div>
            </div>
            <div>
              <p style="float: left; margin:5px 50px; color: #84b1ee; line-height: 6px; font-size: 6px">
                {{item.time}}
              </p>
            </div>

          </el-row>

        </div>
        <!--      自己对话框-->
        <div v-if="item.fid === this.thisid"
             style="padding-top: 1%;">
          <el-row>
            <div style="width: 100%">
              <div style="float: right;margin: 10px 10px 0 10px;">
                <el-avatar shape="square" :src="item.favator" :size="30" style="float: left"></el-avatar>
              </div>
              <div style="float:right;margin: 10px 0 0 10px;">
                <el-card :body-style="{padding: '0px 5px 0px 5px'}"
                         style="max-width:150px;min-height: 30px;background-color: rgba(160,177,203,0.95)"
                         shadow="never">
                  <p style="line-height: 30px">
                    {{item.content}}
                  </p>
                </el-card>
              </div>
            </div>
            <div style="width: 100%">
              <p style="float: right; margin:5px 50px; color: #84b1ee; line-height: 6px; font-size: 6px">
                {{item.time}}
              </p>
            </div>

          </el-row>
        </div>
      </div>
    </div>

<!--    分割线-->
    <div style="background-color: #DCDFE6;height: 1px;"></div>

<!--    书写框-->
    <div>
      <el-form  :model="talkFrom" :rules="rules" ref="talkFrom">
        <el-form-item prop="content" style="margin-bottom: 0">
          <div style="width: 98%;margin: auto;padding-top: 1%;">
            <el-input
                type="textarea"
                :rows="5"
                v-model="talkFrom.content">
            </el-input>
          </div>
        </el-form-item>
        <el-form-item>
          <el-col style="margin-left:85%;" >
            <el-button size="small" type="primary" plain @click="submitForm('talkFrom')">发送</el-button>
          </el-col>
        </el-form-item>
      </el-form>
    </div>
  </el-card>
</div>
</template>

<script>
import axios from "axios";

export default {
  name: "Talking",
  data(){
    return{
      isMe:'',
      talkData:[],
      talkFrom:{},
      rules:{
        content: { required: true, message: '请输入评论内容再提交', trigger: 'blur' },
      },
    }
  },
  props:['thisid','toid'],
  mounted() {
    this.talkFrom.fid=this.thisid
    this.talkFrom.toid=this.toid
    console.log('thisid', this.talkFrom.fid)
    console.log('toid', this.talkFrom.toid)
    this.load()
  },
  watch: {
    talkData() {
      this.timer()
    }
  },
  destroyed() {
    clearTimeout(this.timer())
  },
  methods:{
    timer() {
      return setTimeout(()=>{
        this.load()
      },1000)
    },
    load(){
      axios.get("/api/talks/"+this.thisid+"/"+this.toid).then(
          res=>{
            console.log(res.data.data)
            this.talkData = res.data.data
            console.log("this.talkData",this.talkData)
            this.scrollToBottom()
          }
      )
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log(this.talkFrom)
          axios.post('/api/talks',this.talkFrom).then(res=>{
            this.talkFrom.content = ''
            this.load()
          })
        } else {
          this.$message.error('请输入内容再提交！');
          return false;
        }
      });
    },
    // 滚动条定位到底部
    scrollToBottom () {
      this.$nextTick(() => {
        let ele = document.getElementById('talkArea');
        ele.scrollTop = ele.scrollHeight;
      })
    },
  },

}
</script>

<style scoped>

</style>