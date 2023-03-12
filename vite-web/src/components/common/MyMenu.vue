<template>
  <el-sub-menu v-if="item.list !== null" :index="indexKey.toString()">
    <template #title>

      <el-icon>
        <MyIcon type="icon-custom-user" />
      </el-icon>
      <span>{{ item.menuName }}</span>
    </template>
    <el-menu-item v-for="(item2, index2) in item.list" :key="index2"
      :index="`${indexKey.toString()}-${index2.toString()}`" @click="gotoSite(item2)">{{ item2.menuName }}
    </el-menu-item>
  </el-sub-menu>
  <el-menu-item v-else :index="indexKey.toString()" @click="gotoSite(item)">

    <el-icon>
      <MyIcon type="icon-custom-user" />
    </el-icon>
    <template #title>{{ item.menuName }}</template>
  </el-menu-item>
</template>

<script setup lang="ts">
import { useRouter } from "vue-router";
import { tagsStore } from "../../store/tag"
import icon from '@/utils/icon'
import { User as IconUser, Tickets, Operation } from '@element-plus/icons-vue'

let { MyIcon } = icon()
const store = tagsStore()
const router = useRouter()



defineProps({
  indexKey: { type: Number, default: 0 },
  item: { type: Object, default: () => { return {} } }
})


const gotoSite = (item: any) => {
  // 跳转路由
  router.push(item.menuPath)
  store.addTag(item.menuName, item.menuPath)
  store.selectedTag = store.tags.length
}



</script>

<style scoped>

</style>
