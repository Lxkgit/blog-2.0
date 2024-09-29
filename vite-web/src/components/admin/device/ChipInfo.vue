<template>
  <el-tabs v-model="activeTab" style="flex: auto" @tab-click="activeTabHandleClick">
    <el-tab-pane label="单片机数据" name="chipData">单片机数据</el-tab-pane>
    <el-tab-pane label="传感器数据统计" name="sensorData">传感器数据统计</el-tab-pane>
    <el-tab-pane label="批量控制传感器" name="sensorControl">
      <div style="width: 95%;">
        <el-tabs v-model="sensorControlActiveTab">
          <el-tab-pane label="传感器控制命令" name="sensorControl">
            <el-button @click="dialogFormVisible = true">创建</el-button>
            <el-popover :visible="deleteCommandBtnPopoverByIds" placement="top" :width="160">
              <p>删除所选命令？</p>
              <div style="text-align: right; margin: 0">
                <el-button size="small" text @click="deleteCommandBtnPopoverByIds = false">取消</el-button>
                <el-button size="small" type="primary" @click="deleteSensorControlFun(0)">删除</el-button>
              </div>
              <template #reference>
                <el-button :disabled="commandIds.length > 0 ? false : true" type="danger" plain
                  @click="deleteCommandBtnPopoverByIds = true">删除</el-button>
              </template>
            </el-popover>
            <!-- 传感器控制命令表 -->
            <el-table :data="sensorControlList.data" @selection-change="checkCommandId">
              <el-table-column type="selection" width="55"> </el-table-column>
              <el-table-column prop="controlName" label="名称" width="180" />
              <el-table-column prop="name" label="控制传感器" width="360">
                <template #default="scope">
                  <el-tag :style="'color: ' + tagColor(item.id)" style="margin-right: 2px; margin-bottom: 2px"
                    v-for="item in scope.row.sensorList">
                    {{ item.sensorName }}
                  </el-tag>
                </template>
              </el-table-column>
              <!-- <el-table-column prop="commandGroup" label="命令组" /> -->
              <el-table-column prop="controlMessage" label="消息内容" />
              <el-table-column prop="createTime" label="创建时间" width="180" />
              <el-table-column prop="updateTime" label="最近修改时间" width="180" />
              <el-table-column fixed="right" label="操作" width="110">
                <template #default="scope">
                  <el-button style="margin: 0; padding: 8px;" @click="" size="small" text>
                    <MyIcon type="icon-send" title="发送命令" />
                  </el-button>
                  <el-button style="margin: 0; padding: 8px;" @click="deleteSensorControlFun(scope.row.id)" size="small"
                    text>
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
                          <el-form-item :label="'传感器 ' + id" :label-width="formLabelWidth">
                            <el-select v-model="sensorControlForm.sensor[idx].sensorData" placeholder="选择传感器"
                              @change="selectSensor(idx)" value-key="id">
                              <el-option v-for="(item, sidx) in sensorList.data" :key="idx" :label="item.sensorName"
                                :value="item" />
                            </el-select>
                          </el-form-item>
                        </div>
                        <div style="flex: 1; margin-left: 20px;">

                          <template v-if="idx !== 0">
                            <el-form-item label="命令执行前延时(ms)" :label-width="formLabelWidth">
                              <el-input-number v-model="sensorControlForm.sensor[idx].delay" :min="1" :max="10000" />
                            </el-form-item>
                          </template>


                          <div v-for="item in sensorControlForm.sensor[idx].from">
                            <!-- 传感器控制 数字输入模板 -->
                            <template v-if="item.type === 'input-number'" :key="idx">
                              <el-form-item :label="item.label" :label-width="formLabelWidth">
                                <el-input-number v-model="item.value" :min="item.min" :max="item.max" />
                              </el-form-item>
                            </template>
                          </div>

                        </div>
                      </div>
                    </div>


                  </div>

                  <span style="margin-left: 50px; cursor: pointer;" @click="addSensor">增加传感器</span>
                  <span style="display: float; float: right; margin-right: 50px; cursor: pointer;"
                    @click="deleteSensor">删除传感器</span>


                </div>
              </el-form>
              <template #footer>
                <div class="dialog-footer">
                  <el-button @click="dialogFormVisible = false">取消</el-button>
                  <el-button type="primary" @click="saveSensorControlFun">
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
import {
  selectSensorControlListApi,
  selectSensorListApi,
  saveSensorControlApi,
  deleteSensorControlApi,
  selectSensorTemplateByChipOrSensorIdApi
} from '@/api/file';

import color from "@/utils/color";



let {
  length,
  sensorControlForm,
  sensorList,
  sensorControlList,
  deleteCommandBtnPopoverByIds,
  commandIds,
  selectSensorListFun,
  selectSensor,
  addSensor,
  deleteSensor,
  selectSensorControlListFun,
  saveSensorControlFun,
  checkCommandId,
  deleteSensorControlFun,
  selectSensorTemplateByChipOrSensorIdFun
} = sensorControlFun();

let { MyIcon } = icon();
let { tagColor } = color();

onMounted(() => {
  selectSensorControlListFun();
  selectSensorListFun();
  selectSensorTemplateByChipOrSensorIdFun();
});



// 传感器控制方法
function sensorControlFun() {

  // 传感器命令组中传感器数量
  let length = ref(1);

  // 勾选命令id 用于批量删除
  let commandIds = reactive([]);

  // 多选删除命令Popover弹窗展示
  let deleteCommandBtnPopoverByIds = ref(false);

  // 传感器控制表单
  const sensorControlForm = reactive({
    name: '',
    sensor: [
      {
        id: 0,
        idx: 0,
        sensorData: {} as any,
        sensorType: '',
        sensorCode: '',
        delay: 0,
        from: [] as any
      }
    ],
  })

  // 传感器控制命令数据
  let sensorControlList: any = reactive({ data: [] });

  // 传感器列表
  let sensorList: any = reactive({ data: [] });

  // 查询单片机下全部传感器
  const selectSensorListFun = () => {
    selectSensorListApi({
      pageNum: 1,
      pageSize: 20,
      chipId: props.chipId,
      sensorControlType: 1
    }).then((res: any) => {
      if (res.code === 200) {
        sensorList.data = res.result.list;
      }
    });
  };

  // 舵机表单参数格式
  const duoFrom1: any = {
    sensorType: "DUO-180",
    from: [
      {
        label: '180度舵机',
        type: 'input-number',
        min: 0,
        max: 180,
        value: 0,
        columnKey: 'data',
        columnType: 'Integer'
      }
    ]
  };

  const sensorTemplateFrom: any = reactive({ data: [] });

  // 舵机表单参数格式
  const duoFrom2: any = {
    sensorType: "DUO-360",
    from: [
      {
        label: '360度舵机',
        type: 'input-number',
        min: 0,
        max: 360,
        value: 0,
        columnKey: 'data',
        columnType: 'Integer'
      }
    ]
  };

  // 选择要控制的传感器
  const selectSensor = (idx: any) => {

    sensorControlForm.sensor[idx].id = sensorControlForm.sensor[idx].sensorData.id;
    sensorControlForm.sensor[idx].sensorType = sensorControlForm.sensor[idx].sensorData.sensorType;
    sensorControlForm.sensor[idx].sensorCode = sensorControlForm.sensor[idx].sensorData.sensorCode;
    // idx 用于传感器排序
    sensorControlForm.sensor[idx].idx = idx;
    // 传感器执行前默认延时为 100ms
    sensorControlForm.sensor[idx].delay = idx === 0 ? 0 : 100;

    console.log(sensorTemplateFrom)

    for (let i = 0; i < sensorTemplateFrom.data.length; i++) {
      if (sensorControlForm.sensor[idx].sensorType === sensorTemplateFrom.data[i].sensorType) {
        sensorControlForm.sensor[idx].from = JSON.parse(sensorTemplateFrom.data[i].template);

        // console.log(sensorTemplateFrom.data[i].template)
      }
    }

    
    // if (sensorControlForm.sensor[idx].sensorType === 'DUO-180') {
    //   sensorControlForm.sensor[idx].from = JSON.parse(JSON.stringify(duoFrom1.from));
    // } else if (sensorControlForm.sensor[idx].sensorType === 'DUO-360') {
    //   sensorControlForm.sensor[idx].from = JSON.parse(JSON.stringify(duoFrom2.from));
    // }
  };

  // 表单新增一个传感器
  const addSensor = () => {
    if (length.value == 20) {
      ElMessage.error('传感器数量至少为 20')
      return;
    }
    //@ts-ignore 单行忽略
    sensorControlForm.sensor.push({});
    length.value++;
  }

  // 表单删除一个传感器
  const deleteSensor = () => {
    if (length.value == 1) {
      ElMessage.error('传感器数量至少为 1')
    } else {
      sensorControlForm.sensor.pop();
      length.value--;
    }
  }

  // 查询传感器控制命令
  const selectSensorControlListFun = () => {
    selectSensorControlListApi({
      pageNum: 1,
      pageSize: 10,
      chipId: props.chipId,
    }).then((res: any) => {
      if (res.code === 200) {
        sensorControlList.data = res.result.list;
      }
    });
  };

  // 创建传感器控制命令
  const saveSensorControlFun = () => {
    dialogFormVisible.value = false;

    console.log(sensorControlForm)
    // 传感器命令组保存
    saveSensorControlApi({
      chipId: props.chipId,
      commandGroup: 1,
      controlName: sensorControlForm.name,
      controlMessage: JSON.stringify(sensorControlForm.sensor)
    }).then((res: any) => {
      if (res.code === 200) {
        ElMessage.success('命令创建成功');
        selectSensorControlListFun();
      }
    })
    // let json: any = {};
    // for (let i = 0; i < formItems.data.length; i++) {
    //   json[formItems.data[i].key] = formItems.data[i].value;
    // }
    // saveSensorControlApi({
    //   sensorType: props.sensor?.sensorType,
    //   sensorId: props.sensor?.id,
    //   controlName: sensorControl.controlName,
    //   controlMessage: JSON.stringify(json),
    // }).then((res: any) => {
    //   if (res.code === 200) {
    //     ElMessage.success('命令创建成功');
    //     dialogFormVisible.value = false;
    //     page.value = 1;
    //     sensorControl.controlName = '';
    //     selectSensorControlPageFun(1);
    //   }
    // });
  };

  /**
 * 获取勾选文章id
 */
  const checkCommandId = (val: any[]) => {
    commandIds.splice(0, commandIds.length);
    for (let i = 0; i < val.length; i++) {
      //@ts-ignore 单行忽略
      commandIds.unshift(val[i].id);
    }
  };

  // 删除传感器控制命令
  const deleteSensorControlFun = (id: any) => {

    if (id === 0) {
      deleteCommandBtnPopoverByIds.value = false;
      if (commandIds.length !== 0) {
        deleteSensorControlApi({ ids: commandIds.join() }).then((res: any) => {
          if (res.code === 200) {
            ElMessage.success('命令删除成功');
            selectSensorControlListFun();
          }
        });
      }
    } else {

      deleteSensorControlApi({ ids: id }).then((res: any) => {
        if (res.code === 200) {
          ElMessage.success('命令删除成功');
          selectSensorControlListFun();
        }
      });
    }
  };

  const selectSensorTemplateByChipOrSensorIdFun = () => {
    selectSensorTemplateByChipOrSensorIdApi({ chipId: props.chipId }).then((res: any) => {
      if (res.code === 200) {
        sensorTemplateFrom.data = res.result;
      }
    })
  }


  return {
    length,
    sensorControlForm,
    sensorList,
    sensorControlList,
    deleteCommandBtnPopoverByIds,
    commandIds,
    selectSensorListFun,
    selectSensor,
    addSensor,
    deleteSensor,
    selectSensorControlListFun,
    saveSensorControlFun,
    checkCommandId,
    deleteSensorControlFun,
    selectSensorTemplateByChipOrSensorIdFun
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
    sensorType: '',
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