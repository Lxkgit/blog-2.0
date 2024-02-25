<template>
  <div>
    <div class="title_style">
      <span>服务器设备管理</span>
    </div>
    <el-card style="margin: 18px 2%; width: 95%; height: calc(100vh - 206px);">
      <div style="height: 30px;">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item style="cursor: pointer;" v-if="showType >= 1" @click="showType = 1">服务器设备</el-breadcrumb-item>
          <el-breadcrumb-item style="cursor: pointer;" v-if="showType >= 2" @click="showType = 2">单片机</el-breadcrumb-item>
          <el-breadcrumb-item style="cursor: pointer;" v-if="showType >= 3" @click="showType = 3">传感器</el-breadcrumb-item>
          <el-breadcrumb-item style="cursor: pointer;" v-if="showType == 4" @click="showType = 4">传感器数据</el-breadcrumb-item>
          <el-breadcrumb-item style="cursor: pointer;" v-if="showType == 5" @click="showType = 5">传感器控制</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <div style="display: flex; flex-flow: wrap; overflow-y: auto; align-items:flex-start; ">
        <Device v-if="showType === 1" @deviceId="receiveDeviceId" />
        <Chip v-else-if="showType === 2" :deviceId="deviceId" @chipId="receiveChipId" />
        <Sensor v-else-if="showType === 3" :chipId="chipId" @sersor="receiveSensor" />
        <SensorData v-else-if="showType === 4" :sensor="sensorData" />
        <SensorControl v-else-if="showType === 5" :sensor="sensorData" />
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, reactive, onBeforeUnmount } from 'vue';
import Device from '@/components/admin/device/Device.vue';
import Chip from '@/components/admin/device/Chip.vue';
import Sensor from '@/components/admin/device/Sensor.vue';
import SensorData from '@/components/admin/device/SensorData.vue';
import SensorControl from '@/components/admin/device/SensorControl.vue';

let showType = ref(1);
let deviceId = ref(0);
let chipId = ref(0);
let sensorData = ref({});

// 获取选中的设备id
const receiveDeviceId = (id: any) => {
  showType.value = 2;
  deviceId.value = id;
};

const receiveChipId = (id: any) => {
  showType.value = 3;
  chipId.value = id;
};

const receiveSensor = (sensor: any) => {
  if (sensor.sensorType.sensorType === 0) {
    showType.value = 4;
  } else if (sensor.sensorType.sensorType === 1) {
    showType.value = 5;
  }
  sensorData.value = sensor;
};
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
