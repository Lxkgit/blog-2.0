<template>
  <div>
    <div class="title_style">
      <span>日记管理</span>
    </div>
    <el-card style="margin: 18px 2%;width: 95%">
      <el-button type="primary" plain @click="newDiaryFun">新增</el-button>
      <el-popover :visible="deleteBtnVisible" placement="top" :width="160">
        <p>删除所选日记？</p>
        <div style="text-align: right; margin: 0">
          <el-button size="small" text @click="deleteBtnVisible = false">取消</el-button>
          <el-button size="small" type="primary" @click="deleteDiaryFun(0)">删除</el-button>
        </div>
        <template #reference>
          <el-button type="danger" @click="deleteBtnVisible = true" plain>删除</el-button>
        </template>
      </el-popover>
      <el-button type="info" plain @click="uploadDiaryDialog = true">导入</el-button>

      <el-table :data="diaryList.data" stripe style="width: 100%" height="610" @selection-change="selected">
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
            <el-button style="margin: 0; padding: 8px;" @click="showDiaryFun(scope.row)" size="small" text>
              <i class="fa fa-eye" aria-hidden="true"></i>
            </el-button>
            <el-button style="margin: 0; padding: 8px;" @click="updateDiaryFun(scope.row)" size="small" text>
              <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
            </el-button>
            <el-button style="margin: 0; padding: 8px;" @click="deleteDiaryFun(scope.row.id)" size="small" text>
              <i class="fa fa-trash" aria-hidden="true"></i>
            </el-button>
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
            <el-date-picker v-model="diary.data.diaryDate" type="date" placeholder="选择日期" value-format="YYYY-MM-DD" />
            <span style="font-size: 14px; line-height: 1;">
              <span> 最近保存时间：</span>
              <span v-if="saveTime !== ''">{{ saveTime }}</span>
              <span v-else>未保存</span>
            </span>
          </div>
          <DiaryEditor></DiaryEditor>
          <!-- <v-md-editor v-model="diary.data.diaryMd" height="500px" @save="saveDiaryFun"
            @change="changeDiaryFun"></v-md-editor> -->
        </div>
      </el-dialog>
      <el-dialog v-model="showDiaryDialog" title="查看日记">
        <div>
          <el-date-picker v-model="showDiary.data.diaryDate" type="date" value-format="YYYY-MM-DD" disabled />
          <div
            style="height: 400px; overflow: auto;  margin-top: 10px; margin-bottom: 10px; border-width: 1px; border-style: solid;">
            <v-md-preview :text="showDiary.data.diaryMd"></v-md-preview>
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
              <el-upload :action="uploadUrl" :headers="header" :show-file-list="false" :data="uploadData"
                style="display: inline; margin-left: 12px;" :on-success="zipUploadFun" name="files"
                :before-upload="beforeUploadFun">
                <el-button type="primary">上传日记</el-button>
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
import { ElMessage } from 'element-plus';
import { onMounted, ref, reactive } from 'vue';
// import { getDiaryList, saveDiary, updateDiary, deleteDiaryByIds, importDiary } from "../../../api/article"
import { uploadUrl, header } from "@/utils/upload"
import type { UploadProps } from 'element-plus'
import DiaryEditor from "@/views/admin/diary/DiaryEditor.vue"

let ids = new Array();
let size = ref<number>(14)
let total = ref<number>(0)
let diaryList: any = reactive({ data: [] });
let saveTime = ref("");
let diary: any = reactive({ data: { id: 0 } });
let showDiary: any = reactive({ data: [] });
let deleteBtnVisible = ref(false)
let saveAndUpdateDiaryDialog = ref(false)
let showDiaryDialog = ref(false)
let time: number = 0;
let saveFlag: boolean = false;
let uploadDiaryDialog = ref(false);
let importDiaryForm = reactive({
  year: 0,
  filePath: ""
})

onMounted(() => {
  getDiaryListFun(1);
  time = window.setInterval(() => {
    saveFlag = true;
  }, 30000)
});

const getDiaryListFun = (page: any) => {
  // getDiaryList({
  //   pageNum: page,
  //   pageSize: size.value,
  // }).then((res: any) => {
  //   if (res.code === 200) {
  //     diaryList.data = res.result.diary.list
  //     total.value = res.result.diary.total
  //   }
  // })
}

const selected = (val: any) => {
  ids.splice(0, ids.length)
  for (let i = 0; i < val.length; i++) {
    ids.unshift(val[i].id)
  }
}

const showDiaryFun = (diary: any) => {
  // showDiary = [];
  showDiary.data = diary;
  showDiaryDialog.value = true;
}

const newDiaryFun = () => {
  saveTime.value = ""
  diary.data = { id: 0 }
  diary.data.diaryDate = getNowDate()
  saveAndUpdateDiaryDialog.value = true;
}

const updateDiaryFun = (param: any) => {
  saveTime.value = ""
  diary.data = param;
  saveAndUpdateDiaryDialog.value = true;
}

const saveDiaryFun = () => {
  // console.log(JSON.stringify(diary))
  // if (diary.data.id !== 0) {
  //   updateDiary({
  //     id: diary.data.id,
  //     diaryMd: diary.data.diaryMd,
  //     diaryDate: diary.data.diaryDate
  //   }).then((res: any) => {
  //     if (res.code === 200) {
  //       ElMessage({
  //         message: '日记修改成功',
  //         type: 'success',
  //       })
  //       getDiaryListFun(1)
  //       saveAndUpdateDiaryDialog.value = false;
  //     }
  //   })
  // } else {
  //   saveDiary({
  //     diaryMd: diary.data.diaryMd,
  //     diaryDate: diary.data.diaryDate
  //   }).then((res: any) => {
  //     if (res.code === 200) {
  //       ElMessage({
  //         message: '日记保存成功',
  //         type: 'success',
  //       })
  //       getDiaryListFun(1)
  //       saveAndUpdateDiaryDialog.value = false;
  //     }
  //   })
  // }
}

const deleteDiaryFun = (id?: any) => {
  // deleteBtnVisible.value = false;
  // if (id === 0) {
  //   if (ids.length !== 0) {
  //     deleteDiaryByIds(ids.join()).then((res: any) => {
  //       if (res.code === 200) {
  //         ElMessage({
  //           message: '日记删除成功',
  //           type: 'success',
  //         })
  //         getDiaryListFun(1)
  //       }
  //     })
  //   }
  // } else {
  //   deleteDiaryByIds(id).then((res: any) => {
  //     if (res.code === 200) {
  //       ElMessage({
  //         message: '日记删除成功',
  //         type: 'success',
  //       })
  //       getDiaryListFun(1)
  //     }
  //   })
  // }
}

const changeDiaryFun = () => {
  // if (saveFlag === true) {
  //   saveFlag = false
  //   if (diary.data.id !== 0) {
  //     updateDiary({
  //       id: diary.data.id,
  //       diaryMd: diary.data.diaryMd,
  //       diaryDate: diary.data.diaryDate
  //     }).then((res: any) => {
  //       if (res.code === 200) {
  //         saveTime.value = getNowTime();
  //         getDiaryListFun(1)
  //       }
  //     })
  //   } else {
  //     saveDiary({
  //       diaryMd: diary.data.diaryMd,
  //       diaryDate: diary.data.diaryDate
  //     }).then((res: any) => {
  //       if (res.code === 200) {
  //         saveTime.value = getNowTime();
  //         diary.data.id = res.result;
  //         getDiaryListFun(1)
  //       }
  //     })
  //   }
  // }
}

let uploadData: Record<string, any> = {
  type: "file",
  fileType: "diary"
};

const zipUploadFun = (res: any) => {
  importDiaryForm.filePath = res.result.filePath
  ElMessage({
    message: '文件上传成功',
    type: 'success',
  })
}

const beforeUploadFun: UploadProps['beforeUpload'] = (rawFile) => {
  console.log("rawFile.type: "+ rawFile.type)
  if (rawFile.type === 'application/zip' || rawFile.type === "application/x-zip-compressed" || rawFile.type === "application/x-zip") {
    return true
  }
  ElMessage.error('文件格式错误, 仅支持zip压缩包')
  return false
}

const importDiaryFun = () => {
  // console.log(JSON.stringify(importDiaryForm))
  // if (importDiaryForm.year === 0) {
  //   ElMessage({
  //     message: '请选择日记年份',
  //     type: 'info',
  //   })
  // } else if (importDiaryForm.filePath === "") {
  //   ElMessage({
  //     message: '请选择上传日记压缩包文件',
  //     type: 'info',
  //   })
  // } else {
  //   importDiary({
  //     year: importDiaryForm.year,
  //     filePath: importDiaryForm.filePath
  //   }).then((res: any) => {
  //     if (res.code === 200) {
  //       ElMessage({
  //         message: '日记导入成功',
  //         type: 'success',
  //       })
  //     } else {
  //       ElMessage({
  //         message: '日记导入失败',
  //         type: 'error',
  //       })
  //     }
  //     getDiaryListFun(1);
  //     importDiaryForm.year = 0
  //     importDiaryForm.filePath = ""
  //     uploadDiaryDialog.value = false
  //   })
  // }


}

const getNowDate = () => {
  let data = new Date();
  let year = data.getFullYear();
  let month = data.getMonth();
  let day = data.getDate();
  let monthStr: any = "";
  let dayStr: any = "";
  month = month + 1;
  if (month < 10) {
    monthStr = "0" + month;
  } else {
    monthStr = month;
  }
  if (day < 10) {
    dayStr = "0" + day;
  } else {
    dayStr = day;
  }
  return year + "-" + monthStr + "-" + dayStr;
}

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