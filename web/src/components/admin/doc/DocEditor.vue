<template>
  <div>
    <el-row style="margin: 18px 0px 0px 18px">
      <el-col :span="8">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item>管理中心</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/admin/doc' }">文档管理</el-breadcrumb-item>
          <el-breadcrumb-item>编辑器</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
      <el-col :span="8">
        <div style="font-size: 14px; line-height: 1">
          <span> 最近保存时间：</span>
          <span v-if="saveTime !== ''">{{ saveTime }}</span>
          <span v-else>未保存</span>
        </div>
      </el-col>
    </el-row>
    <el-row style="height: calc(100vh - 50px); margin-top: 10px;">
      <v-md-editor v-model="doc.data.docContentMd" height="100%" @save="useText" :disabled-menus="[]"
        @change="changeText" @upload-image="uploadImageFun"></v-md-editor>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ElMessage } from 'element-plus';
import { reactive, ref, onMounted, onBeforeUnmount } from "vue";
import { useRouter } from "vue-router";
import { docStore } from "../../../store/doc";
import {
  selectDocContent,
  saveDocContent,
  updateDocContent,
  upload,
} from "../../../api/article";


const router = useRouter();
const store = docStore();

let time: number = 0;
let saveTime = ref("");
let saveFlag: boolean = false;
let doc: any = reactive({
  data: {
    id: 0,
    userId: 0,
    catalogId: "",
    docContentMd: "",
    browseCount: 0,
    likeCount: 0,
    createTime: "",
    updateTime: "",
  },
});

onMounted(() => {

  selectDocContent(store.getDocId).then((res: any) => {
    if (res.code === 200) {
      doc.data = res.result
    }
  })

  time = window.setInterval(() => {
    saveFlag = true;
  }, 30000);
});

onBeforeUnmount(() => {
  window.clearInterval(time);
});

const uploadImageFun = (event: any, insertImage: any, files: any) => {
  // 拿到 files 之后上传到文件服务器，然后向编辑框中插入对应的内容
  console.log("file" + files);
  for (let i in files) {
    const formData = new FormData();
    formData.append("files", files[i]);
    formData.append("type", "img");
    formData.append("fileType", "doc");
    upload(
      formData
    ).then((res: any) => {
      if (res.code === 200) {
        ElMessage({
          message: '图片上传成功',
          type: 'success',
        })
        insertImage({
          url: res.result.img,
          desc: files[i].name,
        });
      }
      // 此处只做示例
    });
  }
};

const useText = () => {
  updateDocContent({
    catalogId: store.getDocId,
    docContentMd: doc.data.docContentMd,
    browseCount: 0,
    likeCount: 0,
  }).then((res: any) => {
    if (res.code === 200) {
      ElMessage({
        message: '文档保存成功',
        type: 'success',
      })
      router.go(-1);
    }
  });

};

const changeText = () => {
  if (saveFlag === true) {
    saveFlag = false;
    updateDocContent({
      catalogId: store.getDocId,
      docContentMd: doc.data.docContentMd,
      browseCount: 0,
      likeCount: 0,
    }).then((res: any) => {
      if (res.code === 200) {
        ElMessage({
          message: '文档自动保存',
          type: 'success',
        })
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