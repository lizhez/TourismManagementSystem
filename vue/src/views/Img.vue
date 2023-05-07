<template>
  <div class="wrapper"
       style="padding: 10px 20px;
       height:calc(100vh - 50px);
       width:calc(100vw - 200px);
       overflow-x: hidden;">
    <el-backtop target=".wrapper"></el-backtop>

    <div style="border-bottom:1px solid #DCDFE6;padding-bottom: 10px">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>页面管理</el-breadcrumb-item>
        <el-breadcrumb-item>首页图片</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div style="padding-top: 10px;">
      <div>
        <h3 style="display:inline-block;color: #626262;padding-bottom: 10px">当前显示图片</h3>
        <el-button style="margin-left: 10px;margin-bottom: 5px;"  size="mini" @click="openAdd">添加图片</el-button>
      </div>
      <div style="border: #626262">
        <el-row #default="scope">
          <el-col
              :span="6"
              v-for="(img, index) in imgs"
              :key="index"
              style="width: 300px;
              padding: 0 10px 10px 0"
          >
            <el-card :body-style="{ padding: '0px'}">
              <img
                  :src="img.url"
                  style="width: 300px;height: 300px;"
              />
              <div style="padding: 14px;">
                <span>No.{{index+1}} </span>
                <div class="bottom">
                  <el-button @click="changeImg(img)">更换</el-button>
                  <el-popconfirm title="确定删除这张图片吗？" @confirm="delImg(img.id)">
                    <template #reference>
                      <el-button >删除</el-button>
                    </template>
                  </el-popconfirm>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </div>

    <el-dialog title="更换图片" v-model="dialogVisible" width="30%">
      <el-form ref="form" :model="form" label-width="80px" class="demo-dynamic">
        <el-form-item label="图片id">
          <el-input prop="iid" v-model="form.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="现有图片">
          <el-input v-model="form.url" disabled></el-input>
        </el-form-item>
        <el-form-item label="选择图片">
          <el-input v-model="chaImg" disabled></el-input>
        </el-form-item>
        <el-form-item>
          <el-row>
            <el-col
                :span="6"
                v-for="(cimg, index) in cImgs"
                shadow="hover"
                :key="index"
                style="width: 60px;padding: 0 2px 2px 0"
                @click="chaImg=cimg.url;chaId=cimg.id"
            >
              <el-card :body-style="{ padding: '0px'}">
                <img
                    :src="cimg.url"
                    class="image"
                    style="width: 60px;height: 50px;"
                />
              </el-card>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit()" size="mini">确定</el-button>
          <el-button @click="dialogVisible=false" size="mini">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog title="添加图片" v-model="addVisible" width="30%">
      <el-form ref="form" :model="addform" label-width="80px" class="demo-dynamic">
        <el-form-item label="图片地址">
          <el-input v-model="addform.url" placeholder="请输入网页图片地址"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="addImg" size="mini">确定</el-button>
          <el-button @click="addVisible=false" size="mini">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>

</template>

<script>

import axios from "axios";
export default {
  name: "Img",
  data() {
    return {
      imgs:[],
      cImgs:[],
      form: {
        url:'',
        id:''
      },
      addform: {},
      chaImg:'',
      chaId:'',
      dialogVisible:false,
      addVisible:false,
    }
  },
  mounted() {
    this.getUse();
  },
  methods:{
    getUse(){
      axios.get("/api/imgs").then(
          res => {
            console.log('img',res.data.data)
            this.imgs=res.data.data
          }
      )
    },
    getAll(){
      axios.get("/api/imgs/"+"all").then(
          res => {
            console.log(res.data.data)
            this.cImgs=res.data.data
          }
      )
    },

    changeImg(row){
      console.log(row)
      this.getAll();
      this.chaImg=''
      this.dialogVisible=true
      this.form=JSON.parse(JSON.stringify(row))
    },
    onSubmit(){
      axios.put("/api/imgs/"+this.form.id+"/"+this.chaId).then(
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
            console.log(res)
            this.getUse()
          }
      )
    },

    delImg(id){
      axios.put("/api/imgs/"+id).then(
          res=>{
            this.dialogVisible=false
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
            this.getUse()
          }
      )
    },

    openAdd(){
      this.addform={status:1,}
      this.addVisible=true
    },
    addImg(){
      axios.post("/api/imgs",this.addform).then(
          res=>{
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
            this.getUse()
          }
      )
    },
  },
}
</script>

<style scoped>
.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.image {
  width: 100%;
  display: block;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
</style>