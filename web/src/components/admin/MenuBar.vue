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
import { reactive, onMounted } from "vue";
import { adminStore } from "../../store/tag"
import { getMenuApi } from "../../api/menuApi";

const store = adminStore()

const handleOpen = (key: string, keyPath: string[]) => {
    console.log(key, keyPath)
}

let adminMenus: any = reactive({ data: [] })

onMounted(() => {
    getMenuApi().then((res: any) => {
        if(res.code === 200) {
            adminMenus.data = res.result
        }
        
    })
});

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
