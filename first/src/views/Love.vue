<template>
  <div style="margin-bottom: 10px;">
    <div class="tag-group" style="border-bottom: 1px solid #ccc;padding-bottom: 10px">
      <span class="tag-group__title" style="font-size: small">类型</span>
      <el-tag
          v-for="item in typeItems"
          :key="item.label"
          :type="item.type"
          effect="plain"
          style="margin-left: 5px"
          @click="changeType(item)">
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
    </div>

    <el-table :data="tableData" style="width: 1150px">
        <el-table-column class="ti" label="收藏列表">
          <template  #default="scope">
            <el-card>
              <div >
                <el-image
                    style="width: 150px; height: 85px"
                    :src="tableData.firstImg"
                >
                </el-image>
                <div style=" font-size: 18px;">
                  {{tableData.name}}
                </div>
                <div style="margin-top: 10px;">
                  <i style="margin-right: 20px;" >
                    <el-icon><calendar /></el-icon>
                    {{tableData.time}}
                  </i>
                  <router-link :to="'/main/'+this.linkTo+'/'+scope.row.id">
                    <el-button  style="float: right; "  size="small">
                      <span>查看详情</span>
                    </el-button>
                  </router-link>
                </div>
              </div>
            </el-card>
          </template>

        </el-table-column>
      </el-table>
    <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5,10]"
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
  name: 'Love',
  data(){
    return{
      tableData: [],
      pageNum:1,
      pageSize:3,
      total:5,
      search: '',
      typeItem:'travels',
      linkTo:'item',
      typeItems:[
        { type: 'success', label: '旅行推荐' },
        { type: '', label: '达人点评' },
      ],
    }
  },
  components: {
    Search,
    Top,
    Calendar,
  },
  mounted(){
    //this.getAll();
    this.load()
  },
  methods:{
    load(){
      axios.get("/api/"+this.typeItem,{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          search: this.search,
        }
      }).then(
          res=>{
            console.log("data",res.data.data.records);
            this.tableData=res.data.data.records;
            this.total=res.data.data.records.length
          })
    },
    changeType(item){
      for (const k in this.typeItems) {
        this.typeItems[k].type=''
      }
      item.type='success'
      console.log(item.label)
      if(item.label==='旅行推荐') {
        this.typeItem = 'travels'
        this.linkTo = 'item'
      }
      else {
        this.typeItem = 'articles'
        this.linkTo = 'article'
      }
      console.log(this.typeItem)
      console.log(this.linkTo)
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