<template>
  <el-card style="margin: 18px 2%;width: 95%">
    <el-table :data="sensorDataList.data" stripe style="width: 100%; height: calc(100vh - 408px);">
      <el-table-column prop="sensorData" label="监测数据" fit>
      </el-table-column>
      <el-table-column prop="createTime" label="监测时间" fit>
      </el-table-column>
    </el-table>
    <div style="margin: 20px 0 50px 0">
      <el-pagination background style="float:right;" layout="total, prev, pager, next, jumper" @current-change="selectSensorDataPageFun" :page-size="size" :total="total">
      </el-pagination>
    </div>
  </el-card>
</template>
  
<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import { selectSensorDataListApi } from '@/api/file';
let {
  size,
  total,
  sensorDataList,
  selectSensorDataPageFun,
  selectSensorDataByIdFun,
} = sensorFun();

const emit = defineEmits(['sersor']);

const props = defineProps({
  sensor: Object,
});

onMounted(() => {
  selectSensorDataByIdFun(props.sensor?.id, 1);
});

function sensorFun() {
  // 页面展示传感器数据条数
  let size = ref<number>(12);
  // 总数据数
  let total = ref<number>(0);
  // 传感器数据列表
  let sensorDataList: any = reactive({ data: [] });

  // 获取传感器数据列表
  const selectSensorDataPageFun = (page: any) => {
    selectSensorDataByIdFun(props.sensor?.id, page);
  };

  // 获取传感器数据列表
  const selectSensorDataByIdFun = (id: any, page: any) => {
    selectSensorDataListApi({
      pageNum: page,
      pageSize: size.value,
      sensorId: id,
    }).then((res: any) => {
      if (res.code === 200) {
        total.value = res.result.total;
        sensorDataList.data = res.result.list;
      }
    });
  };

  return {
    size,
    total,
    sensorDataList,
    selectSensorDataPageFun,
    selectSensorDataByIdFun,
  };
}
</script>
  
<style scoped>
.title_style {
  display: flex;
  justify-content: flex-start;
  align-items: baseline;
  max-height: 31px;
  color: #445160;
  font-size: 24px;
  font-weight: 600;
  text-align: left;
}
</style>