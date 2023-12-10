<template>
  <div>
    <div style="height: calc(100vh - 140px)">
      <MarkDownEditor
        @change="updateArticleFun"
        @save="saveArticleDialogFun"
        v-model:text="article.data.contentMd"
        fileTypeCode="1"
        filePathCode="1"
      />
    </div>
    <el-dialog v-model="saveDialog" title="文章信息" width="40%">
      <el-form class="demo-form-inline">
        <el-form-item label="文章标题" style="width: 80%">
          <el-input
            v-model="article.data.title"
            placeholder="文章标题"
            clearable
            maxlength="46"
            show-word-limit
          />
        </el-form-item>
        <el-form-item label="文章描述" style="width: 80%">
          <el-input
            v-model="article.data.contentMemo"
            maxlength="300"
            placeholder="请输入文章描述"
            show-word-limit
            type="textarea"
          />
        </el-form-item>
        <el-form-item label="文章封面">
          <ImgUpload
            @upload="imgUpload"
            :imgList="
              article.data.contentImg === null ||
              article.data.contentImg === undefined ||
              article.data.contentImg === ''
                ? []
                : [article.data.contentImg]
            "
            :num="1"
            fileTypeCode="1"
            filePathCode="1"
            :cropper="1"
            :autoCropWidth="270"
            :autoCropHeight="180"
          />
        </el-form-item>
        <el-form-item label="文章类型">
          <el-tree-select
            style="font-size: 18px; width: 200px"
            v-model="type"
            :data="typeList"
            :check-strictly="true"
            @change="selectType"
          />
        </el-form-item>
        <el-form-item label="文章标签">
          <div style="display: flex; justify-content: space-between; align-items: center">
            <el-popover
              placement="top-start"
              title="文章标签"
              trigger="click"
              :width="360"
            >
              <el-input
                style="padding-bottom: 10px"
                class="w-50 m-2"
                placeholder="搜索文章标签"
                v-model="selectLabel"
                clearable
              />
              <el-row :gutter="24" style="height: 200px">
                <el-col
                  :span="6"
                  style="height: 100%; overflow: auto"
                  class="label_type_col"
                >
                  <ul
                    v-for="(labelType, index) in labelTypeList"
                    style="list-style-type: none; padding: 0; margin: 0"
                  >
                    <li
                      style="cursor: pointer; margin-bottom: 5px"
                      @click="selectLabelList(labelType, index)"
                      class="article_label_tag"
                      :class="labelTypeActive == index ? 'article_label_tag_active' : ''"
                      :key="index"
                    >
                      {{ labelType.label }}
                    </li>
                  </ul>
                </el-col>
                <el-col :span="18" style="">
                  <div style="overflow: auto; height: 200px">
                    <span v-for="label in labelList">
                      <el-tag
                        v-if="filterTag(label.labelName, selectLabel)"
                        style="margin-right: 10px; margin-bottom: 10px; cursor: pointer"
                        :style="'color: ' + tagColor(label.id)"
                        :key="label.id"
                        class="mx-1"
                        @Click="addLabel(label)"
                      >
                        {{ label.labelName }}
                        {{ label.articleNum }}
                      </el-tag>
                    </span>
                    <el-input
                      v-if="tagInputVisible"
                      ref="InputRef"
                      v-model="inputTagValue"
                      class="ml-1 w-20"
                      size="small"
                      style="width: 80%"
                      @keyup.enter="tagInputHandleConfirm"
                      @blur="tagInputHandleConfirm"
                    />
                    <el-button
                      v-else
                      class="button-new-tag ml-1"
                      size="small"
                      @click="showTagInput"
                    >
                      + 创建标签
                    </el-button>
                  </div>
                </el-col>
              </el-row>
              <template #reference>
                <el-button text type="success" size="small">选择标签</el-button>
              </template>
            </el-popover>
            <div style="display: inline-block">
              <el-tag
                style="margin-left: 10px; cursor: pointer"
                v-for="label in labels"
                :key="label.id"
                class="mx-1"
                :style="'color: ' + tagColor(label.id)"
                closable
                @close="deleteLabel(label.id)"
                >{{ label.labelName }}</el-tag
              >
            </div>
          </div>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="saveDialog = false">取消</el-button>
          <el-button type="primary" @click="saveArticleFun"> 保存 </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted, onBeforeUnmount, nextTick } from "vue";
import { ElInput, ElMessage, ElNotification } from "element-plus";
import { useRouter } from "vue-router";
import MarkDownEditor from "@/components/common/MarkDownEditor.vue";
import ImgUpload from "@/components/common/ImgUpload.vue";
import {
  saveArticleApi,
  updateArticleApi,
  getArticleTypeTreeApi,
  getArticleLabelTypeListApi,
  saveArticleLabelApi,
} from "@/api/content";
import { contentStore } from "@/store/content";
import { tagsStore } from "@/store/tag";
import data from "@/utils/date";
import color from "@/utils/color";

// 模块方法引入
let {
  time,
  saveTime,
  saveFlag,
  autoSaveTime,
  saveDialog,
  article,
  type,
  labels,
  saveArticleDialogFun,
  imgUpload,
  saveArticleFun,
  updateArticleFun,
} = articleFn();
let {
  selectLabel,
  labelList,
  labelTypeList,
  labelTypeActive,
  tagInputVisible,
  inputTagValue,
  showTagInput,
  tagInputHandleConfirm,
  getArticleLabelTypeListFun,
  addLabel,
  deleteLabel,
  selectLabelList,
  filterTag,
} = articleLabelFn();
let { typeList, selectType, articleType } = articleTypeFn();

const router = useRouter();

let { tagColor } = color();

const tagStore = tagsStore();
const cStore = contentStore();

onMounted(() => {
  // 初始化文章数据
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

  // 定时保存文章功能
  time = window.setInterval(() => {
    saveFlag = true;
  }, autoSaveTime.value);
});

onBeforeUnmount(() => {
  window.clearInterval(time);
});

// 文章上传方法合集
function articleFn() {
  // 保存文章计时
  let time: number = 0;
  let saveTime = ref("");
  let saveFlag: boolean = false;
  // 文章自动保存时间间隔
  let autoSaveTime = ref(30000);
  // 文章保存dialog
  const saveDialog = ref(false);
  // 当前时间
  let { getNowTime } = data();
  // 文章数据
  let article: any = reactive({
    data: {
      id: 0,
      userId: 0,
      title: "",
      contentMd: "",
      contentImg: "",
      contentMemo: "",
      articleType: "",
      articleLabel: "",
      articleStatus: 1,
      browseCount: 0,
      likeCount: 0,
      createTime: "",
      updateTime: "",
    },
  });

  // 文章类型
  let type = ref("");

  // 文章标签
  let labels: any = ref([]);

  // 保存文章dialog
  const saveArticleDialogFun = () => {
    articleType();
    getArticleLabelTypeListFun();
    saveDialog.value = true;
  };

  // 文章封面上传
  const imgUpload = (upload: any) => {
    article.data.contentImg = upload[0].url;
  };

  // 保存文章方法
  const saveArticleFun = () => {
    saveDialog.value = false;
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
        contentMemo: article.data.contentMemo,
        contentImg: article.data.contentImg,
        articleType: type.value,
        articleLabel: labelId,
        articleStatus: 1,
        browseCount: 0,
        likeCount: 0,
      }).then((res: any) => {
        if (res.code === 200) {
          tagStore.delTagByPath("/admin/article/editor");
          router.push("/admin/article");
          ElMessage.success({
            message: "文章保存成功",
            type: "success",
          });
        }
      });
    } else {
      saveArticleApi({
        title: article.data.title,
        contentMd: article.data.contentMd,
        contentMemo: article.data.contentMemo,
        contentImg: article.data.contentImg,
        articleType: type.value,
        articleLabel: labelId,
        articleStatus: 1,
        browseCount: 0,
        likeCount: 0,
      }).then((res: any) => {
        if (res.code === 200) {
          tagStore.delTagByPath("/admin/article/editor");
          router.push("/admin/article");
          ElMessage.success({
            message: "文章保存成功",
            type: "success",
          });
        }
      });
    }
  };

  // 定时保存文章方法
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
          contentMemo: article.data.contentMemo,
          contentImg: article.data.contentImg,
          articleType: type.value,
          articleLabel: labelId,
          articleStatus: 0,
          browseCount: article.data.browseCount,
          likeCount: article.data.likeCount,
        }).then((res: any) => {
          if (res.code === 200) {
            article.data.id = res.result;
            saveTime.value = getNowTime();
            ElNotification({
              title: "文章自动保存成功",
              message: "保存时间：" + saveTime.value,
              type: "success",
              duration: autoSaveTime.value,
            });
          }
        });
      } else {
        saveArticleApi({
          title: article.data.title,
          contentMd: article.data.contentMd,
          contentMemo: article.data.contentMemo,
          contentImg: article.data.contentImg,
          articleType: type.value,
          articleLabel: labelId,
          articleStatus: 0,
          browseCount: 0,
          likeCount: 0,
        }).then((res: any) => {
          if (res.code === 200) {
            article.data.id = res.result;
            saveTime.value = getNowTime();
            ElNotification({
              title: "文章自动保存成功",
              message: "保存时间：" + saveTime.value,
              type: "success",
              duration: autoSaveTime.value,
            });
          }
        });
      }
    }
  };

  return {
    time,
    saveTime,
    saveFlag,
    autoSaveTime,
    saveDialog,
    article,
    type,
    labels,
    saveArticleDialogFun,
    imgUpload,
    saveArticleFun,
    updateArticleFun,
  };
}

// 文章标签方法合集
function articleLabelFn() {
  // 查询文章标签
  let selectLabel: any = ref("");

  // 文章标签列表
  let labelList: any = ref([]);

  // 文章标签分类列表
  let labelTypeList: any = ref([]);

  // 当前选中标签分类id
  let selectLabelTypeId = ref(0);

  // 文章标签分类激活项
  let labelTypeActive: any = ref(-1);

  // 文章标签上限数量
  let articleLabelMaxNum = ref(6);

  // 新增标签输入框是否展示
  let tagInputVisible = ref(false);

  // 新增标签输入内容
  let inputTagValue = ref("");

  // 新增标签ref
  const InputRef = ref<InstanceType<typeof ElInput>>();

  // 新增标签输入框展示处理
  const showTagInput = () => {
    tagInputVisible.value = true;
    nextTick(() => {
      InputRef.value!.input!.focus();
    });
  };

  // 新增标签处理流程
  const tagInputHandleConfirm = () => {
    if (inputTagValue.value) {
      saveArticleLabelApi({
        labelType: selectLabelTypeId.value,
        labelName: inputTagValue.value,
      }).then((res: any) => {
        if (res.code === 200) {
          ElMessage({ message: "文章标签创建成功", type: "success" });
          getArticleLabelTypeListFun();
        }
      });
    }
    tagInputVisible.value = false;
    inputTagValue.value = "";
  };

  // 获取文章标签（分类+标签）
  const getArticleLabelTypeListFun = () => {
    getArticleLabelTypeListApi().then((res: any) => {
      if (res.code === 200) {
        labelTypeList.value = res.result;
        if (labelTypeList.value.length > 0) {
          if (labelTypeActive.value !== -1) {
            selectLabelTypeId.value = labelTypeList.value[labelTypeActive.value].id;
            labelList.value = labelTypeList.value[labelTypeActive.value].labelList;
          } else {
            labelTypeActive.value = 0;
            selectLabelTypeId.value = labelTypeList.value[0].id;
            labelList.value = labelTypeList.value[0].labelList;
          }
        }
      }
    });
  };

  // 添加文章标签
  const addLabel = (label: any) => {
    let flag = false;
    for (let i = 0; i < labels.value.length; i++) {
      if (labels.value[i].id === label.id) {
        labels.value.splice(i, 1);
        flag = true;
        return;
      }
    }
    if (labels.value.length + 1 > articleLabelMaxNum.value) {
      ElMessage({
        message: "文章标签上限为" + articleLabelMaxNum.value + "个",
        type: "error",
      });
    } else {
      if (!flag) {
        labels.value.push(label);
      }
    }
  };

  // 删除文章标签
  const deleteLabel = (id: any) => {
    for (let i = 0; i < labels.value.length; i++) {
      if (labels.value[i].id === id) {
        labels.value.splice(i, 1);
      }
    }
  };

  // 选择展示标签类型
  const selectLabelList = (labelType: any, index: any) => {
    labelList.value = [];
    labelTypeActive.value = index;
    selectLabelTypeId.value = labelType.id;
    labelList.value = labelType.labelList;
  };

  /**
   * 关键字过滤标签
   */
  const filterTag = (str: string, value: string) => {
    return str.indexOf(value) !== -1;
  };

  return {
    selectLabel,
    labelList,
    labelTypeList,
    labelTypeActive,
    tagInputVisible,
    inputTagValue,
    showTagInput,
    tagInputHandleConfirm,
    getArticleLabelTypeListFun,
    addLabel,
    deleteLabel,
    selectLabelList,
    filterTag,
  };
}

// 文章分类方法合集
function articleTypeFn() {
  // 文章分类列表
  let typeList: any = ref();

  // 选择文章类型
  const selectType = (value: any) => {
    type.value = value;
  };

  // 获取文章分类
  const articleType = () => {
    getArticleTypeTreeApi().then((res: any) => {
      if (res.code === 200) {
        typeList.value = res.result;
      }
    });
  };

  return {
    typeList,
    selectType,
    articleType,
  };
}
</script>

<style>
.article_label_tag:hover {
  color: var(--el-color-primary);
}

.article_label_tag_active {
  background-color: var(--el-menu-hover-bg-color);
}
</style>
