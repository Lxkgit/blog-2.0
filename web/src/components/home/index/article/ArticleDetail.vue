<template>

    <el-row justify="center">
        <el-col :lg="15">
            <h2 style="padding: 16px 32px;">{{article.data.title}}</h2>
            <v-md-preview :text="article.data.contentMd"></v-md-preview>
        </el-col>
    </el-row>


</template>

<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { getArticleById } from "../../../../api/article";

let article: any = reactive({ data: {} })
const route = useRoute();
onMounted(() => {
    getArticleById(route.query.id).then((res: any) => {
        if(res.code===200) {
            article.data = res.result;
        }
    });
});



</script>