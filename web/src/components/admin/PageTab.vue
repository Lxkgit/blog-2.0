<template>
  <div style="background-color: #636363; height: 32px;">
    <div style="height: 100%;">
      <div style="height: 100%; width: 47px; background-color: #5B5B5B; text-align:center; line-height: 32px;">
        <router-link key="index" to="/admin" style="margin: 0 auto;">
        
        </router-link>
      </div>


      <!-- <router-link v-for="(item, index) in tag" :key="index" ref="tag" tag="span" class="tags_view_item"
        :class="{ active: item.active }" :to="item.path" @contextmenu.prevent.native="openMenu(index, $event)">
        {{ item.title }}
      </router-link> -->
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, watch } from "vue";
import { useRouter, useRoute } from "vue-router";
import { tagStore } from "../../store/adminTag"


let store = tagStore();
let tag: any

onMounted(() => {

});

</script>

<style scoped>
</style>

