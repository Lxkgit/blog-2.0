<template>
  <!-- <div
    v-show="list != null && list.length !== 0"
    id="article-index"
    :style="'top:' + top + ';left:' + left"
  >
    <div id="article-index-move">
      文章目录
      <div class="window-option">
        <div v-show="showCatalog" @click="changeShow(false)">
          <font-awesome-icon class="catalog-min" icon="window-minimize" />
        </div>
        <div v-show="!showCatalog" @click="changeShow(true)">
          <font-awesome-icon class="catalog-max" icon="window-maximize" />
        </div>
        <div @click="list = null">
          <font-awesome-icon id="category-close" icon="window-close" />
        </div>
      </div>
    </div>
    <ol v-show="showCatalog" id="index-ul">
      <li
        v-for="(item, index) in list"
        :key="index"
        :class="
          (item.name === 'H3' ? 'index-ul-li' : '') + ' index-list ' + item.id
        "
        :title="item.title"
      >
        <a :href="'#' + item.id">{{ item.title }}</a>
      </li>
    </ol>
  </div> -->
  1
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';


</script>

<style scoped>
/*文章目录部分*/
.catalog-active a {
  color: #618eba !important;
}
#article-index {
  border-radius: 3px;
  width: 200px;
  max-height: 290px;
  position: fixed;
  line-height: 23px;
  background: #ffffff;
  left: 0;
  z-index: 555555;
}
#article-index-move {
  display: flex;
  flex-direction: row;
  cursor: move;
  padding: 5px;
  border-bottom: 1px solid #dfe1e5;
}
#category-close {
  font-weight: 800;
  color: #848484;
  cursor: pointer;
  margin: 1px 6px 0 8px;
}
#index-ul {
  height: auto;
  overflow-y: auto;
  max-height: 250px;
  font-size: 15px;
  padding: 0;
  margin: 0 0 0 13px;
}
#index-ul li {
  padding-top: 2px;
  cursor: pointer;
  color: #24292e;
  list-style: none;
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
  margin-right: 9px;
}
.window-option {
  flex: 1;
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
.catalog-min {
  margin-top: -10px;
  cursor: pointer;
  color: #848484;
}
.catalog-max {
  cursor: pointer;
  margin-left: 5px;
  color: #848484;
}
</style>
