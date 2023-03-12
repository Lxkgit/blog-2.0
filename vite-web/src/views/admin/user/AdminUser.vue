<template>
  <div>
    <div class="title_style">
      <span>用户管理</span>
    </div>
    <el-card style="margin: 18px 2%;width: 95%">
      <el-button type="primary" plain @click="">注册</el-button>
      <el-button type="danger" plain @click="">注销</el-button>
      <el-table :data="userList.data" stripe style="width: 100%" height="610">
        <el-table-column type="selection" width="55">
        </el-table-column>
        <el-table-column prop="username" label="用户名" width="150" />
        <el-table-column prop="headImg" label="头像" width="100">
          <template #default="scope">
            <div>
              <el-avatar :src="scope.row.headImg" />
              <!-- <el-image :src="scope.row.headImg" :preview-src-list="[scope.row.headImg]" fit="cover" z-index="9999"/> -->
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="nickname" label="昵称" width="150" />
        <el-table-column prop="email" label="邮箱地址" width="180" />
        <el-table-column prop="sysRole" label="角色" width="240">
          <template #default="scope">
            <el-tag v-for="role in scope.row.sysRole" style="margin-right: 2px; margin-bottom: 2px;">
              {{ role.roleName }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="账号状态" width="100">
          <template #default="scope">
            <el-tag v-if="(scope.row.status === 1)" class="ml-2" type="success">{{
              userStatus(scope.row.status)
            }}</el-tag>
            <el-tag v-else class="ml-2" type="info">{{
              userStatus(scope.row.status)
            }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="updateTime" label="最近登录" />
        <el-table-column prop="createTime" label="创建日期" width="162" />
        <el-table-column fixed="right" label="操作" width="100">
          <template #default="scope">
            <el-button @click.prevent="loadUserData(scope.row)" size="small" text>
              <MyIcon type="icon-edit"/>
            </el-button>
            <el-button style="margin-left: 0;" @click.prevent="" size="small" text>
              <MyIcon type="icon-shanchu"/>
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="margin: 20px 0 50px 0">
        <el-pagination background style="float:right;" layout="total, prev, pager, next, jumper"
          @current-change="getUserListByPage" :page-size="size" :total="total">
        </el-pagination>
      </div>
    </el-card>
    <el-dialog v-model="userDialog" title="用户信息" width="30%">
      <el-form :model="userDate.data">
        <el-form-item label="用户名：" label-width="100">
          <el-input v-model="userDate.data.username" disabled autocomplete="off" style="width: 230px;" />
        </el-form-item>
        <el-form-item label="头像：" label-width="100">
          <el-avatar v-if="imageUrl != ''" :src="imageUrl" style="margin-right: 10px;" />
          <el-avatar v-else :src="userDate.data.headImg" style="margin-right: 10px;" />
          <el-upload :action="uploadUrl" :headers="header" :show-file-list="false" :data="uploadData" name="files"
            :on-success="imgUploadFun" :before-upload="beforeUploadFun">
            <i class="fa fa-picture-o" aria-hidden="true">
              点击上传
            </i>
          </el-upload>
        </el-form-item>
        <el-form-item label="昵称：" label-width="100">
          <el-input v-model="userDate.data.nickname" autocomplete="off" style="width: 230px;" />
        </el-form-item>
        <el-form-item label="角色：" label-width="100">
          <el-select v-model="userDate.data.roleIds" multiple collapse-tags placeholder="选择角色" style="width: 230px;">
            <el-option v-for="role in roleList.data" :key="role.id" :label="role.label" :value="role.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="邮箱：" label-width="100">
          <el-input v-model="userDate.data.email" autocomplete="off" style="width: 230px;" />
        </el-form-item>
        <el-form-item label="账号状态：" label-width="100">
          <!-- <el-tag v-if="(userDate.data.status === 1)" class="ml-2" type="success">{{
            userStatus(userDate.data.status)
          }}</el-tag>
          <el-tag v-else class="ml-2" type="info">{{
            userStatus(userDate.data.status)
          }}</el-tag> -->
        </el-form-item>
        <el-form-item label="最近登录" label-width="100">
          <el-input v-model="userDate.data.updateTime" disabled autocomplete="off" style="width: 230px;" />
        </el-form-item>
        <el-form-item label="创建日期" label-width="100">
          <el-input v-model="userDate.data.createTime" disabled autocomplete="off" style="width: 230px;" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="userDialog = false">取消</el-button>
          <el-button type="primary" @click="updateUserFun">
            提交
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import mixin from "@/mixins/user"
import { ref, reactive, onMounted } from 'vue';
import { userListApi } from "@/api/user"
import type { UploadProps } from 'element-plus'
import { ElMessage } from 'element-plus'
import { uploadUrl, header } from "@/utils/upload"
import icon from '@/utils/icon'

let { MyIcon } = icon()

let { userStatus } = mixin();
let page = ref<number>(1)
let size = ref<number>(10)
let total = ref<number>(0)
let userList: any = reactive({ data: [] });
let roleList: any = reactive({ data: [] });
let userDialog = ref(false)
let userDate: any = reactive({ data: {} })
let imageName = ref('')
let imageUrl = ref('')

let uploadData: Record<string, any> = {
  type: "img",
  fileType: "head"
};

const beforeUploadFun: UploadProps['beforeUpload'] = (rawFile) => {
  imageName.value = ""
  imageName.value = rawFile.name
  if (rawFile.type !== 'image/jpeg') {
    ElMessage.error('图片格式错误')
    return false
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error('图片最大为2MB')
    return false
  }
  return true
}

onMounted(() => {
  getUserList(1)
});

const getUserListByPage = (page: any) => {
  getUserList(page)
};

const getUserList = (page: any) => {
  userListApi(page, size.value).then((res: any) => {
    if (res.code === 200) {
      userList.data = res.result.list;
      total.value = res.result.total;
    }
  })
}

const getRoleList = () => {
  // roleListApi(1, 20).then((res: any) => {
  //   if (res.code === 200) {
  //     roleList.data = res.result.list;
  //     for (let i = 0; i < roleList.data.length; i++) {
  //       roleList.data[i].label = roleList.data[i].roleName
  //       roleList.data[i].value = roleList.data[i].id
  //     }
  //   }
  // });
};

const loadUserData = (data: any) => {
  userDate.data = data
  userDialog.value = true;
  getRoleList()
}

const imgUploadFun = (res: any) => {
  imageUrl.value = res.result.fileUrl
  ElMessage({
    message: '用户头像上传成功',
    type: 'success',
  })
  console.log(imageUrl.value)
}

const updateUserFun = () => {
  // userDialog.value = false;
  // updateUserApi({
  //   id: userDate.data.id,
  //   nickname: userDate.data.nickname,
  //   roleIds: userDate.data.roleIds,
  //   headImg: imageUrl.value === '' ? userDate.data.headImg : imageUrl.value,
  //   email: userDate.data.email
  // }).then((res: any) => {
  //   if (res.code === 200) {
  //     ElMessage({
  //       message: '用户数据修改成功',
  //       type: 'success',
  //     })
  //     getUserList(1)
  //   }
  // })
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
