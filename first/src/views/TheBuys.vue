<template>
  <div>
    <div style="border-bottom: 1px solid #ccc;padding-bottom: 10px">
      <div class="tag-group" style="margin-top: 10px">
        <span class="tag-group__title" style="font-size: small">支付状态</span>
        <el-tag
              v-for="item in payItems"
              :key="item.label"
              :type="item.type"
              effect="plain"
              style="margin-left: 5px"
              @click="changePay(item)"
          >
            {{ item.label }}
          </el-tag>
      </div>
    </div>
    <div style="margin: 10px 0">
        <el-input v-model="search" placeholder="输入订单编号查询" style="width: 20%" size="mini" clearable></el-input>
        <el-button style="margin-left: 5px;" size="mini" @click="load">
          <el-icon style="vertical-align: middle;">
            <search />
          </el-icon>
          <span style="vertical-align: middle;"> 搜索 </span>
        </el-button>
      </div>

    <el-table :data="tableData" border stripe style="width: 1150px" size="mini"
                :header-cell-style="{'text-align':'center'}"
                :cell-style="{'text-align':'center'}">
        <el-table-column prop="payid" label="订单编号"> </el-table-column>
        <el-table-column prop="title" label="名称"> </el-table-column>
        <el-table-column prop="total" label="价格" sortable> </el-table-column>
        <el-table-column prop="status" label="支付状态">
          <template #default="scope">
            <el-tag v-if="scope.row.status===0" size="small" type="info">未支付</el-tag>
            <el-tag v-else-if="scope.row.status===1" size="small" >已支付</el-tag>
            <el-tag v-else size="small" type="success">已完成</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createtime" label="订单时间" width="200px" sortable> </el-table-column>
        <el-table-column prop="detail" label="详情" width="100px">
          <template #default="scope">
            <router-link :to="'/buyItem/'+scope.row.id">
              <el-button size="small" type="text">查看详情</el-button>
            </router-link>
          </template>
        </el-table-column>
      </el-table>
    <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5,8,10]"
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

export default {
  name: 'TheBuy',
  data(){
    return{
      tableData: [],
      pageNum:1,
      pageSize:8,
      total:'',
      search: '',
      payItem:'',
      payItems:[
        { type: 'success', label: '全部' },
        { type: '', label: '未支付' },
        { type: '', label: '已支付' },
        { type: '', label: '已完成' },
      ],
    }
  },
  components: {
    Search,
  },
  mounted(){
    this.load()
  },
  methods:{
    load(){
      axios.get("/api/pays/travelPage",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          search: this.search,
          req:this.payItem,
          id:JSON.parse(sessionStorage.getItem("user")).id
        }
      }).then(
          res=>{
            console.log(res.data.data.records);
            this.tableData=res.data.data.records;
            this.total=res.data.data.total
          })
    },

    changePay(item){
      for (const k in this.payItems) {
        this.payItems[k].type=''
      }
      item.type='success'
      if(item.label==='全部')
        this.payItem=''
      else if(item.label==='未支付')
        this.payItem=0
      else if(item.label==='已完成')
        this.payItem=-1
      else if(item.label==='已支付')
        this.payItem=1
      console.log(this.payItem)
      this.load()
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