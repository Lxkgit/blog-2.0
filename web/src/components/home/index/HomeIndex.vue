<template>
  <div id="sub-menu" style="display: flex; width: 100%">
    <a-menu v-model:selectedKeys="current" mode="horizontal" style="width: 100%; border: 0; justify-content: center"  @select="loadingBlogListByType">
      <template v-for="item in blogType.data">
        <a-menu-item v-if="item.list == null" :key="item.id">
          {{ item.typeName }}
        </a-menu-item>
        <a-sub-menu v-else :key="-item.id" >
          <template #title>{{ item.typeName }}</template>
          <span v-for="itemList in item.list" :key="itemList.id">
            <a-menu-item v-if="itemList.list == null" :key="itemList.id">
              {{ itemList.typeName }}
            </a-menu-item>
            <a-sub-menu v-else :key="-itemList.id">
              <template #title>{{ itemList.typeName }}</template>
              <a-menu-item v-for="i in itemList.list" :key="i.id">
                {{ i.typeName }}
              </a-menu-item>
            </a-sub-menu>
          </span>
        </a-sub-menu>
      </template>
    </a-menu>
  </div>
  <a-row justify="center">
    <a-col :lg="12" :md="24" :sm="24" :xs="24">
      <a-list item-layout="vertical" :loading="initLoading" size="large" :data-source="blogList.data">
        <template #renderItem="{ item }">
          <a-list-item key="item.title">
            <a-list-item-meta>
              <template #avatar>
                <a-avatar :src="item.avatar" />
              </template>
              <template #title>
                <a :href="item.href">{{ item.blogUser.username }}</a>
              </template>
            </a-list-item-meta>
            <a :href="item.href" style="color: #000000">
              <h3 style="text-align: center">{{ item.title }}</h3>
              {{ sliceStr(item.contentMd) }}
            </a>
            <div style="margin-top: 10px">
              <a-tag v-for="(tag, idx) in item.articleLabels" :key="idx" color="pink">{{
                  tag.labelName
              }}</a-tag>
            </div>
            <span>创建日期： {{ item.createTime }} </span>
            <a-divider type="vertical" style="background-color: #7cb305" />
            <span>点赞数： {{ item.likeCount }} </span>
            <a-divider type="vertical" style="background-color: #7cb305" />
            <span>浏览量： {{ item.browseCount }} </span>
            <template #extra>
              <img width="272" alt="logo" src="https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png" />
            </template>

          </a-list-item>
        </template>
      </a-list>
      <div v-if="page * size" style="text-align: center">没有更多文章了 ... </div>
    </a-col>

    <a-col :lg="4" :md="0" :sm="0" :xs="0">
      <a-card :loading="loading" title="热门标签">
        <div>
          <a-tag color="pink">pink</a-tag>
          <a-tag color="red">red</a-tag>
          <a-tag color="orange">orange</a-tag>
          <a-tag color="green">green</a-tag>
          <a-tag color="cyan">cyan</a-tag>
          <a-tag color="blue">blue</a-tag>
          <a-tag color="purple">purple</a-tag>
          <a-tag color="pink">pink</a-tag>
          <a-tag color="red">red</a-tag>
          <a-tag color="orange">orange</a-tag>
          <a-tag color="green">green</a-tag>
          <a-tag color="cyan">cyan</a-tag>
          <a-tag color="blue">blue</a-tag>
          <a-tag color="purple">purple</a-tag>
        </div>
      </a-card>
    </a-col>
  </a-row>
</template>


<script setup lang="ts">
import { ref, reactive, onMounted, computed } from "vue";
import { getBlogList, getBlogType } from "../../../api/test";

let blogList: any = reactive({ data: [] });
let blogType: any = reactive({ data: [] });
const current = ref<string[]>(["0"]);
const initLoading = ref(true);
const loading = ref(false);
let page = ref<number>(1)
let size = ref<number>(5)
let total = ref<number>(0)

onMounted(() => {
  initLoading.value = false;
  getBlogList(page.value, size.value).then((res: any) => {
    blogList.data = res.list;
    total.value = res.total;
  });
  getBlogType().then((res: any) => {
    blogType.data = res
  })
});

const loadingBlogListByType = () => {
  console.log(current.value)
}

const sliceStr = computed(() => {
  return function (val: string) {
    let len = 150;
    return val.length > len ? val.slice(0, len) + "..." : val;
  };
});

window.addEventListener("scroll", function () {
  // 页面被卷去的高度: window.scrollY
  // 整个页面你的滚动条高度: document.documentElement.scrollHeight
  // 可视页面的高度: document.documentElement.clientHeight
  if (
    document.documentElement.clientHeight + window.scrollY >=
    document.documentElement.scrollHeight
  ) {
    if (page.value * size.value < total.value) {
      page.value = page.value + 1;
      getBlogList(page.value, size.value).then((res: any) => {
        for (let i = 0; i < res.list.length; i++) {
          blogList.data.push(res.list[i]);
        }
      });
    }
  }
});

window.onscroll = function () {
  let topScroll = get_scrollTop_of_body(); //滚动的距离,距离顶部的距离
  let subMenu = document.getElementById("sub-menu"); //获取到导航栏id
  if (subMenu !== null) {
    if (topScroll !== undefined && topScroll > 47) {
      subMenu.style.position = "fixed";
      subMenu.style.top = "0";
      subMenu.style.left = "0";
      subMenu.style.right = "0";
      subMenu.style.zIndex = "100";
      subMenu.style.borderBottom = "1px";
    } else {
      subMenu.style.position = "static";
    }
  }
};
/*解决浏览器兼容问题*/
function get_scrollTop_of_body() {
  let scrollTop;
  if (typeof window.pageYOffset != "undefined") {
    //pageYOffset指的是滚动条顶部到网页顶部的距离
    scrollTop = window.pageYOffset;
  } else if (
    typeof document.compatMode != "undefined" &&
    document.compatMode != "BackCompat"
  ) {
    scrollTop = document.documentElement.scrollTop;
  } else if (typeof document.body != "undefined") {
    scrollTop = document.body.scrollTop;
  }
  return scrollTop;
}
</script>