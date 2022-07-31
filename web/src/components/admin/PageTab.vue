<template>
  <div id="tags-view-container" class="tags-view-container">
    <div class="tags-view-wrapper">
      <router-link
        v-for="(item, index) in tags"
        :key="index"
        ref="tag"
        tag="span"
        class="tags-view-item"
        :class="{ active: item.active }"
        :to="item.path"
        @contextmenu.prevent.native="openMenu(index, $event)"
      >
        {{ item.title }}
        
        <span
          v-if="item.close"
          class="el-icon-close"
          @click.prevent.stop="closeTag(index)"
        />
      </router-link>
    </div>
    <ul
      v-if="visible"
      :style="{ left: left + 'px', top: top + 'px' }"
      class="contextmenu"
    >
      <li @click="refresh">刷新</li>
      <li @click="closeTag(selectedTag)">关闭</li>
      <li @click="closeOther(selectedTag)">关闭其他</li>
      <li @click="closeAll">全部关闭</li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { watch } from "vue";
import { useRouter, useRoute } from "vue-router";

// watch(()=>book.name,()=>{//通过一个函数返回要监听的属性
//  	console.log('书名改变了')
//  })

// watch: {
//     $route() {
//         this.changeTag()
//     },
//     visible(value) { // 监听右键菜单显示变化
//         if (value) {
//             document.body.addEventListener('click', this.closeMenu)
//         } else {
//             document.body.removeEventListener('click', this.closeMenu)
//         }
//     }
// },
const changeTag = () => {
  //   // 判断新加的标签是否为当前页
  //   let path = useRoute;
  //   // 路径不为空则调用vuex方法来处理数据
  //   if (path) {
  //     // this.$store.commit("tab/changeTag", path);
  //   }
};

const closeTag = (index: any) => {
  console.log(index);
  // 把当前标签页放入tags中
  //   this.$store
  //     .dispatch("tab/closeTag", index)
  //     .then((res) => this.$router.push({ path: res }))
  //     .catch(() => {});
};

const openMenu = (index: any, e: any) => {
  console.log(index, e);
  //   if (!this.sideStatus) {
  //     this.left = e.clientX - 210;
  //   } else {
  //     this.left = e.clientX - 64;
  //   }
  //   this.top = e.clientY - 50;
  //   this.visible = true;
  //   this.selectedTag = index;
};
const closeMenu = () => {
  //   this.visible = false;
};

const refresh = () => {
  window.location.reload();
};

const closeOther = (index: any) => {
  console.log(index);
  //   this.$store.dispatch("tab/closeOther", index);
  //   // 手动获取最后一个标签并跳转路由
  //   const path = this.tags[this.tags.length - 1].path;
  //   if (this.$route.path !== path) {
  //     this.$router.push(path);
  //   }
};

const closeAll = () => {
  //   this.$store.dispatch("tab/closeAll");
  //   const path = this.tags[0].path;
  //   if (this.$route.path !== path) {
  //     this.$router.push(path);
  //   }
};
</script>

<style scoped>
.tags-view-container {
  height: 34px;
  width: 100%;
  background: #fff;
  border-bottom: 1px solid #d8dce5;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.12), 0 0 3px 0 rgba(0, 0, 0, 0.04);
}
.tags-view-container .tags-view-wrapper {
  overflow: auto;
  height: 52px;
  white-space: nowrap;
}
.tags-view-container .tags-view-wrapper .tags-view-item {
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
  margin-top: 4px;
}
.tags-view-container .tags-view-wrapper .tags-view-item:first-of-type {
  margin-left: 15px;
}
.tags-view-container .tags-view-wrapper .tags-view-item:last-of-type {
  margin-right: 15px;
}
.tags-view-container .tags-view-wrapper .tags-view-item.active {
  background-color: #42b983;
  color: #fff;
  border-color: #42b983;
}
.tags-view-container .tags-view-wrapper .tags-view-item.active::before {
  content: "";
  background: #fff;
  display: inline-block;
  width: 8px;
  height: 8px;
  border-radius: 50%;
  position: relative;
  margin-right: 2px;
}
.tags-view-container .contextmenu {
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
.tags-view-container .contextmenu li {
  margin: 0;
  padding: 7px 16px;
  cursor: pointer;
}
.tags-view-container .contextmenu li:hover {
  background: #eee;
}
.tags-view-wrapper .tags-view-item .el-icon-close {
  width: 16px;
  height: 16px;
  vertical-align: 2px;
  border-radius: 50%;
  text-align: center;
  transition: all 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
  transform-origin: 100% 50%;
}
.tags-view-wrapper .tags-view-item .el-icon-close:before {
  transform: scale(0.6);
  display: inline-block;
  vertical-align: -3px;
}
.tags-view-wrapper .tags-view-item .el-icon-close:hover {
  background-color: #b4bccc;
  color: #fff;
}
</style>

