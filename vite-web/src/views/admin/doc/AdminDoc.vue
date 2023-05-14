<template>
  <div>
    <div class="title_style">
      <span>文档管理</span>
    </div>
    <el-card style="margin: 18px 2%; width: 95%; height: 732px">
      <div style="height: 40px; border-bottom: 1px solid #000">
        <el-button @click="editDoc"> 编辑 </el-button>
      </div>
      <div>
        <el-row>
          <el-col :span="4">
            <div style="overflow-y: scroll; height: 650px; margin-top: 10px">
              <el-tree :data="catalogList" :highlight-current="true" :expand-on-click-node="false" @node-click="handleNodeClick" />
            </div>
          </el-col>
          <el-col :span="20">
            <div style="margin-top: 10px">
              <el-form :model="form" label-width="120px">
                <el-form-item label="文档名称">
                  <el-input
                    v-model="form.name"
                    :disabled="!edit"
                    style="width: 300px"
                  />
                </el-form-item>
                <el-form-item label="文档分类">
                  <el-select
                    v-model="form.region"
                    :disabled="!edit"
                    placeholder="please select your zone"
                  >
                    <el-option label="Zone one" value="shanghai" />
                    <el-option label="Zone two" value="beijing" />
                  </el-select>
                </el-form-item>

                <el-form-item label="文档封面图">
                  <el-upload
                    v-model:file-list="fileList"
                    :disabled="!edit"
                    list-type="picture-card"
                    :on-preview="handlePictureCardPreview"
                    :on-remove="handleRemove"
                  >
                    <el-icon>
                      <MyIcon type="icon-edit" />
                    </el-icon>
                  </el-upload>
                </el-form-item>

                <el-form-item>
                  <el-button v-if="edit" type="primary" @click="onSubmit"
                    >保存</el-button
                  >
                  <el-button v-else type="primary" @click="edit = true"
                    >编辑</el-button
                  >
                </el-form-item>
              </el-form>
            </div>
          </el-col>
        </el-row>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ElMessage } from "element-plus";
// import { getDocCatalogList, getDocCatalogListById, createCatalog, updateCatalog, deleteDocByIds } from "../../../api/article"
import { ref, reactive, onMounted } from "vue";
import type { FormInstance, FormRules } from "element-plus";
import { useRouter } from "vue-router";
// import { docStore } from "../../../store/doc";
import mixin from "@/mixins/doc";
import icon from "@/utils/icon";
import { getDocCatalogTreeApi } from "@/api/content";
import type Node from "element-plus/es/components/tree/src/model/node";
let { docType } = mixin();

import { tagsStore } from "@/store/tag";
const store = tagsStore();

let ids = new Array();
let docList: any = reactive({ data: [] });
let size = ref(14);
let total = ref(0);
let showUpdateDocDialog = ref(false);
let showInsertDocDialog = ref(false);
const router = useRouter();
// const store = docStore();
let { MyIcon } = icon();
// do not use same name with ref
const form = reactive({
  name: "",
  region: "",
  date1: "",
  date2: "",
  delivery: false,
  type: [],
  resource: "",
  desc: "",
});
let edit = ref(false);
const onSubmit = () => {
  edit.value = false;
  console.log("submit!");
};

// 文档目录列表
const catalogList = ref([]);
const getDocCatalogTreeFun = () => {
  getDocCatalogTreeApi({
    typeLowerLimit: 0,
    typeUpperLimit: 3,
    userId: 1,
  }).then((res: any) => {
    if (res.code === 200) {
      let data = res.result;
      catalogList.value = data.map((i: any, index: any) => {
        return {
          id: i["id"],
          label: i["docName"],
          children: (i["list"] || []).map((j: any, index: any) => {
            return {
              id: j["id"],
              label: j["docName"],
              children: (j["list"] || []).map((k: any, index: any) => {
                return {
                  id: k["id"],
                  label: k["docName"],
                  children: (k["list"] || []).map((m: any, index: any) => {
                    return {
                      id: m["id"],
                      label: m["docName"],
                      children: NaN,
                    };
                  }),
                };
              }),
            };
          }),
        };
      });
    }
  });
};

const handleNodeClick = (data: any) => {
  console.log(data);
};

const editDoc = (article?: any) => {
  let path = "/admin/doc/editor";
  store.addTag("编辑文档", path);
  router.push(path);
};

let createForm = reactive({
  parentId: "",
  docName: "",
  docType: "",
  sort: null,
});
const data: any = [
  {
    label: "Level one 1",
    children: [
      {
        label: "Level two 1-1",
        children: [
          {
            label: "Level three 1-1-1",
          },
        ],
      },
    ],
  },
  {
    label: "Level one 2",
    children: [
      {
        label: "Level two 2-1",
        children: [
          {
            label: "Level three 2-1-1",
          },
        ],
      },
      {
        label: "Level two 2-2",
        children: [
          {
            label: "Level three 2-2-1",
          },
        ],
      },
    ],
  },
  {
    label: "Level one 3",
    children: [
      {
        label: "Level two 3-1",
        children: [
          {
            label: "Level three 3-1-1",
          },
        ],
      },
      {
        label: "Level two 3-2",
        children: [
          {
            label: "Level three 3-2-1",
          },
        ],
      },
    ],
  },
  {
    label: "Level one 3",
    children: [
      {
        label: "Level two 3-1",
        children: [
          {
            label: "Level three 3-1-1",
          },
        ],
      },
      {
        label: "Level two 3-2",
        children: [
          {
            label: "Level three 3-2-1",
          },
        ],
      },
    ],
  },
  {
    label: "Level one 3",
    children: [
      {
        label: "Level two 3-1",
        children: [
          {
            label: "Level three 3-1-1",
          },
        ],
      },
      {
        label: "Level two 3-2",
        children: [
          {
            label: "Level three 3-2-1",
          },
        ],
      },
    ],
  },
  {
    label: "Level one 3",
    children: [
      {
        label: "Level two 3-1",
        children: [
          {
            label: "Level three 3-1-1",
          },
        ],
      },
      {
        label: "Level two 3-2",
        children: [
          {
            label: "Level three 3-2-1",
          },
        ],
      },
    ],
  },
  {
    label: "Level one 3",
    children: [
      {
        label: "Level two 3-1",
        children: [
          {
            label: "Level three 3-1-1",
          },
        ],
      },
      {
        label: "Level two 3-2",
        children: [
          {
            label: "Level three 3-2-1",
          },
        ],
      },
    ],
  },
];

const createFormRules = reactive<FormRules>({
  // sort: [
  //     { required: true, message: '排序值不为空'},
  //     { type: 'number', message: 'sort必须为数字值'},
  //     { pattern: /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])$/, message: '取值范围是1-3000', trigger: 'blur' },
  // ],
});

let updateForm: any = reactive({
  id: 0,
  userId: 0,
  parentId: 0,
  docName: "",
  docType: "",
  sort: 0,
  createTime: "",
  updateTime: "",
});

onMounted(() => {
  getDocCatalogTreeFun();
});

const getDocCatalogListFun = (page: any) => {
  // getDocCatalogList({
  //     pageNum: page,
  //     pageSize: 14,
  //     selectType: "admin",
  // }).then((res: any) => {
  //     if (res.code === 200) {
  //         docList.data = res.result.list
  //         size.value = res.result.size
  //         total.value = res.result.total
  //     }
  // })
};

const load = (row: any, treeNode: any, resolve: (date: any) => void) => {
  // getDocCatalogListById({
  //     type: 0,
  //     parentId: row.id
  // }).then((res: any) => {
  //     resolve(res.result)
  // })
};

const loadTree = (node: any, resolve: (date: any) => void) => {
  // if (node.level === 0) {
  //     getDocCatalogListById({
  //         type: 0,
  //         parentId: 0,
  //         docType: "catalog"
  //     }).then((res: any) => {
  //         resolve(res.result)
  //     })
  // } else {
  //     getDocCatalogListById({
  //         type: 0,
  //         parentId: node.data.id,
  //         docType: "catalog"
  //     }).then((res: any) => {
  //         resolve(res.result)
  //     })
  // }
};

const selected = (val: any) => {
  ids.splice(0, ids.length);
  for (let i = 0; i < val.length; i++) {
    ids.unshift(val[i].id);
  }
};

const editCatalogFun = (row: any) => {
  showUpdateDocDialog.value = true;
  updateForm = [];
  updateForm = row;
};

const updateCatalogFun = () => {
  // showUpdateDocDialog.value = false
  // updateCatalog({
  //     id: updateForm.id,
  //     docName: updateForm.docName,
  //     docType: updateForm.docType,
  //     parentId: updateForm.parentId,
  //     sort: updateForm.sort
  // }).then((res: any) => {
  //     if (res.code === 200) {
  //         ElMessage({
  //             message: '文档修改成功',
  //             type: 'success',
  //         })
  //         window.location.reload()
  //     }
  // })
};

const createCatalogOrDocFun = () => {
  showInsertDocDialog.value = true;
};

// 创建文档方法
const createCatalogFun = () => {
  // createCatalog({
  //     parentId: createForm.parentId === "" ? 0 : createForm.parentId,
  //     docName: createForm.docName,
  //     docType: createForm.docType,
  //     sort: createForm.sort
  // }).then((res: any) => {
  //     if (res.code === 200) {
  //         ElMessage({
  //             message: '文档创建成功',
  //             type: 'success',
  //         })
  //         createForm.docName = ""
  //         createForm.docType = ""
  //         createForm.parentId = ""
  //         window.location.reload()
  //     }
  // })
  // showInsertDocDialog.value = false
};

const editContentFun = (docContentId?: any) => {
  // store.setDocId(docContentId);
  // router.push({
  //     name: "docEditor",
  // })
};

const deleteDocFun = (ids: any) => {
  // deleteDocByIds(ids).then((res: any) => {
  //     if (res.code === 200) {
  //         if (res.result.success !== res.result.delete) {
  //             ElMessage({
  //                 message: res.result.msg,
  //                 type: 'error',
  //             })
  //         } else {
  //             ElMessage({
  //                 message: '文档删除成功',
  //                 type: 'success',
  //             })
  //             window.location.reload()
  //         }
  //     }
  // })
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

