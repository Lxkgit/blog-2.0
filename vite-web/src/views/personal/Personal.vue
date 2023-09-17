<template>
  <el-container>
    <el-header style="height: 96px;">
      <AdminMenu :kind="'back'"></AdminMenu>
      <PageTab></PageTab>
    </el-header>
    <el-container>
      <el-aside width="store.sideBar === true ? '65px' : '200px'">
        <AsideMenu></AsideMenu>
      </el-aside>
      <el-main style="min-height: calc(100vh - 96px);">
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup name="Personal" lang="ts">
import AdminMenu from "@/components/common/AdminMenu.vue";
import AsideMenu from "@/components/personal/AsideMenu.vue";
import PageTab from "@/components/common/PageTab.vue"
import { computed } from "vue";
import { systemStore } from "@/store/system";
const store = systemStore()
// 个人中心导航栏是否折叠
const isCollapse = computed(() => store.asideMenuFold)
</script>

<style scoped lang="scss">
.el-container {
  height: calc(100vh - 96px);

  .el-header {
    padding: 0;
  }
}
</style>
