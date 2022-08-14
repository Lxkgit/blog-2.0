<template>
  <div style="overflow: hidden; margin-top: 98px;">
    <div >
      <div style="
        display: flex;
        justify-content: flex-start;
        align-items: baseline;
        max-height: 31px;
        color: #445160;
        font-size: 24px;
        font-weight: 600;
        text-align: left;
      ">
        <span>日记管理</span>
      </div>
      <div style="height: 100%; background: #fff; padding: 10px; width: auto;">
        <calendar @handleClick="showTime" @changeMonth="getData"></calendar>
      </div>
    </div>

  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const getData = (date: any) => {
  // console.log(date);
};

const showTime = (item: any) => {
  // console.log(item);
};
</script>
<style scoped>

</style>