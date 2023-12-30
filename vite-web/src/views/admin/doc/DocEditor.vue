<template>
  <div>
    <el-row style="height: calc(100vh - 140px)">
      <MarkDownEditor @change="updateDocContentFun" @save="saveArticleDialogFun"
        v-model:text="docContent.data.docContentMd" fileTypeCode="1" filePathCode="2" />
    </el-row>
    <el-dialog v-model="saveDialog" title="文档信息" width="40%">
      <el-form class="demo-form-inline">
        <el-form-item label="文档标题" style="width: 80%">
          <el-input v-model="docCatalog.data.label" placeholder="文档标题" clearable />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="saveDialog = false">取消</el-button>
          <el-button type="primary" @click="saveDocContentFun">
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
import { ElMessage, ElNotification } from 'element-plus'
import MarkDownEditor from "@/components/common/MarkDownEditor.vue";
import { updateContentApi, getDocContentByIdApi, updateCatalogApi } from "@/api/content";
import { contentStore } from "@/store/content";
import { tagsStore } from "@/store/tag";
import data from "@/utils/date"

const router = useRouter();
const cStore = contentStore();
const tagStore = tagsStore()

// 文档自动保存
let time: number = 0;
let saveTime = ref("");
let saveFlag: boolean = false;
// 文档自动保存时间间隔
let autoSaveTime = ref(30000)
// 文档保存dialog
const saveDialog = ref(false)

let { getNowTime } = data();
let { docCatalog, docContent, getDocContentByIdFun, saveDocContentFun, saveArticleDialogFun, updateDocContentFun } = docFun()

onMounted(() => {
  if (cStore.getDocContent !== "null") {
    docCatalog.data = cStore.getDocContent;
    getDocContentByIdFun(docCatalog.data.id);
  }

  time = window.setInterval(() => {
    saveFlag = true;
  }, autoSaveTime.value);
});

onBeforeUnmount(() => {
  window.clearInterval(time);
});

function docFun() {
  // 文档目录
  let docCatalog: any = reactive({ data: {} });

  // 文档内容
  let docContent: any = reactive({
    data: {
      id: 0,
      docContentMd: ""
    }
  });

  // 获取笔记详情
  const getDocContentByIdFun = (contentId: any) => {
    getDocContentByIdApi(contentId).then((res: any) => {
      if (res.code === 200) {
        docContent.data = res.result;
      }
    });
  }

  // 保存文档dialog
  const saveArticleDialogFun = () => {
    saveDialog.value = true
  }

  // 保存文档内容
  const saveDocContentFun = () => {
    updateCatalogApi({
      id: docCatalog.data.id,
      docName: docCatalog.data.label,
    }).then((res: any) => {
      if (res.code === 200) {
        ElMessage.success({
          message: '文档目录修改成功',
          type: 'success'
        });
      }
    })

    updateContentApi({
      id: docContent.data.id,
      docContentMd: docContent.data.docContentMd,
      docStatus: 1
    }).then((res: any) => {
      if (res.code === 200) {
        tagStore.delTagByPath("/admin/doc/editor")
        router.push("/admin/doc")
        ElMessage.success({
          message: '文档保存成功',
          type: 'success'
        });
      }
    });
  };

  // 修改文档内容
  const updateDocContentFun = () => {
    if (saveFlag === true) {
      saveFlag = false;
      updateContentApi({
        id: docContent.data.id,
        docContentMd: docContent.data.docContentMd,
        docStatus: 0
      }).then((res: any) => {
        if (res.code === 200) {
          saveTime.value = getNowTime();
          ElNotification({
            title: '文档自动保存成功',
            message: '保存时间：' + saveTime.value,
            type: 'success',
            duration: autoSaveTime.value
          })
        }
      });
    }
  };

  return {
    docCatalog,
    docContent,
    getDocContentByIdFun,
    saveArticleDialogFun,
    saveDocContentFun,
    updateDocContentFun
  }
}







</script>