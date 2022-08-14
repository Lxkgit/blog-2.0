<!--管理员界面的布局文件-->
<template>
  <div>
    <HeadBar style="position: fixed; left: 0; top: 0; right: 0; z-index: 20" />
    <PageTab style="position: fixed; left: 0; right: 0; top: 50px; z-index: 10; height: 38px; background-color: #ffa;" />
    <div class="main-container" :class="{ 'container-open': store.sideBar }">
      <div  class="main-head">
        <MenuBar />
      </div>
      <div class="main-bg">
        <router-view class="layout-main" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { adminStore } from "../../store/admin"
import { ref, computed, reactive, onMounted } from 'vue';


let store = adminStore();

let adminMenus: any = reactive({ data: [] })


onMounted(() => {
  //   // 页面刷新时及时保存tag信息
  // window.addEventListener('beforeunload', () => this.$store.dispatch('tab/saveStatus'))
  // // 恢复状态
  // this.$store.dispatch('tab/restoreStatus')
});


</script>

<style scoped>
/*图片地址为空时自动隐藏img*/
img[src=""],
img:not([src]) {
  opacity: 0;
}

/*清除输入框选中黑色*/
input {
  outline: none
}

/*输入框界面*/
.el-input.el-input--prefix {
  margin-top: 10px;
}
</style>

<style scoped>
.container-open {
  margin-left: 64px !important;
}

.main-container {
  margin-left: 210px;
  height: 100%;
}

.main-bg {
  bottom: 0;
  top: 200px;
  left: 210px;
  right: 0;
  z-index: 0;
}

.layout-main {
  margin-top: 80px;
  padding: 15px;
  z-index: -1;
}
</style>
