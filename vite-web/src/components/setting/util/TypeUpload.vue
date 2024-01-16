<template>
  <div>
    <div class="tool-img-item">
      <h4>{{ title }}</h4>
      <el-upload v-model:file-list="fileList.data" list-type="picture-card" :on-preview="handlePictureCardPreview"
        :on-remove="handleRemove" :auto-upload="false"
        :class="{ 'img_upload': fileList.data.length > 0 && fileList.data[0].url !== '' }" :on-change="changeUpload">
        <MyIcon type="icon-edit" />
      </el-upload>
      <el-image-viewer v-if="dialogVisible" :initial-index="0" :url-list="dialogImageUrl.data"
        @close="dialogVisible = false">
      </el-image-viewer>
    </div>
  </div>
</template>

<script setup lang="ts">
import icon from "@/utils/icon";
import { onMounted, ref, reactive } from "vue";
import { ElImageViewer } from 'element-plus'
import { updateBlogSettingApi, uploadApi } from "@/api/file";
import { ElMessage } from "element-plus";

const props = defineProps({
  title: String,
  id: Number,
  model: Array<String>,
  type: Number,
  num: Number
})

let imgNumber = ref(0)
let { MyIcon } = icon()
let fileList: any = reactive({ data: [] })
let dialogImageUrl: any = reactive({ data: [] })
let dialogVisible = ref(false)

// 因为vue默认不能直接修改设置，所以我们需要自己创建一个副本
// fileList,
onMounted(() => {
  if (props.num != null && props.num !== undefined) {
    imgNumber.value = props.num
  }
  if (props.model !== null && props.model !== undefined) {
    if (props.model.length === 0) {
      fileList.data = []
    } else {
      for (let i = 0; i < props.model.length; i++) {
        fileList.data.push({ url: props.model[i] })
      }
    }
  }
})

// 限制图片大小
const changeUpload = (file: any, fileLists: any) => {
  const data = new FormData()
  data.append('files', file.raw)
  data.append('fileTypeCode', "1")
  data.append('filePathCode', "2")
  uploadApi(data).then((res: any) => {
    if (res.code === 200) {
      ElMessage.success({ message: '图片上传成功', type: 'success' });
      fileList.data = []
      fileList.data.push({url: res.result[0]})
      updateBlogSettingFun(res.result.fileUrl)
    }
  })
}

const updateBlogSettingFun = (url: any) => {
  updateBlogSettingApi({
    id: props.id,
    valueList: [url]
  }).then((res: any) => {
    if(res.code === 200) {
      ElMessage({ message: '设置修改成功', type: 'success' })
    }
  })
}

const handlePictureCardPreview = (file: any) => {
  dialogImageUrl.data = []
  dialogImageUrl.data.push(file.url)
  dialogVisible.value = true
}

const handleRemove = (file: any) => {
  updateBlogSettingApi({
    id: props.id,
    valueList: []
  }).then((res: any) => {
    if(res.code === 200) {
      ElMessage({ message: '设置修改成功', type: 'success' })
    }
  })
}
</script>
<style>
/*隐藏上传按钮*/
.img_upload .el-upload--picture-card {
  display: none !important;

}

/*清除默认的正方形预览框*/
.el-upload-list--picture-card .el-upload-list__item {
  height: auto !important;
  border: 0 !important;
  outline: none;
}
</style>

<style scoped lang="scss">
/*侧边栏设置的相关设置*/
.tool-img-item {
  display: flex;
  flex-direction: column;
  width: 1200px;

  h4 {
    font-size: 15px;
    font-weight: 800;
    margin: 5px 0;
  }
}
</style>
