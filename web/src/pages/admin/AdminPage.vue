<!--管理员界面的布局文件-->
<template>
  <div>
    <HeadBar style="position: fixed; left: 0; top: 0; right: 0; z-index: 20; background-color: #fff;" />
    <PageTab style="position: fixed; left: 0; right: 0; top: 50px; z-index: 10; height: 38px;" />
    <div class="main_container" :class="{ 'container_open': store.sideBar }">
      <div  class="main-head">
        <MenuBar />
      </div>
      <div>
        <router-view class="layout_main" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { tagsStore } from "../../store/tag"

let store = tagsStore();

</script>

<style scoped>
.container_open {
  margin-left: 64px !important;
}

.main_container {
  margin-left: 210px;
  height: 100%;
}

.layout_main {
  margin-top: 85px;
  padding: 15px;
  z-index: -1;
}
</style>
