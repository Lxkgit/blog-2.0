<template>
  <div id="tags_view_container" class="tags_view_container">
    <div class="tags_view_wrapper">
      <div style="float: left; width: 57px; box-sizing: border-box; line-height: 38px; text-align: center; ">
        <i class="fa fa-home" aria-hidden="true"></i>
      </div>
      <router-link v-for="(item, index) in store.tags" :key="index" ref="tag" tag="span" class="tags_view_item"
        :class="{ 'active': item.active }" :to="item.path" @contextmenu.prevent.native="openMenu(item.path, index, $event)">
        {{ item.title }}
        <!--这里加prevent.stop是为了避免跳转路由-->
        <i v-if="item.close" class="fa fa-close el-icon-close" style="margin-left: 5px;" @click.prevent.stop="closeTag(index)"> </i>
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
  </div>
</template>

<script setup lang="ts">
import { ref, watch } from "vue"
import { adminStore } from "../../store/tag"
import { useRoute, useRouter } from "vue-router"

const route = useRoute()
const router = useRouter()
const store = adminStore()

let visible = ref(false)
let top = ref(0)
let left = ref(0)
let path:any = route.path

// 监听路由变化，激活对应标签
watch(() => route.path,() => {
  store.activeTag(route.path)
})

// 关闭标签
const closeTag = (index?: any) => {
  visible.value = false
  if(index === undefined) {
    store.delTag(store.selectedTag)
    router.push("/admin")
  } else {
    if(store.tags[index].active === true){
      router.push("/admin")
    }
    store.delTag(index)
  }
}

// 打开菜单
const openMenu = (path:any, index: any, e: any) => {
  store.activeTag(path)
  visible.value = true;
  left.value = e.clientX
  top.value = e.clientY-50

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
  router.push("/admin")
  store.delAllTags()
}

const refresh = () => {
  window.location.reload()
}

</script>
  

<style scoped>
.tags_view_container {
  height: 34px;
  width: 100%;
  background: #fff;
  border-bottom: 1px solid #d8dce5;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.12), 0 0 3px 0 rgba(0, 0, 0, 0.04);
}

.tags_view_container .tags_view_wrapper {
  overflow: auto;
  white-space: nowrap;
}

.tags_view_container .tags_view_wrapper .tags_view_item {
  display: inline-block;
  position: relative;
  cursor: pointer;
  height: 26px;
  line-height: 26px;
  border: 1px solid #d8dce5;
  color: #495060;
  background: #fff;
  padding: 0 8px;
  font-size: 12px;
  margin-left: 5px;
  margin-top: 6px;
  text-decoration: none;
}

.tags_view_container .tags_view_wrapper .tags_view_item:first-of-type {
  margin-left: 15px;
}

.tags_view_container .tags_view_wrapper .tags_view_item:last-of-type {
  margin-right: 15px;
}

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