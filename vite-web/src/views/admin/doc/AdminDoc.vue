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
              <el-tree :data="catalogList" :highlight-current="true" :expand-on-click-node="false"
                @node-click="handleNodeClick" />
            </div>
          </el-col>
          <el-col :span="20">
            <div style="margin-top: 10px">
              <div v-if="docType === 1" style="overflow-y: scroll; height: 650px;">
                <MarkDown :text="docContent.data.docContentMd"></MarkDown>
              </div>

              <div v-else-if="docType === 0">
                <el-form :model="docCatalog" label-width="120px">
                  <el-form-item label="文档名称">
                    <el-input v-model="docCatalog.docName" :disabled="!edit" style="width: 300px" />
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
                    <el-button v-if="edit" type="primary" @click="onSubmit">保存</el-button>
                    <el-button v-else type="primary" @click="edit = true">编辑</el-button>
                  </el-form-item>
                </el-form>
              </div>

              <div v-else>
                个人文档数据统计
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ElMessage } from "element-plus";
import { ref, reactive, onMounted } from "vue";
import { useRouter } from "vue-router";
import mixin from "@/mixins/doc";
import icon from "@/utils/icon";
import { getDocCatalogTreeApi, getDocContentByIdApi } from "@/api/content";
import MarkDown from "@/components/detail/MarkDown.vue";
import { tagsStore } from "@/store/tag";

// 引入笔记目录模块
let { docType, docLevel, catalogList, getDocCatalogTreeFun, handleNodeClick } = catalog();
let { editDoc } = content();
const store = tagsStore();

const router = useRouter();
let { MyIcon } = icon();

// 文档目录信息
let docCatalog = reactive({
  docName: "",
  docType: "",
  docImg: ""
});
// 文档内容信息
let docContent: any = reactive({ data: {} });

let edit = ref(false);
const onSubmit = () => {
  edit.value = false;
  console.log("submit!");
};

// 文档目录模块
function catalog() {
  // 文档目录类型
  let docType = ref();
  // 文档目录层级
  let docLevel = ref();
  // 文档目录列表
  const catalogList = ref([]);
  // 加载文档目录树
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
            label: i["docName"],
            docType: i["docType"],
            docLevel: i["docLevel"],
            children: (i["list"] || []).map((j: any, index: any) => {
              return {
                id: j["id"],
                label: j["docName"],
                docType: j["docType"],
                docImg: j["imgUrl"],
                docLevel: j["docLevel"],
                children: (j["list"] || []).map((k: any, index: any) => {
                  return {
                    id: k["id"],
                    label: k["docName"],
                    docType: k["docType"],
                    docLevel: k["docLevel"],
                    children: (k["list"] || []).map((m: any, index: any) => {
                      return {
                        id: m["id"],
                        label: m["docName"],
                        docType: m["docType"],
                        docLevel: m["docLevel"],
                        children: (m["list"] || []).map((n: any, index: any) => {
                          return {
                            id: n["id"],
                            label: n["docName"],
                            docType: n["docType"],
                            docLevel: n["docLevel"],
                            children: NaN,
                          };
                        }),
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
    docContent.data = {}
    docType.value = data.docType;
    docLevel.value = data.docLevel;
    docCatalog.docName = data.label;
    if (data.docType === 1) {
      getDocContentByIdApi(data.id).then((res: any) => {
        if (res.code === 200) {
          docContent.data = res.result
        }
      })
    }
  };
  return {
    docType,
    docLevel,
    catalogList,
    getDocCatalogTreeFun,
    handleNodeClick
  };
}
// 文档内容模块
function content() {
  // 编辑文档
  const editDoc = (doc?: any) => {
    let path = "/admin/doc/editor";
    store.addTag("编辑文档", path);
    router.push(path);
  };
  return {
    editDoc
  }
}
onMounted(() => {
  getDocCatalogTreeFun();
});

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

