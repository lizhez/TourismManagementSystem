<template>
  <div id="user">
    <div style="position: relative">
      <div class="img">
        <el-image :src="url" style="width: 100vw;height: 110px;"></el-image>
      </div>
      <div style="
          position: absolute;
          width: 77px;
          border-bottom: 2px solid #ffffff;
          left: 1197px;
          top:-34px;
          z-index: 10;"></div>
    </div>

    <div class="user">
      <el-card class="card">
        <div class="meg">
          <div>
            <el-avatar shape="square" :size="130"  :src="user.avator" style="margin: 10px 20px"></el-avatar>
            <el-button style="margin-left: 20px;" size="small" @click="changeImg">
              <el-icon style="vertical-align: middle;">
                <edit />
              </el-icon>
              <div style="margin-left: 2px;" @click="openAdd"> 更换头像 </div>
            </el-button>
            <el-popconfirm title="确定退出吗？" @confirm="delUser">
              <template #reference>
                <el-button  style="margin-left: 1px;" type="text" size="small">退出</el-button>
              </template>
            </el-popconfirm>
            <div style="margin: 10px 20px;font-weight:bold;font-size: large">{{ user.name }}</div>
          </div>
          <div>
            <el-menu
                default-active="1"
                class="el-menu-vertical-demo"
                :background-color="backgroundcolor"
                :text-color="textcolor"
                :active-text-color="activetextcolor"
            >
              <router-link to="/main/user/ref">
                <el-menu-item index="1">
                 个人信息
                </el-menu-item>
              </router-link>
              <router-link to="/main/user/mybuys" v-show="this.user.type===0">
                <el-menu-item index="2" >
                  <template #title >我的订单</template>
                </el-menu-item>
              </router-link>
              <router-link :to="'/main/user/comment/'+this.user.id" >
                <el-menu-item index="3">
                  <template #title>我的评论</template>
                </el-menu-item>
              </router-link>
              <router-link :to="'/main/user/public/'+this.user.id" v-show="this.user.type===0">
                <el-menu-item index="4">
                  <template #title>旅行日记</template>
                </el-menu-item>
              </router-link>
              <router-link to="/main/user/thebuys" v-show="this.user.type===1">
                <el-menu-item index="5">
                  <template #title>订单</template>
                </el-menu-item>
              </router-link>
              <router-link :to="'/main/user/load/'+this.user.id" v-show="this.user.type===1">
                <el-menu-item index="6">
                  <template #title>项目</template>
                </el-menu-item>
              </router-link>
            </el-menu>
          </div>
        </div>
        <div class="work">
          <el-card :body-style="{ padding: '20px 20px 0px 20px',minHeight:'580px'}">
            <router-view/>
          </el-card>
        </div>
      </el-card>
    </div>

    <el-dialog title="添加图片" v-model="addVisible" width="30%">
      <el-form ref="form" :model="addform" label-width="80px" class="demo-dynamic">
        <el-form-item label="图片地址">
          <el-input v-model="addform.avator" placeholder="请输入网页图片地址"></el-input>
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
import Header from "@/components/Header";
import Footer from "@/components/Footer";
import {Edit, Search} from "@element-plus/icons";
import axios from "axios";
import Reformation from "@/views/Reformation";
export default {
  name: 'User',
  components: {
    Reformation,
    Footer,
    Header,
    Search,
    Edit,
  },
  data() {
    return {
      url:'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fp3-tt.byteimg.com%2Forigin%2Fpgc-image%2F8c97a9d8189043ceb2af93ad2e3e7153%3Ffrom%3Dpc&refer=http%3A%2F%2Fp3-tt.byteimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661775680&t=1f7af39d5d9a6f7aa96c85afead392ad',
      backgroundcolor:'rgba(105,116,143,0)',
      textcolor:"#000",
      activetextcolor:'rgb(40,36,211)',

      addVisible:false,
      addform: {},

      user:{},

      a:false
    }
  },
  mounted(){
    this.loadUser()
  },
  methods:{
    loadUser(){
      // this.user=this.$store.state.user
      this.user=JSON.parse(sessionStorage.getItem("user"))
      console.log(this.user)
    },
    delUser(){
      window.sessionStorage.removeItem('user');
      this.$router.replace("/")
    },
    openAdd(){
      this.addform=JSON.parse(JSON.stringify(this.user))
      this.addform.avator=''
      this.addVisible=true
      console.log(this.addform)
    },
    addImg(){
      console.log(this.addform)
      axios.put("/api/users",this.addform).then(
          res=>{
            if(res.data.flag){
              this.$message({
                type: "success",
                message: "修改成功"
              })
              sessionStorage.setItem("user",JSON.stringify(this.addform))
            }else {
              this.$message({
                type: "error",
                message: "修改失败"
              })
            }
            this.addVisible=false
            this.loadUser()
          }
      )
    },
  }
}
</script>

<style scoped>
.img{
  position: absolute;
  z-index: -1;
  left: 0;
  top:-120px;
}

.user{
  width: 100vw;
  margin-top: 10px;
  margin-bottom: 10px;
  padding: 10px 110px;
}
.card{
  background-color: rgba(122, 149, 150, 0.3);
}
.meg{
  float: left;
  width: 260px;
  height: 600px;
}
.work{
  float: left;
  width: 874px;
  padding-bottom: 20px;
}

</style>