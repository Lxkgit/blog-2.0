<template>
  <div id="sub-menu" style="display: flex; width: 100%">
    <a-menu
      v-model:selectedKeys="current"
      mode="horizontal"
      style="width: 100%; border: 0; justify-content: center"
    >
      <a-menu-item key="mail"> 前端框架 </a-menu-item>
      <a-sub-menu key="sub1">
        <template #title>Java基础</template>
        <a-menu-item key="List1">List</a-menu-item>
        <a-menu-item key="Map1">Map</a-menu-item>
        <a-menu-item key="多线程1">多线程</a-menu-item>
      </a-sub-menu>
      <a-sub-menu key="sub2">
        <template #title>Java基础</template>
        <a-menu-item key="List2">List</a-menu-item>
        <a-menu-item key="Map2">Map</a-menu-item>
        <a-menu-item key="多线程2">多线程</a-menu-item>
        <a-sub-menu key="sub2-2">
          <template #title>Java基础</template>
          <a-menu-item key="List-2">List</a-menu-item>
          <a-menu-item key="Map-2-2">Map</a-menu-item>
          <a-menu-item key="多线程-2-2    ">多线程</a-menu-item>
        </a-sub-menu>
      </a-sub-menu>
      <a-sub-menu key="sub3">
        <template #title>Java基础</template>
        <a-menu-item key="List3">List</a-menu-item>
        <a-menu-item key="Map3">Map</a-menu-item>
        <a-menu-item key="多线程3">多线程</a-menu-item>
      </a-sub-menu>
      <a-sub-menu key="sub4">
        <template #title>Java基础</template>
        <a-menu-item key="List4">List</a-menu-item>
        <a-menu-item key="Map4">Map</a-menu-item>
        <a-menu-item key="多线程4">多线程</a-menu-item>
      </a-sub-menu>
    </a-menu>
  </div>
  <a-row justify="center">
    <!-- <a-col :lg="12" :md="24" :sm="24" :xs="24">
            <a-list item-layout="vertical" :loading="initLoading" size="large" :data-source="listData">
                <template #renderItem="{ item }">
                    <a-list-item key="item.title">
                        <a-list-item-meta>
                            <template #avatar>
                                <a-avatar :src="item.avatar" />
                            </template>
                            <template #title>
                                <a :href="item.href">{{ blog[0] }} + {{item.user.name}}</a>
                            </template>
                        </a-list-item-meta>

                        <a :href="item.href" style="color: #000000;">
                            <h2 style="text-align: center;">{{ item.title }}</h2>
                            {{ sliceStr(item.content) }}
                        </a>
                        <div style="margin-top: 10px;">
                            <a-tag v-for="tag in tags" :color="tag.color">{{ tag.tag }}</a-tag>
                        </div>
                        <template #actions>
                            <span v-for="{ type, text } in actions" :key="type">
                                <component :is="type" style="margin-right: 8px" />
                                {{ text }}
                            </span>
                        </template>
                        <template #extra>
                            <img width="272" alt="logo"
                                src="https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png" />
                        </template>
                    </a-list-item>
                </template>
            </a-list>
        </a-col> -->
    <a-col :lg="12" :md="24" :sm="24" :xs="24">
      <a-list
        item-layout="vertical"
        :loading="initLoading"
        size="large"
        :data-source="blogList.data"
      >
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
              <h2 style="text-align: center">{{ item.title }}</h2>
              {{ sliceStr(item.contentMd) }}
            </a>
            <div style="margin-top: 10px">
              <a-tag v-for="(tag, idx) in item.articleLabels" :key="idx" color="pink">{{
                tag.labelName
              }}</a-tag>
            </div>
            <!-- <template #actions>
              <span :key="item.createTime">
                <component :is="item.createTime" style="margin-right: 8px" />
                {{ item.createTime }}
              </span>
              <span :key="item.likeCount">
                <component :is="item.likeCount" style="margin-right: 8px" />
                {{ item.likeCount }}
              </span>
              <span :key="item.browseCount">
                <component :is="item.browseCount" style="margin-right: 8px" />
                {{ item.browseCount }}
              </span>
            </template> -->
            <template #extra>
              <img
                width="272"
                alt="logo"
                src="https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
              />
            </template> 

          </a-list-item>
        </template>
      </a-list>
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
import { getBlogList } from "../../../api/test";
const blog = ref<string[]>(["username"]);
let blogList = reactive({
    data: []
});
const current = ref<string[]>(["mail"]);
let count = 6;
const initLoading = ref(true);
const loading = ref(false);
let listData: Record<string, string | object>[] = reactive([]);
//JS睡眠sleep()
const sleep = (numberMillis: number) => {
  var now = new Date();
  var exitTime = now.getTime() + numberMillis;
  while (true) {
    now = new Date();
    if (now.getTime() > exitTime) {
      return;
    }
  }
};

let testdata = reactive({ name: '张三'})


const tags = [
  {
    color: "pink",
    tag: "pink",
  },
  {
    color: "red",
    tag: "red",
  },
  {
    color: "blue",
    tag: "blue",
  },
];
onMounted(() => {
  initLoading.value = false;
  // for (let i = 0; i < count; i++) {
  //     listData.push({
  //         user: { id: 1, name: 'gszero' },
  //         href: 'https://www.antdv.com/',
  //         title: `ant design vue part ${i}`,
  //         avatar: 'https://joeschmoe.io/api/v1/random',
  //         description:
  //             'Ant Design, a design language for background applications, is refined by Ant UED Team.',
  //         content:
  //             'We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.',
  //     });
  // }


  getBlogList().then((res: any) => {
    blogList.data = res.list;
    console.log(blogList);
  });
});

const sliceStr = computed(() => {
  return function (val: string) {
    let len = 150;
    return val.length > len ? val.slice(0, len) + "..." : val;
  };
});

window.addEventListener("scroll", function () {
  //页面被卷去的高度: window.scrollY
  //页面被卷去的高度: window.pageYOffset
  //页面被卷去的高度: document.documentElement.scrollTop
  // console.log("页面被卷去的高度:",window.scrollY,window.pageYOffset,document.documentElement.scrollTop);

  // body页面的滚动条高度: document.body.scrollHeight
  // 整个页面你的滚动条高度: document.documentElement.scrollHeight
  // console.log(document.body.scrollHeight,document.documentElement.scrollHeight);

  // 可视页面的高度: document.documentElement.clientHeight
  // console.log(document.documentElement.clientHeight);

  if (
    document.documentElement.clientHeight + window.scrollY >=
    document.documentElement.scrollHeight
  ) {
    console.log("触底了!!!!");
    sleep(1000);
    for (let i = 0; i < count; i++) {
      listData.push({
        user: { id: 1, name: "gszero" },
        href: "https://www.antdv.com/",
        title: `【洛谷】四方定理 - dfs解法 ${i}`,
        avatar: "https://joeschmoe.io/api/v1/random",
        description:
          "Ant Design, a design language for background applications, is refined by Ant UED Team.",
        content:
          "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
      });
    }
  }
});

window.onscroll = function () {
  let topScroll = get_scrollTop_of_body(); //滚动的距离,距离顶部的距离
  let subMenu = document.getElementById("sub-menu"); //获取到导航栏id
  if (topScroll > 47) {
    subMenu.style.position = "fixed";
    subMenu.style.top = "0";
    subMenu.style.left = "0";
    subMenu.style.right = "0";
    subMenu.style.zIndex = "100";
    subMenu.style.borderBottom = "1px";
  } else {
    subMenu.style.position = "static";
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

const list = [
  { id: 1, type: "前端框架" },
  { id: 2, type: "Java基础" },
  { id: 3, type: "shell命令" },
  { id: 4, type: "centos安装配置" },
];

const pagination = {
  onChange: (page: number) => {
    console.log(page);
  },
  pageSize: 3,
};
const actions: Record<string, string>[] = [
  { type: "StarOutlined", text: "156" },
  { type: "LikeOutlined", text: "156" },
  { type: "MessageOutlined", text: "2" },
];
</script>