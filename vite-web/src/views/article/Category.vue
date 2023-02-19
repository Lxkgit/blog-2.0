<template>
  <section class="category">
    <NavMenu></NavMenu>
    <div class="page">
      <article class="animate__animated animate__fadeInLeft">
        <div class="current-position">
          <span>您的位置：</span>
          <span>
            <el-breadcrumb separator=">">
              <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
              <el-breadcrumb-item>文章分类</el-breadcrumb-item>
              <el-breadcrumb-item v-for="item in articleType.date"> {{ item.typeName }} </el-breadcrumb-item>
            </el-breadcrumb>
          </span>
        </div>
        <div class="article-list">
          <el-card class="box-card">
            <template #header>
              <div class="card-header">
                <span class="card-title">📜 文章列表</span>
              </div>
            </template>
            <ul>
              <li v-for="item in article.list" :key="item.id">
                <ArticleItem :article="item"></ArticleItem>
              </li>
            </ul>
            <div class="paging">
              <Pagination :total="parseInt(article.total)" @changePage="changePage"></Pagination>
            </div>
          </el-card>
        </div>
      </article>
      <aside>
        <Aside></Aside>
      </aside>
    </div>
    <Footer></Footer>
    <BackTop></BackTop>
  </section>
</template>

<script setup name="Category" lang="ts">
import NavMenu from "@/components/common/NavMenu.vue";
import ArticleItem from "@/components/common/ArticleItem.vue";
import Aside from "@/components/common/Aside.vue"
import Footer from "@/components/common/Footer.vue"
import BackTop from "@/components/common/BackTop.vue"
import Pagination from "@/components/common/Pagination.vue"
import { onActivated, onMounted, reactive, ref } from "vue";
import { onBeforeRouteUpdate, useRouter } from "vue-router";
import { systemStore } from "@/store/system";
import { getArticleList, getArticleTypeById } from "@/api/content";

const store = systemStore()
const router = useRouter()
// 当前文章分类id
const categoryID = ref()
// 文章分类名
const articleType:any = reactive({ date: [] })

// 获取文章分类名称
async function articleTypeData(categoryID: any) {
  getArticleTypeById(categoryID).then((res: any) => {
    if (res.code === 200) {
      articleType.date = res.result
    }
  })

}

// 文章列表
const article: any = reactive({
  list: [],
  total: '',
})

// 获取文章数据
async function articleData(page: any, size: any, categoryID: any) {
  const params = {
    pageNum: page,
    pageSize: size,
    articleType: categoryID
  }
  getArticleList(params).then((res: any) => {
    if (res.code === 200) {
      console.log(JSON.stringify(res) + " --- ")
      article.list = res.result.list
      article.total = res.result.total
    }
  })
}

// 分页-页面跳转
const changePage = (pageSize: any, pageNumber: any) => {
  console.log("categoryID", categoryID.value)
  window.scrollTo({ top: 0 })
  console.log(pageSize, pageNumber)
  articleData(pageNumber, pageSize, categoryID.value)
}

onMounted(() => {
  categoryID.value = router.currentRoute.value.params.id
  articleTypeData(categoryID.value)
  articleData(1, 10, categoryID.value)
})
onBeforeRouteUpdate(async (to) => {
  categoryID.value = to.params.id
  await articleTypeData(categoryID.value)
  await articleData(1, 10, categoryID.value)
});
onActivated(() => {
  store.setMenuIndex('2-' + router.currentRoute.value.params.id)
})
</script>

<style scoped lang="scss">
.category {
  .article-list {
    margin-top: 15px;

    ul {
      list-style-type: none;
      padding: 0;
      margin: 0;

      li {}
    }

    .paging {}
  }
}
</style>
