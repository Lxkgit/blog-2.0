<template>
    <div>
        <div class="title_style">
            <span>文章管理</span>
        </div>
        <el-card style="margin: 18px 2%;width: 95%">
            <el-button type="primary" plain @click="editArticle('null')">新增</el-button>
            <el-button type="danger" plain @click="deleteArticle(0)">删除</el-button>
            <el-table :data="articleList.list" stripe style="width: 100%" height="610" @selection-change="selected">
                <el-table-column type="selection" width="55">
                </el-table-column>
                <el-table-column prop="title" label="标题" fit>
                </el-table-column>
                <el-table-column label="文章分类" width="300">
                    <template #default="scope">
                        <el-tag style="margin-right: 2px; margin-bottom: 2px;" v-for="item in scope.row.articleTypes">
                            {{ item.typeName }}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="文章标签" width="180">
                    <template #default="scope">
                        <el-tag style="margin-right: 2px; margin-bottom: 2px;" v-for="item in scope.row.articleLabels">
                            {{ item.labelName }}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="文章状态" width="80">
                    <template #default="scope">
                        <el-tag v-if="(scope.row.articleStatus == 0)" class="ml-2" type="info">
                            {{ articleStatus(scope.row.articleStatus) }}
                        </el-tag>
                        <el-tag v-if="(scope.row.articleStatus == 1)" class="ml-2" type="success">
                            {{ articleStatus(scope.row.articleStatus) }}
                        </el-tag>
                        <el-tag v-if="(scope.row.articleStatus == 2)" class="ml-2">
                            {{ articleStatus(scope.row.articleStatus) }}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="updateTime" label="最近更新" width="162">
                </el-table-column>
                <el-table-column fixed="right" label="操作" width="100">
                    <template #default="scope">
                        <el-button @click="editArticle(scope.row)" size="small" text>
                            <MyIcon type="icon-edit"/>
                        </el-button>
                        <el-button style="margin-left: 0;" @click="deleteArticle(scope.row.id)"
                            size="small" text>
                            <MyIcon type="icon-shanchu"/>
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style="margin: 20px 0 50px 0">
                <el-pagination background style="float:right;" layout="total, prev, pager, next, jumper"
                @current-change="getArticleListByPage" :page-size="size" :total="total">
                </el-pagination>
            </div>
        </el-card>
    </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { tagsStore } from "@/store/tag"
import { contentStore } from "@/store/content"
import { getArticleListApi, deleteArticleByIdsApi } from "@/api/content";
import icon from '@/utils/icon'
import mixin from "@/mixins/article"

let { articleStatus } = mixin();
const store = tagsStore()
const cStore = contentStore()
const router = useRouter()

let { MyIcon } = icon()
let page = ref<number>(1)
let size = ref<number>(13)
let total = ref<number>(0)
// 文章列表
const articleList: any = reactive({
  list: [],
  total: '',
})
let labelList: any = ref([]);

onMounted(() => {
    articleData(page.value,size.value)
    // articleLabel();
});


// 获取文章数据
async function articleData(page: any, size: any) {
  const params = {
    pageNum: page,
    pageSize: size
  }
  getArticleListApi(params).then((res: any) => {
    if (res.code === 200) {
      articleList.list = res.result.list
      total.value = res.result.total
    }
  })
}

let ids = new Array();
const selected = (val: any) => {
    ids.splice(0, ids.length)
    for (let i = 0; i < val.length; i++) {
        ids.unshift(val[i].id)
    }
}

const getArticleListByPage = (page: any) => {
    articleData(page, size.value)
};

const editArticle = (article?: any) => {
    let path = "/admin/article/editor"
    store.addTag("编辑文章", path)
    router.push(path)
    cStore.setArticle(article);
}

const deleteArticle = (id?: any) => {
    if (id === 0) {
        if (ids.length !== 0) {
            deleteArticleByIdsApi(ids.join()).then((res: any) => {
                if (res.code === 200) {
                    getArticleListByPage(1)
                }
            })
        }
    } else {
        deleteArticleByIdsApi(id).then((res: any) => {
            getArticleListByPage(1)
        })
    }
};

</script>

<style scoped>
.title_style {
    display: flex;
    justify-content: flex-start;
    align-items: baseline;
    max-height: 31px;
    color: #445160;
    font-size: 24px;
    font-weight: 600;
    text-align: left;
}
</style>

