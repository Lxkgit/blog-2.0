<template>
  <div class="tool-number-item">
    <h4>{{ title }}</h4>
    <el-switch
      v-model="data"
      active-color="#13ce66"
      inactive-color="#ff4949"
      @change="updateBlogSettingFun"
    />
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, reactive } from "vue";
import { updateBlogSettingApi } from "@/api/file";
import { ElMessage } from 'element-plus'

const props = defineProps({
  title: String,
  id: Number,
  model: Boolean,
  type: Number
})

let data = ref()
onMounted(() => {
  if (props.model != null && props.model !== undefined) {
    data.value = props.model
  } 
})

const updateBlogSettingFun = () => {
  updateBlogSettingApi({
    id: props.id,
    bool: data.value
  }).then((res: any) => {
    if(res.code === 200) {
      ElMessage({ message: '设置修改成功', type: 'success' })
    }
  })
}
</script>

<style scoped lang="scss">
  .tool-number-item {
    display: flex;
    flex-direction: column;

    h4 {
      font-size: 14px;
      font-weight: bold;
      margin: 6px 0;
    }
  }
</style>
