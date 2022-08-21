<template>
  <el-sub-menu v-if="item.list !== null" :index="indexKey.toString()">
    <template #title>
      <el-icon>
        <i :class="item.menuIcon"></i>
      </el-icon>
      <span>{{ item.menuName }}</span>
    </template>
    <el-menu-item
      v-for="(item2, index2) in item.list"
      :key="index2"
      :index="`${indexKey.toString()}-${index2.toString()}`"
      @click="gotoSite(item2)"
      >{{ item2.menuName }}
    </el-menu-item>
  </el-sub-menu>
  <el-menu-item v-else :index="indexKey.toString()" @click="gotoSite(item)">
    <el-icon>
      <i :class="item.menuIcon"></i>
    </el-icon>
    <template #title>{{ item.menuName }}</template>
  </el-menu-item>
</template>

<script setup lang="ts">
import { ref } from "vue"
import { useRouter } from "vue-router";
import { adminStore } from "../../store/tag"

const store = adminStore()
const router = useRouter()


defineProps( {
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
