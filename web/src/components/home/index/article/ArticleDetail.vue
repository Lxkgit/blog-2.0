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
import { getBlogById } from "../../../../api/article";

let article: any = reactive({ data: {} })
const route = useRoute();
onMounted(() => {
    getBlogById(route.query.id).then((res: any) => {
        article.data = res;
    });
});



</script>