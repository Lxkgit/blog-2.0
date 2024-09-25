<template>
  <span>777</span>
  <span>{{ deviceInfoList.data }}</span>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { selectDeviceInfoByIdApi } from '@/api/file';

let {
  deviceInfoList,
  getDeviceInfoListFun
} = deviceFun();

const props = defineProps({
  deviceId: Number,
});

onMounted(() => {
  getDeviceInfoListFun(props.deviceId);
});

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