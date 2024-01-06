<template>
  <div>
    <div class="title_style">
      <span>角色管理</span>
    </div>
    <el-card style="margin: 18px 2%; width: 95%">
      <el-button type="primary" plain @click="roleCreateDialog = true">创建</el-button>

      <el-popover :visible="deleteBtnPopoverByIds" placement="top" :width="160">
        <p>删除所选角色？</p>
        <div style="text-align: right; margin: 0">
          <el-button size="small" text @click="deleteBtnPopoverByIds = false">取消</el-button>
          <el-button size="small" type="primary" @click="deleteRoleFun(0)">删除</el-button>
        </div>
        <template #reference>
          <el-button :disabled="ids.length > 0 ? false : true" type="danger" plain
            @click="deleteBtnPopoverByIds = true">删除</el-button>
        </template>
      </el-popover>

      <el-table :data="roleList.data" stripe style="width: 100%; height: calc(100vh - 328px)"
        @selection-change="selected">
        <el-table-column type="selection" width="55"> </el-table-column>
        <el-table-column prop="roleCode" label="角色编码" />
        <el-table-column prop="roleName" label="角色名称" />
        <el-table-column prop="updateTime" label="近期修改" />
        <el-table-column prop="createTime" label="创建日期" width="162" />
        <el-table-column fixed="right" label="操作" width="100">
          <template #default="scope">
            <el-button style="margin-left: 0" @click.prevent="loadMenuData(scope.row.id, 2)" size="small" text
              title="修改角色信息">
              <MyIcon type="icon-edit" />
            </el-button>

            <el-popover :visible="deleteBtnPopoverById  && selectRow === scope.$index" placement="top" :width="160" :ref="`popover-${scope.$index}`">
              <p>删除所选角色？</p>
              <div style="text-align: right; margin: 0">
                <el-button size="small" text @click="deleteBtnPopoverById = false">取消</el-button>
                <el-button size="small" type="primary" @click="deleteRoleFun(scope.row.id)">删除</el-button>
              </div>
              <template #reference>
                <el-button style="margin-left: 0" @click="
                  deleteBtnPopoverById = true;
                  selectRow = scope.$index;
                " size="small" text title="删除角色">
                  <MyIcon type="icon-delete" />
                </el-button>
              </template>
            </el-popover>
          </template>
        </el-table-column>
      </el-table>
      <div style="margin: 20px 0 50px 0">
        <el-pagination background v-model:page-size="size" :page-sizes="[10, 20, 50, 100]" style="float: right"
          layout="total, sizes, prev, pager, next, jumper" @current-change="pageChange" @size-change="sizeChange" :total="total">
        </el-pagination>
      </div>
    </el-card>
    <div>
      <el-dialog v-model="updateRolePerDialog" title="角色权限修改" width="30%">
        <div style="height: 300px; overflow: auto">
          <el-tree :data="menuList.data" node-key="id" :default-checked-keys="roleMenu" show-checkbox
            @check="handleCheckChange" />
        </div>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="updateRolePerDialog = false">取消</el-button>
            <el-button type="primary" @click="updateRolePerFun()"> 提交 </el-button>
          </span>
        </template>
      </el-dialog>

      <el-dialog v-model="roleCreateDialog" title="创建新角色" style="width: 30%; max-width: 500px">
        <div style="height: auto; overflow: auto">
          <el-form :model="roleDate" ref="createRoleFormRef" :rules="createRoleRules">
            <el-form-item label="角色编码：" label-width="100" prop="roleCode">
              <el-input v-model="roleDate.roleCode" autocomplete="off" style="width: 80%; max-width: 250px" />
            </el-form-item>
            <el-form-item label="角色名称：" label-width="100" prop="roleName">
              <el-input v-model="roleDate.roleName" autocomplete="off" style="width: 80%; max-width: 250px" />
            </el-form-item>
          </el-form>
        </div>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="roleCreateDialog = false">取消</el-button>
            <el-button type="primary" @click="createRoleFun()"> 提交 </el-button>
          </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from "vue";
import { ElMessage } from "element-plus";
import {
  roleListApi,
  createRoleApi,
  updateRoleApi,
  deleteRoleApi,
  selectRolePerListApi,
  updateRolePerApi,
  allMenuApi,
} from "@/api/user";
import icon from "@/utils/icon";

let {
  roleCreateDialog,
  createRoleFormRef,
  roleDate,
  createRoleRules,
  createRoleFun,
} = createRoleFn();

let {
  ids,
  page,
  size,
  total,
  roleList,
  updateRolePerDialog,
  menuList,
  roleMenu,
  roleId,
  rolePer,
  deleteBtnPopoverByIds,
  deleteBtnPopoverById,
  selected,
  deleteRoleFun,
  updateRolePerFun,
  handleCheckChange,
  loadMenuData,
  pageChange,
  sizeChange,
  getRoleList,
  getMenuApi,
  selectRolePerListFun,
} = roleFn();

// 界面icon
let { MyIcon } = icon();
let selectRow = ref(0);
/**
 * 页面初始化
 */
onMounted(() => {
  pageChange(1);
});

/**
 * 创建角色方法合集
 */
function createRoleFn(): any {
  // 创建角色数据
  let roleDate = reactive({
    roleCode: "",
    roleName: "",
  });

  // 角色创建修改dialog
  let roleCreateDialog = ref(false);

  // 角色编码格式校验
  const checkRoleCode = (rule: any, value: any, callback: any) => {
    if (!value) {
      return callback(new Error("请输入角色编码(只允许大小写字母与数字)"));
    }
    const pattern = /^[a-zA-Z0-9]+$/;
    if (!pattern.test(value)) {
      callback(new Error("请输入正确的角色编码(只允许大小写字母与数字)"));
    } else {
      callback();
    }
  };

  // 注册表单验证规则
  const createRoleRules = {
    roleCode: [{ required: true, validator: checkRoleCode, trigger: "blur" }],
    roleName: [{ required: true, message: "请输入角色名称", trigger: "blur" }],
  };

  // 创建角色表单
  const createRoleFormRef: any = ref(null);

  /**
   * 创建角色
   */
  const createRoleFun = () => {
    if (createRoleFormRef.value !== null) {
      createRoleFormRef.value.validate((valid: any) => {
        if (valid) {
          createRoleApi(roleDate).then((res: any) => {
            if (res.code === 200) {
              pageChange(1);
              ElMessage.success({ message: "角色创建成功", type: "success" });
              roleCreateDialog.value = false;
              roleDate.roleCode = "";
              roleDate.roleName = "";
            }
          });
        }
      });
    }
  };

  return {
    roleCreateDialog,
    createRoleFormRef,
    roleDate,
    createRoleRules,
    createRoleFun,
  };
}

/**
 * 角色删改查接口方法合集
 */
function roleFn(): any {
  // 勾选角色id（删除使用）
  let ids = reactive([]);

  // 分页数据 （查询页面）
  let page = ref<number>(1);
  let size = ref<number>(20);
  let total = ref<number>(0);

  // 角色列表 （查询）
  let roleList: any = reactive({ data: [] });

  // 角色权限修改dialog
  let updateRolePerDialog = ref(false);

  // 全部菜单列表
  let menuList: any = reactive({ data: [] });

  // 角色菜单
  let roleMenu: any = ref([]);

  // 修改角色权限时角色 id 与 权限列表
  let roleId: any = ref();
  let rolePer: any = ref([]);

  // 多选删除角色Popover弹窗展示
  let deleteBtnPopoverByIds = ref(false);
  // 单选删除角色Popover弹窗展示
  let deleteBtnPopoverById = ref(false);

  // 获取勾选角色id
  const selected = (val: any[]) => {
    ids.splice(0, ids.length);
    for (let i = 0; i < val.length; i++) {
      //@ts-ignore 单行忽略
      ids.unshift(val[i].id);
    }
  };
  /**
   * 删除ids勾选的角色
   */
  const deleteRoleFun = (id?: any) => {
    if (id === 0) {
      if (ids.length !== 0) {
        deleteRoleApi(ids.join()).then((res: any) => {
          if (res.code === 200) {
            deleteBtnPopoverByIds.value = false;
            pageChange(1);
            ElMessage.success({ message: "角色删除成功", type: "success" });
          }
        });
      }
    } else {
      deleteRoleApi(id).then((res: any) => {
        deleteBtnPopoverById.value = false;
        pageChange(1);
        ElMessage.success({ message: "角色删除成功", type: "success" });
      });
    }
  };

  /**
   * 修改角色权限
   */
  const updateRolePerFun = () => {
    updateRolePerDialog.value = false;
    if (roleId.value === 1) {
      ElMessage({
        message: "超级管理员权限无法修改",
        type: "error",
      });
    } else {
      updateRolePerApi({
        id: roleId.value,
        perIds: rolePer.value,
      }).then((res: any) => {
        if (res.code === 200) {
          ElMessage({
            message: "角色权限修改成功",
            type: "success",
          });
        }
      });
    }
  };

  /**
   * 更新角色权限数组
   */
  const handleCheckChange = (data: any, checked: any) => {
    rolePer.value = checked.checkedKeys;
  };

  /**
   * 加载角色权限
   */
  const loadMenuData = (id: any, menuType: any) => {
    menuList.data = [];
    roleMenu.value = [];
    rolePer.value = [];
    updateRolePerDialog.value = true;
    roleId.value = id;
    getMenuApi(menuType);
    selectRolePerListFun(id, menuType);
  };

  const pageChange = (page: any) => {
    getRoleList(page, size.value)
  }

  const sizeChange = (size: any) => {
    getRoleList(1, size)
  }

  /**
   * 分页查询角色
   */
  const getRoleList = (page: any, size: any) => {
    roleListApi(page, size).then((res: any) => {
      if (res.code === 200) {
        roleList.data = res.result.list;
        total.value = res.result.total;
      }
    });
  };

  /**
   * 获取全部菜单
   */
  const getMenuApi = (menuType: any) => {
    allMenuApi(menuType).then((res: any) => {
      if (res.code === 200) {
        menuList.data = res.result;
        menuList.data.list = "";
      }
    });
  };

  /**
   * 获取当前用户拥有权限的菜单
   */
  const selectRolePerListFun = (roleId: any, menuType: any) => {
    selectRolePerListApi(roleId, menuType).then((res: any) => {
      if (res.code === 200) {
        roleMenu.value = res.result.perIds;
        rolePer.value = res.result.perIds;
      }
    });
  };

  return {
    ids,
    page,
    size,
    total,
    roleList,
    updateRolePerDialog,
    menuList,
    roleMenu,
    roleId,
    rolePer,
    deleteBtnPopoverByIds,
    deleteBtnPopoverById,
    selected,
    deleteRoleFun,
    updateRolePerFun,
    handleCheckChange,
    loadMenuData,
    pageChange,
    sizeChange,
    getRoleList,
    getMenuApi,
    selectRolePerListFun,
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
