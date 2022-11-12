<template>
    <div>
        <el-row style="margin: 18px 0px 0px 18px ">
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item>管理中心</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/admin/article' }">文章管理</el-breadcrumb-item>
                <el-breadcrumb-item>编辑器</el-breadcrumb-item>
            </el-breadcrumb>
        </el-row>
        <el-row>
            <div style="display: flex; justify-content: space-between; align-items: center; height: 50px; margin-left: 18px;">
                <div style="display: flex; justify-content: space-between; align-items: center;">
                    <span>文章标题:</span>
                    <el-input v-model="article.data.title" style="margin-left:12px; font-size: 18px; width: 500px;" placeholder="请输入标题"/>
                </div>
                <div style="display: flex; justify-content: space-between; align-items: center; margin-left: 20px;">
                    <span>文章分类:</span>
                    <el-tree-select style="margin-left:12px; font-size: 18px; width: 280px;" v-model="type" :data="data" check-strictly :render-after-expand="false" @change="selectType"/>
                </div>
            </div>
        </el-row>
        <el-row style="height: calc(100vh - 90px);">
            <v-md-editor v-model="article.data.contentMd" height="100%" @save="useText"></v-md-editor>
        </el-row>
    </div>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { articleStore } from "../../../store/article";
import { saveArticle, updateArticle, getArticleType } from '../../../api/article';

const store = articleStore();
const route = useRoute();
const router = useRouter();
let type = ref("");
let article: any = reactive({
    data: {
        id: 0,
        userId: 0,
        title: "",
        contentMd: "",
        contentHtml: "-",
        articleType: "",
        articleLabel: "",
        articleStatus: 1,
        browseCount: 0,
        likeCount: 0,
        createTime: "",
        updateTime: ""
    }
})

const selectType = (value:any) => {
    type.value = value
}
 
let data:any = ref()

onMounted(() => {
    if(store.getArticle !== undefined) {
        article.data = store.getArticle
        console.log("data: ", article.data)
        if (article.data !== null) {
            let articleType = article.data.articleType;
            type.value = articleType.substr(-1)    
        }
    }
    articleType();
});

const articleType = () => {
    getArticleType().then((res: any) => {
        if(res.code === 200) {
            data.value = res.result
        }
    })
}

const useText = () => {
    if (article.data.id !== 0) {
        updateArticle({
            id: article.data.id,
            userId: 1,
            title: article.data.title,
            contentMd: article.data.contentMd,
            articleType: type.value,
            articleLabel: "",
            articleStatus: 1,
            browseCount: 0,
            likeCount: 0
        }).then((res:any) => {
            if(res.code === 200) {
                router.go(-1)
            }
        })
    } else {
        saveArticle({
            id: article.data.id,
            userId: 1,
            title: article.data.title,
            contentMd: article.data.contentMd,
            articleType: type.value,
            articleLabel: "",
            articleStatus: 1,
            browseCount: 0,
            likeCount: 0
        }).then((res:any) => {
            if(res.code === 200) {
                router.go(-1)
            }
        })
    }
}

</script>