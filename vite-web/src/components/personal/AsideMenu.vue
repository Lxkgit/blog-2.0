<template>
  <div style="height: calc(100vh - 138px);" :class="{ 'side_bar_open': store.sideBar }">
    <div @click="store.sideBar = !store.sideBar" style="height: 40px; display: flex;
    align-items: center; padding-left: 10px; border-right: solid 1px var(--el-menu-border-color); border-bottom: solid 1px var(--el-menu-border-color); background-color: var(--el-menu-bg-color);
    border-top: solid 1px var(--el-menu-border-color);">
      <el-icon v-if="store.sideBar === true">
        开
      </el-icon>
      <el-icon v-else>
        关
      </el-icon>
    </div>
    <el-menu :collapse="store.sideBar" :collapse-transition="true" width="60px" class="el-menu-vertical-demo">
      <el-menu-item index="/personal/myIndex" @click="router.push('/personal/myIndex')">
        <el-icon>
          <icon-user />
        </el-icon>
        <span class="menu-icon-text">个人中心</span>
      </el-menu-item>
      <MyMenu v-for="(item, index) in adminMenus.data" :key="index" :index-key="index" :item="item" />
    </el-menu>
  </div>
</template>

<script setup lang="ts">
import MyMenu from "@/components/common/MyMenu.vue"


import { User as IconUser, Tickets, Operation } from '@element-plus/icons-vue'
import { computed, onMounted, reactive, ref } from "vue";
import user from "@/utils/user";
import { useRouter } from "vue-router";
import { ElMessage, ElMessageBox } from "element-plus";
import icon from "@/utils/icon";
import { systemStore } from "@/store/system";
// import {getSiteConfig} from "@/api/management";
// import {getUserinfoId, putUserinfoId} from "@/api/account";






let adminMenus: any = reactive({ data: [{ "id": 1, "parentId": 0, "menuName": "系统管理", "menuIcon": "fa fa-home", "menuPath": null, "permission": "sys:manage", "component": "", "menuType": "0", "list": [{ "id": 50, "parentId": 1, "menuName": "角色管理", "menuIcon": "", "menuPath": "/admin/role", "permission": "sys:role", "component": "", "menuType": "1", "list": null, "children": null, "label": "角色管理" }, { "id": 51, "parentId": 1, "menuName": "用户管理", "menuIcon": "", "menuPath": "/admin/user", "permission": "sys:user", "component": "", "menuType": "1", "list": null, "children": null, "label": "用户管理" }], "children": [{ "id": 50, "parentId": 1, "menuName": "角色管理", "menuIcon": "", "menuPath": "/admin/role", "permission": "sys:role", "component": "", "menuType": "1", "list": null, "children": null, "label": "角色管理" }, { "id": 51, "parentId": 1, "menuName": "用户管理", "menuIcon": "", "menuPath": "/admin/user", "permission": "sys:user", "component": "", "menuType": "1", "list": null, "children": null, "label": "用户管理" }], "label": "系统管理" }, { "id": 2, "parentId": 0, "menuName": "内容管理", "menuIcon": "fa fa-book", "menuPath": null, "permission": "sys:content", "component": "", "menuType": "0", "list": [{ "id": 60, "parentId": 2, "menuName": "文章管理", "menuIcon": "", "menuPath": "/admin/article", "permission": "sys:article", "component": "", "menuType": "1", "list": null, "children": null, "label": "文章管理" }, { "id": 61, "parentId": 2, "menuName": "日记管理", "menuIcon": null, "menuPath": "/admin/diary", "permission": "sys:diary", "component": "", "menuType": "1", "list": null, "children": null, "label": "日记管理" }, { "id": 62, "parentId": 2, "menuName": "文档管理", "menuIcon": null, "menuPath": "/admin/doc", "permission": "sys:doc", "component": null, "menuType": "1", "list": null, "children": null, "label": "文档管理" }], "children": [{ "id": 60, "parentId": 2, "menuName": "文章管理", "menuIcon": "", "menuPath": "/admin/article", "permission": "sys:article", "component": "", "menuType": "1", "list": null, "children": null, "label": "文章管理" }, { "id": 61, "parentId": 2, "menuName": "日记管理", "menuIcon": null, "menuPath": "/admin/diary", "permission": "sys:diary", "component": "", "menuType": "1", "list": null, "children": null, "label": "日记管理" }, { "id": 62, "parentId": 2, "menuName": "文档管理", "menuIcon": null, "menuPath": "/admin/doc", "permission": "sys:doc", "component": null, "menuType": "1", "list": null, "children": null, "label": "文档管理" }], "label": "内容管理" }, { "id": 3, "parentId": 0, "menuName": "系统设置", "menuIcon": "fa fa-cog", "menuPath": null, "permission": "sys:setting", "component": "", "menuType": "0", "list": [{ "id": 71, "parentId": 3, "menuName": "全局设置", "menuIcon": null, "menuPath": null, "permission": "sys:setting:all", "component": "", "menuType": "1", "list": null, "children": null, "label": "全局设置" }, { "id": 72, "parentId": 3, "menuName": "个人设置", "menuIcon": null, "menuPath": null, "permission": "sys:setting:user", "component": "", "menuType": "1", "list": null, "children": null, "label": "个人设置" }], "children": [{ "id": 71, "parentId": 3, "menuName": "全局设置", "menuIcon": null, "menuPath": null, "permission": "sys:setting:all", "component": "", "menuType": "1", "list": null, "children": null, "label": "全局设置" }, { "id": 72, "parentId": 3, "menuName": "个人设置", "menuIcon": null, "menuPath": null, "permission": "sys:setting:user", "component": "", "menuType": "1", "list": null, "children": null, "label": "个人设置" }], "label": "系统设置" }] })



const store = systemStore()
const { MyIcon } = icon()
const route = useRouter()
const router = useRouter()
// 引入用户信息模块
const { userId } = user();
//导航菜单-logo和name
const siteConfig = reactive({
  logo: '',
  name: '',
})

// 获取网站数据
async function siteConfigData() {
  // let data = await getSiteConfig()
  // siteConfig.logo = data.logo
  // siteConfig.name = data.name
}

// 当前激活的id
const asideMenuIndex = ref('/personal/myIndex')
// 个人中心导航栏是否折叠
const isCollapse = computed(() => store.asideMenuFold)
// 用户信息
const userInfo = reactive({})

// 获取用户信息
async function getUserinfo() {
  // Object.assign(userInfo, await getUserinfoId(userId.value))
}

// 设置是否接收邮件通知
const changeFlow = () => {
  // if (userInfo.email.length === 0) {
  //   ElMessageBox.alert('您的账号未绑定邮箱，请先绑定邮箱后再进行设置！', '绑定邮箱', {
  //     confirmButtonText: '确定',
  //     callback: () => {
  //       router.push('/personal/changeEmail')
  //     },
  //   })
  // } else {
  //   flowDialog.value = true
  // }
}
// 是否开启订阅弹窗
const flowDialog = ref(false)
// 订阅弹窗开启事件
const flowOpen = () => {
  console.log("点了开启了")
  // userInfo.is_flow = true
  // putUserinfoId(userId.value, userInfo).then((response) => {
  //   console.log(response)
  //   ElMessage({
  //     message: '已开启新文章发布邮件通知！',
  //     type: 'success',
  //   })
  // }).catch(response => {
  //   //发生错误时执行的代码
  //   console.log(response)
  //   ElMessage.error('开启新文章发布邮件通知失败！')
  // });
  flowDialog.value = false
}
// 订阅弹窗关闭事件
const flowClose = () => {
  console.log("点了关闭了")
  // userInfo.is_flow = false
  // putUserinfoId(userId.value, userInfo).then((response) => {
  //   console.log(response)
  //   ElMessage({
  //     message: '已取消新文章发布邮件通知！',
  //     type: 'success',
  //   })
  // }).catch(response => {
  //   //发生错误时执行的代码
  //   console.log(response)
  //   ElMessage.error('取消新文章发布邮件通知失败！')
  // });
  flowDialog.value = false
}
onMounted(() => {
  asideMenuIndex.value = route.currentRoute.value.fullPath
  siteConfigData()
  getUserinfo()
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
