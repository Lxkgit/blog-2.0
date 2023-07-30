<template>
  <div>
    <div class="title_style">
      <span>个人设置</span>
    </div>
    <el-card style="margin: 18px 2%;">
      <el-tabs v-model="activeName" @tab-click="change">
        <el-tab-pane label="站点设置" name="1">
          <WebSetting />
        </el-tab-pane>
        <el-tab-pane label="壁纸设置" name="2">
          <WallpaperSetting />
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script setup name="WebSetting" lang="ts">
import { ref } from 'vue'
import WebSetting from '@/components/setting/WebSetting.vue';
import WallpaperSetting from '@/components/setting/WallpaperSetting.vue';

const activeName = ref('1')

const change = (tab: any, event: Event) => {
  // console.log(tab)
}
</script>

<style scoped>
.title_style {
  display: flex;
  justify-content: flex-start;
  align-items: baseline;
  max-height: 31px;
  color: #445160;
  font-size: 24px;
  font-weight: 600;
  text-align: left;
}
</style>