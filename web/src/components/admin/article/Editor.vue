<template>
    <div>
        <el-row style="margin: 18px 0px 0px 18px ">
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/admin' }">管理中心</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/admin/article' }">文章管理</el-breadcrumb-item>
                <el-breadcrumb-item>编辑器</el-breadcrumb-item>
            </el-breadcrumb>
        </el-row>
        <el-row>
            <el-input v-model="article.data.title" style="margin: 10px 0;font-size: 18px;" placeholder="请输入标题">
            </el-input>
        </el-row>
        <el-row style="height: calc(100vh - 90px);">
            <v-md-editor v-model="article.data.contentMd" height="100%" @save="useText"></v-md-editor>
        </el-row>


    </div>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { articleStore } from "../../../store/article";

const store = articleStore();
const route = useRoute();

let article: any = reactive({
    data: {
        id: 0,
        userId: 0,
        title: "",
        contentMd: "-",
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


onMounted(() => {
    if (route.params.article) {
        article.data = JSON.parse(route.params.article)
    }
});

const useText = () => {
    console.log("article: " + JSON.stringify(article))
}

</script>

<style scoped>
/*设置card的边距为0，同时设置分割线的间距*/
.option-card .el-card__header {
    padding: 0 !important;
}

.option-card .el-divider--horizontal {
    margin: 10px 0 !important;
}

/*消除多选*/
.el-radio-group .el-radio {
    margin-right: 14px !important;
}
</style>

<style scoped>
.el-tag+.el-tag {
    margin-top: 10px;
    margin-left: 10px;

}

.button-new-tag {
    margin-top: 10px;
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
}

.input-new-tag {
    margin-top: 10px;
    width: 370px;
    margin-left: 10px;
    vertical-align: bottom;
}

.infinite-list li:hover {
    color: rgba(0, 1, 0, 0.5);
}
</style>
