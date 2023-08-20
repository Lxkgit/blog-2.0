<template>
  <div class="tool-img-item">
    <el-upload v-model:file-list="fileList.data" list-type="picture-card" :on-preview="handlePictureCardPreview"
      :on-remove="handleRemove" :auto-upload="false" :class="{ 'img_upload': fileList.data.length >= imgNumber }"
      :on-change="changeUpload">
      <MyIcon type="icon-plus" />
    </el-upload>
    <el-image-viewer v-if="imgViewerDialog" :initial-index="imgNum" :url-list="dialogImageUrl.data"
      @close="imgViewerDialog = false">
    </el-image-viewer>
    <el-dialog title="图片裁剪" v-model="imgCroppingDialog">
      <div style="height: 400px;">
        <VueCropper ref="cropperRef" :img="option.img" :output-size="option.outputSize" :output-type="option.outputType"
          :info="option.info" :can-scale="option.canScale" :auto-crop="option.autoCrop" :fixedNumber="option.fixedNumber"
          :auto-crop-width="option.autoCropWidth" :auto-crop-height="option.autoCropHeight" :full="option.full"
          :fixed-box="option.fixedBox" :can-move="option.canMove" :can-move-box="option.canMoveBox"
          :original="option.original" :center-box="option.centerBox" />
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="imgCroppingDialog = false">
            取消
          </el-button>
          <el-button type="primary" :loading="loading" @click="finish">
            确认
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted } from "vue";
import icon from "@/utils/icon"
import { uploadApi } from "@/api/file";
import { ElImageViewer, ElMessage } from 'element-plus'
import 'vue-cropper/dist/index.css'
import { VueCropper } from "vue-cropper";
import { number } from "echarts";


let imgNumber = ref(0)
let { MyIcon } = icon()
// 返回图片列表数据
let fileList: any = reactive({ data: [] })
const emit = defineEmits(['upload'])

// 模块方法引入
let { cropperRef, imgCroppingDialog, option, loading, finish } = cropper();
let { imgNum, dialogImageUrl, imgViewerDialog, handlePictureCardPreview } = imgViewer();
let { handleRemove, changeUpload } = imgUpload();
const props = defineProps({
  imgList: {
    type: Array<String>,
    required: true
  },
  fileTypeCode: {
    type: String,
    required: true
  },
  filePathCode: {
    type: String,
    required: true
  },
  num: {
    type: Number,
    required: true,
    default: 1
  },
  // 图片是否需要裁剪 0：不需要 1：需要
  cropper: {
    type: Number,
    default: 0
  },
  // 截图框宽高比例 [ 宽度 , 高度 ]
  fixedNumber: {
    type: Array,
    default: [1,1]
  },
  // 默认生成截图框宽度
  autoCropWidth: {
    type: Number,
    default: 100
  },
  // 默认生成截图框高度
  autoCropHeight: {
    type: Number,
    default: 100
  },
  // 固定截图框大小
  fixedBox: {
    type: Boolean,
    default: true
  }
})

onMounted(() => {
  if (props.num !== null && props.num !== undefined) {
    imgNumber.value = props.num
  }
  if (props.imgList !== null && props.imgList !== undefined) {
    if (props.imgList.length === 0) {
      fileList.data = []
    } else {
      for (let i = 0; i < props.imgList.length; i++) {
        fileList.data.push({ url: props.imgList[i] })
      }
    }
  }
  if (props.cropper === 1) {
    if (props.autoCropWidth !== null && props.autoCropWidth !== undefined) {
      option.autoCropWidth = props.autoCropWidth
    }
    if (props.autoCropHeight !== null && props.autoCropHeight !== undefined) {
      option.autoCropHeight = props.autoCropHeight
    }
    if (props.fixedNumber !== null && props.fixedNumber !== undefined) {
      option.fixedNumber = props.fixedNumber
    }
    if (props.fixedBox !== null && props.fixedBox !== undefined) {
      option.fixedBox = props.fixedBox
    }
  }

})

function imgUpload() {
  const handleRemove = (file: any) => {

  }
  // 限制图片大小
  const changeUpload = (file: any, fileLists: any) => {
    if (props.cropper == 0) {
      const data = new FormData()
      data.append('files', file.raw)
      data.append('fileTypeCode', props.fileTypeCode)
      data.append('filePathCode', props.filePathCode)
      uploadApi(data).then((res: any) => {
        if (res.code === 200) {
          console.log(fileList)
          ElMessage.success({ message: '图片上传成功', type: 'success' });
          fileList.data.push({ url: res.result.fileUrl })
          fileList.data = fileList.data.filter(function (item: any) {
            return item.status !== "ready"
          })
          emit('upload', fileList.data)
        }
      })
    } else if (props.cropper == 1) {
      option.img = URL.createObjectURL(file.raw)
      option.imgName = file.name
      imgCroppingDialog.value = true
    }
  }

  return {
    handleRemove, changeUpload
  }
}

function imgViewer() {
  let imgNum = ref(0)
  let imgViewerDialog = ref(false)
  let dialogImageUrl: any = reactive({ data: [] })
  const handlePictureCardPreview = (file: any) => {
    dialogImageUrl.data = []
    for (let i = 0; i < fileList.data.length; i++) {
      let url = fileList.data[i].url;
      if (url === file.url) {
        imgNum.value = i
      }
      dialogImageUrl.data.push(url)
    }
    imgViewerDialog.value = true
  }
  return {
    imgNum, dialogImageUrl, imgViewerDialog, handlePictureCardPreview
  }
}

// 图片裁剪方法
function cropper() {
  // cropper文档
  // http://github.xyxiao.cn/vue-cropper/
  let imgCroppingDialog = ref(false)
  let option: any = reactive({
    img: '', //裁剪图片地址
    outputSize: 1, //裁剪生成图片质量
    outputType: 'png', //裁剪生成图片格式
    info: true, //裁剪框大小信息
    canScale: true,//图片是否允许缩放
    autoCrop: true,//是否默认生成截图框
    // 只有自动截图开启 宽度高度才生效
    autoCropWidth: 0,//默认生成截图框宽度
    autoCropHeight: 0,//默认生成截图框高度
    fixedNumber: [1, 1],// 截图框的宽高比例
    full: false, //是否输出原图比例截图
    fixedBox: true, //固定截图框大小
    canMove: true, //上传图片是否可以移动
    canMoveBox: true,//截图框是否拖动
    original: true,//上传图片按照原始比例渲染
    centerBox: true,//截图框是否被限制在图片里面
    // 自定义
    imgName: '', //图片名称
  });
  // 防止重复提交
  let loading = ref(false)
  // 裁剪组件Ref
  const cropperRef: any = ref({})
  // Blob 转 File
  const blobToFile = (blob: any, fileName: any) => {
    const file = new File([blob], fileName, { type: blob.type });
    return file;
  }
  // 图片上传
  const finish = () => {
    cropperRef.value.getCropBlob((blob: any) => {
      loading.value = true
      const data = new FormData()
      let fileName = "";
      let fileNameArr = option.imgName.split(".")
      for (let i = 0; i < fileNameArr.length - 1; i++) {
        fileName = fileName + fileNameArr[i]
      }
      data.append('files', blobToFile(blob, fileName + "." + option.outputType))
      data.append('fileTypeCode', props.fileTypeCode)
      data.append('filePathCode', props.filePathCode)
      uploadApi(data).then((res: any) => {
        if (res.code === 200) {
          ElMessage.success({
            message: '图片上传成功',
            type: 'success'
          });
          fileList.data.push({ url: res.result.fileUrl })
          loading.value = false
          imgCroppingDialog.value = false
          fileList.data = fileList.data.filter(function (item: any) {
            return item.status !== "ready"
          })
          emit('upload', fileList.data)
        }
      })
    })
  }

  return {
    cropperRef,
    imgCroppingDialog,
    option,
    loading,
    finish
  }
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

  h4 {
    font-size: 15px;
    font-weight: 800;
    margin: 5px 0;
  }
}
</style>