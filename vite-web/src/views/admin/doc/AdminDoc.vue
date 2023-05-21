<template>
  <div>
    <div class="title_style">
      <span>文档管理</span>
    </div>
    <el-card style="margin: 18px 2%; width: 95%; height: 732px">
      <!-- <div style="height: 40px; border-bottom: 1px solid #000">
        <el-button @click="editDoc"> 编辑 </el-button>
      </div> -->
      <div>
        <el-row>
          <el-col :span="4">
            <div style="overflow-y: scroll; height: 650px; margin-top: 10px">
              <el-input v-model="filterText" placeholder="Filter keyword" />
              <el-tree
                ref="treeRef"
                class="filter-tree"
                :data="catalogList"
                :highlight-current="true"
                :expand-on-click-node="false"
                @node-click="handleNodeClick"
                @node-contextmenu="nodeRightClick"
                :filter-node-method="filterNode"
              />
            </div>
          </el-col>
          <el-col :span="20">
            <div style="margin-top: 10px">
              <div
                v-if="docType === 1"
                style="overflow-y: scroll; height: 650px"
              >
                <MarkDown :text="docContent.data.docContentMd"></MarkDown>
              </div>

              <div v-else-if="docType === 0">
                <el-form :model="docCatalog" label-width="120px">
                  <el-form-item label="文档名称">
                    <el-input
                      v-model="docCatalog.docName"
                      :disabled="!edit"
                      style="width: 300px"
                    />
                  </el-form-item>
                  <el-form-item label="文档分类">
                    <el-select v-model="docCatalog.docType" :disabled="!edit">
                      <el-option label="目录" value="0" />
                      <el-option label="内容" value="1" />
                    </el-select>
                  </el-form-item>

                  <!-- <el-form-item v-if="docLevel === 1" label="文档封面图">
                    <el-upload v-model:file-list="fileList" :disabled="!edit" list-type="picture-card"
                      :on-preview="handlePictureCardPreview" :on-remove="handleRemove">
                      <el-icon>
                        <MyIcon type="icon-edit" />
                      </el-icon>
                    </el-upload>
                  </el-form-item> -->

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
              <div v-else>个人文档数据统计</div>
            </div>
          </el-col>
        </el-row>
      </div>
    </el-card>
    <div
      v-show="visible"
      :style="{ left: left + 'px', top: top + 'px' }"
      class="contextmenu"
    >
      <div
        v-if="clickRightItem.docLevel <= 2 && clickRightItem.docType == 0"
        @click="createCatalogDialogFun()"
      >
        创建目录
      </div>
      <div
        v-if="clickRightItem.docLevel >= 1 && clickRightItem.docType == 0"
        @click="createContentDialogFun()"
      >
        创建文档
      </div>
      <div
        v-if="clickRightItem.docType == 1"
        @click="editDocContentFun(clickRightItem)"
      >
        编辑
      </div>
      <div
        v-if="clickRightItem.delete == 1"
        @click="deleteDocCatalogFun(clickRightItem)"
      >
        删除
      </div>
      <div @click="visible = false">关闭</div>
    </div>

    <el-dialog v-model="createCatalogDialog" title="创建文档目录" width="460px">
      <el-form :model="createCatalogFrom" label-width="120px">
        <el-form-item label="目录名称">
          <el-input v-model="createCatalogFrom.docName" style="width: 264px" />
        </el-form-item>
        <el-form-item label="文档根目录">
          <el-switch
            v-model="createCatalogFrom.root"
            inline-prompt
            active-text="是"
            inactive-text="否"
          />
        </el-form-item>
        <el-form-item v-if="!createCatalogFrom.root" label="分类组织结构：">
          <el-tree-select
            v-model="createCatalogFrom.node"
            class="filter-tree"
            :data="catalogListT"
            @node-click="setTreeData"
            :render-after-expand="false"
            check-strictly
            clearable
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="createCatalogDialog = false">取消</el-button>
          <el-button type="primary" @click="saveCatalogDialogFun">
            创建
          </el-button>
        </span>
      </template>
    </el-dialog>

    <el-dialog v-model="createContentDialog" title="创建文档" width="460px">
      <el-form :model="createCatalogFrom" label-width="120px">
        <el-form-item label="文档名称">
          <el-input v-model="createCatalogFrom.docName" style="width: 264px" />
        </el-form-item>
        <el-form-item label="文档根目录">
          <el-switch
            v-model="createCatalogFrom.root"
            inline-prompt
            active-text="是"
            inactive-text="否"
          />
        </el-form-item>
        <el-form-item v-if="!createCatalogFrom.root" label="分类组织结构：">
          <el-tree-select
            v-model="createCatalogFrom.node"
            class="filter-tree"
            :data="catalogListT"
            @node-click="setTreeData"
            :render-after-expand="false"
            check-strictly
            clearable
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="createCatalogDialog = false">取消</el-button>
          <el-button type="primary" @click="saveContentDialogFun">
            创建
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ElMessage } from "element-plus";
import { ref, reactive, onMounted, watch } from "vue";
import { useRouter } from "vue-router";
import mixin from "@/mixins/doc";
import icon from "@/utils/icon";
import {
  getDocCatalogTreeApi,
  getDocContentByIdApi,
  saveDocCatalogApi,
  deleteDocApi,
} from "@/api/content";
import MarkDown from "@/components/detail/MarkDown.vue";
import { tagsStore } from "@/store/tag";
import { contentStore } from "@/store/content";
import { ElTree } from "element-plus";

// 引入笔记目录模块
let {
  docType,
  docLevel,
  catalogList,
  catalogListT,
  getDocCatalogTreeFun,
  handleNodeClick,
  deleteDocCatalogFun,
  createCatalogDialogFun,
  saveCatalogDialogFun,
  getDocCatalogThreeLevelCatalogTreeFun
} = catalog();
let { editDocContentFun, createContentDialogFun, saveContentDialogFun } = content();
const store = tagsStore();
const cStore = contentStore();
const router = useRouter();
let { MyIcon } = icon();
let createCatalogDialog = ref(false);
let createContentDialog = ref(false);

const filterText = ref("");
const treeRef = ref<InstanceType<typeof ElTree>>();

// 监听文档目录树筛选输入框
watch(filterText, (val) => {
  treeRef.value!.filter(val);
});

// 页面加载调用方法
onMounted(() => {
  getDocCatalogTreeFun();
});

// 过滤主文档树选项
const filterNode = (value: string, data: any) => {
  if (!value) return true;
  return data.label.includes(value);
};

// 文档目录信息
let docCatalog = reactive({
  docName: "",
  docType: "",
  docImg: "",
});

// 创建文档目录表单
let createCatalogFrom: any = reactive({
  node: null,
  id: Number(null),
  docName: "",
  docLevel: Number(null),
  docType: 0,
  imgUrl: "",
  root: false,
});

// 文档内容信息
let docContent: any = reactive({ data: {} });
// 文档目录树邮件菜单是否展示控制
let visible = ref(false);
// 右键菜单的左边距
let left = ref(0);
// 右键菜单的上边距
let top = ref(0);
// 右键选中的对象
let clickRightItem: any = ref({});
// 右键选中对象的node信息
let clickRightNode: any = ref({});
// 主展示页面是否可以编辑
let edit = ref(false);
// 编辑按钮控制方法
const onSubmit = () => {
  edit.value = false;
};
// 设置创建文档默认数据
const setTreeData = (data: any) => {
  createCatalogFrom.id = data.id;
  createCatalogFrom.docLevel = data.docLevel;
};
// 节点点击事件
const nodeRightClick = (event: any, data: any, node: any, self: any) => {
  clickRightItem = data;
  clickRightNode = node;
  createCatalogFrom.node = data.id;
  createCatalogFrom.docLevel = data.docLevel;
  createCatalogFrom.id = data.id;
  // 设置右键菜单位置
  left.value = event.clientX;
  top.value = event.clientY;
  visible.value = true;
};

// 文档目录模块
function catalog() {
  // 文档目录类型
  let docType = ref();
  // 文档目录层级
  let docLevel = ref();
  // 文档目录列表
  let catalogList = ref([]);
  // 文档前三级目录
  let catalogListT = ref([]);
  // 加载文档主目录树
  const getDocCatalogTreeFun = () => {
    getDocCatalogTreeApi({
      typeLowerLimit: 0,
      typeUpperLimit: 4,
      userId: 1,
    }).then((res: any) => {
      if (res.code === 200) {
        let data = res.result;
        catalogList.value = data.map((i: any, index: any) => {
          return {
            id: i["id"],
            value: i["id"],
            label: i["docName"],
            docType: i["docType"],
            docLevel: i["docLevel"],
            delete: i["list"] === null ? 1 : 0,
            children: (i["list"] || []).map((j: any, index: any) => {
              return {
                id: j["id"],
                value: j["id"],
                label: j["docName"],
                docType: j["docType"],
                docImg: j["imgUrl"],
                docLevel: j["docLevel"],
                delete: j["list"] === null ? 1 : 0,
                children: (j["list"] || []).map((k: any, index: any) => {
                  return {
                    id: k["id"],
                    value: k["id"],
                    label: k["docName"],
                    docType: k["docType"],
                    docLevel: k["docLevel"],
                    delete: k["list"] === null ? 1 : 0,
                    children: (k["list"] || []).map((m: any, index: any) => {
                      return {
                        id: m["id"],
                        value: m["id"],
                        label: m["docName"],
                        docType: m["docType"],
                        docLevel: m["docLevel"],
                        delete: m["list"] === null ? 1 : 0,
                        children: (m["list"] || []).map(
                          (n: any, index: any) => {
                            return {
                              id: n["id"],
                              value: n["id"],
                              label: n["docName"],
                              docType: n["docType"],
                              docLevel: n["docLevel"],
                              delete: n["list"] === null ? 1 : 0,
                              children: NaN,
                            };
                          }
                        ),
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
  // 加载文档前 x 级目录树
  const getDocCatalogThreeLevelCatalogTreeFun = (upperLimit: any) => {
    getDocCatalogTreeApi({
      typeLowerLimit: 0,
      typeUpperLimit: upperLimit,
      docType: 0,
      userId: 1,
    }).then((res: any) => {
      if (res.code === 200) {
        let data = res.result;
        catalogListT.value = data.map((i: any, index: any) => {
          return {
            id: i["id"],
            value: i["id"],
            label: i["docName"],
            docType: i["docType"],
            docLevel: i["docLevel"],
            delete: i["list"] === null ? 1 : 0,
            children: (i["list"] || []).map((j: any, index: any) => {
              return {
                id: j["id"],
                value: j["id"],
                label: j["docName"],
                docType: j["docType"],
                docImg: j["imgUrl"],
                docLevel: j["docLevel"],
                delete: j["list"] === null ? 1 : 0,
                children: (j["list"] || []).map((k: any, index: any) => {
                  return {
                    id: k["id"],
                    value: k["id"],
                    label: k["docName"],
                    docType: k["docType"],
                    docLevel: k["docLevel"],
                    delete: k["list"] === null ? 1 : 0,
                    children: (k["list"] || []).map((m: any, index: any) => {
                      return {
                        id: m["id"],
                        value: m["id"],
                        label: m["docName"],
                        docType: m["docType"],
                        docLevel: m["docLevel"],
                        delete: m["list"] === null ? 1 : 0,
                        children: NaN
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
  // 点击目录树上文章标签加载对应文章
  const handleNodeClick = (data: any) => {
    docContent.data = {};
    docType.value = data.docType;
    docLevel.value = data.docLevel;
    docCatalog.docName = data.label;
    if (data.docType === 1) {
      getDocContentByIdApi(data.id).then((res: any) => {
        if (res.code === 200) {
          docContent.data = res.result;
        }
      });
    }
  };
  // 删除文档目录
  const deleteDocCatalogFun = (node: any) => {
    docType.value = null
    visible.value = false;
    deleteDocApi(node.id).then((res: any) => {
      if (res.code === 200) {
        console.log(res);
        getDocCatalogTreeFun();
      }
    });
  };
  // 创建文档目录
  const createCatalogDialogFun = () => {
    getDocCatalogThreeLevelCatalogTreeFun(2);
    createCatalogDialog.value = true;
    visible.value = false;
  };
  // 文档目录创建完成dialog模块保存方法
  const saveCatalogDialogFun = () => {
    createCatalogDialog.value = false;
    saveDocCatalogFun();
  };
  // 保存文档目录接口方法
  const saveDocCatalogFun = () => {
    console.log(JSON.stringify(createCatalogFrom));
    saveDocCatalogApi({
      parentId: createCatalogFrom.id == null ? 0 : createCatalogFrom.id,
      docName: createCatalogFrom.docName,
      docLevel:
        createCatalogFrom.docLevel == null ? 0 : createCatalogFrom.docLevel + 1,
      docType: 0,
    }).then((res: any) => {
      console.log(res);
      getDocCatalogTreeFun();
    });
  };
  return {
    docType,
    docLevel,
    catalogList,
    catalogListT,
    getDocCatalogTreeFun,
    handleNodeClick,
    deleteDocCatalogFun,
    createCatalogDialogFun,
    saveCatalogDialogFun,
    getDocCatalogThreeLevelCatalogTreeFun
  };
}
// 文档内容模块
function content() {
  // 编辑文档
  const editDocContentFun = (docContent?: any) => {
    let path = "/admin/doc/editor";
    store.addTag("编辑文档", path);
    cStore.setDocContent(docContent);
    router.push(path);
  };
  // 创建文档dialog方法
  const createContentDialogFun = () => {
    getDocCatalogThreeLevelCatalogTreeFun(3);
    createContentDialog.value = true
    visible.value = false;
  };
  // 保存文档调用接口方法
  const saveDocContentFun = () => {
    console.log(JSON.stringify(createCatalogFrom));
    saveDocCatalogApi({
      parentId: createCatalogFrom.id == null ? 0 : createCatalogFrom.id,
      docName: createCatalogFrom.docName,
      docLevel:
        createCatalogFrom.docLevel == null ? 0 : createCatalogFrom.docLevel + 1,
      docType: 1,
    }).then((res: any) => {
      getDocCatalogTreeFun();
      createCatalogFrom = {}
    });
  };
  // 保存文档dialog方法
  const saveContentDialogFun = () => {
    createContentDialog.value = false
    saveDocContentFun()
  };
  return {
    editDocContentFun,
    createContentDialogFun,
    saveContentDialogFun
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
</style>

