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
                    <el-tree-select style="margin-left:12px; font-size: 18px; width: 280px;" v-model="type" :data="typeList" check-strictly :render-after-expand="false" @change="selectType"/>
                </div>
                <div style="display: flex; justify-content: space-between; align-items: center; margin-left: 20px;">
                    <el-popover placement="bottom" title="文章标签" trigger="click" :width="360">
                        <el-tag style="margin-right: 10px; margin-bottom: 10px;" v-for="label in labelList" :key="label.id" class="mx-1" @Click="addLabel(label)">{{ label.labelName }}</el-tag>        
                        <template #reference>
                            <el-button>文章标签:</el-button>
                        </template>    
                    </el-popover>
                </div>
                <el-tag style="margin-left: 10px;" v-for="label in labels" :key="label.id" class="mx-1" closable @close="deleteLabel(label.id)">{{ label.labelName }}</el-tag>
            </div>
        </el-row>
        <el-row style="height: calc(100vh - 90px);">
            <v-md-editor v-model="article.data.contentMd" height="100%" @save="useText"></v-md-editor>
        </el-row>
    </div>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { articleStore } from "../../../store/article";
import { saveArticle, updateArticle, getArticleType, getArticleLabel } from '../../../api/article';

const store = articleStore();
const router = useRouter();
let type = ref("");
let labels: any = ref([]);
let typeList:any = ref();
let labelList: any = ref([]);
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

const addLabel = (label:any) => {
    let flag = false
    for(let i=0; i<labels.value.length; i++) {
        if(labels.value[i].id === label.id) {
            labels.value.splice(i, 1)
            flag = true
        }
    }
    if (!flag) {
        labels.value.push(label)
    }
}

const deleteLabel = (id:any) => {
    for (let i=0; i<labels.value.length; i++) {
        if (labels.value[i].id === id) {
            labels.value.splice(i, 1)
        }
    }
}

onMounted(() => {
    articleType();
    articleLabel();
    if(store.getArticle !== 'null') {
        article.data = store.getArticle
        if (article.data !== null) {
            let articleType = article.data.articleType;
            type.value = articleType.substr(-1);
            if (article.data.articleLabels !== null) {
                labels.value = article.data.articleLabels;
            }
        }
    }
});

const articleType = () => {
    getArticleType().then((res: any) => {
        if(res.code === 200) {
            typeList.value = res.result
        }
    })
}

const articleLabel = () => {
    getArticleLabel().then((res: any) => {
        if(res.code === 200) {
            labelList.value = res.result
        }
    })
}

const useText = () => {
    let labelId = "";
    for(let i=0; i<labels.value.length; i++) {
        if(i===0) {
            labelId = labels.value[i].id;
        } else {
            labelId = labelId + "," + labels.value[i].id;
        }
    }
    if (article.data.id !== 0) {
        updateArticle({
            id: article.data.id,
            userId: 1,
            title: article.data.title,
            contentMd: article.data.contentMd,
            articleType: type.value,
            articleLabel: labelId,
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
            articleLabel: labelId,
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