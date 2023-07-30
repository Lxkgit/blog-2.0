<template>
  <div>
    <section class="detail">
      <NavMenu></NavMenu>
      <div class="detail-page">
        <div  :class="'detail-left animate__animated animate__' +
          (catalogShow === true ? 'fadeIn' : 'fadeOut')
          " >
          <el-tree v-if="catalogShow" accordion :data="catalogList" @node-click="handleNodeClick" style="overflow-x:auto; overflow-y:auto; height: calc(100vh - 142px); "
            :default-expanded-keys="expanded" node-key="id" :highlight-current="true" :current-node-key="current"
            ref="treeRef"></el-tree>
        </div>
        <div class="detail-center">
          <div class="current-position">
            <span>您的位置：</span>
            <span>
              <el-breadcrumb separator=">">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item><a @click="">
                    {{ sectionData.data.label }}</a></el-breadcrumb-item>
                <el-breadcrumb-item>笔记正文</el-breadcrumb-item>
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
                <!-- <span>
                  <MyIcon type="icon-category" />{{ sectionData.note }}
                </span> -->
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
      <LoginPopup ref="loginPopupRef"></LoginPopup>
    </section>
  </div>
</template>

<script setup name="SectionDetail" lang="ts">
import NavMenu from "@/components/common/NavMenu.vue";
import Footer from "@/components/common/Footer.vue";
import BackTop from "@/components/common/BackTop.vue";
//@ts-ignore
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
import { getDocCatalogTreeApi, getDocContentByIdApi } from "@/api/content";

const store = systemStore();
let { MyIcon } = icon();
let { timeFull } = timeFormat();
const router = useRouter();

// 引入用户信息模块
let { userId, isLogin } = user();
// 引入公共模块
let { sectionID, contentID } = publicFn();
// 引入笔记内容模块
let { docTitle, sectionData, getDocContentByIdFun, contextData } = section();
// 引入笔记目录模块
let { catalogShow, catalogList, expanded, current, catalogueData, handleNodeClick, treeRef } = catalog();
// 引入markdown模块
let { rollTo, scrollTop, scroll } = markdown();
// 调用动作菜单模块
let { likeClick, isCollect, collectClick } = action(sectionID, sectionData);

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
  store.setMenuIndex(3);
  loading.close();
  await catalogueData();
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

  onMounted(() => {
    // siteConfigData();
  });
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
        let data: any = res.result;
        catalogList.value = data.map((i: any, index: any) => {
          return {
            id: i["id"],
            label: i["docName"],
            docType: i["docType"],
            children: (i["list"] || []).map((j: any, index: any) => {
              return {
                id: j["id"],
                label: j["docName"],
                docType: j["docType"],
                children: (j["list"] || []).map((k: any, index: any) => {
                  return {
                    id: k["id"],
                    label: k["docName"],
                    docType: k["docType"],
                    children: NaN,
                  };
                }),
              };
            }),
          };
        });
      }
    });
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
    treeRef,
  };
}

// 笔记内容模块
function section() {
  // 当前导航栏id
  const activeMenu = ref();
  // 当前笔记标题
  let docTitle = ref("")
  // 笔记详情数据
  let sectionData: any = reactive({ data: {} });
  // 笔记上下篇
  // const context: any = reactive({});

  // 获取笔记详情
  async function getDocContentByIdFun(contentId: any) {
    contentID.value = contentId
    getDocContentByIdApi(contentId).then((res: any) => {
      if (res.code === 200) {
        sectionData.data = {}
        sectionData.data = res.result
      }
    })
    // const detail_data = await getSectionDetail(DetailID)
    // for (let i in detail_data) {
    //   if (i === 'body') {
    //     // 图片防盗链处理
    //     sectionData.body = detail_data.body
    //     const pattern = /!\[(.*?)\]\((https:\/\/cdn.nlark.com.*?)\)/gm;
    //     let matcher;
    //     let imgArr = [];
    //     while ((matcher = pattern.exec(sectionData.body)) !== null) {
    //       imgArr.push(matcher[2]);
    //     }
    //     for (let i = 0; i < imgArr.length; i++) {
    //       sectionData.body = sectionData.body.replace(
    //         imgArr[i],
    //         getImgProxy(imgArr[i])
    //       );
    //     }
    //   } else {
    //     sectionData[i] = detail_data[i]
    //   }
    // }
  }

  // 获取笔记上下篇
  async function contextData(DetailID: any) {
    // Object.assign(context, await getContextSection(DetailID));
    // console.log(context)
  }

  return { docTitle, sectionData, getDocContentByIdFun, contextData };
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
function action(sectionID: any, sectionData: any) {
  // 引入用户信息模块
  let { userId, isLogin } = user();
  // 笔记点赞事件
  const likeClick = () => {
    console.log("爹收到点赞事件了");
    // const params = { 'like': sectionData.like + 1 }
    // patchSectionDetail(sectionID.value, params).then((response) => {
    //   console.log(response)
    //   ElMessage({
    //     message: '笔记点赞成功！',
    //     type: 'success',
    //   })
    //   sectionData.like = params.like
    // }).catch(response => {
    //   //发生错误时执行的代码
    //   console.log(response)
    //   ElMessage.error(response.msg)
    // });
  };
  // 笔记收藏状态
  const isCollect = ref(false);

  // 获取笔记浏览记录（是否已收藏）
  async function getSectionHistoryData() {
    // await nextTick()
    // if (isLogin.value === true) {
    //   let res = await getSectionHistory(sectionID.value, userId.value)
    //   console.log(res)
    //   isCollect.value = res.is_collect
    //   console.log(isCollect.value)
    // }
  }

  // 添加/取消收藏表单
  const CollectForm = reactive({
    user: "",
    is_collect: "",
  });
  // 子组件添加/取消收藏事件
  const collectClick = () => {
    // if (isLogin.value === true) {
    //   console.log("当前收藏状态是", isCollect.value)
    //   isCollect.value = !isCollect.value
    //   CollectForm.user = userId.value
    //   CollectForm.is_collect = isCollect.value
    //   CollectForm['section_id'] = sectionID
    //   putSectionHistory(CollectForm).then((response) => {
    //     console.log(response)
    //     if (response.is_collect === true) {
    //       ElMessage({
    //         message: '已添加收藏！',
    //         type: 'success',
    //       })
    //     } else {
    //       ElMessage({
    //         message: '已取消收藏！',
    //         type: 'success',
    //       })
    //     }
    //   }).catch(response => {
    //     //发生错误时执行的代码
    //     console.log(response)
    //     ElMessage.error(response.msg)
    //   });
    // } else {
    //   console.log("先登录")
    //   store.commit('setNextPath', router.currentRoute.value.fullPath)
    //   loginPopupRef.value.showPopup()
    // }
  };
  // 添加笔记浏览记录表单
  const sectionHistoryForm = reactive({
    section_id: "",
    user: "",
  });

  // 添加笔记浏览记录
  async function postSectionHistoryData(section_id: any) {
    // if (isLogin.value === true) {
    //   sectionHistoryForm.section_id = section_id
    //   sectionHistoryForm.user = userId.value
    //   console.log(sectionHistoryForm)
    //   let res = await postSectionHistory(sectionHistoryForm)
    //   console.log(res)
    // }
  }

  onMounted(() => {
    getSectionHistoryData();
  });
  return {
    likeClick,
    isCollect,
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
    padding: 20px 10px;
    min-height: 700px;
    background-color: var(--el-bg-color-overlay);
  }
}
</style>
