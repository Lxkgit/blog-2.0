<template>
  <div>
    <el-radio-group v-model="isCollapse" style="margin-bottom: 20px">
      <el-radio-button :label="false">expand</el-radio-button>
      <el-radio-button :label="true">collapse</el-radio-button>
    </el-radio-group>
    <div
      v-for="(item, index) in adminMenus"
      :key="index"
      :index-key="index"
      :item="item"
    >
      <el-menu
        default-active="2"
        class="el-menu-vertical-demo"
        :collapse="isCollapse"
        @open="handleOpen"
        @close="handleClose"
        @click="gotoSite(item.path, item.nameZh)"
      >
        <el-menu-item index="2">
          <el-icon><icon-menu /></el-icon>
          <template #title>{{ item.nameZh }}</template>
        </el-menu-item>
        <el-menu-item index="4">
          <el-icon><setting /></el-icon>
          <template #title>Navigator Four</template>
        </el-menu-item>
      </el-menu>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";

import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
} from "@element-plus/icons-vue";

const adminMenus: any = [{ nameZh: "日记管理", path: "/admin/diary" }];

const isCollapse = ref(true);

const gotoSite = (path: any, name: any) => {
  console.log(path, name);
  //   // 跳转路由
  //   this.$router.push(path);
  //   // 添加标签
  //   this.$store.dispatch("tab/addTag", { name, path });
};
const handleOpen = (key: string, keyPath: string[]) => {
  console.log("打开 .. ");
  console.log(key, keyPath);
};
const handleClose = (key: string, keyPath: string[]) => {
  console.log("关闭 .. ");
  console.log(key, keyPath);
};
</script>

<style>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
}


</style>
