<template>
  <el-card style="margin: 18px 2%; width: 94%">
    <div style="margin: 18px 2%; display: flex">
      <div style="width: 80%">
        <div>
          <span>单片机名称：</span> <span>{{ chip.chipName }}</span>
        </div>
        <div>
          <span>单片机编码：</span> <span>{{ chip.chipCode }}</span>
        </div>
        <div>
          <span>单片机状态：</span>
          <span
            ><el-tag type="success">{{ chip.chipStatus }}</el-tag></span
          >
        </div>
        <div>
          <span>备注信息：</span> <span>{{ chip.memo }}</span>
        </div>
        <div>
          <span>创建时间：</span> <span>{{ chip.createTime }}</span>
        </div>
        <div>
          <span>修改时间：</span> <span>{{ chip.updateTime }}</span>
        </div>
      </div>
      <div style="width: 15%">
        <img
          src="http://localhost/files/1/user/2024-02-08_17-00-49_47c7df_WeMos_D1.png"
          height="150"
        />
      </div>
    </div>
  </el-card>
  <el-card
    v-for="(sensor, id) in sensorList.data"
    @click="openSensor(sensor)"
    style="
      margin: 18px 2%;
      width: 45%;
      height: 200px;
      margin-bottom: 20px;
      cursor: pointer;
    "
    :key="id"
  >
    <div style="display: flex">
      <div style="width: 60%">
        <div>
          <span>传感器名称：</span> <span>{{ sensor.sensorName }}</span>
        </div>
        <div>
          <span>传感器编码：</span> <span>{{ sensor.sensorCode }}</span>
        </div>
        <div>
          <span>备注信息：</span> <span>{{ sensor.memo }}</span>
        </div>
        <div>
          <span>创建时间：</span> <span>{{ sensor.createTime }}</span>
        </div>
        <div>
          <span>修改时间：</span> <span>{{ sensor.updateTime }}</span>
        </div>
      </div>
      <div style="width: 40%">
        <img
          src="http://localhost/files/1/user/2024-02-08_17-10-00_389e15_DHT11.png"
          height="150"
        />
      </div>
    </div>
  </el-card>
  <el-card
    style="margin: 18px 2%; width: 45%; height: 200px; cursor: pointer"
    @click="dialogFormVisible = true; selectSensorTypeListFun()"
  >
    + 新增传感器
  </el-card>

  <el-dialog
    v-model="dialogFormVisible"
    title="新增传感器"
    width="500"
    :close-on-click-modal="false"
  >
    <el-form :model="sensor" ref="sensorRef" :rules="sensorRules">
      <el-form-item prop="sensorName" label="传感器名称" :label-width="formLabelWidth">
        <el-input v-model="sensor.sensorName" autocomplete="off" />
      </el-form-item>
      <el-form-item prop="sensorCode" label="传感器编码" :label-width="formLabelWidth">
        <el-input v-model="sensor.sensorCode" autocomplete="off" />
      </el-form-item>
      <el-form-item prop="sensorTypeId" label="传感器类型" :label-width="formLabelWidth">
        <el-select v-model="sensor.sensorTypeId" placeholder="选择传感器类型">
          <el-option v-for="item in sensorTypeList.data" :key="item.id" :label="item.sensorName" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item prop="memo" label="备注信息" :label-width="formLabelWidth">
        <el-input v-model="sensor.memo" autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="saveSensorFun()"> 保存 </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from "vue";
import { ElMessage } from 'element-plus';
import {
  selectChipByIdApi,
  selectSensorListApi,
  selectSensorTypeListApi,
  saveSensorApi,
} from "@/api/file";
let {
  sensorRef,
  dialogFormVisible,
  formLabelWidth,
  sensor,
  sensorRules,
  chip,
  sensorList,
  sensorTypeList,
  selectChipByIdFun,
  selectSensorListFun,
  saveSensorFun,
  selectSensorTypeListFun,
  openSensor,
} = sensorFun();

const emit = defineEmits(["sersor"]);

const props = defineProps({
  chipId: Number,
});

onMounted(() => {
  selectChipByIdFun(props.chipId);
  selectSensorListFun();
});

function sensorFun() {
  // 新增设备表单对象
  const sensorRef: any = ref(null);
  // 新增设备dialog
  const dialogFormVisible = ref(false);
  const formLabelWidth = "100px";
  // 新增设备信息
  const sensor = reactive({
    sensorName: "",
    sensorCode: "",
    sensorTypeId: "",
    memo: "",
  });

  // 新增设备表单验证规则
  const sensorRules = {
    sensorName: [
      {
        required: true,
        message: "请输入设备名称",
        trigger: "blur",
      },
    ],
    sensorCode: [
      {
        required: true,
        message: "请输入设备编码",
        trigger: "blur",
      },
    ],
    sensorTypeId: [
      {
        required: true,
        message: "请输入设备位置",
        trigger: "blur",
      },
    ],
    memo: [
      {
        required: true,
        message: "请输入设备备注信息",
        trigger: "blur",
      },
    ],
  };
  // 单片机信息
  let chip: any = ref({});

  // 传感器列表
  let sensorList: any = reactive({ data: [] });

  // 传感器列表
  let sensorTypeList: any = reactive({ data: [] });

  // 获取单片机信息
  const selectChipByIdFun = (id: any) => {
    selectChipByIdApi({ id: id }).then((res: any) => {
      if (res.code === 200) {
        chip.value = res.result;
      }
    });
  };

  // 查询单片机下全部传感器
  const selectSensorListFun = () => {
    selectSensorListApi({
      pageNum: 1,
      pageSize: 10,
      chipId: props.chipId,
    }).then((res: any) => {
      if (res.code === 200) {
        sensorList.data = res.result.list;
      }
    });
  };

  const saveSensorFun = () => {
    saveSensorApi({
      chipId: props.chipId,
      sensorTypeId: sensor.sensorTypeId,
      sensorName: sensor.sensorName,
      sensorCode: sensor.sensorCode,
      memo: sensor.memo
    }).then((res: any) => {
      if(res.code === 200) {
        dialogFormVisible.value = false;
        ElMessage.success('传感器创建成功');
        selectSensorListFun();
        sensor.sensorName =  "";
        sensor.sensorCode =  "";
        sensor.sensorTypeId = "";
        sensor.memo = "";
      }
    })
  }

  // 查询支持的全部传感器类型
  const selectSensorTypeListFun = () => {
    selectSensorTypeListApi().then((res: any) => {
      sensorTypeList.data = res.result;
    });
  };

  // 打开传感器
  const openSensor = (sensor: any) => {
    emit("sersor", sensor);
  };

  return {
    sensorRef,
    dialogFormVisible,
    formLabelWidth,
    sensor,
    sensorRules,
    chip,
    sensorList,
    sensorTypeList,
    selectChipByIdFun,
    selectSensorListFun,
    saveSensorFun,
    selectSensorTypeListFun,
    openSensor,
  };
}
</script>

<style scoped></style>
