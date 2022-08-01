<template>
    <adminMenu></adminMenu>
    <a-divider style="height: 1px; background-color: #45494D; margin: 0;" />
    <pageTab></pageTab>
    <router-view @route-change="onRouteChange" />
</template>
<script setup lang="ts">
import { useRouter } from 'vue-router';

const router = useRouter()

const onRouteChange = (path: string) => {
    router.push(path)
}
</script>
