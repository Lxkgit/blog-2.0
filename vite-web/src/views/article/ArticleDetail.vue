<template>
  <div>
    <section class="detail">
      <NavMenu></NavMenu>
      <div class="detail-page">
        <div class="detail-left">
        </div>
        <div class="detail-center">
          <div class="current-position">
            <span>您的位置：</span>
            <span>
              <el-breadcrumb separator=">">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item v-for="item in articleData.data.articleTypes">
                  <a @click="toCategory(item.id)">
                    {{ item.typeName }}
                  </a>
                </el-breadcrumb-item>
                <el-breadcrumb-item>文章正文</el-breadcrumb-item>
              </el-breadcrumb>
            </span>
          </div>
          <div class="main detail-card">
            <div v-if="JSON.stringify(articleData.data) === '{}'">
              <el-skeleton :rows="20" animated />
            </div>
            <div v-else>
              <h1>{{ articleData.data.title }}</h1>
              <div class="info">
                <span class="type">
                  <MyIcon type="icon-category" />
                  <span v-for="item in articleData.data.articleTypes">{{ item.typeName }}</span>
                </span>
                <span>
                  <MyIcon type="icon-tag" />
                  <span v-for="(label, index) in articleData.data.articleLabels" :key="index">{{ label.labelName }}</span>
                </span>
                <span>
                  <MyIcon type="icon-time" />{{ timeFull(articleData.data.createTime) }}
                </span>
                <span>
                  <MyIcon type="icon-view" />{{ articleData.data.browseCount }}
                </span>
                <span>
                  <MyIcon type="icon-like" />{{ articleData.data.likeCount }}
                </span>
                <span>
                  <MyIcon type="icon-collect" />{{ articleData.collect }}
                </span>
                <span>
                  <MyIcon type="icon-comment" />{{ articleData.comment }}
                </span>
              </div>
              <MarkDown :text="articleData.data.contentMd"></MarkDown>
            </div>
            <div class="context">
              <span :class="context.last ? 'detail-context-hover' : ''" @click="toDetail(context.last.id)">
                <p>
                  <MyIcon type="icon-last" />
                </p>
                <p v-if="context.last">{{ context.last.title }}</p>
                <p v-else>已是第一篇</p>
              </span>
              <span>
                <p>文章分类：
                  <span v-for="item in articleData.data.articleTypes" class="tag article-tag-hover"
                    :style="'background-color: ' + tagColor(item.id)">
                    {{ item.typeName }}
                  </span>
                </p>
                <p>文章标签：
                  <span v-for="item in articleData.data.articleLabels" class="tag article-tag-hover"
                    :style="'background-color: ' + tagColor(item.id)">
                    {{ item.labelName }}
                  </span>
                </p>
              </span>
              <span :class="context.next ? 'detail-context-hover' : ''" @click="toDetail(context.next.id)">
                <p>
                  <MyIcon type="icon-next" />
                </p>
                <p v-if="context.next">{{ context.next.title }}</p>
                <p v-else>已是最后一篇</p>
              </span>
            </div>
          </div>
          <div class="guess detail-card">
            <h2>💖 猜你喜欢</h2>
            <div>
              <span class="recommend-hover" v-for="item in recommendList" @click="toDetail(item.id)">
                <el-image :src="item.cover" style="width: 90%" :fit="'fill'" lazy>
                  <template #placeholder>
                    <Loading></Loading>
                  </template>
                </el-image>
                <p>{{ item.title }}</p>
              </span>
            </div>
          </div>
          
        </div>
        <div class="detail-right">
          <Outline @rollTo="rollTo" :scrollTop="scrollTop"></Outline>
          <Action :detailType="'article'" @likeClick="likeClick" :isCollect="isCollect" @collectClick="collectClick">
          </Action>
          <BackTop></BackTop>
        </div>
      </div>
      <Footer></Footer>
    </section>
  </div>
</template>

<script setup name="ArticleDetail" lang="ts">
import NavMenu from "@/components/common/NavMenu.vue";
import Loading from "@/components/common/Loading.vue"
import Footer from "@/components/common/Footer.vue"
import BackTop from "@/components/common/BackTop.vue"
import MarkDown from "@/components/detail/MarkDown.vue"
import Action from "@/components/detail/Action.vue"
import Outline from "@/components/detail/Outline.vue"
import { ElLoading, ElMessage } from 'element-plus'

import { onMounted, reactive, ref, onBeforeUnmount, nextTick, getCurrentInstance } from "vue";
import { onBeforeRouteUpdate, useRouter } from "vue-router";
import timeFormat from "@/utils/timeFormat";
import icon from "@/utils/icon";
import color from "@/utils/color";
import user from "@/utils/user";
import { systemStore } from "@/store/system";
import { getArticleByIdApi } from "@/api/content"

const store = systemStore()
let { MyIcon } = icon()
let { timeFull } = timeFormat()
let { tagColor } = color()
const router = useRouter()
// 引入用户信息模块
let { userId, isLogin } = user();
// 引入公共模块
let { articleID, sitename, toDetail, toCategory } = publicFn()
// 引入文章内容模块
let { articleData, context, recommendList, getArticleData, getContextData, getGuessLikeData } = article()
// 引入markdown模块
let { rollTo, scrollTop, scroll } = markdown()
// 调用动作菜单模块
let { likeClick, isCollect, getArticleHistoryData, collectClick, postArticleHistoryData } = action(articleID, articleData)

onMounted(async () => {
  // 开启加载中动画
  const loading = ElLoading.service({
    lock: true,
    text: '正在加载中……',
    background: 'rgba(255, 255, 255, 0.3)',
  })
  window.scrollTo({ top: 0 })
  store.setOutline("")
  articleID.value = router.currentRoute.value.params.id
  await getArticleData(articleID.value)
  loading.close()
  // await getContextData(articleID.value)
  // await getGuessLikeData(articleID.value)
  // await postArticleHistoryData(articleID.value)
  window.addEventListener('scroll', scroll())
})

onBeforeUnmount(() => {
  window.removeEventListener('scroll', scroll())
})

onBeforeRouteUpdate(async (to) => {
  // 开启加载中动画
  const loading = ElLoading.service({
    lock: true,
    text: '正在加载中……',
    background: 'rgba(255, 255, 255,0.3)',
  })
  window.scrollTo({ top: 0 })
  store.setOutline("")
  await getArticleData(to.params.id)
  loading.close()
});

// 公共模块
function publicFn() {
  // 当前文章id
  const articleID = ref()
  // 站点名称
  const sitename = ref('')
  //跳转文章列表
  const toCategory = (categoryId: any) => {
    router.push({ path: `/category/${categoryId}` })
  }

  // 获取站点名称
  async function siteConfigData() {
    // let siteConfig_data = await getSiteConfig()
    // sitename.value = siteConfig_data.name
  }

  // 点击跳转其他文章事件
  const toDetail = (detailID: any) => {
    articleID.value = detailID
    router.push({ path: `/detail/article/${articleID.value}` })
  }
  onMounted(() => {
    siteConfigData()
  })
  return { articleID, sitename, toDetail, toCategory }
}

// 文章模块
function article() {
  // 当前文章分类id
  const activeMenu = ref()
  // 文章详情数据
  let articleData: any = reactive({ data: {} })
  // 文章上下篇
  const context: any = reactive({})
  // 猜你喜欢
  const recommendList: any = ref([])

  // 获取文章详情
  async function getArticleData(DetailID: any) {
    const detail_data: any = await getArticleByIdApi(DetailID)
    articleData.data = detail_data.result
  }

  // 获取文章上下篇
  async function getContextData(DetailID: any) {
  }

  // 获取猜你喜欢
  async function getGuessLikeData(DetailID: any) {
  }
  return { articleData, context, recommendList, getArticleData, getContextData, getGuessLikeData }
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
      heading.scrollIntoView({ behavior: "smooth", block: "start" })
    }
  }
  // markdown-页面滚动高度
  const scrollTop = ref()
  // markdown-页面滚动
  const scroll = () => {
    let timeOut: any = null; // 初始化空定时器
    return () => {
      clearTimeout(timeOut)   // 频繁操作，一直清空先前的定时器
      timeOut = setTimeout(() => {  // 只执行最后一次事件
        scrollTop.value = window.scrollY
      }, 500)
    }
  }
  return { rollTo, scrollTop, scroll }
}

// 侧边栏动作模块
function action(articleID: any, articleData: any) {
  // 引入用户信息模块
  let { userId, isLogin } = user();
  // 文章点赞事件
  const likeClick = () => {
    const params = { like: articleData.like + 1 }
  }
  // 文章收藏状态
  const isCollect = ref(false)

  // 获取文章浏览记录（是否已收藏）
  async function getArticleHistoryData() {
  }

  // 添加/取消收藏表单
  const CollectForm = reactive({
    user: '',
    is_collect: ''
  })
  // 子组件添加/取消收藏事件
  const collectClick = () => {

  }
  // 添加文章浏览记录表单
  const articleHistoryForm = reactive({
    article_id: '',
    user: ''
  })

  // 添加文章浏览记录
  async function postArticleHistoryData(article_id: any) {
    if (isLogin.value === true) {
      articleHistoryForm.article_id = article_id
      articleHistoryForm.user = userId.value
    }
  }

  onMounted(() => {
    getArticleHistoryData()
  })
  return {
    likeClick, isCollect, getArticleHistoryData, collectClick, postArticleHistoryData
  }
}
</script>

<style scoped lang="scss">
.detail {
  .detail-page {
    margin-top: 10px;
    display: flex;
    justify-content: space-between;

    .detail-left {
      width: 15%;
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

          .type {
            span:nth-child(3) {
              margin-left: 10px;
            }
          }

          >span {
            margin: 0 2%;

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
            border-right: 2px solid var(--el-text-color-placeholder);

            span {
              margin: 0 5px
            }
          }
        }
      }

      .guess {
        >div {
          display: flex;
          justify-content: center;
          align-items: center;
          margin: 10px;

          &:hover {
            span {
              opacity: 0.3;
            }
          }

          span {
            text-align: center;
            flex: 1;

            &:hover {
              opacity: 1;
            }

            p {
              font-size: 14px;
              color: var(--el-text-color-secondary);
              margin: 10px 0px;
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
          padding: 0px 15px 0px 5px;
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
    background-color: var(--el-bg-color-overlay);
  }
}
</style>
