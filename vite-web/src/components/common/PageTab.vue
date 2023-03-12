<template>
  <transition enter-active-class="animate__animated animate__fadeInDown"
    leave-active-class="animate__animated animate__fadeOutUp" mode="in-out">
    <header id="tags_view_container" class="tags_view_container">
      <div class="tags_view_wrapper">
        <span style="float: left; width: 57px; box-sizing: border-box; line-height: 38px; text-align: center;">
          <MyIcon type="icon-home3" @click="router.push('/')"/>
        </span>
        <router-link v-for="(item, index) in store.tags" :key="index" ref="tag" tag="span" class="tags_view_item"
          :class="{ 'active': item.active }" :to="item.path" @contextmenu.prevent="openMenu(item.path, index, $event)">
          {{ item.title }}
          <!--这里加prevent.stop是为了避免跳转路由-->
          <i v-if="item.close"
            @click.prevent.stop="closeTag(index)"> 
            <MyIcon  style="width: 16px; height:16px" type="icon-close"/>
          </i>
        </router-link>
      </div>
      <ul v-if="visible" :style="{ left: left + 'px', top: top + 'px' }" class="contextmenu">
        <li @click="refresh">
          刷新
        </li>
        <li @click="closeMenu()">
          关闭菜单
        </li>
        <li @click="closeTag()">
          关闭标签
        </li>
        <li @click="closeOther()">
          关闭其他
        </li>
        <li @click="closeAll">
          全部关闭
        </li>
      </ul>
    </header>
  </transition>

</template>

<script setup lang="ts">
import { ref, watch, reactive } from "vue"
import { tagsStore } from "@/store/tag"
import { useRoute, useRouter } from "vue-router"
import icon from '@/utils/icon'

const route = useRoute()
const router = useRouter()
const store = tagsStore()
let { MyIcon } = icon()
let visible = ref(false)
let top = ref(100)
let left = ref(100)
// let path: any = route.path

// 监听路由变化，激活对应标签
watch(() => route.path, () => {
  console.log("PageTab path: " + route.path)
  store.activeTag(route.path)
})

// 关闭标签
const closeTag = (index?: any) => {
  visible.value = false
  if(index === undefined) {
    store.delTag(store.selectedTag)
    router.push("/admin/index")
  } else {
    if(store.tags[index].active){
      router.push("/admin/index")
    }
    store.delTag(index)
  }
}

// 打开菜单
const openMenu = (path: any, index: any, e: any) => {
  store.activeTag(path)
  visible.value = true;
  left.value = e.clientX
  top.value = e.clientY

}
// 关闭菜单
const closeMenu = () => {
  visible.value = false
}

const closeOther = () => {
  visible.value = false
  store.delOtherTags()
}

const closeAll = () => {
  visible.value = false
  router.push("/admin/index")
  store.delAllTags()
}

const refresh = () => {
  window.location.reload()
}

</script>
  

<style scoped lang="scss">
.tags_view_container {
  height: 35px;
  width: 100%;
  background-color: var(--el-bg-color-overlay);
  border-bottom: 1px solid var(--el-border-color);
}

.tags_view_container .tags_view_wrapper {
  overflow: auto;
  white-space: nowrap;
}

.tags_view_container .tags_view_wrapper .tags_view_item {
  display: inline-block;
  position: relative;
  cursor: pointer;
  margin-left: 5px;
  margin-top: 5px;
  height: 23px;
  line-height: 23px;
  border: 1px solid #d8dce5;
  color: #495060;
  background: #fff;
  padding: 0 6px;
  font-size: 12px;
  text-decoration: none;
}

.tags_view_container .tags_view_wrapper .tags_view_item i:hover{
  background-color: #949790;
  border-radius: 50%;
}

// .tags_view_container .tags_view_wrapper .tags_view_item:first-of-type {
//   margin-left: 15px;
// }

// .tags_view_container .tags_view_wrapper .tags_view_item:last-of-type {
//   margin-right: 15px;
// }

.tags_view_container .tags_view_wrapper .tags_view_item.active {
  background-color: #42b983;
  color: #fff;
  border-color: #42b983;
}

.tags_view_container .tags_view_wrapper .tags_view_item.active::before {
  content: '';
  background: #fff;
  display: inline-block;
  width: 8px;
  height: 8px;
  border-radius: 50%;
  position: relative;
  margin-right: 2px;
}

.tags_view_container .contextmenu {
  margin: 0;
  background: #fff;
  z-index: 3000;
  position: absolute;
  list-style-type: none;
  padding: 5px 0;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 400;
  color: #333;
  box-shadow: 2px 2px 3px 0 rgba(0, 0, 0, 0.3);
}

.tags_view_container .contextmenu li {
  margin: 0;
  padding: 7px 16px;
  cursor: pointer;
}

.tags_view_container .contextmenu li:hover {
  background: #eee;
}

.tags_view_wrapper .tags_view_item .el_icon_close {
  width: 16px;
  height: 16px;
  vertical-align: 2px;
  border-radius: 50%;
  text-align: center;
  transition: all 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
  transform-origin: 100% 50%;
}

.tags_view_wrapper .tags_view_item .el_icon_close:before {
  transform: scale(0.6);
  display: inline-block;
  vertical-align: -3px;
}

.tags_view_wrapper .tags_view_item .el_icon_close:hover {
  background-color: #b4bccc;
  color: #fff;
}

.el-icon-close {
  width: 16px;
  height: 16px;
  vertical-align: 2px;
  border-radius: 50%;
  text-align: center;
  transition: all 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
  transform-origin: 100% 50%;
}

.el-icon-close:before {
  transform: scale(0.6);
  display: inline-block;
  vertical-align: -3px;
}

.el-icon-close:hover {
  background-color: #b4bccc;
  color: #fff;
}
</style>