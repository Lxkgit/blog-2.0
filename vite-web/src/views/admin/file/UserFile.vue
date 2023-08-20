<template>
  <div>
    <div class="title_style">
      <span>个人云盘</span>
    </div>
    <el-card style="margin: 18px 2%; width: 95%; height: 732px; color: #606266;">
      <div style="display: flex; font-size: 14px;">
        <el-upload :auto-upload="false" multiple :show-file-list="false" :on-change="changeUpload">
          <el-button type="success" size="small" text>上传文件</el-button>
        </el-upload>
        <div style="line-height: 23px; margin-left: 20px;">
          <MyIcon :style="[filePath === '/' ? { 'pointer-events': 'none' } : { 'cursor': 'pointer' }]"
            style="margin-right: 20px; outline:0;" type="icon-shangyibu" @click="changePath(-2)" title="返回上一级" />
          <span style="">当前路径：&nbsp;</span>
          <div style="display: inline; margin-left: 10px;">
            <span class="file_path" style="cursor: pointer;" @click="changePath(-1)">&nbsp;/&nbsp;</span>
            <div v-if="filePath !== '/'" v-for="(item, idx) in filePathArr" style="display: inline;">
              <span v-if="idx != 0">&nbsp;/&nbsp;</span>
              <span class="file_path" style="cursor: pointer;" @click="changePath(idx)"> {{ item }} </span>
            </div>
          </div>
        </div>
      </div>
      <el-divider content-position="left">文件目录</el-divider>
      <div style="display: flex; flex-wrap: wrap; align-content: flex-start; height:630px; overflow:auto; margin: 10px;"
        @contextmenu.prevent="openMenu($event, null)">
        <div v-for="item in dirList.data">
          <el-card :body-style="{ padding: '0px' }" shadow="hover"
            style="width: 159px; height: 150px; margin-bottom: 25px; margin-right: 10px;"
            @contextmenu.prevent.stop="openMenu($event, item)" :key="item.id">
            <div style="width: 159px; height: 125px; border-bottom:1px solid #dcdfe6; position: relative;"
              class="file_item">
              <div v-if="item.type === 0" style="height: 125px; cursor: pointer;" @dblclick="openFileDirFun(item)">
                目录
              </div>
              <div v-else-if="item.type === 1"
                style="height: 125px; display: flex; justify-content: space-between; align-items: center;">
                <img :src="item.imgPath" class="image" style="width: 100%; height: 100%; object-fit: cover;" @click="" />
                <div class="show_icon">
                  <MyIcon title="预览" class="icon_type" type="icon-search" @click="showImg(item)" />
                  <MyIcon title="查看文件信息" class="icon_type" type="icon-file" @click="showFileDesc(item)" />
                  <MyIcon title="删除" class="icon_type" type="icon-delete" @click="deleteFileDirOrFileFun(item)" />
                </div>
              </div>
              <div v-else style="height: 125px;">
                <div class="show_icon">
                  <MyIcon title="查看文件信息" class="icon_type" type="icon-file" @click="showFileDesc(item)" />
                  <MyIcon title="删除" class="icon_type" type="icon-delete" @click="deleteFileDirOrFileFun(item)" />
                </div>
                其它类型文件
              </div>
            </div>
            <div style="display: flex; padding-left: 5px; padding-top:2px">
              <el-tag class="mx-1" size="small" style="">{{ fileType(item.type) }}</el-tag>
              <span :title="item.name"
                style="line-height: 20px; margin-left:5px; width: 90px; height: 20px; overflow: hidden; text-overflow: ellipsis; -o-text-overflow: ellipsis; white-space:nowrap; display:inline-block;">{{
                  item.name }}</span>
              <MyIcon v-if="item.type !== 0" type="icon-download" style="line-height: 21px;" />
            </div>
          </el-card>
        </div>
      </div>
    </el-card>
    <el-image-viewer v-if="dialogImageUrl.show" :initial-index="dialogImageUrl.index" :url-list="dialogImageUrl.url"
      @close="dialogImageUrl.show = false">
    </el-image-viewer>
    <ul v-if="menu.visible" :style="{ left: menu.left + 'px', top: menu.top + 'px' }" class="contextmenu">
      <li @click="refreshDir">
        刷新
      </li>
      <li v-if="menu.type !== -1" @click="showFileDesc(null)">
        查看文件信息
      </li>
      <li v-if="menu.type !== -1" @click="">
        删除文件
      </li>
      <li @click="menu.visible = false">
        关闭菜单
      </li>
    </ul>
    <el-dialog v-model="menu.fileDialog" width="40%">
      <el-form :model="menu.file" label-width="100px">
        <el-form-item label="文件名称: ">
          <el-input v-model="menu.file.name" size="small" />
        </el-form-item>
        <el-form-item v-if="menu.file.type === 0" label="目录大小: ">
          <el-input v-model="menu.file.fileSize" size="small" />
        </el-form-item>
        <el-form-item v-else label="文件大小: ">
          <el-input v-model="menu.file.fileSize" size="small" />
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { selectFileDirOrFileApi, deleteFileDirOrFileApi, uploadApi } from "@/api/file";
import icon from '@/utils/icon'
import { onMounted, ref, reactive } from "vue";
import mixin from "@/mixins/fileType";
import { ElImageViewer } from 'element-plus'
import { ElMessage } from 'element-plus'

let { fileType } = mixin();
let { MyIcon } = icon()
let filePath: any = ref("/user")
let filePathArr = ref([])
let dirList: any = reactive({ data: [] })
let dialogImageUrl: any = reactive({ url: [], index: 0, show: false })

let menu: any = reactive({
  visible: false,
  left: 0,
  top: 0,
  type: -1,
  fileDialog: false,
  file: null,
})

onMounted(() => {
  selectFileDirOrFileFun()
})

const changeUpload = (file: any, fileLists: any) => {
  if (file.size / 1024 / 1024 > 100) {
    ElMessage.error('单文件最大上传大小为100M')
    return
  }
  const data = new FormData()
  data.append('files', file.raw)
  data.append('fileTypeCode', "3")
  data.append('filePathCode', "4")
  data.append('addPath', filePath.value)
  uploadApi(data).then((res: any) => {
    if (res.code === 200) {
      ElMessage.success({ message: '文件上传成功', type: 'success' });
      selectFileDirOrFileFun()
    } else {
      ElMessage.error({ message: res.message, type: 'error' });
    }
  })
}

// 打开菜单
const openMenu = (e: any, item?: any) => {
  if (item !== null) {
    // 选定文件、目录打开菜单
    menu.file = item
    menu.type = item.type
  } else {
    // 空白页面打开菜单
    menu.type = -1
  }
  menu.visible = true
  menu.left = e.clientX
  menu.top = e.clientY
}

// 查看文件详细信息
const showFileDesc = (item: any) => {
  if (item === null) {
    menu.visible = false
  } else {
    menu.file = item
  }
  menu.fileDialog = true
}

const refreshDir = () => {
  menu.visible = false
  selectFileDirOrFileFun()
}

const selectFileDirOrFileFun = () => {
  selectFileDirOrFileApi({
    filePath: filePath.value
  }).then((res: any) => {
    if (res.code === 200) {
      dirList.data = res.result
      filePathArr.value = filePath.value.substr(1).split("/")
    }
  })
}

const changePath = (idx: any) => {
  // 回到根目录
  if (idx === -1) {
    filePath.value = "/"
    selectFileDirOrFileFun()
  } else if (idx === -2) {
    // 回到上一级
    if (filePathArr.value.length <= 1) {
      filePath.value = "/"
    } else {
      idx = filePathArr.value.length - 2
      filePath.value = ""
      for (let i = 0; i <= idx; i++) {
        filePath.value += "/" + filePathArr.value[i]
      }
    }
    selectFileDirOrFileFun()
  } else {
    // 进入指定目录
    if (idx !== filePathArr.value.length - 1) {
      filePath.value = ""
      for (let i = 0; i <= idx; i++) {
        filePath.value += "/" + filePathArr.value[i]
      }
      selectFileDirOrFileFun()
    }
  }
}

const openFileDirFun = (dir: any) => {
  if (filePath.value === "/") {
    filePath.value += dir.name
  } else {
    filePath.value += "/" + dir.name
  }
  selectFileDirOrFileFun()
}

const showImg = (img: any) => {
  dialogImageUrl.show = true
  dialogImageUrl.url = []
  for (let i = 0; i < dirList.data.length; i++) {
    if (dirList.data[i].type === 1) {
      if (dirList.data[i].imgPath === img.imgPath) {
        dialogImageUrl.index = dialogImageUrl.url.length
      }
      dialogImageUrl.url.push(dirList.data[i].imgPath)
    }
  }
}

const deleteFileDirOrFileFun = (item: any) => {
  deleteFileDirOrFileApi({
    filePath: filePath.value,
    name: item.name
  }).then((res: any) => {
    console.log(res)
    if (res.code === 200) {
      ElMessage.success('文件删除成功')
      selectFileDirOrFileFun()
    }
  })
}
</script>

<style scoped>
.title_style {
  display: flex;
  justify-content: flex-start;
  align-items: baseline;
  max-height: 31px;
  color: #445160;
  font-size: 24px;
  font-weight: 600;
  text-align: left;
}

.contextmenu {
  position: fixed;
  min-width: min-content;
  z-index: 1900;
  border: 1px solid #d4d4d5;
  line-height: 1.4285em;
  max-width: 150px;
  background: #fff;
  font-weight: 400;
  font-style: normal;
  color: rgba(0, 0, 0, 0.87);
  border-radius: 0.28571429rem;
  box-shadow: 0 2px 4px 0 rgba(34, 36, 38, 0.12),
    0 2px 10px 0 rgba(34, 36, 38, 0.15);
}

.contextmenu div {
  position: relative;
  vertical-align: middle;
  line-height: 1;
  -webkit-tap-highlight-color: transparent;
  padding: 10px 15px;
  color: rgba(0, 0, 0, 0.87);
  font-size: 14px;
  cursor: pointer;
}

.contextmenu div:hover {
  background: #eee;
}

.file_path:hover {
  color: var(--el-color-primary);
}

.show_icon {
  display: none;
  position: absolute;
  left: 40px;
  top: 50px;
  font-size: 20px;
}

.file_item:hover .show_icon {
  display: inline;
}

.icon_type {
  cursor: pointer;
  margin-right: 5px;
  outline: 0;
}

.contextmenu {
  margin: 0;
  background: #fff;
  z-index: 3000;
  position: absolute;
  list-style-type: none;
  padding: 5px 0;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 400;
  color: #333;
  box-shadow: 2px 2px 3px 0 rgba(0, 0, 0, 0.3);
}

.contextmenu li {
  margin: 0;
  padding: 7px 16px;
  cursor: pointer;
}

.contextmenu li:hover {
  background: #eee;
}
</style>

