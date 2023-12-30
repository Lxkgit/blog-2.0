<template>
  <section class="aside animate__animated animate__fadeInRight">
    <el-card class="card-hover">
      <template #header>
        <span class="card-title no-choose">🔈 网站公告</span>
      </template>
      <div class="recommend">
        {{ webNotice.value }}
      </div>
    </el-card>
    <el-card class="card-hover">
      <template #header>
        <span class="card-title no-choose">👍 推荐阅读</span>
      </template>
      <div class="recommend">
        <span class="recommend-hover" v-for="article in recommend" :key="article.id" @click="toDetail(article.id)">
          <el-image style="width: 115px;height: 76px" :src="article.cover" :fit="'fill'">
            <template #placeholder>
              <span class="loading" v-loading="true"></span>
            </template>
          </el-image>
          <p class="no-choose">{{ article.title }}</p>
        </span>
      </div>
    </el-card>
    <el-card class="card-hover">
      <template #header>
        <el-dropdown @visible-change="dropdownChange" @command="handleCommand">
          <span class="no-choose">
            <span class="card-title no-choose">🔥 {{ isRanking }}
              <i :class="isDropdown ? 'el-icon-caret-top' : 'el-icon-caret-bottom' + ' el-icon&#45;&#45;right'"></i>
              <el-icon v-if="isDropdown">
                <ArrowUp />
              </el-icon>
              <el-icon v-else>
                <ArrowDown />
              </el-icon>
            </span>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item v-for="(item, index) in ranking" :key="index" :command="index"
                :disabled="item.name === isRanking ? true : false">
                {{ item.name }}
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </template>
      <ol class="ranking" v-loading="rankingLoading">
        <li v-for="article in articleRanking" :key="article.id" @click="toDetail(article.id)">
          <p class="no-choose ranking-hover">{{ article.title }}</p>
        </li>
      </ol>
    </el-card>
    <el-card class="card-hover tag-box">
      <template #header>
        <span class="card-title no-choose">🏷️ 所有标签</span>
      </template>
      <div class="all-tag">
        <TagCloud></TagCloud>
      </div>
    </el-card>
    <el-card class="card-hover">
      <template #header>
        <span class="card-title no-choose">📊 网站统计</span>
      </template>
      <div class="statistics">
        <div>
          <MyIcon type="icon-time" />
          运行时间：<span v-html='runTimeString'></span>
        </div>
        <div>
          <MyIcon type="icon-fangwenliang" />
          总访问: {{ blogData.data.visits }}次
        </div>
        <div>
          <MyIcon type="icon-fangwenrenshu" />
          用户: {{ blogData.data.userCount }}人
        </div>
        <div>
          <MyIcon type="icon-ip" />
          访问IP: {{ blogData.data.ipCount }}个
        </div>
        <div>
          <MyIcon type="icon-article" />
          文章: {{ blogData.data.articleCount }}篇
        </div>
        <!-- <div>
          <MyIcon type="icon-wenzhangfenlei" />
          文章分类数: {{ blogData.data.articleTypeCount }}个
        </div> -->
        <div>
          <MyIcon type="icon-tag" />
          标签: {{ blogData.data.articleLabelCount }}个
        </div>
        <div>
          <MyIcon type="icon-book" />
          笔记: {{ blogData.data.docCount }}篇
        </div>
        <!-- <div>
          <MyIcon type="icon-wenzhangfenlei1" />
          笔记分类数: {{ blogData.data.docTypeCount }}个
        </div> -->
      </div>
    </el-card>
  </section>
</template>

<script setup lang="ts">
import { ArrowDown, ArrowUp } from '@element-plus/icons-vue'
import TagCloud from "@/components/common/TagCloud.vue";
import { onMounted, reactive, ref, onUnmounted } from "vue";
import timeFormat from "@/utils/timeFormat";
import icon from "@/utils/icon";
import { useRouter } from "vue-router";
import { selectBlogDataApi, selectBlogSettingByIdApi } from "@/api/file"
import mitter from "@/utils/mitt";

let { MyIcon } = icon()

const router = useRouter()
const webNotice: any = ref({})
// 推荐阅读文章列表
const recommend: any = ref([])
// 博客数据
const blogData: any = reactive({data: {}})
async function recommendData() {
  const params = {
    page: 1,
    size: 6,
    ordering: '-is_recommend,-created_time'
  }
}

mitter.on("SYSTEM_DATA", (data: any) => {
  blogData.data.visits = data.visits
  blogData.data.userCount = data.userCount
  blogData.data.ipCount = data.ipCount
  blogData.data.articleCount = data.articleCount
  blogData.data.articleTypeCount = data.articleTypeCount
  blogData.data.articleLabelCount = data.articleLabelCount
  blogData.data.docCount = data.docCount
  blogData.data.docTypeCount = data.docTypeCount
})

//Vue3中的写法
onUnmounted(()=>{
  mitter.off("SYSTEM_DATA")
})


// 排行列表-全部种类
const ranking: any = ref([
  { name: '阅读排行', value: '-view' },
  { name: '点赞排行', value: '-like' },
  { name: '收藏排行', value: '-collect' },
  { name: '评论排行', value: '-comment' },
])
// 排行列表-当前种类
const isRanking: any = ref('阅读排行')
// 排行列表-文章排行
const articleRanking: any = ref([])
// 排列列表-加载状态
const rankingLoading: any = ref(false)
// 排行列表-切换种类
const handleCommand = (index: any) => {
  rankingLoading.value = true
  isRanking.value = ranking.value[index].name
  const params = {
    page: 1,
    size: 10,
    ordering: ranking.value[index].value
  }
};
// 排行列表-是否下拉状态
const isDropdown: any = ref(false)

// 排行列表-下拉事件
const dropdownChange = (value: any) => {
  isDropdown.value = value
}

async function rankingData() {
  const params = {
    page: 1,
    size: 10,
    ordering: '-view',
  }
}

// 跳转文章详情页
const toDetail = (detailID: any) => {
  router.push({ path: `/detail/article/${detailID}` })
}

const selectBlogDataFun = () => {
  selectBlogDataApi().then((res: any) => {
      if(res.code === 200) {
        blogData.data = res.result
        runTime(res.result.deploymentTime)
      }
  })
}

const selectBlogSettingByIdFun = () => {
  selectBlogSettingByIdApi(2).then((res: any) => {
    webNotice.value = res.result
  })
}

// 运行时间
let runTimeString = ref();
const runTime = (time: any) => {
  let oldTime = new Date(time)
  setInterval(function () {
    let nowTime = new Date()
    let longTime = nowTime.getTime() - oldTime.getTime()
    let days = parseInt(String(longTime / 1000 / 60 / 60 / 24), 10) // 计算剩余的天数
    let hours = parseInt(String(longTime / 1000 / 60 / 60 % 24), 10) // 计算剩余的小时
    let minutes = parseInt(String(longTime / 1000 / 60 % 60), 10) // 计算剩余的分钟
    runTimeString.value = days + '天' + hours + '时' + minutes + '分';
  }, 1000)

}

onMounted(() => {
  recommendData()
  rankingData()
  selectBlogDataFun()
  selectBlogSettingByIdFun()
})
</script>

<style scoped lang="scss">
.aside {
  .recommend {
    display: flex;
    flex-wrap: wrap;
    transition: all 0.5s;

    &:hover {
      span {
        opacity: 0.5;
      }
    }

    span {
      width: 113px;

      &:hover {
        opacity: 1;
      }

      p {
        text-align: center;
        color: var(--el-text-color-regular);
        margin: 7px 0;
        line-height: 20px;
        font-size: 14px;
      }
    }

    span:nth-child(odd) {
      margin-right: 12px;
    }
  }

  .ranking {
    padding-left: 25px;
    line-height: 28px;

    li {
      p {
        color: var(--el-text-color-regular);
      }
    }

    li:nth-child(1) {
      color: #ff2c00;
    }

    li:nth-child(2) {
      color: #ff5a00;
    }

    li:nth-child(3) {
      color: #ff8105;
    }

    li:nth-child(4) {
      color: #fd9a15;
    }

    li:nth-child(5) {
      color: #dfad1c;
    }

    li:nth-child(6) {
      color: #6bc211;
    }

    li:nth-child(7) {
      color: #3cc71e;
    }

    li:nth-child(8) {
      color: #3cbe85;
    }

    li:nth-child(9) {
      color: #51b2ef;
    }

    li:nth-child(10) {
      color: #3498db;
    }
  }

  .info {
    line-height: 30px;
    color: var(--el-text-color-regular);

    .anticon {
      margin-right: 5px;
      font-size: 20px;
    }

    .contact {
      display: flex;
      justify-content: center;

      span {
        margin: 10px 3px 0 3px;

        .anticon {
          font-size: 30px !important;
        }
      }

      span:hover {
        cursor: pointer;
      }
    }
  }

  .statistics {
    div {
      font-size: 16px;
      line-height: 30px;
      color: var(--el-text-color-regular);

      .anticon {
        margin-right: 5px;
      }
    }
  }
}

.loading {
  display: flex;
  justify-content: center;
  padding-top: 80px;
}

.el-card {
  margin-bottom: 15px;
}
</style>

