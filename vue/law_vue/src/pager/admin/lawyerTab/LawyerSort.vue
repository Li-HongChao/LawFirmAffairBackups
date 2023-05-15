<template>
  <el-card style="margin: 2% auto 0 auto;width: 70%; height: 55%; padding: 5%;" shadow="always">
    <div style="width:700px;height:500px" ref="chart"></div>
  </el-card>
</template>

<script>
//局部引用
import axios from "axios";

const echarts = require('echarts');
export default{
  name: "LawyerSort",
  data(){
    return {
        nameList:[],
        sortList:[]
    }
  },
  methods: {
    initCharts(){
      let myChart = echarts.init(this.$refs.chart);
      let nameList = this.nameList
      let sortList = this.sortList
      // 绘制图表
      myChart.setOption({
        //标题
        title: { text: '预约排名' },
        //提示框
        tooltip: {},
        //x轴数据标记
        xAxis: {
          data:nameList
        },
        //y轴数据标记
        yAxis: {},
        //数据可视化
        series: [{
          name: '预约总量',
          type: 'bar',
          barWidth: 20,
          //设置柱状图渐变颜色
          itemStyle: {
            normal: {
              color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [{
                offset: 0,
                color: "#1268f3" // 0% 处的颜色
              }, {
                offset: 0.6,
                color: "#08a4fa" // 60% 处的颜色
              }, {
                offset: 1,
                color: "#01ccfe" // 100% 处的颜色
              }], false)
            }
          },
           data:sortList
        }],

        dataZoom:[
          {
            type: "slider", //隐藏或显示（true）组件
            show: true,
            backgroundColor: "rgb(222,222,222)", // 组件的背景颜色。
            fillerColor: "rgb(255,255,255)", // 选中范围的填充颜色。
            borderColor: "rgb(232,232,232)", // 边框颜色
            showDetail: false, //是否显示detail，即拖拽时候显示详细数值信息
            startValue: 0,
            endValue: 5,
            filterMode: "empty",
            width: "50%", //滚动条高度
            height: 8, //滚动条显示位置
            left: "center",
            zoomLoxk: true, // 是否锁定选择区域（或叫做数据窗口）的大小
            handleSize: 0, //控制手柄的尺寸
            bottom: 3, // dataZoom-slider组件离容器下侧的距离
          },
          {
            //没有下面这块的话，只能拖动滚动条，鼠标滚轮在区域内不能控制外部滚动条
            type: "inside",
            zoomOnMouseWheel: false, //滚轮是否触发缩放
            moveOnMouseMove: true, //鼠标滚轮触发滚动
            moveOnMouseWheel: true,
          },
        ],
      });
    },
    getAll(){
      axios.get("/order/sort").then(res=>{
        this.nameList=res.data.data.lawyer
        this.sortList=res.data.data.sort
        console.log(this.sortList)
        console.log(this.nameList)
        this.initCharts();
      })
    },
  },
  created () {
    this.getAll()
  }
}
</script>
<style>
</style>

