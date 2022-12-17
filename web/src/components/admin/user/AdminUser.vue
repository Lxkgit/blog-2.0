<template>
  <div>
    <div class="title_style">
      <span>用户管理</span>
    </div>
    <el-card style="margin: 18px 2%;width: 95%">
      <el-button type="primary" plain @click="">注册</el-button>
      <el-button type="danger" plain @click="">注销</el-button>
      <el-table :data="userList.data" stripe style="width: 100%" height="642">
        <el-table-column type="selection" width="55">
        </el-table-column>
        <el-table-column prop="username" label="用户名" width="150" />
        <el-table-column prop="headImg" label="头像" width="100" >
          <template #default="scope">
            <div class="list-img">
              <el-avatar :src="scope.row.headImg" />
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="nickname" label="昵称" width="150" />
        <el-table-column prop="email" label="邮箱地址" width="180" />
        <el-table-column prop="status" label="账号状态" width="100" >
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
        <el-table-column fixed="right" label="操作" width="90">
          <template #default="scope">
            <el-button @click.native.prevent="" size="small" text>
              <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
            </el-button>
            <el-button style="margin-left: 0;" @click.native.prevent="" size="small" text>
              <i class="fa fa-trash" aria-hidden="true"></i>
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
  </div>
</template>

<script setup lang="ts">
import mixin from "../../../mixins/user"
import { ref, reactive, onMounted } from 'vue';
import { userListApi } from "../../../api/user"
let { userStatus } = mixin();
let page = ref<number>(1)
let size = ref<number>(10)
let total = ref<number>(0)
let userList: any = reactive({ data: [] });

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
