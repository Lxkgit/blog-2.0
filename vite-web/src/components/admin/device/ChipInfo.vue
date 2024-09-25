<template>
  <el-tabs v-model="activeTab" style="flex: auto" @tab-click="activeTabHandleClick">
    <el-tab-pane label="单片机数据" name="chipData">单片机数据</el-tab-pane>
    <el-tab-pane label="传感器数据统计" name="sensorData">传感器数据统计</el-tab-pane>
    <el-tab-pane label="批量控制传感器" name="sensorControl">
      <div style="width: 95%;">
        <el-tabs v-model="sensorControlActiveTab">
          <el-tab-pane label="传感器控制命令" name="sensorControl">
            <el-button @click="dialogFormVisible = true">创建</el-button>
            <el-button>删除</el-button>
            <el-table :data="tableData" @selection-change="">
              <el-table-column type="selection" width="55"> </el-table-column>
              <el-table-column prop="date" label="Date" width="180" />
              <el-table-column prop="name" label="Name" width="180" />
              <el-table-column prop="address" label="Address" />
              <el-table-column fixed="right" label="操作" width="110">
                <template #default="scope">
                  <el-button style="margin: 0; padding: 8px;" @click="" size="small" text>
                    <MyIcon type="icon-send" title="发送命令" />
                  </el-button>
                  <el-button style="margin: 0; padding: 8px;" @click="" size="small" text>
                    <MyIcon type="icon-delete" title="删除命令" />
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
            <div style="margin: 20px 0 50px 0">
              <el-pagination background style="float:right;" layout="total, prev, pager, next, jumper"
                @current-change="" :page-size="10" :total="100">
              </el-pagination>
            </div>

            <el-dialog v-model="dialogFormVisible" title="创建命令组" width="1000" style="height: 700px;">
              <el-form :model="sensorControlForm">

                <div>
                  <el-form-item prop="controlName" label="指令名称" :label-width="100">
                    <el-input v-model="sensorControlForm.name" autocomplete="off" />
                  </el-form-item>

                  <div style="height: 400px; overflow: auto;">

                    <div v-for="(id, idx) in length">
                      <el-divider />
                      <div style="display: flex;">
                        <div style="flex: 1;">
                          <el-form-item label="传感器" :label-width="formLabelWidth">
                            <el-select v-model="sensorControlForm.sensor[idx].type" placeholder="选择传感器"
                              @change="selectSensor(idx)">
                              <el-option v-for="(item, sidx) in sensorList.data" :key="idx" :label="item.label"
                                :value="item.value" />
                            </el-select>
                          </el-form-item>
                        </div>
                        <div style="flex: 1; margin-left: 20px;">

                          <template v-if="sensorControlForm.sensor[idx].type === 'DUO-180'" :key="idx">
                            <el-form-item v-if="sensorControlForm.sensor[idx].from.type === 'input-number'"
                              :label="sensorControlForm.sensor[idx].from.label"
                              :prop="sensorControlForm.sensor[idx].from.key">
                              <el-input-number v-model="sensorControlForm.sensor[idx].from.value"
                                :min="sensorControlForm.sensor[idx].from.min"
                                :max="sensorControlForm.sensor[idx].from.max" />
                            </el-form-item>
                          </template> 

                          <template v-if="sensorControlForm.sensor[idx].type === 'DUO-360'" :key="idx">
                            <el-form-item v-if="sensorControlForm.sensor[idx].from.type === 'input-number'"
                              :label="sensorControlForm.sensor[idx].from.label"
                              :prop="sensorControlForm.sensor[idx].from.key">
                              <el-input-number v-model="sensorControlForm.sensor[idx].from.value"
                                :min="sensorControlForm.sensor[idx].from.min"
                                :max="sensorControlForm.sensor[idx].from.max" />
                            </el-form-item>
                          </template>
                        </div>
                      </div>
                    </div>


                  </div>

                  <span style="margin-left: 50px; cursor: pointer;" @click="addSensor">增加传感器</span>
                  <span style="display: float; float: right; margin-right: 50px; cursor: pointer;" @click="deleteSensor">删除传感器</span>


                </div>
              </el-form>
              <template #footer>
                <div class="dialog-footer">
                  <el-button @click="dialogFormVisible = false">取消</el-button>
                  <el-button type="primary" @click="dialogFormVisible = false">
                    确认
                  </el-button>
                </div>
              </template>
            </el-dialog>

          </el-tab-pane>
          <el-tab-pane label="传感器控制历史" name="sensorControlHistory">
            <el-table :data="tableData">
              <el-table-column prop="date" label="Date" width="180" />
              <el-table-column prop="name" label="Name" width="180" />
              <el-table-column prop="address" label="Address" />
              <el-table-column fixed="right" label="操作" width="110">
                <template #default="scope">
                  <el-button style="margin: 0; padding: 8px;" @click="" size="small" text>
                    <MyIcon type="icon-send" title="发送命令" />
                  </el-button>
                  <el-button style="margin: 0; padding: 8px;" @click="" size="small" text>
                    <MyIcon type="icon-delete" title="删除命令" />
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
            <div style="margin: 20px 0 50px 0">
              <el-pagination background style="float:right;" layout="total, prev, pager, next, jumper"
                @current-change="" :page-size="10" :total="100">
              </el-pagination>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
    </el-tab-pane>
  </el-tabs>


</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import type { TabsPaneContext } from 'element-plus'
import icon from '@/utils/icon';
import { ElMessage } from 'element-plus';

let {
  length,
  sensorControlForm,
  sensorList,
  selectSensor,
  addSensor,
  deleteSensor
} = sensorControlFun();

let { MyIcon } = icon();



function sensorControlFun() {

  // 传感器命令组中传感器数量
  let length = ref(1);
  // 传感器控制表单
  const sensorControlForm = reactive({
    name: '',
    sensor: [
      {
        type: '',
        idx: 0,
        from: {} as any
      }
    ],
  })

  // 传感器列表
  let sensorList: any = reactive({
    data: [
      {
        label: "舵机-180",
        value: "DUO-180"
      },
      {
        label: "舵机-360",
        value: "DUO-360"
      }
    ]
  });

  // 舵机表单参数格式
  const duoFrom: any = [
    {
      label: '舵机旋转角度180',
      type: 'input-number',
      min: 0,
      max: 180,
      key: 'data',
      value: 0,
    },
    {
      label: '舵机旋转角度360',
      type: 'input-number',
      min: 0,
      max: 360,
      key: 'data',
      value: 0,
    }
  ];


  // 选择要控制的传感器
  const selectSensor = (idx: any) => {

    sensorControlForm.sensor[idx].idx = idx;
    if (sensorControlForm.sensor[idx].type === 'DUO-180') {
      sensorControlForm.sensor[idx].from = JSON.parse(JSON.stringify(duoFrom[0]));
    } else if(sensorControlForm.sensor[idx].type === 'DUO-360') {
      sensorControlForm.sensor[idx].from = JSON.parse(JSON.stringify(duoFrom[1]));
    }
    

  };

  const addSensor = () => {
    //@ts-ignore 单行忽略
    sensorControlForm.sensor.push({});
    length.value++;
  }

  const deleteSensor = () => {
    if(length.value == 1) {
      ElMessage.error('传感器数量至少为 1')
    } else {
      sensorControlForm.sensor.pop();
      length.value--;
    }
  }




  return {
    length,
    sensorControlForm,
    sensorList,
    selectSensor,
    addSensor,
    deleteSensor
  }

}


// 页面第一层tab标签
const activeTab = ref('sensorControl')
// 传感器控制页面tab标签
const sensorControlActiveTab = ref('sensorControl')

// 页面第一层tab标签点击事件
const activeTabHandleClick = (tab: TabsPaneContext, event: Event) => {
  console.log(tab, event)
}

const dialogTableVisible = ref(false)
const dialogFormVisible = ref(false)
const formLabelWidth = '140px'

// 创建传感器命令表单
let formItems: any = reactive({ data: [] });



// 舵机表单参数格式
const duoFrom: any = [
  {
    label: '舵机旋转角度',
    type: 'input-number',
    min: 0,
    max: 180,
    key: 'data',
    value: 0,
  },
];



// 传感器表单赋值
const setFromItemsFun = (sensorCode: any) => {
  if (sensorCode === 'DUO-180') {
    formItems.data = duoFrom;
  }
};

const form = reactive({
  name: '',
  region: '',
  date1: '',
  date2: '',
  delivery: false,
  type: [],
  resource: '',
  desc: '',
})

const tableData = [
  {
    date: '2016-05-03',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-02',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-04',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-01',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-03',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-03',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-03',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
]

const props = defineProps({
  chipId: Number,
});

</script>

<style scoped></style>