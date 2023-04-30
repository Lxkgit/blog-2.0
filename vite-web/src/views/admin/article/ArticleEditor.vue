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
          <span>文章标题:</span>
          <el-input v-model="article.data.title" placeholder="文章标题" maxlength="30" show-word-limit clearable style="margin-left: 12px; font-size: 18px; width: 250px"/>
        </div>
        <div style="font-size: 14px;">
          <span> 最近保存时间：</span>
          <span v-if="saveTime !== ''">{{ saveTime }}</span>
          <span v-else>未保存</span>
        </div>
        <div style="
              display: flex;
              justify-content: space-between;
              align-items: center;
              margin-left: 20px;
            ">
          <span>文章分类:</span>
          <el-tree-select style="margin-left: 12px; font-size: 18px; width: 280px" v-model="type" :data="typeList"
            :render-after-expand="false" @change="selectType" />
        </div>
        <div style="
              display: flex;
              justify-content: space-between;
              align-items: center;
              margin-left: 20px;
            ">
          <el-popover placement="bottom" title="文章标签" trigger="click" :width="360">
            <el-tag style="margin-right: 10px; margin-bottom: 10px" v-for="label in labelList" :key="label.id"
              class="mx-1" @Click="addLabel(label)">{{ label.labelName }}</el-tag>
            <template #reference>
              <el-button>文章标签:</el-button>
            </template>
          </el-popover>
        </div>
        <el-tag style="margin-left: 10px" v-for="label in labels" :key="label.id" class="mx-1" closable
          @close="deleteLabel(label.id)">{{ label.labelName }}</el-tag>
      </div>
    </el-row>
    <el-row style="height: calc(100vh - 200px)">
      <v-md-editor
        v-model="article.data.contentMd"
        height="100%"
        @save="useText"
        :disabled-menus="[]"
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
import { systemStore } from "@/store/system";
import { contentStore } from "@/store/content"
import { tagsStore } from "@/store/tag"
import { ElImageViewer } from 'element-plus'
import { saveArticleApi, updateArticleApi, getArticleTypeTreeApi, getArticleLabelListApi } from "@/api/content"
import VMdEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';
import hljs from 'highlight.js/lib/core';
import python from 'highlight.js/lib/languages/python';
import json from 'highlight.js/lib/languages/json';
import yaml from 'highlight.js/lib/languages/yaml';
import sql from 'highlight.js/lib/languages/sql';
import javascript from 'highlight.js/lib/languages/javascript';
import css from 'highlight.js/lib/languages/css';
import scss from 'highlight.js/lib/languages/scss';
import xml from 'highlight.js/lib/languages/xml';
import java from 'highlight.js/lib/languages/java'

hljs.registerLanguage('json', json);
hljs.registerLanguage('python', python);
hljs.registerLanguage('yaml', yaml);
hljs.registerLanguage('sql', sql);
hljs.registerLanguage('javascript', javascript);
hljs.registerLanguage('scss', scss);
hljs.registerLanguage('css', css);
hljs.registerLanguage('xml', xml);
hljs.registerLanguage('java', java);
VMdEditor.use(githubTheme, {
  Hljs: hljs,
});

const tagStore = tagsStore()
const cStore = contentStore()
const router = useRouter();
let type = ref("");
let labels: any = ref([]);
let typeList: any = ref();
let labelList: any = ref([]);
let time: number = 0;
let saveTime = ref("");
let saveFlag: boolean = false;
let article: any = reactive({
  data: {
    id: 0,
    userId: 0,
    title: "",
    contentMd: "",
    contentHtml: "-",
    articleType: "",
    articleLabel: "",
    articleStatus: 1,
    browseCount: 0,
    likeCount: 0,
    createTime: "",
    updateTime: "",
  },
});

onMounted(() => {
  articleType();
  articleLabel();
  if (cStore.getArticle !== "null") {
    article.data = cStore.getArticle;
    if (article.data !== null && article.data.articleType !== null) {
      let articleTypeArr = article.data.articleType.split(",");
      console.log(articleTypeArr);
      type.value = articleTypeArr[articleTypeArr.length - 1];
      if (article.data.articleLabels !== null) {
        labels.value = article.data.articleLabels;
      }
    }
  }

  time = window.setInterval(() => {
    saveFlag = true;
  }, 30000);
});

onBeforeUnmount(() => {
  window.clearInterval(time);
});

const selectType = (value: any) => {
  type.value = value;
};

const addLabel = (label: any) => {
  let flag = false;
  for (let i = 0; i < labels.value.length; i++) {
    if (labels.value[i].id === label.id) {
      labels.value.splice(i, 1);
      flag = true;
    }
  }
  if (!flag) {
    labels.value.push(label);
  }
};

const deleteLabel = (id: any) => {
  for (let i = 0; i < labels.value.length; i++) {
    if (labels.value[i].id === id) {
      labels.value.splice(i, 1);
    }
  }
};

const articleType = () => {
  getArticleTypeTreeApi().then((res: any) => {
    if (res.code === 200) {
      typeList.value = res.result;
    }
  });
};

const articleLabel = () => {
  getArticleLabelListApi(0).then((res: any) => {
    if (res.code === 200) {
      labelList.value = res.result;
    }
  });
};

const uploadImageFun = (event: any, insertImage: any, files: any) => {
  // 拿到 files 之后上传到文件服务器，然后向编辑框中插入对应的内容
  // console.log("file" + files);
  // for (let i in files) {
  //   const formData = new FormData();
  //   formData.append("files", files[i]);
  //   formData.append("type", "img");
  //   formData.append("fileType", "article");
  //   upload(
  //     formData
  //   ).then((res: any) => {
  //     if (res.code === 200) {
  //       console.log( JSON.stringify(res) + "++")
  //       insertImage({
  //         url: res.result.fileUrl,
  //         desc: files[i].name,
  //       });
  //     }
  //     // 此处只做示例
  //   });
  // }
};

const useText = () => {
  let labelId = "";
  for (let i = 0; i < labels.value.length; i++) {
    if (i === 0) {
      labelId = labels.value[i].id;
    } else {
      labelId = labelId + "," + labels.value[i].id;
    }
  }
  if (article.data.id !== 0) {
    updateArticleApi({
      id: article.data.id,
      title: article.data.title,
      contentMd: article.data.contentMd,
      articleType: type.value,
      articleLabel: labelId,
      articleStatus: 1,
      browseCount: 0,
      likeCount: 0,
    }).then((res: any) => {
      if (res.code === 200) {
        tagStore.delTagByPath("/admin/article/editor")
        router.go(-1);
      }
    });
  } else {
    saveArticleApi({
      title: article.data.title,
      contentMd: article.data.contentMd,
      articleType: type.value,
      articleLabel: labelId,
      articleStatus: 1,
      browseCount: 0,
      likeCount: 0,
    }).then((res: any) => {
      if (res.code === 200) {
        tagStore.delTagByPath("/admin/article/editor")
        router.go(-1);
      }
    });
  }
};

const changeText = () => {
  if (saveFlag === true) {
    saveFlag = false;
    let labelId = "";
    for (let i = 0; i < labels.value.length; i++) {
      if (i === 0) {
        labelId = labels.value[i].id;
      } else {
        labelId = labelId + "," + labels.value[i].id;
      }
    }
    if (article.data.id !== 0) {
      updateArticleApi({
        id: article.data.id,
        title: article.data.title,
        contentMd: article.data.contentMd,
        articleType: type.value,
        articleLabel: labelId,
        articleStatus: 0,
        browseCount: article.data.browseCount,
        likeCount: article.data.likeCount,
      }).then((res: any) => {
        if (res.code === 200) {
          saveTime.value = getNowTime();
        }
      });
    } else {
      saveArticleApi({
        title: article.data.title,
        contentMd: article.data.contentMd,
        articleType: type.value,
        articleLabel: labelId,
        articleStatus: 0,
        browseCount: 0,
        likeCount: 0,
      }).then((res: any) => {
        if (res.code === 200) {
          article.data.id = res.result;
          saveTime.value = getNowTime();
        }
      });
    }
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