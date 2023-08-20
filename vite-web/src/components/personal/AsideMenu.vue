<template>
  <div style="height: calc(100vh - 138px);" :class="{ 'side_bar_open': store.sideBar }">
    <div @click="store.sideBar = !store.sideBar" style="height: 40px; display: flex;
    align-items: center; padding-left: 25px; border-right: solid 1px var(--el-menu-border-color); border-bottom: solid 1px var(--el-menu-border-color); background-color: var(--el-menu-bg-color);
    border-top: solid 1px var(--el-menu-border-color);">
      <el-icon v-if="store.sideBar === true">
        <MyIcon type="icon-indent" />
      </el-icon>
      <el-icon v-else>
        <MyIcon type="icon-outdent" />
      </el-icon>
    </div>
    <el-menu :collapse="store.sideBar" :collapse-transition="true" width="60px" class="el-menu-vertical-demo">
      <el-menu-item index="/admin/index" @click="router.push('/admin/index')">
        <el-icon>
          <MyIcon type="icon-user"/>
        </el-icon>
        <span class="menu-icon-text">个人中心</span>
      </el-menu-item>
      <MyMenu v-for="(item, index) in adminMenus.data" :key="index" :index-key="index" :item="item" />
    </el-menu>
  </div>
</template>

<script setup lang="ts">
import MyMenu from "@/components/common/MyMenu.vue"
import { onMounted, reactive, ref } from "vue";
import { useRouter } from "vue-router";
import icon from "@/utils/icon";
import { systemStore } from "@/store/system";
import { userMenuApi } from "@/api/user"

let adminMenus: any = reactive({ data: [] })
const getMenuFun = () => {
  userMenuApi(2).then((res: any) => {
    if(res.code === 200) {
      adminMenus.data = res.result
    }
  })
}

const store = systemStore()
const { MyIcon } = icon()
const router = useRouter()


onMounted(() => {
  getMenuFun()
})
</script>

<style scoped lang="scss">
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
}

.el-menu {
  height: 100%;
}
</style>
