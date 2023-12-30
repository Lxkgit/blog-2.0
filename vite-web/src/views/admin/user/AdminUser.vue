<template>
  <div>
    <div class="title_style">
      <span>用户管理</span>
    </div>
    <el-card style="margin: 18px 2%; width: 95%">
      <el-table
        :data="userList.data"
        stripe
        style="width: 100%; height: calc(100vh - 296px)"
      >
        <el-table-column type="selection" width="55"> </el-table-column>
        <el-table-column prop="username" label="用户名" width="150" />
        <el-table-column prop="headImg" label="头像" width="100">
          <template #default="scope">
            <div>
              <el-avatar :src="scope.row.headImg" />
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="nickname" label="昵称" width="150" />
        <el-table-column prop="email" label="邮箱地址" width="180" />
        <el-table-column prop="sysRole" label="角色" width="240">
          <template #default="scope">
            <el-tag
              v-for="role in scope.row.sysRole"
              style="margin-right: 2px; margin-bottom: 2px"
            >
              {{ role.roleName }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="账号状态" width="100">
          <template #default="scope">
            <el-tag v-if="scope.row.status === 1" class="ml-2" type="success">{{
              userStatus(scope.row.status)
            }}</el-tag>
            <el-tag v-else class="ml-2" type="error">{{
              userStatus(scope.row.status)
            }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="updateTime" label="最近登录" />
        <el-table-column prop="createTime" label="创建日期" width="162" />
        <el-table-column fixed="right" label="操作" width="100">
          <template #default="scope">
            <el-button
              style="margin-left: 0"
              @click.prevent="loadUserData(scope.row)"
              size="small"
              text
              title="修改信息"
            >
              <MyIcon type="icon-edit" />
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="margin: 20px 0 50px 0">
        <el-pagination
          background
          v-model:current-page="page"
          v-model:page-size="size"
          :page-sizes="[10, 20, 50, 100]"
          style="float: right"
          layout="total, sizes, prev, pager, next, jumper"
          @current-change="pageChange"
          @size-change="sizeChange"
          :total="total"
        >
        </el-pagination>
      </div>
    </el-card>
    <el-dialog v-model="updateUserDialog" title="用户信息" width="30%">
      <el-form :model="userDate.data">
        <el-form-item label="用户名：" label-width="100">
          <el-input
            v-model="userDate.data.username"
            disabled
            autocomplete="off"
            style="width: 230px"
          />
        </el-form-item>
        <el-form-item label="头像：" label-width="100">
          <div>
            <ImgUpload
              @upload="userHeadUpload"
              :key="userDate.data.id"
              :imgList="
                userDate.data.headImg === null ||
                userDate.data.headImg === undefined ||
                userDate.data.headImg === ''
                  ? []
                  : [userDate.data.headImg]
              "
              :num="1"
              fileTypeCode="1"
              filePathCode="3"
              :cropper="1"
              :autoCropWidth="100"
              :autoCropHeight="100"
            />
          </div>
        </el-form-item>
        <el-form-item label="昵称：" label-width="100">
          <el-input
            v-model="userDate.data.nickname"
            autocomplete="off"
            style="width: 230px"
          />
        </el-form-item>
        <el-form-item label="角色：" label-width="100">
          <el-select
            v-model="userDate.data.roleIds"
            multiple
            collapse-tags
            placeholder="选择角色"
            style="width: 230px"
          >
            <el-option
              v-for="role in roleList.data"
              :key="role.id"
              :label="role.label"
              :value="role.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="邮箱：" label-width="100">
          <el-input
            v-model="userDate.data.email"
            autocomplete="off"
            style="width: 230px"
          />
        </el-form-item>
        <el-form-item label="账号状态：" label-width="100">
          <el-select v-model="userDate.data.status">
            <el-option label="正常" :value="1" key="1" />
            <el-option label="禁用" :value="2" key="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="最近登录" label-width="100">
          <el-input
            v-model="userDate.data.updateTime"
            disabled
            autocomplete="off"
            style="width: 230px"
          />
        </el-form-item>
        <el-form-item label="创建日期" label-width="100">
          <el-input
            v-model="userDate.data.createTime"
            disabled
            autocomplete="off"
            style="width: 230px"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="updateUserDialog = false">取消</el-button>
          <el-button type="primary" @click="updateUserFun"> 提交 </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import mixin from "@/mixins/user";
import { ref, reactive, onMounted } from "vue";
import { userListApi, roleListApi, updateUserPerApi } from "@/api/user";
import type { UploadProps } from "element-plus";
import { ElMessage } from "element-plus";
import { uploadUrl, header } from "@/utils/upload";
import icon from "@/utils/icon";
import ImgUpload from "@/components/common/ImgUpload.vue";

let {
  page,
  size,
  total,
  userList,
  roleList,
  updateUserDialog,
  userDate,
  pageChange,
  sizeChange,
  getUserList,
  getRoleList,
  loadUserData,
  userHeadUpload,
  updateUserFun,
} = userFn();

let { MyIcon } = icon();
let { userStatus } = mixin();

onMounted(() => {
  pageChange(1);
});

/**
 * 用户增删改查操作方法合集
 */
function userFn(): any {
  // 分页参数
  let page = ref<number>(1);
  let size = ref<number>(20);
  let total = ref<number>(0);

  // 用户列表
  let userList: any = reactive({ data: [] });
  // 角色列表
  let roleList: any = reactive({ data: [] });
  // 修改用户dialog
  let updateUserDialog = ref(false);
  // 修改用户数据
  const userDate: any = reactive({ data: {} });

  /**
   * 页数修改查询数据
   */
  const pageChange = (page: any) => {
    getUserList(page);
  };

  /**
   * 页大小修改查询数据
   */
  const sizeChange = (size: any) => {
    getUserList(1);
  };

  /**
   * 分页获取用户列表
   */
  const getUserList = (page: any) => {
    userListApi(page, size.value).then((res: any) => {
      if (res.code === 200) {
        userList.data = res.result.list;
        total.value = res.result.total;
      }
    });
  };

  /**
   * 获取全部角色列表
   */
  const getRoleList = () => {
    roleListApi(1, 200).then((res: any) => {
      if (res.code === 200) {
        roleList.data = res.result.list;
        for (let i = 0; i < roleList.data.length; i++) {
          roleList.data[i].label = roleList.data[i].roleName;
          roleList.data[i].value = roleList.data[i].id;
        }
      }
    });
  };

  /**
   * 修改用户信息
   */
  const loadUserData = (data: any) => {
    // 转为json 再转回来防止表单修改影响页面展示
    userDate.data = JSON.parse(JSON.stringify(data));
    updateUserDialog.value = true;
    getRoleList();
  };

  /**
   * 上传用户头像回调方法
   */
  const userHeadUpload = (upload: any) => {
    userDate.data.headImg = upload[0].url;
  };

  /**
   * 管理员修改用户数据
   */
  const updateUserFun = () => {
    updateUserDialog.value = false;
    updateUserPerApi({
      id: userDate.data.id,
      nickname: userDate.data.nickname,
      roleIds: userDate.data.roleIds,
      headImg: userDate.data.headImg,
      email: userDate.data.email,
      status: userDate.data.status,
    }).then((res: any) => {
      if (res.code === 200) {
        ElMessage({
          message: "用户数据修改成功",
          type: "success",
        });
        pageChange(1);
      }
    });
  };

  return {
    page,
    size,
    total,
    userList,
    roleList,
    updateUserDialog,
    userDate,
    pageChange,
    sizeChange,
    getUserList,
    getRoleList,
    loadUserData,
    userHeadUpload,
    updateUserFun,
  };
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
