<!-- 舵机控制
<template>
    <div>
        <div class="tool-input-item">
            <h4>{{ title }}</h4>
            <el-input v-model="data" @change="updateBlogSettingFun" />
        </div>
    </div>

    <el-input-number v-model="item.value" :min="item.min" :max="item.max" />
</template>

<script setup lang="ts">
const props = defineProps(
    {
        label: '舵机旋转角度',
        type: 'input-number',
        min: 0,
        max: 180,
        key: 'data',
        value: 0,
    })

</script>

<style></style> -->