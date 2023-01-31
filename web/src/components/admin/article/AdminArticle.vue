<template>
    <div>
        <div class="title_style">
            <span>文章管理</span>
        </div>
        <el-card style="margin: 18px 2%;width: 95%">
            <el-button type="primary" plain @click="editArticle('null')">新增</el-button>
            <el-button type="danger" plain @click="deleteArticle(0)">删除</el-button>
            <el-table :data="articleList.data" stripe style="width: 100%" height="630" @selection-change="selected">
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
                <el-table-column fixed="right" label="操作" width="90">
                    <template #default="scope">
                        <el-button @click.native.prevent="editArticle(scope.row)" size="small" text>
                            <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
                        </el-button>
                        <el-button style="margin-left: 0;" @click.native.prevent="deleteArticle(scope.row.id)"
                            size="small" text>
                            <i class="fa fa-trash" aria-hidden="true"></i>
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
import mixin from "../../../mixins/article"
import { ref, reactive, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { articleStore } from "../../../store/article";
import { getArticleList, deleteArticleByIds, getArticleLabel } from "../../../api/article";

let { articleStatus } = mixin();
const router = useRouter()
const store = articleStore();
let page = ref<number>(1)
let size = ref<number>(14)
let total = ref<number>(0)
let articleList: any = reactive({ data: [] });
let labelList: any = ref([]);

let ids = new Array();

onMounted(() => {
    getArticleListFun(1)
    articleLabel();
});

const articleLabel = () => {
    getArticleLabel().then((res: any) => {
        if (res.code === 200) {
            labelList.value = res.result
            for (let i = 0; i < labelList.value.length; i++) {
                labelList.value[i].text = labelList.value[i].labelName
                labelList.value[i].value = labelList.value[i].id
            }
        }
    })
}

const getArticleListFun = (page: any) => {
    getArticleList({
        pageNum: page,
        pageSize: size.value,
        type: "admin"
    }).then((res: any) => {
        if (res.code === 200) {
            articleList.data = res.result.list.list;
            total.value = res.result.list.total;
        }
    })
}

const getArticleListByPage = (page: any) => {
    getArticleListFun(page)
};

const selected = (val: any) => {
    ids.splice(0, ids.length)
    for (let i = 0; i < val.length; i++) {
        ids.unshift(val[i].id)
    }
}

const editArticle = (article?: any) => {
    store.setArticle(article);
    router.push({
        name: "articleEditor",
    })
};

const deleteArticle = (id?: any) => {
    if (id === 0) {
        if (ids.length !== 0) {
            deleteArticleByIds(ids.join()).then((res: any) => {
                if (res.code === 200) {
                    getArticleListByPage(1)
                }
            })
        }
    } else {
        deleteArticleByIds(id).then(res => {
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

