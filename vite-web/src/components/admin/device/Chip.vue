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
  <el-card v-for="(chip, id) in chipList.data" style="margin: 18px 2%; width: 45%; height: 200px; margin-bottom: 20px; cursor: pointer;" @click="openChip(chip)" :key="id">
    <div style="display: flex;">
      <div style="width: 60%">
        <div>
          <span>单片机名称：</span> <span>{{chip.chipName}}</span>
        </div>
        <div>
          <span>单片机类型：</span> <span>{{chip.chipType}}</span>
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
  <el-card style="margin: 18px 2%; width: 45%; height: 200px; cursor: pointer;" @click="dialogFormVisible = true">
    + 新增单片机
  </el-card>

  <el-dialog v-model="dialogFormVisible" title="新增单片机" width="500" :close-on-click-modal="false">
    <el-form :model="chip" ref="chipRef" :rules="chipRules">
      <el-form-item prop="chipName" label="单片机名称" :label-width="formLabelWidth">
        <el-input v-model="chip.chipName" autocomplete="off" />
      </el-form-item>
      <el-form-item prop="chipCode" label="单片机编码" :label-width="formLabelWidth">
        <el-input v-model="chip.chipCode" autocomplete="off" />
      </el-form-item>
      <el-form-item prop="chipType" label="单片机类型" :label-width="formLabelWidth">
        <el-input v-model="chip.chipType" autocomplete="off" />
      </el-form-item>
      <el-form-item prop="memo" label="备注信息" :label-width="formLabelWidth">
        <el-input v-model="chip.memo" autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="addChipFun"> 保存 </el-button>
      </div>
    </template>
  </el-dialog>
</template>
  
<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import { selectDeviceByIdApi, selectChipListApi, saveChipApi } from '@/api/file';
import { ElMessage } from 'element-plus';
let {
  chipRef,
  dialogFormVisible,
  formLabelWidth,
  chipRules,
  device,
  chip,
  chipList,
  selectDeviceByIdFun,
  selectChipListFun,
  openChip,
  addChipFun
} = chipFun();

const props = defineProps({
  deviceId: Number,
});

const emit = defineEmits(['chipId']);

onMounted(() => {
  selectDeviceByIdFun(props.deviceId);
  selectChipListFun();
});

function chipFun() {
  // 新增单片机表单对象
  const chipRef: any = ref(null);
  // 新增单片机dialog
  const dialogFormVisible = ref(false);
  const formLabelWidth = '100px';
  // 新增单片机表单验证规则
  const chipRules = {
    chipName: [
      {
        required: true,
        message: '请输入单片机名称',
        trigger: 'blur',
      },
    ],
    chipCode: [
      {
        required: true,
        message: '请输入单片机编码',
        trigger: 'blur',
      },
    ],
    chipType: [
      {
        required: true,
        message: '请输入单片机类型',
        trigger: 'blur',
      },
    ],
    memo: [
      {
        required: true,
        message: '请输入单片机备注信息',
        trigger: 'blur',
      },
    ],
  };
  // 设备信息
  let device: any = ref({});
  // 新增单片机信息
  const chip = reactive({
    chipName: '',
    chipCode: '',
    chipType: '',
    memo: '',
  });
  // 单片机列表
  let chipList: any = reactive({ data: [] });

  // 获取设备信息
  const selectDeviceByIdFun = (id: any) => {
    selectDeviceByIdApi({ id: id }).then((res: any) => {
      if (res.code === 200) {
        device.value = res.result;
      }
    });
  };

  // 获取单片机列表
  const selectChipListFun = () => {
    selectChipListApi({
      pageNum: 1,
      pageSize: 10,
      deviceId: props.deviceId,
    }).then((res: any) => {
      if (res.code === 200) {
        chipList.data = res.result.list;
      }
    });
  };

  // 打开单片机
  const openChip = (chip: any) => {
    emit('chipId', chip.id);
  };

  // 创建单片机
  const addChipFun = () => {
    if (chipRef.value !== null) {
        chipRef.value.validate((valid: any) => {
        if (valid) {
          dialogFormVisible.value = false;
          saveChipApi({
            chipName: chip.chipName,
            chipCode: chip.chipCode,
            deviceId: props.deviceId,
            chipType: chip.chipType,
            memo: chip.memo,
          }).then((res: any) => {
            if (res.code === 200) {
              ElMessage.success('单片机创建成功');
              selectChipListFun();
            }
          });
          chip.chipName = '';
          chip.chipCode = '';
          chip.chipType = '';
          chip.memo = '';
        }
      });
    }
  }
  return {
    chipRef,
    dialogFormVisible,
    formLabelWidth,
    chipRules,
    device,
    chip,
    chipList,
    selectDeviceByIdFun,
    selectChipListFun,
    openChip,
    addChipFun
  };
}
</script>
  
<style scoped></style>
  