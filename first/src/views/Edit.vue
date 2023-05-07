<template>
  <div>
    <div style="width: 100%;height: 50px;">
      <el-button style="float: right;" @click="goo">返回</el-button>
    </div>
    <div>
      <el-form ref="editForm" status-icon :model="editForm" :rules="rules1"  label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="editForm.title"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="meg">
          <el-input v-model="editForm.meg"></el-input>
        </el-form-item>
        <el-form-item label="首页图片" prop="firstImg">
          <el-input v-model="editForm.firstImg"></el-input>
        </el-form-item>
        <el-form-item label="内容" prop="context">
          <mavon-editor ref=md v-model="editForm.context" style="height: 345px"/>
        </el-form-item>
        <el-form-item style="margin-bottom: 20px">
          <el-button type="primary" @click="toSubmit('editForm')">发布文章</el-button>
          <el-button type="error" @click="editForm={}">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>

</template>
<script>
  import axios from "axios";

  export default {
    name: "Editor",
    data() {
      return {
        editForm: {},
        rules1: {  //表单校验
          title: [
            {required: true, message: '请输入标题', trigger: 'blur'},
            {min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur'}
          ],
          meg: [
            {required: true, message: '请输入简介', trigger: 'blur'}
          ],
          firstImg: [
            {required: true, message: '请输入图片地址', trigger: 'blur'}
          ],
          context: [
            {required: true, message: '请输入文章内容', trigger: 'blur'}
          ],
        },
      }
    },
    props:['id'],
    methods: {
      goo(){
        this.$router.go(-1);
      },
      toSubmit(formName) {
        this.$refs[formName].validate(
            valid => {
              if (valid) {
                this.editForm.uid=this.id
                axios.post("/api/articles",this.editForm).then(
                    res=>{
                      if(res.data.flag){
                        this.$message({
                          type: "success",
                          message: "发布成功"
                        })
                        this.editForm={}
                      }else {
                        this.$message({
                          type: "error",
                          message: "发布失败"
                        })
                      }
                    })
              } else {
                return false;
              }
            })
      },
    }
  }
</script>

<style>
</style>
