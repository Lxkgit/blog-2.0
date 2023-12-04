<template>
  <div>
    <div class="title_style">
      <span>文档管理</span>
    </div>
    <el-card style="margin: 18px 2%; width: 95%;">
      <div>
        <el-row>
          <el-col :span="4">
            <div
              style="border-bottom: 1px solid rgb(233, 235, 238); font-weight: 700; height: 20px; border-right: 1px solid rgb(233, 235, 238); display: flex; justify-content: space-between;">
              <p style="">文档目录</p>
              <MyIcon style="margin-right: 20px; " @click="createCatalogDialogFun(true)" type="icon-plus" />
            </div>
            <div style="overflow-y: auto; height: calc(100vh - 277px); padding-top: 10px; border-right: 1px solid rgb(233, 235, 238);">
              <el-input v-model="filterText" placeholder="请输入标题" style="width: 95%; height: 30px;"
                size="small" />
              <el-tree style="margin-top: 20px;" ref="treeRef" class="filter-tree" :data="catalogList" draggable
                @node-drag-start="handleDragStart" @node-drag-end="handleDragEnd" :highlight-current="true"
                :expand-on-click-node="false" @node-click="handleNodeClick" @node-contextmenu="nodeRightClick"
                :filter-node-method="filterNode" />
            </div>
          </el-col>
          <el-col :span="20">
            <div style="margin-top: 10px">
              <div v-if="docType === 1" style="overflow-y: scroll;">
                <MarkDown :text="docContent.data.docContentMd"></MarkDown>
              </div>
              <div v-else-if="docType === 0">
                <el-form :model="docCatalog.data" label-width="120px">
                  <el-form-item label="文档名称">
                    <el-input v-model="docCatalog.data.docName" style="width: 300px" />
                  </el-form-item>
                  <el-form-item v-if="docLevel === 1" label="文档封面图">
                    <ImgUpload @upload="imgUpload" :key="docCatalog.data.catalogId"
                      :imgList="(docCatalog.data.docImg === null || docCatalog.data.docImg === undefined || docCatalog.data.docImg === '') ? [] : [docCatalog.data.docImg]"
                      :num="1" fileTypeCode="1" filePathCode="2" :cropper="1" :autoCropWidth="228"
                      :autoCropHeight="240" />
                  </el-form-item>
                  <el-form-item>
                    <el-button type="primary" @click="updateCatalogFun">修改</el-button>
                  </el-form-item>
                </el-form>
              </div>
              <div v-else>个人文档数据统计</div>
            </div>
          </el-col>
        </el-row>
      </div>
    </el-card>
    <div v-show="visible" :style="{ left: left + 'px', top: top + 'px' }" class="contextmenu">
      <div v-if="clickRightItem.docLevel <= 2 && clickRightItem.docType == 0" @click="createCatalogDialogFun(false)">
        创建目录
      </div>
      <div v-if="clickRightItem.docLevel >= 1 && clickRightItem.docType == 0" @click="createContentDialogFun()">
        创建文档
      </div>
      <div v-if="clickRightItem.docType == 1" @click="editDocContentFun(clickRightItem)">
        编辑
      </div>
      <div v-if="clickRightItem.delete == 1" @click="deleteDocCatalogFun(clickRightItem)">
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
          <el-switch v-model="createCatalogFrom.root" inline-prompt active-text="是" inactive-text="否" />
        </el-form-item>
        <el-form-item v-if="!createCatalogFrom.root" label="分类组织结构：">
          <el-tree-select v-model="createCatalogFrom.node" class="filter-tree" :data="catalogListT"
            @node-click="setTreeData" :render-after-expand="false" check-strictly clearable />
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
          <el-switch v-model="createCatalogFrom.root" inline-prompt active-text="是" inactive-text="否" />
        </el-form-item>
        <el-form-item v-if="!createCatalogFrom.root" label="分类组织结构：">
          <el-tree-select v-model="createCatalogFrom.node" class="filter-tree" :data="catalogListT"
            @node-click="setTreeData" :render-after-expand="false" check-strictly clearable />
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
import { ref, reactive, onMounted, watch } from "vue";
import { ElMessage, ElTree } from "element-plus";
import { useRouter } from "vue-router";
import ImgUpload from "@/components/common/ImgUpload.vue"
import MarkDown from "@/components/detail/MarkDown.vue";
import { getDocCatalogTreeApi, getDocContentByIdApi, saveDocCatalogApi, deleteDocApi, updateCatalogApi } from "@/api/content";
import { tagsStore } from "@/store/tag";
import { contentStore } from "@/store/content";
import icon from "@/utils/icon";
// 引入笔记目录模块
let { filterText, treeRef, docCatalog, createCatalogFrom, createCatalogDialog, docType, docLevel, catalogList, catalogListT, imgUpload,
  filterNode, getDocCatalogTreeFun, handleNodeClick, deleteDocCatalogFun, createCatalogDialogFun, saveCatalogDialogFun, updateCatalogFun,
  getDocCatalogThreeLevelCatalogTreeFun } = catalog();
let { docContent, createContentDialog, editDocContentFun, createContentDialogFun, saveContentDialogFun } = content();

const store = tagsStore();
const cStore = contentStore();
const router = useRouter();
let { MyIcon } = icon();

// 页面加载调用方法
onMounted(() => {
  getDocCatalogTreeFun();
});


const handleDragStart = (node: any,
  // ev: DragEvents
) => {
  console.log('drag start', node.data)
  if (node.data.docLevel === 0) {
    ElMessage.error({ message: '根目录禁止拖动' });
    return false
  }
}
const handleDragEnd = (
  // draggingNode: Node,
  dropNode: Node,
  // dropType: NodeDropType,
  // ev: DragEvents
) => {
  // console.log('tree drag end:', dropNode && dropNode.label, dropType)
  console.log('tree drag end:', dropNode)


}


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

  // 关键字查找
  const filterText = ref("");
  const treeRef = ref<InstanceType<typeof ElTree>>();
  // 监听文档目录树筛选输入框
  watch(filterText, (val) => {
    treeRef.value!.filter(val);
  });
  // 过滤主文档树选项
  const filterNode = (value: string, data: any) => {
    if (!value) return true;
    return data.label.includes(value);
  };

  // 文档目录信息
  let docCatalog: any = reactive({ data: {} });

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
  // 创建文档目录
  let createCatalogDialog = ref(false);

  // 文档目录类型
  let docType = ref();
  // 文档目录层级
  let docLevel = ref();
  // 文档目录列表
  let catalogList = ref([]);
  // 文档前三级目录
  let catalogListT = ref([]);

  // 文章封面上传
  const imgUpload = (upload: any) => {
    docCatalog.data.docImg = upload[0].url
  }
  // 加载文档主目录树
  const getDocCatalogTreeFun = () => {
    getDocCatalogTreeApi({
      typeLowerLimit: 0,
      typeUpperLimit: 4,
      type: 1,
    }).then((res: any) => {
      if (res.code === 200) {
        catalogList.value = res.result;
      }
    });
  };
  // 加载文档前 x 级目录树
  const getDocCatalogThreeLevelCatalogTreeFun = (upperLimit: any) => {
    getDocCatalogTreeApi({
      typeLowerLimit: 0,
      typeUpperLimit: upperLimit,
      docType: 0,
      type: 1,
    }).then((res: any) => {
      if (res.code === 200) {
        catalogListT.value = res.result;
      }
    });
  };
  // 点击目录树上文章标签加载对应文章
  const handleNodeClick = (data: any) => {
    docContent.data = {};
    docCatalog.data = {};
    docType.value = data.docType;
    docLevel.value = data.docLevel;
    docCatalog.data.catalogId = data.id;
    docCatalog.data.docName = data.label;
    if (data.docImg === null || data.docImg === "") {
      docCatalog.data.docImg = ""
    } else {
      docCatalog.data.docImg = data.imgUrl;
    }
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
        getDocCatalogTreeFun();
      }
    });
  };
  // 创建文档目录
  const createCatalogDialogFun = (root: boolean) => {
    createCatalogFrom.root = root;
    getDocCatalogThreeLevelCatalogTreeFun(2);
    createCatalogDialog.value = true;
    visible.value = false;
  };
  // 修改文档目录
  const updateCatalogFun = () => {
    updateCatalogApi({
      id: docCatalog.data.catalogId,
      docName: docCatalog.data.docName,
      imgUrl: docCatalog.data.docImg
    }).then((res: any) => {
      if (res.code === 200) {
        ElMessage.success({
          message: '文档目录修改成功',
          type: 'success'
        });
        docType.value = null
        getDocCatalogTreeFun();
      }
    })
  }
  // 文档目录创建完成dialog模块保存方法
  const saveCatalogDialogFun = () => {
    createCatalogDialog.value = false;
    saveDocCatalogFun();
  };
  // 保存文档目录接口方法
  const saveDocCatalogFun = () => {
    saveDocCatalogApi({
      parentId: createCatalogFrom.root === true ? 0 : createCatalogFrom.id,
      docName: createCatalogFrom.docName,
      docLevel: createCatalogFrom.root === true ? 0 : createCatalogFrom.docLevel + 1,
      docType: 0,
    }).then((res: any) => {
      if (res.code === 200) {
        getDocCatalogTreeFun();
        createCatalogFrom.node = null;
        createCatalogFrom.id = null;
        createCatalogFrom.docName = "";
        createCatalogFrom.docLevel = null;
        createCatalogFrom.docType = 0;
        createCatalogFrom.imgUrl = "";
        createCatalogFrom.root = false;
      }
    });
  };





  return {
    filterText,
    treeRef,
    docCatalog,
    createCatalogFrom,
    createCatalogDialog,
    docType,
    docLevel,
    catalogList,
    catalogListT,
    imgUpload,
    filterNode,
    getDocCatalogTreeFun,
    handleNodeClick,
    deleteDocCatalogFun,
    createCatalogDialogFun,
    saveCatalogDialogFun,
    updateCatalogFun,
    getDocCatalogThreeLevelCatalogTreeFun
  };
}

// 文档内容模块
function content() {
  // 文档内容信息
  let docContent: any = reactive({ data: {} });
  // 创建文档
  let createContentDialog = ref(false);
  // 编辑文档
  const editDocContentFun = (docContent?: any) => {
    let path = "/admin/doc/editor";
    store.addTag("编辑文档", path, true);
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
    saveDocCatalogApi({
      parentId: createCatalogFrom.id == null ? 0 : createCatalogFrom.id,
      docName: createCatalogFrom.docName,
      docLevel:
        createCatalogFrom.docLevel == null ? 0 : createCatalogFrom.docLevel + 1,
      docType: 1,
    }).then((res: any) => {
      if (res.code === 200) {
        getDocCatalogTreeFun();
        createCatalogFrom.node = null;
        createCatalogFrom.id = null;
        createCatalogFrom.docName = "";
        createCatalogFrom.docLevel = null;
        createCatalogFrom.docType = 0;
        createCatalogFrom.imgUrl = "";
        createCatalogFrom.root = false;
      }
    });
  };
  // 保存文档dialog方法
  const saveContentDialogFun = () => {
    createContentDialog.value = false
    saveDocContentFun()
  };
  return {
    docContent,
    createContentDialog,
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
  /* min-width: min-content; */
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

.img_upload .el-upload--picture-card {
  display: none !important;
  background-color: black;
}

.dialog-footer button:first-child {
  margin-right: 10px;
}
</style>

