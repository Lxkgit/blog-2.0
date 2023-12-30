<template>
  <div>
    <section class="detail">
      <NavMenu></NavMenu>
      <div class="detail-page">
        <div :class="'detail-left animate__animated animate__' + (catalogShow === true ? 'fadeIn' : 'fadeOut')">
          <el-tree v-if="catalogShow" accordion :data="catalogList" @node-click="handleNodeClick"
            style="overflow-x:auto; overflow-y:auto; height: calc(100vh - 142px); " :default-expanded-keys="expanded"
            node-key="id" :highlight-current="true" :current-node-key="current" ref="treeRef">
            <template #default="{ node, data }">
              <span class="custom-tree-node">
                <span>
                  <el-tag v-if="data.docType === 1" class="mx-1" >文章</el-tag>
                  <el-tag v-else class="mx-1" size="small" type="success">目录</el-tag>
                </span>
                <span>{{ node.label }} </span>
              </span>
            </template></el-tree>
        </div>
        <div class="detail-center">
          <div class="current-position">
            <span>您的位置：</span>
            <span>
              <el-breadcrumb separator=">">
                <el-breadcrumb-item :to="{ path: '/document' }">文档</el-breadcrumb-item>
                <el-breadcrumb-item>{{ docName }}</el-breadcrumb-item>
                <el-breadcrumb-item v-if="docTitle === ''">首页</el-breadcrumb-item>
                <el-breadcrumb-item v-else>{{ docTitle }}</el-breadcrumb-item>
              </el-breadcrumb>
            </span>
          </div>
          <div class="main detail-card">
            <div v-if="contentID === 0">
              <div>文档首页</div>
            </div>
            <div v-else>
              <h1>{{ sectionData.title }}</h1>
              <div class="info">
                <span>
                  <MyIcon type="icon-like" />
                  {{ docTitle }}
                </span>
                <span>
                  <MyIcon type="icon-time" />{{
                    timeFull(sectionData.created_time)
                  }}
                </span>
                <span>
                  <MyIcon type="icon-view" />{{ sectionData.view }}
                </span>
                <span>
                  <MyIcon type="icon-like" />{{ sectionData.like }}
                </span>
              </div>
              <MarkDown :text="sectionData.data.docContentMd"></MarkDown>
            </div>
          </div>
        </div>
        <div class="detail-right">
          <Outline @rollTo="rollTo" :scrollTop="scrollTop"></Outline>
          <Action :detailType="'section'" @setCatalog="catalogShow = !catalogShow" :catalogShow="catalogShow"
            @likeClick="likeClick" :isCollect="isCollect" @collectClick="collectClick"></Action>
          <BackTop></BackTop>
        </div>
      </div>
      <Footer></Footer>
    </section>
  </div>
</template>

<script setup name="SectionDetail" lang="ts">
import NavMenu from "@/components/common/NavMenu.vue";
import Footer from "@/components/common/Footer.vue";
import BackTop from "@/components/common/BackTop.vue";
import MarkDown from "@/components/detail/MarkDown.vue";
import Action from "@/components/detail/Action.vue";
import Outline from "@/components/detail/Outline.vue";
import { ElMessage, ElLoading } from "element-plus";
import { onMounted, reactive, ref, onBeforeUnmount } from "vue";
import { onBeforeRouteUpdate, useRouter } from "vue-router";
import timeFormat from "@/utils/timeFormat";
import icon from "@/utils/icon";
import { systemStore } from "@/store/system";
import user from "@/utils/user";
import { getDocCatalogTreeApi, getDocCatalogByIdApi, getDocContentByIdApi } from "@/api/content";

const store = systemStore();
let { MyIcon } = icon();
let { timeFull } = timeFormat();
const router = useRouter();

// 引入用户信息模块
let { userId, isLogin } = user();
// 引入公共模块
let { sectionID, contentID } = publicFn();
// 引入笔记内容模块
let { docTitle, docName, sectionData, getDocContentByIdFun } = section();
// 引入笔记目录模块
let { catalogShow, catalogList, expanded, current, catalogueData, handleNodeClick, getDocCatalogByIdFun, treeRef } = catalog();
// 引入markdown模块
let { rollTo, scrollTop, scroll } = markdown();
// 调用动作菜单模块
let { likeClick, isCollect, collectClick } = action();

onMounted(async () => {
  // 开启加载中动画
  const loading = ElLoading.service({
    lock: true,
    text: "正在加载中……",
    background: "rgba(255, 255, 255, 0.3)",
  });
  window.scrollTo({ top: 0 });
  store.setOutline("");
  sectionID.value = router.currentRoute.value.params.id;
  store.setMenuIndex("3");
  loading.close();
  await catalogueData();
  await getDocCatalogByIdFun();
  window.addEventListener("scroll", scroll());
});
onBeforeUnmount(() => {
  window.removeEventListener("scroll", scroll());
  store.setOutline("");
});
onBeforeRouteUpdate(async (to) => {
  // 开启加载中动画
  const loading = ElLoading.service({
    lock: true,
    text: "正在加载中……",
    background: "rgba(255, 255, 255, 0.3)",
  });
  window.scrollTo({ top: 0 });
  store.setOutline("");
  loading.close();
});

// 公共模块
function publicFn() {
  // 当前笔记id
  const sectionID = ref();
  // 当前文档展示内容ID
  const contentID = ref(0);

  return { sectionID, contentID };
}

// 笔记目录模块
function catalog() {

  // 笔记目录是否显示
  const catalogShow = ref(true);
  // 树形组件对象
  const treeRef = ref(null);
  // 笔记目录列表
  const catalogList = ref([]);
  // 当前笔记展开的目录id
  const expanded = ref([]);
  // 当前高亮的笔记目录id
  const current = ref();
  // 获取笔记目录数据
  async function catalogueData() {
    getDocCatalogTreeApi({
      typeLowerLimit: 2,
      typeUpperLimit: 4,
      parentId: sectionID.value,
      type: 0
    }).then((res: any) => {
      if (res.code === 200) {
        catalogList.value = res.result;
      }
    });
  }
  // 获取笔记目录
  async function getDocCatalogByIdFun() {
    getDocCatalogByIdApi(sectionID.value).then((res: any) => {
      if (res.code === 200) {
        docName.value = res.result.docName
      }
    })
  }
  // 点击跳转指定笔记
  const handleNodeClick = (data: any) => {
    if (data.docType === 1) {
      docTitle.value = data.label
      getDocContentByIdFun(data.id)
    }
  };
  return {
    catalogShow,
    catalogList,
    expanded,
    current,
    catalogueData,
    handleNodeClick,
    getDocCatalogByIdFun,
    treeRef,
  };
}

// 笔记内容模块
function section() {

  // 当前笔记标题
  let docTitle = ref("")
  // 笔记名称
  let docName = ref("")
  // 笔记详情数据
  let sectionData: any = reactive({ data: {} });
  // 获取笔记详情
  async function getDocContentByIdFun(contentId: any) {
    contentID.value = contentId
    getDocContentByIdApi(contentId).then((res: any) => {
      if (res.code === 200) {
        sectionData.data = {}
        sectionData.data = res.result
      }
    })
  }

  return { docTitle, docName, sectionData, getDocContentByIdFun };
}

// markdown模块
function markdown() {
  // 点击大纲跳转事件
  const rollTo = (anchor: any) => {
    const { lineIndex } = anchor;
    const heading = document.querySelector(
      `.v-md-editor-preview [data-v-md-line="${lineIndex}"]`
    );
    if (heading) {
      heading.scrollIntoView({ behavior: "smooth", block: "start" });
    }
  };
  // markdown-页面滚动高度
  const scrollTop = ref();
  // markdown-页面滚动
  const scroll = () => {
    let timeOut: any = null; // 初始化空定时器
    return () => {
      clearTimeout(timeOut); // 频繁操作，一直清空先前的定时器
      timeOut = setTimeout(() => {
        // 只执行最后一次事件
        scrollTop.value = window.pageYOffset;
      }, 500);
    };
  };
  return { rollTo, scrollTop, scroll };
}

// 动作菜单模块
function action() {

  // 笔记收藏状态
  const isCollect = ref(false);

  // 笔记点赞事件
  const likeClick = () => {

  };

  // 子组件添加/取消收藏事件
  const collectClick = () => {

  };

  // 获取笔记浏览记录（是否已收藏）
  async function getSectionHistoryData() {
  }

  // 添加笔记浏览记录
  async function postSectionHistoryData(section_id: any) {
  }

  return {
    isCollect,
    likeClick,
    collectClick,
    getSectionHistoryData,
    postSectionHistoryData,
  };
}
</script>

<style scoped lang="scss">
.detail {
  .detail-page {
    margin-top: 10px;
    margin-bottom: 20px;
    display: flex;
    justify-content: space-between;

    .detail-left {
      width: 15%;
      overflow-x: auto;
      overflow-y: auto;

      .el-tree {
        width: 15%;
        position: fixed;
        background-color: var(--el-background-color-base);
      }
    }

    .detail-center {
      width: 70%;

      .main {
        h1 {
          text-align: center;
          margin: 20px 0;
        }

        .info {
          display: flex;
          justify-content: center;
          align-items: center;
          color: var(--el-text-color-regular);
          background-color: var(--el-border-color);
          padding: 5px 0px;
          margin: 10px 30px;
          border-radius: 20px;

          >span {
            margin: 0 4%;

            .anticon {
              margin-right: 10px;
            }
          }

          span:nth-child(2) {
            span:nth-child(2) {
              margin-right: 10px;
            }
          }
        }

        .context {
          display: flex;
          align-items: center;
          justify-content: center;
          color: var(--el-text-color-regular);
          background-color: var(--el-border-color);
          margin: 10px 30px;
          padding: 10px 10px;
          border-radius: 10px;

          span {
            flex: 1;
            text-align: center;

            .anticon {
              color: var(--el-color-primary);
              margin: 0 10px;
              font-size: 25px;
            }

            p {
              margin: 15px 0;
            }
          }

          >span:nth-child(2) {
            border-left: 2px solid var(--el-text-color-placeholder);

            span {
              margin: 0 5px;
            }
          }
        }
      }

      .comments {
        margin-bottom: 15px;

        .input-field {
          display: flex;
          justify-content: center;

          >span:nth-child(1) {
            width: 10%;
            padding-top: 10px;
            text-align: center;
          }

          >span:nth-child(2) {
            width: 80%;
          }

          >span:nth-child(3) {
            width: 10%;
            padding-top: 85px;
            text-align: center;
          }

          .editor {
            margin: 10px 0 30px 0 !important;
          }
        }

        .comment-list {
          padding: 0px 25px 0px 5px;
        }
      }

      h2 {
        border-bottom: 1px solid var(--el-border-color);
        padding: 10px 0;
        font-weight: normal;
      }
    }

    .detail-right {
      width: 15%;
    }
  }

  .detail-card {
    margin-top: 15px;
    padding: 20px 0px;
    min-height: 700px;
    background-color: var(--el-bg-color-overlay);
  }
}
</style>
