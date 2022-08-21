<template>
    <div>
        <div class="title_style">
            <span>文章管理</span>
        </div>
        <el-card style="margin: 18px 2%;width: 95%">
            <el-button @click="editArticle">写文章</el-button>
            <el-table :data="articleList.data" stripe style="width: 100%" :max-height="tableHeight">
                <el-table-column type="selection" width="55">
                </el-table-column>
                <el-table-column prop="title" label="标题" fit>
                </el-table-column>
                <el-table-column label="文章分类" width="200">
                    <template #default="scope">
                        <el-tag style="margin-right: 2px; margin-bottom: 2px;" v-for="item in scope.row.articleTypes">
                            {{ item.typeName }}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="文章标签" width="180" :filters="labelList.data" :filter-method="filterTag"
                    filter-placement="bottom-end">
                    <template #default="scope">
                        <el-tag style="margin-right: 2px; margin-bottom: 2px;" v-for="item in scope.row.articleLabels">
                            {{ item.labelName }}
                        </el-tag>
                    </template>
                </el-table-column>

                <el-table-column prop="createTime" label="发布日期" width="170">
                </el-table-column>
                <el-table-column fixed="right" label="操作" width="110">
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
                    @current-change="getBlogListByPage" :page-size="size" :total="total">
                </el-pagination>
            </div>
        </el-card>
    </div>

</template>

<script setup lang="ts">
import { computed } from '@vue/reactivity';
import { ref, reactive, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { getBlogList, getBlogType } from "../../../api/article";

const router = useRouter()
let page = ref<number>(1)
let size = ref<number>(14)
let total = ref<number>(0)
let articleList: any = reactive({ data: [] });
let labelList: any = reactive({
    data: [
        { text: "算法", value: "算法" },
        { text: "算法1", value: "算法1" }
    ]
});


let tableHeight = computed(() => {
    return window.innerHeight - 310
});

onMounted(() => {
    getBlogList(page.value, size.value).then((res: any) => {

        articleList.data = res.list;
        total.value = res.total;

    });
});


const filterTag = (value: string, row: any) => {
    return row.articleLabels.some((item: any) => { return item.labelName === value })
}

const editBlog = () => {
    // this.$router.push(
    //   {
    //     name: 'Editor'
    //   }
    // )
};

const deleteBlog = (id: number) => {
    // this.$store.dispatch('article/deleteBlog', id).then(resp => {
    //   if (resp && resp.code === 200){
    //     this.getBlogList({ currentPage: 1, pageSize: 10 })
    //   }
    // })
};

const getBlogListByPage = (page: any) => {
    getBlogList(page, size.value).then((res: any) => {
        articleList.data = res.list;
    })
};

const updateBlog = (article: any) => {
    // this.$router.push(
    //   {
    //     name: 'Editor',
    //     params: {
    //       article: article
    //     }
    //   }
    // )
};

const deleteRow = (index: number) => {
    articleList.data.splice(index, 1)
};

const editArticle = (article: any) => {
    router.push({
        name: "editor",
        params: {
            article: JSON.stringify(article)
        }
    })
};

const deleteArticle = (id: number) => {

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

