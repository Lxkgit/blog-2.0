<template>
    <el-card style="margin: 18px 2%; width: 94%;">
        <div style="margin: 18px 2%; display: flex">
            <div style="width:80%">
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
                    <span>时间模板：</span> <span>{{ device.timeTemplate }}</span>
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
            <div style="width: 15%">
                <img src="http://localhost/files/1/user/2024-02-08_13-44-10_739305_树莓派4b.png" height="150" class="image" />
            </div>
        </div>
    </el-card>
    <el-card v-for="chip in chipList.data" style="margin: 18px 2%; width: 45%; height: 200px; margin-bottom: 20px; cursor: pointer;" @click="openChip(chip)">
        <div style="display: flex;">
            <div style="width: 60%">
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
                    <span>备注信息：</span> <span>{{chip.chipStatus}}</span>
                </div>
                <div>
                    <span>创建时间：</span> <span>{{chip.createTime}}</span>
                </div>
                <div>
                    <span>修改时间：</span> <span>{{chip.updateTime}}</span>
                </div>
            </div>
            <div style="width: 40%">
                <img src="http://localhost/files/1/user/2024-02-08_17-00-49_47c7df_WeMos_D1.png" height="150" />
            </div>
        </div>
    </el-card>
    <el-card style="margin: 18px 2%; width: 45%; height: 200px; cursor: pointer;">
        + 新增设备
    </el-card>
</template>
  
<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import { selectDeviceByIdApi, selectChipListApi } from "@/api/file";
let { device, chipList, selectDeviceByIdFun, selectChipListFun, openChip } = chipFun();

const props = defineProps({
    deviceId: Number
})

const emit = defineEmits(['chipId'])

onMounted(() => {
    selectDeviceByIdFun(props.deviceId)
    selectChipListFun()
})

function chipFun() {
    let device: any = ref({})
    // 设备列表
    let chipList: any = reactive({ data: [] });

    // 获取设备信息
    const selectDeviceByIdFun = (id: any) => {
        selectDeviceByIdApi({ "id": id }).then((res: any) => {
            if (res.code === 200) {
                device.value = res.result
            }
        })
    }

    const selectChipListFun = () => {
        selectChipListApi({"pageNum": 1 , "pageSize": 10}).then((res: any) => {
            if (res.code === 200) {
                chipList.data = res.result.list
            }
        })
    }

    const openChip = (chip: any) => {

        emit('chipId', chip.id)
    }

    return { device, chipList, selectDeviceByIdFun, selectChipListFun, openChip }
}

</script>
  
<style scoped></style>
  