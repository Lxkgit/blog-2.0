<template>
    <homeMenu></homeMenu>
    <router-view></router-view>
    <!-- <TestPage></TestPage> -->
    <homeBottom></homeBottom>
</template>

<script setup lang="ts">
import homeMenu from "../../components/home/homeMenu.vue"
import homeBottom from "../../components/home/HomeBottom.vue" 

</script>