<template>
  <div>
    <el-row :gutter="20">
      <!--功能选择区-->
      <el-col :xs="24" :sm="15" :md="7">
        <TypeUtil v-if='webSetting.length != 0' :options="webSetting" />
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import TypeUtil from "@/components/setting/util/TypeUtil.vue"
import { selectBlogSettingApi } from "@/api/file"

let webSetting = ref([])

onMounted(() => {
  selectBlogSettingFun()
})

const selectBlogSettingFun = () => {
  selectBlogSettingApi({settingType: "0-0"}).then((res: any) => {
    if (res.code === 200) {     
      webSetting.value = res.result
    }
  })
}
</script>

<style scoped>

</style>
