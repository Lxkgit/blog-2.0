<template>
  <div>
    <div class="title_style">
      <span>日记管理</span>
    </div>
    <el-card style="margin: 18px 2%;width: 95%">
      <el-button type="primary" plain @click="newDiaryDialogFun">新增</el-button>
      <el-popover :visible="deleteBtnVisible" placement="top" :width="160">
        <p>删除所选日记？</p>
        <div style="text-align: right; margin: 0">
          <el-button size="small" text @click="deleteBtnVisible = false">取消</el-button>
          <el-button size="small" type="primary" @click="deleteDiaryFun(0)">删除</el-button>
        </div>
        <template #reference>
          <el-button :disabled="ids.length > 0 ? false : true" type="danger" @click="deleteBtnVisible = true" plain>删除</el-button>
        </template>
      </el-popover>
      <el-button type="info" plain @click="uploadDiaryDialog = true">导入</el-button>
      <el-table :data="diaryList.data" stripe style="width: 100%; height: calc(100vh - 328px);" @selection-change="selected">
        <el-table-column type="selection" width="55">
        </el-table-column>
        <el-table-column prop="diaryDate" label="日期" fit>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" fit>
        </el-table-column>
        <el-table-column prop="updateTime" label="最近更新" fit>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="110">
          <template #default="scope">
            <el-button style="margin: 0; padding: 8px;" @click="showDiaryDialogFun(scope.row)" size="small" text>
              <MyIcon type="icon-eye" />
            </el-button>
            <el-button style="margin: 0; padding: 8px;" @click="updateDiaryDialogFun(scope.row)" size="small" text>
              <MyIcon type="icon-edit" />
            </el-button>
            <el-popover :visible="deleteDiaryVisible && selectRow === scope.$index" placement="top" :width="160"
              :ref="`popover-${scope.$index}`">
              <p>删除所选日记？</p>
              <div style="text-align: right; margin: 0">
                <el-button size="small" text @click="deleteDiaryVisible = false">取消</el-button>
                <el-button size="small" type="primary" @click="deleteDiaryFun(scope.row.id)">删除</el-button>
              </div>
              <template #reference>
                <el-button style="margin: 0; padding: 8px;" @click="deleteDiaryVisible = true; selectRow = scope.$index"
                  size="small" text>
                  <MyIcon type="icon-delete" />
                </el-button>
              </template>
            </el-popover>
          </template>
        </el-table-column>
      </el-table>
      <div style="margin: 20px 0 50px 0">
        <el-pagination background style="float:right;" layout="total, prev, pager, next, jumper"
          @current-change="getDiaryListFun" :page-size="size" :total="total">
        </el-pagination>
      </div>
      <el-dialog v-model="saveAndUpdateDiaryDialog" title="新增日记" :close-on-click-modal="false"
        :close-on-press-escape="false">
        <div>
          <div style="margin-bottom: 10px;">
            <span>日期：</span>
            <el-date-picker v-model="showDiary.data.diaryDate" type="date" placeholder="选择日期" value-format="YYYY-MM-DD"
              :default-value="new Date()" />
            <span style="font-size: 14px; line-height: 1;">
              <span> 最近保存时间：</span>
              <span v-if="saveTime !== ''">{{ saveTime }}</span>
              <span v-else>未保存</span>
            </span>
          </div>
          <div style="height: 400px;">
            <MarkDownEditor @change="updateDiaryFun" @save="saveDiaryFun" v-model:text="showDiary.data.diaryMd"
              fileTypeCode="1" filePathCode="5" />
          </div>
        </div>
      </el-dialog>
      <el-dialog v-model="showDiaryDialog" title="查看日记">
        <div>
          <el-date-picker v-model="showDiary.data.diaryDate" type="date" value-format="YYYY-MM-DD" disabled />
          <div
            style="height: 400px; overflow: auto;  margin-top: 10px; margin-bottom: 10px; border-width: 1px; border-style: solid; border-color: beige;">
            <MarkDown :text="showDiary.data.diaryMd" />
          </div>
          <span>创建日期：{{ showDiary.data.createTime }}</span>
          &nbsp;&nbsp;
          <span>最近更新：{{ showDiary.data.updateTime }}</span>
        </div>
      </el-dialog>
      <el-dialog v-model="uploadDiaryDialog" title="导入日记" width="460px">
        <div>
          <el-form :model="importDiaryForm" label-width="120px">
            <el-form-item label="日记日期">
              <div class="block">
                <el-date-picker v-model="importDiaryForm.year" type="year" placeholder="Pick a year"
                  value-format="YYYY" />
              </div>
            </el-form-item>
            <el-form-item label="上传日记">
              <el-upload :auto-upload="false" multiple :show-file-list="false" :on-change="changeUpload">
                <el-button type="success" size="small" text>上传日记</el-button>
              </el-upload>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="importDiaryFun">创建</el-button>
              <el-button @click="uploadDiaryDialog = false">取消</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-dialog>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, reactive, onBeforeUnmount } from 'vue';
import { UploadProps, ElMessage, ElNotification } from 'element-plus'
import { getDiaryListApi, saveDiaryApi, updateDiaryApi, deleteDiaryByIdsApi } from "@/api/content"
import { uploadApi, importDiaryApi } from "@/api/file";
import MarkDownEditor from "@/components/common/MarkDownEditor.vue";
import MarkDown from "@/components/detail/MarkDown.vue"
import icon from '@/utils/icon'
import data from "@/utils/date"

let { ids, size, total, diaryList, showDiary, deleteBtnVisible, deleteDiaryVisible, saveAndUpdateDiaryDialog, showDiaryDialog, selectRow, selected, showDiaryDialogFun,
  newDiaryDialogFun, updateDiaryDialogFun, getDiaryListFun, deleteDiaryFun, saveDiaryFun, updateDiaryFun } = diaryFun();
let { changeUpload } = uploadFun();
let { getNowDate, getNowTime } = data();
let { MyIcon } = icon()


// 自动保存日记
let time: number = 0;
let saveTime = ref("");
let saveFlag: boolean = false;
// 日记自动保存时间间隔
let autoSaveTime = ref(5000)

// 上传日记dialog
let uploadDiaryDialog = ref(false);
// 上传日记表单
let importDiaryForm = reactive({
  year: 0,
  filePath: ""
})

onMounted(() => {
  getDiaryListFun(1);
  time = window.setInterval(() => {
    saveFlag = true;
  }, autoSaveTime.value)
});

onBeforeUnmount(() => {
  window.clearInterval(time);
});

function diaryFun() {

  // 勾选日记id列表
  let ids = reactive([]);
  // 页面展示日记条数
  let size = ref<number>(14)
  // 总日记数
  let total = ref<number>(0)
  // 日记列表
  let diaryList: any = reactive({ data: [] });
  // 展示日记
  let showDiary: any = reactive({ data: [] });
  // 多选删除日记弹窗
  let deleteBtnVisible = ref(false)
  // 单选删除日记弹窗
  let deleteDiaryVisible = ref(false)
  // 新增日记dialog
  let saveAndUpdateDiaryDialog = ref(false)
  // 展示日记dialog
  let showDiaryDialog = ref(false)

  let selectRow = ref(0)

  // 获取勾选日记id
  const selected = (val: any) => {
    ids.splice(0, ids.length)
    for (let i = 0; i < val.length; i++) {
      //@ts-ignore 单行忽略
      ids.unshift(val[i].id)
    }
  }

  // 打开查看日记dialog
  const showDiaryDialogFun = (diary: any) => {
    showDiary.data = {};
    showDiary.data = diary;
    showDiaryDialog.value = true;
  }

  // 打开新增日记dialog
  const newDiaryDialogFun = () => {
    saveFlag = false
    saveTime.value = ""
    showDiary.data = {}
    showDiary.data.diaryDate = getNowDate()
    saveAndUpdateDiaryDialog.value = true;
  }

  // 打开修改日记dialog
  const updateDiaryDialogFun = (param: any) => {
    saveTime.value = ""
    showDiary.data = {}
    showDiary.data = param;
    saveAndUpdateDiaryDialog.value = true;
  }

  // 获取日记列表
  const getDiaryListFun = (page: any) => {
    getDiaryListApi({
      pageNum: page,
      pageSize: size.value,
    }).then((res: any) => {
      if (res.code === 200) {
        diaryList.data = res.result.diary.list
        console.log(diaryList.data)
        total.value = res.result.diary.total
      }
    })
  }

  // 删除日记
  const deleteDiaryFun = (id?: any) => {
    deleteDiaryVisible.value = false
    deleteBtnVisible.value = false;
    if (id === 0) {
      if (ids.length !== 0) {
        deleteDiaryByIdsApi(ids.join()).then((res: any) => {
          if (res.code === 200) {
            ElMessage({
              message: '日记删除成功',
              type: 'success',
            })
            getDiaryListFun(1)
          }
        })
      }
    } else {
      deleteDiaryByIdsApi(id).then((res: any) => {
        if (res.code === 200) {
          ElMessage({
            message: '日记删除成功',
            type: 'success',
          })
          getDiaryListFun(1)
        }
      })
    }
  }

  // 手动保存日记方法
  const saveDiaryFun = () => {
    if (showDiary.data.id !== undefined) {
      updateDiaryApi({
        id: showDiary.data.id,
        diaryMd: showDiary.data.diaryMd,
        diaryDate: showDiary.data.diaryDate,
        diaryStatus: 1
      }).then((res: any) => {
        if (res.code === 200) {
          ElMessage({
            message: '日记修改成功',
            type: 'success',
          })
          getDiaryListFun(1)
          saveAndUpdateDiaryDialog.value = false;
        }
      })
    } else {
      saveDiaryApi({
        diaryMd: showDiary.data.diaryMd,
        diaryDate: showDiary.data.diaryDate,
        diaryStatus: 1
      }).then((res: any) => {
        if (res.code === 200) {
          ElMessage({
            message: '日记保存成功',
            type: 'success',
          })
          getDiaryListFun(1)
          saveAndUpdateDiaryDialog.value = false;
        }
      })
    }
  };

  // 自动保存日记方法
  const updateDiaryFun = () => {
    if (saveFlag === true && saveAndUpdateDiaryDialog.value === true) {
      saveFlag = false;
      if (showDiary.data.id !== undefined) {
        updateDiaryApi({
          id: showDiary.data.id,
          diaryMd: showDiary.data.diaryMd,
          diaryDate: showDiary.data.diaryDate,
          diaryStatus: 0
        }).then((res: any) => {
          if (res.code === 200) {
            getDiaryListFun(1)
            saveTime.value = getNowTime();
            ElNotification({
              title: '文章自动保存成功',
              message: '保存时间：' + saveTime.value,
              type: 'success',
              duration: autoSaveTime.value
            })
          }
        })
      } else {
        saveDiaryApi({
          diaryMd: showDiary.data.diaryMd,
          diaryDate: showDiary.data.diaryDate,
          diaryStatus: 0
        }).then((res: any) => {
          if (res.code === 200) {
            getDiaryListFun(1)
            saveTime.value = getNowTime();
            ElNotification({
              title: '文章自动保存成功',
              message: '保存时间：' + saveTime.value,
              type: 'success',
              duration: autoSaveTime.value
            })
            showDiary.data.id = res.result
          }
        })
      }
    }
  };

  return {
    ids,
    size,
    total,
    diaryList,
    showDiary,
    deleteBtnVisible,
    deleteDiaryVisible,
    saveAndUpdateDiaryDialog,
    showDiaryDialog,
    selectRow,
    selected,
    showDiaryDialogFun,
    newDiaryDialogFun,
    updateDiaryDialogFun,
    getDiaryListFun,
    deleteDiaryFun,
    saveDiaryFun,
    updateDiaryFun
  }
}

function uploadFun() {

  const changeUpload = (file: any, fileLists: any) => {
    if (file.size / 1024 / 1024 > 100) {
      ElMessage.error('单文件最大上传大小为100M')
      return
    }
    const data = new FormData()
    data.append('files', file.raw)
    data.append('fileTypeCode', "4")
    data.append('filePathCode', "5")
    uploadApi(data).then((res: any) => {
      if (res.code === 200) {
        ElMessage.success({ message: '文件上传成功', type: 'success' });
        importDiaryForm.filePath = res.result[0]
      } else {
        ElMessage.error({ message: res.message, type: 'error' });
      }
    })
  }

  return {
    changeUpload
  }
}

const importDiaryFun = () => {
  console.log(JSON.stringify(importDiaryForm))
  if (importDiaryForm.year === 0) {
    ElMessage({
      message: '请选择日记年份',
      type: 'info',
    })
  } else if (importDiaryForm.filePath === "") {
    ElMessage({
      message: '请选择上传日记压缩包文件',
      type: 'info',
    })
  } else {
    importDiaryApi({
      year: importDiaryForm.year,
      filePath: importDiaryForm.filePath
    }).then((res: any) => {
      if (res.code === 200) {
        ElMessage({
          message: '日记导入成功',
          type: 'success',
        })
      } else {
        ElMessage({
          message: '日记导入失败',
          type: 'error',
        })
      }
      getDiaryListFun(1);
      importDiaryForm.year = 0
      importDiaryForm.filePath = ""
      uploadDiaryDialog.value = false
    })
  }
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
</style>