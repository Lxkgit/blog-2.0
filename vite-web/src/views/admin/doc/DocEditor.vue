<template>
  <div>
    <el-row>
      <div
        style="
          display: flex;
          justify-content: space-between;
          align-items: center;
          height: 50px;
          margin-left: 18px;
        "
      >
        <div
          style="
            display: flex;
            justify-content: space-between;
            align-items: center;
          "
        >
          <span>文档标题:</span>
          <el-input
            v-model="docCatalog.data.label"
            placeholder="文档标题"
            maxlength="30"
            show-word-limit
            clearable
            style="margin-left: 12px; font-size: 18px; width: 400px"
          />
        </div>
        <div style="font-size: 14px">
          <span> 最近保存时间：</span>
          <span v-if="saveTime !== ''">{{ saveTime }}</span>
          <span v-else>未保存</span>
        </div>
        <el-tag
          style="margin-left: 10px"
          v-for="label in labels"
          :key="label.id"
          class="mx-1"
          closable
          @close="deleteLabel(label.id)"
          >{{ label.labelName }}</el-tag
        >
      </div>
    </el-row>
    <el-row style="height: calc(100vh - 200px)">
      <v-md-editor
        v-model="docContent.data.docContentMd"
        height="100%"
        @save="useText"
        :disabled-menus="[]"
        left-toolbar="undo redo clear | h bold italic strikethrough quote | ul ol table hr | link image code file | save"
        :toolbar="toolbar"
        @change="changeText"
        @upload-image="uploadImageFun"
      ></v-md-editor>
    </el-row>
  </div>
</template>

<script setup lang="ts">
//@ts-nocheck
import { reactive, ref, onMounted, onBeforeUnmount } from "vue";
import { useRouter } from "vue-router";
import { contentStore } from "@/store/content";
import { tagsStore } from "@/store/tag";
import { updateContentApi, updateCatalogApi } from "@/api/content";
import { getDocContentByIdApi } from "@/api/content";
import { upload } from "@/api/file";
import icon from "@/utils/icon";
import VMdEditor from "@kangc/v-md-editor";
import "@kangc/v-md-editor/lib/style/base-editor.css";
import githubTheme from "@kangc/v-md-editor/lib/theme/github.js";
import "@kangc/v-md-editor/lib/theme/style/github.css";
import hljs from "highlight.js/lib/core";
import python from "highlight.js/lib/languages/python";
import json from "highlight.js/lib/languages/json";
import yaml from "highlight.js/lib/languages/yaml";
import sql from "highlight.js/lib/languages/sql";
import javascript from "highlight.js/lib/languages/javascript";
import css from "highlight.js/lib/languages/css";
import scss from "highlight.js/lib/languages/scss";
import xml from "highlight.js/lib/languages/xml";
import java from "highlight.js/lib/languages/java";

hljs.registerLanguage("json", json);
hljs.registerLanguage("python", python);
hljs.registerLanguage("yaml", yaml);
hljs.registerLanguage("sql", sql);
hljs.registerLanguage("javascript", javascript);
hljs.registerLanguage("scss", scss);
hljs.registerLanguage("css", css);
hljs.registerLanguage("xml", xml);
hljs.registerLanguage("java", java);
VMdEditor.use(githubTheme, {
  Hljs: hljs,
});

const router = useRouter();
let { MyIcon } = icon();
const cStore = contentStore();
const tagStore = tagsStore()
let labels: any = ref([]);
let time: number = 0;
let saveTime = ref("");
let saveFlag: boolean = false;
let docCatalog: any = reactive({ data: {} });
let docContent: any = reactive({ data: {
  id: 0,
  docContentMd: ""
} });

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
function getDocContentByIdFun(contentId: any) {
  getDocContentByIdApi(contentId).then((res: any) => {
    if (res.code === 200) {
      docContent.data = res.result;
      console.log(JSON.stringify(docContent))
    }
  });
}

const uploadImageFun = (event: any, insertImage: any, files: any) => {
  // 拿到 files 之后上传到文件服务器，然后向编辑框中插入对应的内容
  console.log("file" + files);
  for (let i in files) {
    const formData = new FormData();
    formData.append("files", files[i]);
    formData.append("fileTypeCode", 1);
    formData.append("filePathCode", 2);
    upload(formData).then((res: any) => {
      if (res.code === 200) {
        insertImage({
          url: res.result.fileUrl,
          desc: files[i].name,
        });
      }
    });
  }
};

const useText = () => {
  console.log(JSON.stringify(docContent))
  updateCatalogApi({
    id: docCatalog.data.id,
    docName: docCatalog.data.label,
  }).then((res: any) => {});
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

const changeText = () => {
  if (saveFlag === true) {
    saveFlag = false;
    updateCatalogApi({
      id: docCatalog.data.id,
      docName: docCatalog.data.label,
    }).then((res: any) => {});
    updateContentApi({
      id: docContent.data.id,
      docContentMd: docContent.data.docContentMd,
    }).then((res: any) => {
      if(res.code === 200) {
        saveTime.value = getNowTime();
      }
    });
  }
};

const getNowTime = () => {
  let data = new Date();
  let hours = data.getHours();
  let min = data.getMinutes();
  let second = data.getSeconds();
  let hoursStr: any = "";
  let minStr: any = "";
  let secondStr: any = "";
  if (hours < 10) {
    hoursStr = "0" + hours;
  } else {
    hoursStr = hours;
  }
  if (min < 10) {
    minStr = "0" + min;
  } else {
    minStr = min;
  }
  if (second < 10) {
    secondStr = "0" + second;
  } else {
    secondStr = second;
  }
  return hoursStr + ":" + minStr + ":" + secondStr;
};
</script>