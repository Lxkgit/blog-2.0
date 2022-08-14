<template>
    <div class="side_contain" :class="{ 'side_bar_open': store.sideBar }">
        <div class="side_button" @click="store.sideBar = !store.sideBar">
            <i v-if="store.sideBar === true" class="fa fa-indent"></i>
            <i v-else class="fa fa-outdent"></i>
        </div>

        <el-menu class="side_menu el-menu-vertical-demo" :collapse="store.sideBar" :collapse-transition="false">
            <MyMenu v-for="(item, index) in adminMenus.data" :key="index" :index-key="index" :item="item" />
        </el-menu>
    </div>
</template>

<script setup lang="ts">
import { reactive } from "vue";
import { adminStore } from "../../store/admin"

const store = adminStore()

const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}

let adminMenus: any = reactive({
    data: [
        {
            id: 1,
            parentId: 0,
            menuName: "系统管理",
            menuIcon: "fa fa-home",
            menuPath: null,
            permission: "sys:manage",
            component: null,
            menuType: "0",
            list: [
                {
                    id: 51,
                    parentId: 1,
                    menuName: "用户管理",
                    menuIcon: "",
                    menuPath: "/admin/user",
                    permission: "sys:user",
                    component: null,
                    menuType: "1",
                    list: null,
                },
                {
                    id: 50,
                    parentId: 1,
                    menuName: "角色管理",
                    menuIcon: "",
                    menuPath: "/admin/role",
                    permission: "sys:role",
                    component: null,
                    menuType: "1",
                    list: [
                        {
                            id: 103,
                            parentId: 50,
                            menuName: "修改角色权限",
                            menuIcon: null,
                            menuPath: null,
                            permission: "sys:role:permission:update",
                            component: null,
                            menuType: "2",
                            list: null,
                        },
                        {
                            id: 101,
                            parentId: 50,
                            menuName: "删除角色",
                            menuIcon: null,
                            menuPath: null,
                            permission: "sys:role:delete",
                            component: null,
                            menuType: "2",
                            list: null,
                        },
                        {
                            id: 102,
                            parentId: 50,
                            menuName: "修改角色",
                            menuIcon: null,
                            menuPath: null,
                            permission: "sys:role:update",
                            component: "",
                            menuType: "2",
                            list: null,
                        },
                        {
                            id: 100,
                            parentId: 50,
                            menuName: "添加角色",
                            menuIcon: null,
                            menuPath: null,
                            permission: "sys:role:insert",
                            component: null,
                            menuType: "2",
                            list: null,
                        },
                    ],
                },
            ],
        },
        {
            id: 2,
            parentId: 0,
            menuName: "内容管理",
            menuIcon: "fa fa-home",
            menuPath: null,
            permission: "sys:content",
            component: null,
            menuType: "0",
            list: [
                {
                    id: 60,
                    parentId: 2,
                    menuName: "文章管理",
                    menuIcon: "",
                    menuPath: "/admin/article",
                    permission: "sys:article",
                    component: null,
                    menuType: "1",
                    list: null,
                },
                {
                    id: 61,
                    parentId: 2,
                    menuName: "日记管理",
                    menuIcon: null,
                    menuPath: "/admin/diary",
                    permission: "sys:diary",
                    component: null,
                    menuType: "1",
                    list: null,
                },
            ],
        },
        {
            id: 3,
            parentId: 0,
            menuName: "系统设置",
            menuIcon: null,
            menuPath: null,
            permission: "sys:setting",
            component: null,
            menuType: "0",
            list: [
                {
                    id: 72,
                    parentId: 3,
                    menuName: "个人设置",
                    menuIcon: null,
                    menuPath: null,
                    permission: "sys:setting:user",
                    component: null,
                    menuType: "1",
                    list: null,
                },
                {
                    id: 71,
                    parentId: 3,
                    menuName: "全局设置",
                    menuIcon: null,
                    menuPath: null,
                    permission: "sys:setting:all",
                    component: null,
                    menuType: "1",
                    list: null,
                },
            ],
        },
        {
            id: 5,
            parentId: 0,
            menuName: "测试空值",
            menuIcon: null,
            menuPath: null,
            permission: "sys:setting",
            component: null,
            menuType: "0",
            list: null
        },
        {
            id: 6,
            parentId: 0,
            menuName: "测试空值",
            menuIcon: null,
            menuPath: null,
            permission: "sys:setting",
            component: null,
            menuType: "0",
            list: null
        },
        {
            id: 7,
            parentId: 0,
            menuName: "测试空值",
            menuIcon: null,
            menuPath: null,
            permission: "sys:setting",
            component: null,
            menuType: "0",
            list: null
        },

    ]
})


</script>


<style scoped>
.side_bar_open {
    width: 64px !important;
}

.side_button {
    height: 32px;
    padding: 0 12px;
    line-height: 32px;
    border-bottom: 1px solid #dcdfe6;
    border-right: 1px solid #dcdfe6;
    font-size: 16px;
}

.side_contain {
    width: 210px;
    position: fixed;
    bottom: 0;
    top: 88px;
    left: 0;
}

.side_menu {
    height: 100%;
}
</style>
