<template>
  <el-card style="margin: 10px 2%; width: 94%;">
    <div style="margin: 18px 2%; display: flex;">
      <div style="width:8%; min-width: 90px">
        <el-button @click="dialogFormVisible = true">创建命令</el-button>
      </div>
      <div style="width:70%">
        <div>
          <span>传感器名称：</span> <span>{{ sensor.sensorName }}</span>
        </div>
        <div>
          <span>传感器编码：</span> <span>{{ sensor.sensorCode }}</span>
        </div>
        <div>
          <span>传感器状态：</span> <span><el-tag type="success">{{ sensor.sensorStatus }}</el-tag></span>
        </div>
        <div>
          <span>创建时间：</span> <span>{{ sensor.createTime }}</span>
        </div>
        <div>
          <span>修改时间：</span> <span>{{ sensor.updateTime }}</span>
        </div>
      </div>
      <div style="width: 15%">
        <img src="http://localhost/files/1/user/2024-02-08_17-00-49_47c7df_WeMos_D1.png" height="150" />
      </div>
    </div>
    <el-table :data="sensorControlList.data" stripe style="width: 100%; height: calc(100vh - 597px);">
      <el-table-column prop="controlName" label="控制命令名称" fit>
      </el-table-column>
      <el-table-column prop="controlMessage" label="控制命令" fit>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" fit>
      </el-table-column>
      <el-table-column prop="updateTime" label="最近修改时间" fit>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="110">
        <template #default="scope">
          <el-button style="margin: 0; padding: 8px;" @click="sendSensorControlFun(scope.row)" size="small" text >
            <MyIcon type="icon-send" title="发送命令"/>
          </el-button>
          <el-button style="margin: 0; padding: 8px;" @click="deleteSensorControlFun(scope.row.id)" size="small" text>
            <MyIcon type="icon-delete" title="删除命令"/>
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin: 20px 0 50px 0">
      <el-pagination background style="float:right;" layout="total, prev, pager, next, jumper" @current-change="selectSensorControlPageFun" :page-size="size" :total="total">
      </el-pagination>
    </div>
  </el-card>

  <el-dialog v-model="dialogFormVisible" title="创建传感器控制命令" width="500" :close-on-click-modal="false">
    <el-form :model="sensorControl">
      <el-form-item prop="controlName" label="指令名称" :label-width="100">
        <el-input v-model="sensorControl.controlName" autocomplete="off" />
      </el-form-item>
      <template v-for="(item, id) in formItems.data">
        <el-form-item v-if="item.type==='input-number'" :label="item.label" :prop="item.key" :key="id">
          <el-input-number v-model="item.value" :min="item.min" :max="item.max" />
        </el-form-item>
      </template>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="saveSensorControlFun()"> 保存 </el-button>
      </div>
    </template>
  </el-dialog>

</template>
  
<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import {
  selectSensorByIdApi,
  selectSensorControlListApi,
  sendSensorControlApi,
  saveSensorControlApi,
  deleteSensorControlApi
} from '@/api/file';
let {
  size,
  total,
  sensor,
  sensorControlList,
  dialogFormVisible,
  sensorControl,
  formItems,
  setFromItemsFun,
  selectSensorByIdFun,
  selectSensorControlPageFun,
  selectSensorControlListFun,
  sendSensorControlFun,
  saveSensorControlFun,
  deleteSensorControlFun,
} = sensorControlFun();
import icon from '@/utils/icon';
import { ElMessage } from 'element-plus';
// import { fa } from 'element-plus/es/locale';

let { MyIcon } = icon();

const props = defineProps({
  sensor: Object,
});

onMounted(() => {
  setFromItemsFun(props.sensor?.sensorCode);
  selectSensorByIdFun(props.sensor?.id);
  selectSensorControlListFun(props.sensor?.id, 1);
});

function sensorControlFun() {
  // 页面展示控制命令条数
  let size = ref<number>(7);

  // 分页数据总数
  let total = ref<number>(0);

  // 传感器数据
  let sensor: any = ref({});

  // 传感器控制命令列表
  let sensorControlList: any = reactive({ data: [] });

  // 新增命令dialog
  const dialogFormVisible = ref(false);

  // 新增设备信息
  const sensorControl = reactive({
    controlName: '',
  });

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
    if (sensorCode === 'DUO') {
      formItems.data = duoFrom;
    }
  };

  // 获取传感器信息
  const selectSensorByIdFun = (id: any) => {
    selectSensorByIdApi({ id: id }).then((res: any) => {
      if (res.code === 200) {
        sensor.value = res.result;
      }
    });
  };

  // 分页获取传感器控制命令
  const selectSensorControlPageFun = (page: any) => {
    selectSensorControlListFun(props.sensor?.id, page);
  };

  // 查询传感器控制命令
  const selectSensorControlListFun = (sensorId: any, page: any) => {
    selectSensorControlListApi({
      pageNum: page,
      pageSize: size.value,
      sensorId: sensorId,
    }).then((res: any) => {
      if (res.code === 200) {
        total.value = res.result.total;
        sensorControlList.data = res.result.list;
      }
    });
  };

  // 发送传感器控制命令
  const sendSensorControlFun = (sensorControl: any) => {
    sendSensorControlApi({ id: sensorControl.id }).then((res: any) => {
      if (res.code === 200) {
        ElMessage.success('命令发送成功');
      }
    });
  };

  // 创建传感器控制命令
  const saveSensorControlFun = () => {
    let json: any = {};
    for (let i = 0; i < formItems.data.length; i++) {
      json[formItems.data[i].key] = formItems.data[i].value;
    }
    saveSensorControlApi({
      sensorCode: props.sensor?.sensorCode,
      sensorId: props.sensor?.id,
      controlName: sensorControl.controlName,
      controlMessage: JSON.stringify(json),
    }).then((res: any) => {
      if (res.code === 200) {
        ElMessage.success('命令创建成功');
        dialogFormVisible.value = false;
        selectSensorControlPageFun(1);
      }
    });
  };

  // 删除传感器控制命令
  const deleteSensorControlFun = (id: any) => {
    deleteSensorControlApi({id: id}).then((res: any) => {
      if(res.code === 200) {
        ElMessage.success('命令删除成功');
        selectSensorControlPageFun(1);
      }
    })
  }

  return {
    size,
    total,
    sensor,
    sensorControlList,
    dialogFormVisible,
    sensorControl,
    formItems,
    setFromItemsFun,
    selectSensorByIdFun,
    selectSensorControlPageFun,
    selectSensorControlListFun,
    sendSensorControlFun,
    saveSensorControlFun,
    deleteSensorControlFun,
  };
}
</script>
  
<style scoped></style>
  