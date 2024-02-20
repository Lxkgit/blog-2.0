<template>
  <el-card style="margin: 18px 2%; width: 94%;">
    <div style="margin: 18px 2%; display: flex;">
      <div style="width:80%">
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
          <el-button style="margin: 0; padding: 8px;" @click="sendSensorControlFun(scope.row)" size="small" text>
            <MyIcon type="icon-eye" />
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin: 20px 0 50px 0">
      <el-pagination background style="float:right;" layout="total, prev, pager, next, jumper" @current-change="selectSensorControlPageFun" :page-size="size" :total="total">
      </el-pagination>
    </div>
  </el-card>
</template>
  
<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import {
  selectSensorByIdApi,
  selectSensorControlListApi,
  sendSensorControlApi,
} from '@/api/file';
let {
  size,
  total,
  sensor,
  sensorControlList,
  selectSensorByIdFun,
  selectSensorControlPageFun,
  selectSensorControlListFun,
  sendSensorControlFun,
} = sensorControlFun();
import icon from '@/utils/icon';
import { ElMessage } from 'element-plus';

let { MyIcon } = icon();

const props = defineProps({
  sensor: Object,
});

onMounted(() => {
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

  return {
    size,
    total,
    sensor,
    sensorControlList,
    selectSensorByIdFun,
    selectSensorControlPageFun,
    selectSensorControlListFun,
    sendSensorControlFun,
  };
}
</script>
  
<style scoped></style>
  