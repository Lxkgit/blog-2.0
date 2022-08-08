<template>
    <adminMenu></adminMenu>
    
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
