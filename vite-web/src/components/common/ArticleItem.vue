<template>
  <section class="article-item article-item-hover">
    <span class="describe no-choose">
      <h2 class="article-title-hover" @click="toDetail(article.id)">{{ article.title }}</h2>
      <p class="article-abstract-hover" @click="toDetail(article.id)">{{ article.contentMd }}</p>
      <div class="info">
        <span>
          {{ article.blogUser.nickname }}
        </span>
        <span class="tag article-tag-hover" :style="'background-color: ' + tagColor(article.articleTypes[0].id)">{{
          article.articleTypes[0].typeName
        }}</span>
        <span>
          <MyIcon type="icon-4TIME" />{{ timeAgo(article.createTime) }}
        </span>
        <span>
          <MyIcon type="icon-eye1" />{{ article.browseCount }}
        </span>
        <span>
          <MyIcon type="icon-like" />{{ article.likeCount }}
        </span>
        <!-- <span>
          <MyIcon type="icon-collect" />{{ article.collect }}
        </span> -->
        <span>
          <MyIcon type="icon-review" />1{{ article.comment }}
        </span>
      </div>
    </span>
    <span class="cover" @click="toDetail(article.id)">
      <!-- <el-image :src="article.cover" style="width: 270px; height: 180px" :fit="'fill'" lazy> -->
      <el-image src="https://img2.baidu.com/it/u=2241198009,1203637343&fm=253&fmt=auto" style="width: 270px; height: 180px" :fit="'fill'" lazy>
        <template #placeholder>
          <Loading type="image"></Loading>
        </template>
      </el-image>
    </span>
  </section>
  <el-divider></el-divider>
</template>

<script setup lang="ts">
import Loading from "@/components/common/Loading.vue"
import icon from "@/utils/icon";
import timeFormat from "@/utils/timeFormat";
import color from "@/utils/color";
import { useRouter } from "vue-router";

const router = useRouter()
const props = defineProps({
  // 参数校验与默认值
  article: {
    type: Object,
    required: true,
  },
})
let { MyIcon } = icon()
let { timeAgo } = timeFormat()
let { tagColor } = color()
// 跳转文章详情页
const toDetail = (detailID: any) => {
  router.push({ path: `/detail/article/${detailID}` })
}
</script>

<style scoped lang="scss">
.article-item {
  display: flex;
  padding: 10px;
  margin-bottom: 10px;
  border-radius: 10px;

  .describe {
    margin-left: 10px;
    min-width: 555px;

    h2 {
      color: var(--el-text-color-primary);
      margin: 0;
    }

    p {
      margin: 15px 0;
      color: var(--el-text-color-secondary);
      line-height: 28px;
      height: 108px;
      overflow: hidden;
      -webkit-line-clamp: 4;
      display: -webkit-box;
      -webkit-box-orient: vertical;
    }

    .info {
      display: flex;
      justify-content: center;
      align-items: center;
      color: var(--el-text-color-placeholder);

      span {
        margin: 0 5px 0 15px;
      }
    }
  }

  .cover {
    width: 270px;
    padding: 3px;
    display: inline-block;
    border: 1px solid var(--el-bg-color);
    border-radius: 8px;
  }
}

.el-divider--horizontal {
  margin: 5px 0 !important;
}
</style>
