<template>
  <div>
    <div class="title_style">
      <span>角色管理</span>
    </div>
    <el-card style="margin: 18px 2%;width: 95%">
      <el-button type="primary" plain @click="">创建</el-button>
      <el-button type="danger" plain @click="">删除</el-button>
      <el-table :data="roleList.data" stripe style="width: 100%" height="642">
        <el-table-column type="selection" width="55">
        </el-table-column>
        <el-table-column prop="roleCode" label="角色编码" />
        <el-table-column prop="roleName" label="角色名称" />
        <el-table-column prop="updateTime" label="近期修改" />
        <el-table-column prop="createTime" label="创建日期" width="162" />
        <el-table-column fixed="right" label="操作" width="90">
          <template #default="scope">
            <el-button @click.native.prevent="loadMenuData(scope.row.id)" size="small" text>
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
          @current-change="getRoleListByPage" :page-size="size" :total="total">
        </el-pagination>
      </div>
    </el-card>

    <el-dialog v-model="dialogVisible" title="Tips" width="30%" height="500">
      <div style="height: 300px; overflow:auto;">
        <el-tree :data="menuList.data" node-key="id" :default-checked-keys="roleMenu" show-checkbox />
        
      </div>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">Cancel</el-button>
            <el-button type="primary" @click="dialogVisible = false">
              Confirm
            </el-button>
          </span>
        </template>
    </el-dialog>
  </div>

</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import { roleListApi, selectRolePerListApi } from "../../../api/user"
import { menuApi } from "../../../api/menuApi"
let page = ref<number>(1)
let size = ref<number>(14)
let total = ref<number>(0)
let roleList: any = reactive({ data: [] });
let dialogVisible = ref(false)
let menuList: any = reactive({ data: [] });
let roleMenu: any = ref([])

onMounted(() => {
  getRoleList(1)
});

const loadMenuData = (roleId: any) => {
  console.log(">>> " + roleId)
  dialogVisible.value = true
  getMenuApi()
  selectRolePerListFun(roleId)
};

const getRoleListByPage = (page: any) => {
  getRoleList(page)
};

const getRoleList = (page: any) => {
  roleListApi(page, size.value).then((res: any) => {
    if (res.code === 200) {
      roleList.data = res.result.list;
      total.value = res.result.total;
    }
  })
}

const getMenuApi = () => {
  menuApi().then((res: any) => {
    if (res.code === 200) {
      menuList.data = res.result;
    }
  })
}

const selectRolePerListFun = (roleId: any) => {
  selectRolePerListApi(roleId).then((res: any) => {
    if(res.code === 200) {
      roleMenu.value = res.result.perIds;
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