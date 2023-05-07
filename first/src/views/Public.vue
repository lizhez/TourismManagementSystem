<template>
  <div style="margin-bottom: 10px;">

    <div class="tag-group" style="margin-top: 10px">
      <span class="tag-group__title" style="font-size: small">状态</span>
      <el-tag
          v-for="item in statusItems"
          :key="item.label"
          :type="item.type"
          effect="plain"
          style="margin-left: 5px"
          @click="changeStatus(item)"
      >
        {{ item.label }}
      </el-tag>
    </div>

    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="输入关键字查询" style="width: 20%" size="mini" clearable></el-input>
      <el-button style="margin-left: 5px;" size="mini" @click="load">
        <el-icon style="vertical-align: middle;">
          <search />
        </el-icon>
        <span style="vertical-align: middle;"> 搜索 </span>
      </el-button>
      <router-link :to="'/main/user/edit/'+id">
        <el-button size="mini" style="float: right;">新的旅行日记</el-button>
      </router-link>
    </div>

    <div v-for="(item,index) in tableData" :key="index">
      <el-card shadow="hover" :body-style="{ padding: '15px' }" style="height: 180px;">
        <el-image style="width: 150px; height: 85px" :src="item.firstImg"></el-image>
        <div style=" font-size: 18px;">{{item.title}}</div>
        <div style="margin-top: 10px;">
          <i style="margin-right: 20px;" >
            <el-icon><calendar /></el-icon>
            {{item.time}}
          </i>
          <el-popconfirm title="确定删除此文章吗？" @confirm="delArt(item.id)">
            <template #reference>
              <el-button size="small" type="danger" style="float: right;">删除</el-button>
            </template>
          </el-popconfirm>
          <el-button  style="float: right;margin-right: 10px;" size="small" @click="to(item.id)" v-if="item.active!==-2">查看详情</el-button>
        </div>
      </el-card>
      <el-divider></el-divider>
    </div>

    <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[3,5,10]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          style="margin-top: 10px"
      >
      </el-pagination>
  </div>
</template>

<script>

import axios from 'axios'
import { Search } from '@element-plus/icons'
import { Top } from '@element-plus/icons'
import { Calendar } from '@element-plus/icons'

export default {
  name: 'Public',
  data(){
    return{
      tableData: [],
      pageNum:1,
      pageSize:3,
      total:5,
      search: '',

      statusItem:'',
      statusItems:[
        { type: 'success', label: '已发布' },
        { type: '', label: '待审核' },
        { type: '', label: '已拒绝' },
      ],
    }
  },
  components: {
    Search,
    Top,
    Calendar,
  },
  mounted(){
    this.load()
  },
  props:['id'],
  methods:{
    load(){
      axios.get("/api/articles/user",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          search: this.search,
          req: this.statusItem,
          id:this.id,
        }
      }).then(
          res=>{
            console.log("data",res.data.data.records);
            this.tableData=res.data.data.records;
            this.total=res.data.data.records.length
          })
    },

    scrollToTop () {
      this.$nextTick(() => {
        let ele = document.getElementById(this.id);
        console.log("ele: ",ele.scrollTop)
        ele.scrollTop = 0;
      })
    },
    to(id){
      this.scrollToTop()
      this.$router.push("/main/article/"+id)
    },

    changeStatus(item){
      for (const k in this.statusItems) {
        this.statusItems[k].type=''
      }
      item.type='success'
      if(item.label==='已发布')
        this.statusItem=''
      else if(item.label==='待审核')
        this.statusItem=0
      else if(item.label==='已拒绝')
        this.statusItem=-2
      this.load()
    },

    delArt(id){
      axios.put("/api/articles/"+id).then(
          res=>{
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

</style>