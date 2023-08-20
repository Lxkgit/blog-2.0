<template>
  <div>
    <el-row>
      <div style="
          display: flex;
          justify-content: space-between;
          align-items: center;
          height: 50px;
          margin-left: 18px;
        ">
        <div style="
            display: flex;
            justify-content: space-between;
            align-items: center;
          ">
          <span>文档标题:</span>
          <el-input v-model="docCatalog.data.label" placeholder="文档标题" maxlength="30" show-word-limit clearable
            style="margin-left: 12px; font-size: 18px; width: 400px" />
        </div>
        <div style="font-size: 14px">
          <span> 最近保存时间：</span>
          <span v-if="saveTime !== ''">{{ saveTime }}</span>
          <span v-else>未保存</span>
        </div>
      </div>
    </el-row>
    <el-row style="height: calc(100vh - 200px)">
      <MarkDownEditor @change="updateDiaryFun" @save="saveDiaryFun" v-model:text="docContent.data.docContentMd"
        :fileTypeCode="1" :filePathCode="2" />
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted, onBeforeUnmount } from "vue";
import { useRouter } from "vue-router";
import { contentStore } from "@/store/content";
import { tagsStore } from "@/store/tag";
import { updateContentApi } from "@/api/content";
import { getDocContentByIdApi } from "@/api/content";
import MarkDownEditor from "@/components/common/MarkDownEditor.vue";
import data from "@/utils/date"

let { getNowTime } = data();
const router = useRouter();
const cStore = contentStore();
const tagStore = tagsStore()
let time: number = 0;
let saveTime = ref("");
let saveFlag: boolean = false;
let docCatalog: any = reactive({ data: {} });
let docContent: any = reactive({
  data: {
    id: 0,
    docContentMd: ""
  }
});

onMounted(() => {
  if (cStore.getDocContent !== "null") {
    docCatalog.data = cStore.getDocContent;
    getDocContentByIdFun(docCatalog.data.id);
  }
  time = window.setInterval(() => {
    saveFlag = true;
  }, 30000);
});

onBeforeUnmount(() => {
  window.clearInterval(time);
});

// 获取笔记详情
const getDocContentByIdFun = (contentId: any) => {
  getDocContentByIdApi(contentId).then((res: any) => {
    if (res.code === 200) {
      docContent.data = res.result;
    }
  });
}

const saveDiaryFun = () => {
  updateContentApi({
    id: docContent.data.id,
    docContentMd: docContent.data.docContentMd,
  }).then((res: any) => {
    if (res.code === 200) {
      tagStore.delTagByPath("/admin/doc/editor")
      router.go(-1);
    }
  });
};

const updateDiaryFun = () => {
  if (saveFlag === true) {
    saveFlag = false;
    updateContentApi({
      id: docContent.data.id,
      docContentMd: docContent.data.docContentMd,
    }).then((res: any) => {
      if (res.code === 200) {
        saveTime.value = getNowTime();
      }
    });
  }
};

</script>