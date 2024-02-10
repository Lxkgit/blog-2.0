<template>
    <el-card v-for="device in deviceList.data" @click="openDevice(device)"
        style="margin: 18px 2%; width: 45%; height: 200px; margin-bottom: 20px; cursor: pointer;">
        <div style="display: flex;">
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
                    <span>设备状态：</span> <span><el-tag type="success">{{ device.deviceStatus }}</el-tag></span>
                </div>
                <div>
                    <span>时间模板：</span> <span>模板1</span>
                </div>
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
    <el-card style="margin: 18px 2%; width: 45%; height: 200px; cursor: pointer;">
        + 新增设备
    </el-card>
</template>
  
<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import { selectDeviceListApi } from "@/api/file";
let { deviceList, getDiaryListFun, openDevice } = deviceFun();

const emit = defineEmits(['deviceId'])

onMounted(() => {
    getDiaryListFun()
})


function deviceFun() {
    // 设备列表
    let deviceList: any = reactive({ data: [] });

    // 获取设备列表
    const getDiaryListFun = () => {
        selectDeviceListApi().then((res: any) => {
            if (res.code === 200) {
                deviceList.data = res.result
            }
        })
    }

    const openDevice = (device: any) => {
        emit('deviceId', device.id)
    }

    return { deviceList, getDiaryListFun, openDevice }
}

</script>
  
<style scoped></style>
  