<template>
    <div id="menu" style="width: 200px; height: 100%; position: fixed; bottom: 0; top: 89px;">
        <a-button type="text" size="middle" style="" @click="toggleCollapsed">
            <MenuUnfoldOutlined v-if="state.collapsed" />
            <MenuFoldOutlined v-else />
        </a-button>
        <a-divider style="height: 1px; background-color: #000000; margin: 0; width: auto;" />
        <a-menu v-model:openKeys="state.openKeys" v-model:selectedKeys="state.selectedKeys" mode="inline" theme="light"
            :inline-collapsed="state.collapsed">
            <a-menu-item key="1">
                <template #icon>
                    <PieChartOutlined />
                </template>
                <span>Option 1</span>
            </a-menu-item>
        </a-menu>
    </div>
</template>
<script setup lang="ts">
import {  reactive, watch } from 'vue';
import {
    MenuFoldOutlined,
    MenuUnfoldOutlined,
    PieChartOutlined,
    MailOutlined,
} from '@ant-design/icons-vue';

const state = reactive({
    collapsed: false,
    selectedKeys: ['1'],
    openKeys: ['sub1'],
    preOpenKeys: ['sub1'],
});

watch(
    () => state.openKeys,
    (_val, oldVal) => {
        state.preOpenKeys = oldVal;
    },
);
const toggleCollapsed = () => {
    const menu = document.getElementById("menu")
    if(menu !== null) {
        menu.style.width="50px"
    }
    state.collapsed = !state.collapsed;
    state.openKeys = state.collapsed ? [] : state.preOpenKeys;
};


</script>

