<template>
  <section>
    <el-card>
      <div style="width: auto; display: flex">
        <div style="width: 10%">
          <span
            class="select_article_type"
            style="cursor: pointer; align-items: center; justify-content: center"
            :key="0"
            :style="selectKey === 0 ? 'color: var(--el-color-primary);' : ''"
            @click="selectArticleTypeFun(null, 0)"
            >全部分类</span
          >
        </div>
        <div style="width: 90%">
          <ul style="list-style: none; display: inline">
            <li
              class="select_article_type"
              v-for="item in articleTypeList.value"
              style="margin-left: 8px; cursor: pointer; float: left"
              :key="item.id"
              :style="selectKey === item.id ? 'color: var(--el-color-primary);' : ''"
              @click="selectArticleTypeFun(item, 0)"
            >
              {{ item.typeName }}
            </li>
          </ul>
        </div>
      </div>
      <div>
        <div
          class="select_article_type"
          style="margin-left: 88%; cursor: pointer"
          @click="showType = !showType"
        >
          详细分类
          <MyIcon v-if="showType" type="icon-article" />
          <MyIcon v-else type="icon-setting" />
        </div>
      </div>
      <div :style="{ display: showType ? 'flex' : 'none' }" style="width: auto">
        <div style="width: 10%">
          <span
            class="select_article_type"
            style="cursor: pointer; align-items: center; justify-content: center"
            :key="0"
            :style="selectKey1 === 0 ? 'color: var(--el-color-primary);' : ''"
            @click="selectArticleTypeFun(null, 1)"
            >二级分类</span
          >
        </div>
        <div style="width: 90%">
          <ul style="list-style: none; display: inline">
            <li
              class="select_article_type"
              v-for="item in articleTypeList1.value"
              style="margin-left: 8px; cursor: pointer; float: left"
              :key="item.id"
              :style="selectKey1 === item.id ? 'color: var(--el-color-primary);' : ''"
              @click="selectArticleTypeFun(item, 1)"
            >
              {{ item.typeName }}
            </li>
          </ul>
        </div>
      </div>
      <div
        :style="{ display: showType ? 'flex' : 'none' }"
        style="cursor: pointer; width: auto"
      >
        <div style="width: 10%">
          <span
            class="select_article_type"
            style="cursor: pointer; align-items: center; justify-content: center"
            :key="0"
            :style="selectKey2 === 0 ? 'color: var(--el-color-primary);' : ''"
            @click="selectArticleTypeFun(null, 2)"
            >三级分类</span
          >
        </div>
        <div style="width: 90%">
          <ul style="list-style: none; display: inline">
            <li
              class="select_article_type"
              v-for="item in articleTypeList2.value"
              style="margin-left: 8px; cursor: pointer; float: left"
              :key="item.id"
              :style="selectKey2 === item.id ? 'color: var(--el-color-primary);' : ''"
              @click="selectArticleTypeFun(item, 2)"
            >
              {{ item.typeName }}
            </li>
          </ul>
        </div>
      </div>
    </el-card>
  </section>
</template>

<script setup lang="ts">
import { computed, onMounted, reactive, ref, onActivated } from "vue";
import { getArticleTypeTreeApi } from "@/api/content";
import icon from "@/utils/icon";

let { MyIcon } = icon();
const emit = defineEmits(['selectArticleFun'])

let showType = ref(false);
let selectKey = ref(0);
let selectKey1 = ref(0);
let selectKey2 = ref(0);
let articleTypeList: any = reactive({ data: [] });
let articleTypeList1: any = reactive({ data: [] });
let articleTypeList2: any = reactive({ data: [] });

async function categoryData() {
  getArticleTypeTreeApi().then((res: any) => {
    if (res.code === 200) {
      articleTypeList.value = res.result;
    }
  });
}

let selectArticleTypeFun = (item: any, type: any) => {
  let selectArticleType = 0;
  if (type === 0) {
    selectKey1.value = 0;
    selectKey2.value = 0;
    if (item !== null) {
      selectKey.value = item.id;
      articleTypeList1.value = item.children;
      articleTypeList2.value = [];
      selectArticleType = item.id;
    } else {
      selectKey.value = 0;
      selectKey1.value = 0;
      selectKey2.value = 0;
      articleTypeList1.value = [];
      articleTypeList2.value = [];
    }
  } else if (type === 1) {
    selectKey2.value = 0;
    if (item !== null) {
      selectKey1.value = item.id;
      articleTypeList2.value = item.children;
      selectArticleType = item.id;
    } else {
      selectKey1.value = 0;
      selectArticleType = selectKey.value === 0 ? 0 : selectKey.value;
      articleTypeList2.value = [];
    }
  } else if (type === 2) {
    if (item !== null) {
      selectKey2.value = item.id;
      selectArticleType = item.id;
    } else {
      selectArticleType = selectKey1.value === 0 ? 0 : selectKey1.value;
      selectKey2.value = 0;
    }
  }
  emit("selectArticleFun", selectArticleType)

};

onMounted(() => {
  categoryData();
});
</script>

<style scoped lang="scss">
.select_article_type:hover {
  color: var(--el-color-primary);
}
</style>
