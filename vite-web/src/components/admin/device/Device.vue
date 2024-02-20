<template>
  <el-card v-for="(device, id) in deviceList.data" @click="openDevice(device)" :key="id" style="
      margin: 18px 2%;
      width: 45%;
      height: 200px;
      margin-bottom: 20px;
      cursor: pointer;
    ">
    <div style="display: flex">
      <div style="width: 60%">
        <div>
          <span>设备名称：</span> <span>{{ device.deviceName }}</span>
        </div>
        <div>
          <span>设备编码：</span> <span>{{ device.deviceCode }}</span>
        </div>
        <div>
          <span>设备位置：</span> <span>{{ device.devicePosition }}</span>
        </div>
        <div>
          <span>设备状态：</span>
          <span>
            <el-tag v-if="device.deviceStatus === 1" type="success">{{ deviceStatus(device.deviceStatus) }}</el-tag>
            <el-tag v-if="device.deviceStatus === 0" type="error">{{ deviceStatus(device.deviceStatus) }}</el-tag>
          </span>
        </div>
        <div><span>时间模板：</span> <span>模板1</span></div>
        <div>
          <span>备注信息：</span> <span>{{ device.memo }}</span>
        </div>
        <div>
          <span>创建时间：</span> <span>{{ device.createTime }}</span>
        </div>
        <div>
          <span>修改时间：</span> <span>{{ device.updateTime }}</span>
        </div>
      </div>
      <div style="width: 40%">
        <img src="http://localhost/files/1/user/2024-02-08_13-44-10_739305_树莓派4b.png" height="150" />
      </div>
    </div>
  </el-card>
  <el-card style="margin: 18px 2%; width: 45%; height: 200px; cursor: pointer" @click="dialogFormVisible = true">
    + 新增设备
  </el-card>

  <el-dialog v-model="dialogFormVisible" title="新增服务器设备" width="500" :close-on-click-modal="false">
    <el-form :model="device" ref="deviceRef" :rules="deviceRules">
      <el-form-item prop="deviceName" label="设备名称" :label-width="formLabelWidth">
        <el-input v-model="device.deviceName" autocomplete="off" />
      </el-form-item>
      <el-form-item prop="deviceCode" label="设备编码" :label-width="formLabelWidth">
        <el-input v-model="device.deviceCode" autocomplete="off" />
      </el-form-item>
      <el-form-item prop="devicePosition" label="设备位置" :label-width="formLabelWidth">
        <el-input v-model="device.devicePosition" autocomplete="off" />
      </el-form-item>
      <el-form-item prop="memo" label="备注信息" :label-width="formLabelWidth">
        <el-input v-model="device.memo" autocomplete="off" />
      </el-form-item>
      <el-form-item prop="timeTemplate" label="时间模板" :label-width="formLabelWidth">
        <el-select v-model="device.timeTemplate" placeholder="选择时间模板">
          <el-option label="时间模板1" :value="1" />
          <el-option label="时间模板2" :value="2" />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="addDeviceFun"> 保存 </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import { selectDeviceListApi, saveDeviceApi } from '@/api/file';
import { ElMessage } from 'element-plus';
import mixin from '@/mixins/device';

let { deviceStatus } = mixin();

let {
  deviceRef,
  dialogFormVisible,
  formLabelWidth,
  device,
  deviceRules,
  deviceList,
  getDeviceListFun,
  openDevice,
  addDeviceFun,
} = deviceFun();

const emit = defineEmits(['deviceId']);

onMounted(() => {
  getDeviceListFun();
});

function deviceFun() {
  // 新增设备表单对象
  const deviceRef: any = ref(null);
  // 新增设备dialog
  const dialogFormVisible = ref(false);
  const formLabelWidth = '90px';
  // 新增设备信息
  const device = reactive({
    deviceName: '',
    deviceCode: '',
    devicePosition: '',
    memo: '',
    timeTemplate: '',
  });

  // 新增设备表单验证规则
  const deviceRules = {
    deviceName: [
      {
        required: true,
        message: '请输入设备名称',
        trigger: 'blur',
      },
    ],
    deviceCode: [
      {
        required: true,
        message: '请输入设备编码',
        trigger: 'blur',
      },
    ],
    devicePosition: [
      {
        required: true,
        message: '请输入设备位置',
        trigger: 'blur',
      },
    ],
    memo: [
      {
        required: true,
        message: '请输入设备备注信息',
        trigger: 'blur',
      },
    ],
    timeTemplate: [
      {
        required: true,
        message: '请选择时间模板',
        trigger: 'blur',
      },
    ],
  };
  // 设备列表
  let deviceList: any = reactive({ data: [] });

  // 获取设备列表
  const getDeviceListFun = () => {
    selectDeviceListApi().then((res: any) => {
      if (res.code === 200) {
        deviceList.data = res.result;
      }
    });
  };

  // 打开设备
  const openDevice = (device: any) => {
    emit('deviceId', device.id);
  };

  // 新增设备
  const addDeviceFun = () => {
    if (deviceRef.value !== null) {
      deviceRef.value.validate((valid: any) => {
        if (valid) {
          dialogFormVisible.value = false;
          saveDeviceApi({
            deviceName: device.deviceName,
            deviceCode: device.deviceCode,
            devicePosition: device.devicePosition,
            memo: device.memo,
            timeTemplate: device.timeTemplate,
          }).then((res: any) => {
            if (res.code === 200) {
              ElMessage.success('设备创建成功');
              getDeviceListFun();
            }
          });
          device.deviceName = '';
          device.deviceCode = '';
          device.devicePosition = '';
          device.memo = '';
          device.timeTemplate = '';
        }
      });
    }
  };

  return {
    deviceRef,
    dialogFormVisible,
    formLabelWidth,
    device,
    deviceRules,
    deviceList,
    getDeviceListFun,
    openDevice,
    addDeviceFun,
  };
}
</script>

<style scoped></style>
