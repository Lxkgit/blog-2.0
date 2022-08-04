<template>
    <div style="width: 210px;">
        <a-button type="primary" style="margin-bottom: 16px" @click="toggleCollapsed">
            <MenuUnfoldOutlined v-if="state.collapsed" />
            <MenuFoldOutlined v-else />
        </a-button>
        <a-menu v-model:openKeys="state.openKeys" v-model:selectedKeys="state.selectedKeys" mode="inline" theme="dark"
            :inline-collapsed="state.collapsed">
            <a-menu-item key="1">
                <template #icon>
                    <PieChartOutlined />
                </template>
                <span>Option 1</span>
            </a-menu-item>
            <a-menu-item key="2">
                <template #icon>
                    <DesktopOutlined />
                </template>
                <span>Option 2</span>
            </a-menu-item>
            <a-menu-item key="3">
                <template #icon>
                    <InboxOutlined />
                </template>
                <span>Option 3</span>
            </a-menu-item>
            <a-sub-menu key="sub1">
                <template #icon>
                    <MailOutlined />
                </template>
                <template #title>Navigation One</template>
                <a-menu-item key="5">Option 5</a-menu-item>
                <a-menu-item key="6">Option 6</a-menu-item>
                <a-menu-item key="7">Option 7</a-menu-item>
                <a-menu-item key="8">Option 8</a-menu-item>
            </a-sub-menu>
            <a-sub-menu key="sub2">
                <template #icon>
                    <AppstoreOutlined />
                </template>
                <template #title>Navigation Two</template>
                <a-menu-item key="9">Option 9</a-menu-item>
                <a-menu-item key="10">Option 10</a-menu-item>
                <a-sub-menu key="sub3" title="Submenu">
                    <a-menu-item key="11">Option 11</a-menu-item>
                    <a-menu-item key="12">Option 12</a-menu-item>
                </a-sub-menu>
            </a-sub-menu>
        </a-menu>
    </div>
</template>
<script setup lang="ts">
import { reactive, watch } from 'vue';
import {
    MenuFoldOutlined,
    MenuUnfoldOutlined,
    PieChartOutlined,
    MailOutlined,
    DesktopOutlined,
    InboxOutlined,
    AppstoreOutlined,
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
    state.collapsed = !state.collapsed;
    state.openKeys = state.collapsed ? [] : state.preOpenKeys;
};




</script>