<template>
    <el-card style="margin: 18px 2%; width: 94%;">
        <div style="margin: 18px 2%; display: flex">
            <div style="width:80%">
                <div>
                    <span>单片机名称：</span> <span>{{chip.chipName}}</span>
                </div>
                <div>
                    <span>单片机编码：</span> <span>{{chip.chipCode}}</span>
                </div>
                <div>
                    <span>单片机状态：</span> <span><el-tag type="success">{{chip.chipStatus}}</el-tag></span>
                </div>
                <div>
                    <span>备注信息：</span> <span>{{chip.memo}}</span>
                </div>
                <div>
                    <span>创建时间：</span> <span>{{chip.createTime}}</span>
                </div>
                <div>
                    <span>修改时间：</span> <span>{{chip.updateTime}}</span>
                </div>
            </div>
            <div style="width: 15%">
                <img src="http://localhost/files/1/user/2024-02-08_17-00-49_47c7df_WeMos_D1.png" height="150" />
            </div>
        </div>
    </el-card>
    <el-card v-for="sensor in sensorList.data" @click="openSensor(sensor)" style="margin: 18px 2%; width: 45%; height: 200px; margin-bottom: 20px; cursor: pointer;">
        <div style="display: flex;">
            <div style="width: 60%">
                <div>
                    <span>传感器名称：</span> <span>{{sensor.sensorName}}</span>
                </div>
                <div>
                    <span>传感器编码：</span> <span>{{sensor.sensorCode}}</span>
                </div>
                <!-- <div>
                    <span>传感器类型：</span> <span>{{sensor.sensorType}}</span>
                </div> -->
                <div>
                    <span>备注信息：</span> <span>{{sensor.sensorCode}}</span>
                </div>
                <div>
                    <span>创建时间：</span> <span>{{sensor.createTime}}</span>
                </div>
                <div>
                    <span>修改时间：</span> <span>{{sensor.updateTime}}</span>
                </div>
            </div>
            <div style="width: 40%">
                <img src="http://localhost/files/1/user/2024-02-08_17-10-00_389e15_DHT11.png" height="150"/>
            </div>
        </div>
    </el-card>
    <el-card style="margin: 18px 2%; width: 45%; height: 200px; cursor: pointer;">
        + 新增设备
    </el-card>
</template>
  
<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import { selectChipByIdApi, selectSensorListApi } from "@/api/file";
let { chip, sensorList, selectChipByIdFun, selectSensorListFun, openSensor } = sensorFun();

const emit = defineEmits(['sersor'])

const props = defineProps({
    chipId: Number
})

onMounted(() => {
    selectChipByIdFun(props.chipId)
    selectSensorListFun()
})

function sensorFun() {
    let chip: any = ref({})
    // 设备列表
    let sensorList: any = reactive({ data: [] });

    // 获取设备信息
    const selectChipByIdFun = (id: any) => {
        selectChipByIdApi({ "id": id }).then((res: any) => {
            if (res.code === 200) {
                chip.value = res.result
            }
        })
    }

    const selectSensorListFun = () => {
        selectSensorListApi({"pageNum": 1 , "pageSize": 10}).then((res: any) => {
            if (res.code === 200) {
                sensorList.data = res.result.list
            }
        })
    }

    const openSensor = (sensor: any) => {
        emit('sersor', sensor)
    }

    return { chip, sensorList, selectChipByIdFun, selectSensorListFun, openSensor }
}

</script>
  
<style scoped></style>
  