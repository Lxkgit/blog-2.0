<template>
  <div>
    <div class="title_style">
      <span>角色管理</span>
    </div>
    <el-card style="margin: 18px 2%; width: 95%">
      <el-button type="primary" plain @click="">创建</el-button>
      <el-button type="danger" plain @click="">删除</el-button>
      <el-table :data="roleList.data" stripe style="width: 100%" height="630">
        <el-table-column type="selection" width="55"> </el-table-column>
        <el-table-column prop="roleCode" label="角色编码" />
        <el-table-column prop="roleName" label="角色名称" />
        <el-table-column prop="updateTime" label="近期修改" />
        <el-table-column prop="createTime" label="创建日期" width="162" />
        <el-table-column fixed="right" label="操作" width="138">
          <template #default="scope">
            <el-button
              style="margin-left: 0"
              @click.prevent="loadMenuData(scope.row.id, 2)"
              size="small"
              text
            >
              <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
            </el-button>
            <el-button
              style="margin-left: 0"
              @click.prevent=""
              size="small"
              text
            >
              <i class="fa fa-trash" aria-hidden="true"></i>
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="margin: 20px 0 50px 0">
        <el-pagination
          background
          style="float: right"
          layout="total, prev, pager, next, jumper"
          @current-change="getRoleListByPage"
          :page-size="size"
          :total="total"
        >
        </el-pagination>
      </div>
    </el-card>
    <div>
      <el-dialog v-model="dialogVisible" title="角色权限修改" width="30%">
        <div style="height: 300px; overflow: auto">
          <el-tree
            :data="menuList.data"
            node-key="id"
            :default-checked-keys="roleMenu"
            show-checkbox
            @check="handleCheckChange"
          />
        </div>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">Cancel</el-button>
            <el-button type="primary" @click="updateRolePerFun()">
              Confirm
            </el-button>
          </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from "vue";
import {
  roleListApi,
  selectRolePerListApi,
  updateRolePerApi,
} from "../../../api/user";
import { menuApi } from "../../../api/menuApi";
let page = ref<number>(1);
let size = ref<number>(14);
let total = ref<number>(0);
let roleList: any = reactive({ data: [] });
let dialogVisible = ref(false);
let menuList: any = reactive({ data: [] });
let roleMenu: any = ref([]);
let roleId: any = ref();
let rolePer: any = ref([]);

onMounted(() => {
  getRoleList(1);
});

const loadMenuData = (id: any, menuType: any) => {
  menuList.data = [];
  roleMenu.value = [];
  rolePer.value = [];
  dialogVisible.value = true;
  roleId.value = id;
  getMenuApi(menuType);
  selectRolePerListFun(id, menuType);
};

const getRoleListByPage = (page: any) => {
  getRoleList(page);
};

const getRoleList = (page: any) => {
  roleListApi(page, size.value).then((res: any) => {
    if (res.code === 200) {
      roleList.data = res.result.list;
      total.value = res.result.total;
    }
  });
};

const getMenuApi = (menuType: any) => {
  menuApi(menuType).then((res: any) => {
    if (res.code === 200) {
      menuList.data = res.result;
      menuList.data.list = "";
    }
  });
};

const selectRolePerListFun = (roleId: any, menuType: any) => {
  selectRolePerListApi(roleId, menuType).then((res: any) => {
    if (res.code === 200) {
      roleMenu.value = res.result.perIds;
      rolePer.value = res.result.perIds;
    }
  });
};

const updateRolePerFun = () => {
  dialogVisible.value = false;
  updateRolePerApi({
    id: roleId.value,
    perIds: rolePer.value,
  }).then((res: any) => {
    console.log(res);
  });
};

const handleCheckChange = (data: any, checked: any) => {
  rolePer.value = checked.checkedKeys;
  console.log("checked:", rolePer.value);
};
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