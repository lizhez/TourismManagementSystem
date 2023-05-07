<template>
  <div class="myHeader">
    <div class="logo">
<!--      <img src="@/assets/logo.png" style="width: 30px;height: 30px;float: left;margin-top: 10px">-->
      <h1 style="display: block;float:left;width: 100px;">ToFLY</h1>
      <img src="@/assets/logo.png" style="width: 30px;height: 30px;float: left;margin-top: 10px">
    </div>

  </div>
</template>

<script>

import { Avatar } from '@element-plus/icons'

export default {

  name: "Header",
  data(){
     return{
       information:true
     }
  },
  components:{
    Avatar,
  }
}

</script>

<style scoped>
  .myHeader{
    height: 50px;
    line-height: 50px;
    border-bottom: 1px solid #ccc;
    display: flex;
  }
  .logo{
    width: 200px;
    margin-left: 30px;
    color: #626262;
  }
  .headerCenter{
    flex: 1;
  }
  .identify{
    width: 100px;
    margin-top: 20px;
  }
  .el-dropdown-link {
    cursor: pointer;
    color: #409eff;
  }
  .el-icon-arrow-down {
    font-size: 12px;
  }
</style>