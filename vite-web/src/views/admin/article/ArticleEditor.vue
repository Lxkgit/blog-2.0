<template>
  <div>
    <div style="height: calc(100vh - 140px)">
      <MarkDownEditor @change="updateArticleFun" @save="saveArticleDialogFun" v-model:text="article.data.contentMd"
        fileTypeCode="1" filePathCode="1" />
    </div>
    <el-dialog v-model="saveDialog" title="文章信息" width="40%">
      <el-form class="demo-form-inline">
        <el-form-item label="文章标题" style="width: 80%">
          <el-input placeholder="文章标题" clearable />
        </el-form-item>
        <el-form-item label="文章描述" style="width: 80%">
          <el-input maxlength="300" placeholder="请输入文章描述" show-word-limit type="textarea" />
        </el-form-item>
        <el-form-item label="文章封面">
          <ImgUpload @upload="imgUpload" :imgList="list" :num="1" fileTypeCode="1" filePathCode="1" :cropper="1"
            :autoCropWidth="270" :autoCropHeight="180" />
        </el-form-item>
        <el-form-item label="文章类型">
          <el-tree-select style="font-size: 18px; width: 200px" v-model="type" :data="typeList"
            :render-after-expand="false" @change="selectType" />
        </el-form-item>
        <el-form-item label="文章标签">
          <div style="display: flex; justify-content: space-between; align-items: center; ">
            <el-popover placement="top" title="文章标签" trigger="click" :width="360">
              <el-input style="padding-bottom: 10px;" class="w-50 m-2" placeholder="Please Input" />
              <el-row :gutter="24" style="height: 200px;">
                <el-col :span="6">
                  left
                </el-col>
                <el-col :span="18">
                  <el-tag style="margin-right: 10px; margin-bottom: 10px; cursor: pointer;" v-for="label in labelList"
                    :style="'color: ' + tagColor(label.id)" :key="label.id" class="mx-1" @Click="addLabel(label)">
                    {{ label.labelName }}
                  </el-tag>
                  <!-- <el-input v-if="inputVisible" ref="InputRef" v-model="inputValue" class="ml-1 w-20" size="small"
                    @keyup.enter="handleInputConfirm" @blur="handleInputConfirm" />
                  <el-button v-else class="button-new-tag ml-1" size="small" @click="showInput">
                    + New Tag
                  </el-button> -->
                </el-col>
              </el-row>
              <template #reference>
                <el-button text type="success" size="small">选择标签</el-button>
              </template>
            </el-popover>
            <el-tag style="margin-left: 10px; cursor: pointer;" v-for="label in labels" :key="label.id" class="mx-1"
              :style="'color: ' + tagColor(label.id)" closable @close="deleteLabel(label.id)">{{
                label.labelName }}</el-tag>
          </div>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="saveDialog = false">取消</el-button>
          <el-button type="primary" @click="saveDialog = false">
            保存
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted, onBeforeUnmount } from "vue";
import { useRouter } from "vue-router";
import { contentStore } from "@/store/content"
import { tagsStore } from "@/store/tag"
import { saveArticleApi, updateArticleApi, getArticleTypeTreeApi, getArticleLabelListApi } from "@/api/content"
import MarkDownEditor from "@/components/common/MarkDownEditor.vue";
import ImgUpload from "@/components/common/ImgUpload.vue"
import data from "@/utils/date"
import color from "@/utils/color";

let { tagColor } = color()
let { getNowTime } = data();
const tagStore = tagsStore()
const cStore = contentStore()
const router = useRouter();
const saveDialog = ref(false)

let list = ref(["http://localhost/files/1/doc/img/2023-08-17_14-55-53_KooHLF_2023-08-15_09-10-36_PngxNK_image.png"])

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

const imgUpload = (upload: any) => {
  console.log("upload")
  console.log(upload)
}

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

const saveArticleDialogFun = () => {
  saveDialog.value = true
}


const saveArticleFun = () => {
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

const updateArticleFun = () => {
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

</script>