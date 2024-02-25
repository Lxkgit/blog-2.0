<template>
  <div style="width: 100%; height: 100%; display: flex; flex-flow: wrap;overflow-y: auto; align-items:flex-start;" @contextmenu.prevent="openMenu($event, null)">
    <el-card v-for="(device, id) in deviceList.data" @click="openDevice(device)" :key="id" @contextmenu.prevent.stop="openMenu($event, device)" style="
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
              <el-tag v-if="device.deviceStatus === 0" type="warning">{{ deviceStatus(device.deviceStatus) }}</el-tag>
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

    <ul v-if="menu.visible" :style="{ left: menu.left + 'px', top: menu.top + 'px' }" class="contextmenu">
      <li @click="refreshDevice()">刷新</li>
      <li v-if="menu.type === 1">
        修改设备信息
      </li>
      <li @click="menu.visible = false">关闭菜单</li>
    </ul>
  </div>

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
  menu,
  deviceRules,
  deviceList,
  openMenu,
  refreshDevice,
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

  let menu: any = reactive({
    visible: false,
    left: 0,
    top: 0,
    type: 0
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

  /**
   * 打开菜单
   */
  const openMenu = (e: any, item?: any) => {
    if (item !== null) {
      menu.device = item;
      menu.type = 1;
    } else {
      menu.device = null;
      menu.type = 0;
    }
    menu.visible = true;
    menu.left = e.clientX;
    menu.top = e.clientY;
  };

  /**
   * 刷新当前目录
   */
  const refreshDevice = () => {
    menu.visible = false;
    getDeviceListFun();
  };

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
    menu,
    deviceRules,
    deviceList,
    openMenu,
    refreshDevice,
    getDeviceListFun,
    openDevice,
    addDeviceFun,
  };
}
</script>

<style scoped>
.contextmenu {
  position: fixed;
  min-width: min-content;
  z-index: 1900;
  border: 1px solid #d4d4d5;
  line-height: 1.4285em;
  max-width: 150px;
  background: #fff;
  font-weight: 400;
  font-style: normal;
  color: rgba(0, 0, 0, 0.87);
  border-radius: 0.28571429rem;
  box-shadow: 0 2px 4px 0 rgba(34, 36, 38, 0.12),
    0 2px 10px 0 rgba(34, 36, 38, 0.15);
}

.contextmenu div {
  position: relative;
  vertical-align: middle;
  line-height: 1;
  -webkit-tap-highlight-color: transparent;
  padding: 10px 15px;
  color: rgba(0, 0, 0, 0.87);
  font-size: 14px;
  cursor: pointer;
}

.contextmenu div:hover {
  background: #eee;
}

.contextmenu {
  margin: 0;
  background: #fff;
  z-index: 3000;
  position: absolute;
  list-style-type: none;
  padding: 5px 0;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 400;
  color: #333;
  box-shadow: 2px 2px 3px 0 rgba(0, 0, 0, 0.3);
}

.contextmenu li {
  margin: 0;
  padding: 7px 16px;
  cursor: pointer;
}

.contextmenu li:hover {
  background: #eee;
}
</style>
