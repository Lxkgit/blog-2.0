<template>
  <div>
    <div class="tool-input-item">
      <h4>{{ title }}</h4><el-input v-model="data" autosize type="textarea" @change="updateBlogSettingFun" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, reactive } from "vue";
import { updateBlogSettingApi } from "@/api/file";
import { ElMessage } from 'element-plus'

const props = defineProps({
  title: String,
  id: Number,
  model: String,
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
    value: data.value
  }).then((res: any) => {
    if(res.code === 200) {
      ElMessage({ message: '设置修改成功', type: 'success' })
    }
  })
}
</script>

<style lang="scss" scoped>
  /*侧边栏设置的相关设置*/
  .tool-input-item{
    display: flex;
    flex-direction: column;
    h4{
      font-size: 14px;
      font-weight: bold;
      margin: 6px 0;
    }
    .item-input{
      display: flex;
      flex-direction: row;
      margin: 5px 0 0 0;
    }
    .item-input .el-input{
      margin-right: 5px;
    }
  }
</style>
