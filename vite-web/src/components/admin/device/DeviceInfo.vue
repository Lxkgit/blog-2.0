<template>
  <el-tabs v-model="activeTab" style="flex: auto" @tab-click="activeTabHandleClick">
    <el-tab-pane label="设备信息" name="deviceInfo">
      <div style="display: flex; height: 550px; overflow: auto;">
        <div style="flex: 1; margin-right: 10px;">
          <div>
            <el-card shadow="hover">
              <template #header>
                <span>CPU信息</span>
              </template>
              <div id="trend" :style="{ 'min-height': '300px' }"></div>
            </el-card>
          </div>
          <div style="margin-top: 10px; height: 200px;">
            磁盘空间
          </div>
          <div style="margin-top: 10px; background-color: aqua;">
            单片机数据
          </div>


        </div>
        <div style="flex: 1; margin-left: 10px;">
          <el-card shadow="hover">
            <template #header>
              <span>内存信息</span>
            </template>
            <div id="trend" :style="{ 'min-height': '300px' }"></div>
          </el-card>
        </div>
      </div>



    </el-tab-pane>
    <el-tab-pane label="单片机数据" name="chipData">单片机数据</el-tab-pane>
  </el-tabs>
</template>

<script setup lang="ts">
//@ts-nocheck
import { onMounted, reactive, ref, watch } from "vue";
import type { TabsPaneContext } from 'element-plus'
import { selectDeviceInfoByIdApi } from '@/api/file';
import * as echarts from 'echarts'
import dark from "@/utils/dark";

let {
  deviceInfoList,
  getDeviceInfoListFun
} = deviceFun();

let { isDark } = dark()
// 页面第一层tab标签
const activeTab = ref('deviceInfo')
// 传感器控制页面tab标签
const sensorControlActiveTab = ref('deviceInfo')

// 页面第一层tab标签点击事件
const activeTabHandleClick = (tab: TabsPaneContext, event: Event) => {
  console.log(tab, event)
}

const props = defineProps({
  deviceId: Number,
});

// 监听切换主题色事件
watch(() => isDark.value, (newVal) => {
  console.log("切换颜色了")
  console.log(newVal)
  setColor()
  trend()
  article()
  note()
  time()
}, { deep: true })


onMounted(() => {
  getDeviceInfoListFun(props.deviceId);
  console.log("dark", isDark.value)
  setColor()

  // statisticsData()
  trend()

  // note()
  // time()
})



// echarts明亮模式曲线颜色
const echartsLight = ref([
  "#008dd4",
  "#f1c40f",
  "#2ecc71",
  "#f2b3c9",
  "#16a085",
  "#e67e22",
  '#008dd0',
  '#c22931',
  '#8e44ad',
  '#157623',
])
// echarts暗黑模式曲线颜色
const echartsDark = ref([
  '#e1605e',
  '#3498db',
  '#658f95',
  '#e9937e',
  '#7cb9a0',
  '#f0734f',
  '#eed875',
  '#62996a',
  '#5db0b3',
  '#1abc9c',
])
// echarts背景色
const bgc = ref()
// echarts颜色
const color = ref()
// echarts文本颜色
const text = ref()
// 设置echarts主题色
const setColor = () => {
  console.log("判断是否是深色模式了")
  console.log(isDark.value)
  if (isDark.value === true) {
    // bgc.value = '#1d1e1f'
    color.value = echartsDark.value
    text.value = '#b2b2b2'
  } else {
    // bgc.value = '#ffffff'
    color.value = echartsLight.value
    text.value = '#2a2b2d'
  }
  console.log(bgc.value)
}

// 浏览趋势折线图
async function trend() {
  const query = { chart: 'trend', user: 1 }
  // const chartData = await getUserEcharts(query)
  const chartData: any[] = [{ article_view: 1, article_collect: 2, article_comment: 3, section_view: 4, section_collect: 5, section_comment: 6 },
  { article_view: 11, article_collect: 12, article_comment: 13, section_view: 14, section_collect: 15, section_comment: 16 },
  { article_view: 11, article_collect: 12, article_comment: 13, section_view: 14, section_collect: 15, section_comment: 16 }
  ]
  console.log("trend", chartData)
  const date = []
  const article_view = []
  const article_collect = []
  const article_comment = []
  const section_view = []
  const section_collect = []
  const section_comment = []
  for (let i in chartData) {
    // date.push(chartData[i].date.slice(5))
    article_view.push(chartData[i].article_view)
    article_collect.push(chartData[i].article_collect)
    article_comment.push(chartData[i].article_comment)
    section_view.push(chartData[i].section_view)
    section_collect.push(chartData[i].section_collect)
    section_comment.push(chartData[i].section_comment)
  }
  let myChart: any;
  if (isDark.value) {
    myChart = echarts.init(document.getElementById("trend") as HTMLElement, 'dark');
  } else {
    myChart = echarts.init(document.getElementById("trend") as HTMLElement);
  }
  // 绘制图表
  myChart.setOption({
    color: color.value,
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'cross',
        label: {
          backgroundColor: '#6a7985'
        }
      }
    },
    legend: {
      data: ['浏览文章数', '收藏文章数', '评论文章数', '浏览笔记数', '收藏笔记数', '评论笔记数'],
      textStyle: {
        color: text.value
      }
    },
    grid: {
      left: '3%',
      right: '5%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: [
      {
        type: 'category',
        boundaryGap: false,
        data: date
      }
    ],
    yAxis: [
      {
        type: 'value'
      }
    ],
    series: [
      {
        name: '浏览文章数',
        type: 'line',
        emphasis: {
          focus: 'series'
        },
        label: {
          show: true,
          position: 'bottom',
          textStyle: {
            fontSize: 20
          }
        },
        data: article_view
      },
      {
        name: '收藏文章数',
        type: 'line',
        emphasis: {
          focus: 'series'
        },
        data: article_collect
      },
      {
        name: '评论文章数',
        type: 'line',
        emphasis: {
          focus: 'series'
        },
        data: article_comment
      },
      {
        name: '浏览笔记数',
        type: 'line',
        emphasis: {
          focus: 'series'
        },
        data: section_view
      },
      {
        name: '收藏笔记数',
        type: 'line',
        emphasis: {
          focus: 'series'
        },
        data: section_comment
      },
      {
        name: '评论笔记数',
        type: 'line',
        emphasis: {
          focus: 'series'
        },
        data: section_collect
      }
    ],
    backgroundColor: bgc.value
  });
  //自适应大小
  window.onresize = function () {
    myChart.resize();
  };
}

function deviceFun() {

  // 设备详细信息列表
  let deviceInfoList: any = reactive({ data: [] });

  // 获取设备详细信息列表
  const getDeviceInfoListFun = (id: any) => {
    selectDeviceInfoByIdApi({ id: id }).then((res: any) => {
      if (res.code === 200) {
        deviceInfoList.data = res.result;
      }
    });
  };



  return {
    deviceInfoList,
    getDeviceInfoListFun
  };
}

</script>

<style scoped></style>