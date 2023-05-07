<template>
<div>
  <div style="margin:0 500px;font-size: 35px;line-height: 100px;color: #7e7e7e;font-family: 宋体;">网站数据</div>
  <div style="width: calc(100vw - 200px);height:calc(100vh - 150px);padding: 20px 60px;">
    <div class="data" id="type"></div>
    <div class="data" id="pay"></div>
    <div class="data" id="travel"></div>
  </div>
</div>
</template>

<script>
import * as echarts from 'echarts';
import axios from "axios";
export default {
  name: "Main",
  data(){
    return{
      option1:{
        title: {
          text: "网站用户类型比例",
        },
        xAxis: {
          data:['游客', '旅行社'],
        },
        legend: {
          left:'50%',
          data: ['男', '女', '未知']
        },
        yAxis: {},
        series: [
          {
            name:'男',
            data: [],
            type: 'line',
            areaStyle: {}
          },
          {
            name:'女',
            data: [],
            type: 'line',
            areaStyle: {
              opacity: 0.5
            }
          },
          {
            name:'未知',
            data: [],
            type: 'line',
            areaStyle: {
              opacity: 0.5
            }
          }
        ]
      },
      option2:{
        title: {
          text: '订单状态',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: '订单',
            type: 'pie',
            radius: '50%',
            data: [
              {
                value: '',
                name: '未支付'
              },
              {
                value: '',
                name: '已取消'
              },
              {
                value: '',
                name: '已支付'
              },
              {
                value: '',
                name: '已完成'
              }
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      },
      articleName:[],
      articleData: [],
        option3:{
        title: {
          text: "旅游项目热度 TOP",
          subtext: '在推项目浏览量',
        },
        tooltip: {},
        xAxis: {
          axisLabel: {
            interval:0,
            rotate:40
          },
          data: []
        },
        yAxis: {},
        series: [
          {
            name: "浏览量",
            type: "bar",
            data: [],
          },
        ],
      },
    }
  },
  mounted() {
    this.load()
  },
  methods:{
    load(){
      let myChart = echarts.init(document.getElementById('type'));
      document.getElementById('type').setAttribute('_echarts_instance_', '')
      myChart.setOption(this.option1)
      axios.get("/api/users/count/men").then(
          res=>{
            console.log("men: ",res.data.data)
            myChart.setOption({
              series:[
                {
                  name:'男',
                  data: res.data.data
                },
              ]
            })
          }
      )
      axios.get("/api/users/count/women").then(
          res=>{
            console.log("women: ",res.data.data)
            myChart.setOption({
              series:[
                {
                  name:'女',
                  data: res.data.data
                },
              ]
            })
          }
      )
      axios.get("/api/users/count/none").then(
          res=>{
            console.log("none: ",res.data.data)
            myChart.setOption({
              series:[
                {
                  name:'未知',
                  data: res.data.data
                }
              ]
            })
          }
      )
      let myChart1 = echarts.init(document.getElementById('pay'));
      document.getElementById('pay').setAttribute('_echarts_instance_', '')
      myChart1.setOption(this.option2)
      axios.get("/api/pays/count").then(
          res=>{
            console.log("pay: ",res.data.data)
            myChart1.setOption({
              series: [
                {
                  data: [
                    {
                      value: res.data.data[2],
                      name: '未支付'
                    },
                    {
                      value: res.data.data[1],
                      name: '已取消'
                    },
                    {
                      value: res.data.data[0],
                      name: '已支付'
                    },
                    {
                      value: res.data.data[3],
                      name: '已完成'
                    }
                  ],
                }
              ]
            })
          }
      )
      let myChart2 = echarts.init(document.getElementById('travel'));
      document.getElementById('travel').setAttribute('_echarts_instance_', '')
      myChart2.setOption(this.option3)
      axios.get("/api/articles/count").then(
          res=>{
            console.log("article: ",res.data.data.length)
            for (const k in res.data.data) {
              this.articleName[k]=res.data.data[k].title
            }
            for (const k in res.data.data) {
              this.articleData[k]=res.data.data[k].view
            }
            myChart2.setOption({
              xAxis: {
                data: this.articleName
              },
              series: [
                {
                  data: this.articleData
                },
              ],
            })
          }
      )
    },
  },
}
</script>

<style scoped>
.data{
  display:inline-block;
  width: calc((100vw - 350px)/3);
  height:100%;
  margin-right:10px;
  padding: 100px 5px;
}
</style>