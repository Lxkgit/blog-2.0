<template>
    <div style="margin-top: 50px;">
        <a-row justify="center">
            <a-col :span="16">
                <div v-for="(menu, idx) in menuList.data" :key="idx" style="clear: both;
    width: 1000px;
    overflow: hidden;
    margin-bottom: 15px; ">
                    <div>
                        <h3 style="    margin-bottom: 18px;
    text-align: left;
    font-size: 18px;
    font-weight: 400;
    font-stretch: normal;
    line-height: 24px;
    letter-spacing: 0;
    color: #313b46;">{{ menu.menuName }}</h3>

                        <div v-for="(item, index) in menu.list" :key="index" style="float: left; box-sizing: border-box;position: relative; width: 120px; height: 120px; border: 1px solid #f1f1f1; background: #fff;
                    cursor: pointer; font-size: 12px; color: #76797c; margin: 0 20px 20px 0;" :title="item.menuName"
                            @click="btn(item.menuPath)">
                            <div style="position: absolute;
    width: 100%;
    height: 80px;
    left: 0;
    top: 10px;
    bottom: 0;
    right: 0;
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: center;
    -ms-flex-pack: center;
    justify-content: center;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    ">
                                <img :src="item.menuIcon" />
                            </div>
                            <p style="    position: absolute;
    left: 0;
    right: 0;
    text-align: center;
    bottom: 8px;
    margin: 0;
    font-size: 14px;
    color: #282d33;">
                                <span :title="item.menuName" style="max-width: 100px;display: inline-block;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    vertical-align: middle;">{{ item.menuName }}</span>
                            </p>
                        </div>
                    </div>
                </div>
            </a-col>
        </a-row>
    </div>
</template>


<script setup lang="ts">
import { onMounted, reactive } from 'vue';

const emit = defineEmits(["route-change"]);
let menuList: any = reactive({ data: [] });

onMounted(() => {
    menuList.data = [
        {
            "id": 1,
            "parentId": 0,
            "menuName": "系统管理",
            "menuIcon": null,
            "menuPath": null,
            "permission": "sys:manage",
            "component": null,
            "menuType": "0",
            "list": [
                {
                    "id": 51,
                    "parentId": 1,
                    "menuName": "用户管理",
                    "menuIcon": "/src/assets/img/role.png",
                    "menuPath": "/admin/user",
                    "permission": "sys:user",
                    "component": null,
                    "menuType": "1",
                    "list": null
                },
                {
                    "id": 50,
                    "parentId": 1,
                    "menuName": "角色管理",
                    "menuIcon": "/src/assets/img/user.png",
                    "menuPath": "/admin/role",
                    "permission": "sys:role",
                    "component": null,
                    "menuType": "1",
                    "list": [
                        {
                            "id": 103,
                            "parentId": 50,
                            "menuName": "修改角色权限",
                            "menuIcon": null,
                            "menuPath": null,
                            "permission": "sys:role:permission:update",
                            "component": null,
                            "menuType": "2",
                            "list": null
                        },
                        {
                            "id": 101,
                            "parentId": 50,
                            "menuName": "删除角色",
                            "menuIcon": null,
                            "menuPath": null,
                            "permission": "sys:role:delete",
                            "component": null,
                            "menuType": "2",
                            "list": null
                        },
                        {
                            "id": 102,
                            "parentId": 50,
                            "menuName": "修改角色",
                            "menuIcon": null,
                            "menuPath": null,
                            "permission": "sys:role:update",
                            "component": "",
                            "menuType": "2",
                            "list": null
                        },
                        {
                            "id": 100,
                            "parentId": 50,
                            "menuName": "添加角色",
                            "menuIcon": null,
                            "menuPath": null,
                            "permission": "sys:role:insert",
                            "component": null,
                            "menuType": "2",
                            "list": null
                        }
                    ]
                }
            ]
        },
        {
            "id": 2,
            "parentId": 0,
            "menuName": "内容管理",
            "menuIcon": null,
            "menuPath": null,
            "permission": "sys:content",
            "component": null,
            "menuType": "0",
            "list": [
                {
                    "id": 60,
                    "parentId": 2,
                    "menuName": "文章管理",
                    "menuIcon": "/src/assets/img/content.png",
                    "menuPath": "/admin/article",
                    "permission": "sys:article",
                    "component": null,
                    "menuType": "1",
                    "list": null
                },
                {
                    "id": 61,
                    "parentId": 2,
                    "menuName": "日记管理",
                    "menuIcon": null,
                    "menuPath": "/admin/diary",
                    "permission": "sys:diary",
                    "component": null,
                    "menuType": "1",
                    "list": null
                }
            ]
        },
        {
            "id": 3,
            "parentId": 0,
            "menuName": "系统设置",
            "menuIcon": null,
            "menuPath": null,
            "permission": "sys:setting",
            "component": null,
            "menuType": "0",
            "list": [
                {
                    "id": 72,
                    "parentId": 3,
                    "menuName": "个人设置",
                    "menuIcon": null,
                    "menuPath": null,
                    "permission": "sys:setting:user",
                    "component": null,
                    "menuType": "1",
                    "list": null
                },
                {
                    "id": 71,
                    "parentId": 3,
                    "menuName": "全局设置",
                    "menuIcon": null,
                    "menuPath": null,
                    "permission": "sys:setting:all",
                    "component": null,
                    "menuType": "1",
                    "list": null
                }
            ]
        }
    ]
        
        
    
})

const btn = (path: string) => {
    emit('route-change', {path})
}

</script>
