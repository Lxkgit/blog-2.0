<template>
  <div>
    <div class="title_style">
      <span>文章管理</span>
    </div>
    <el-card style="margin: 18px 2%; width: 95%">
      <el-button type="primary" plain @click="editArticle('null')">新增</el-button>

      <el-popover :visible="deleteBtnPopoverByIds" placement="top" :width="160">
        <p>删除所选文章？</p>
        <div style="text-align: right; margin: 0">
          <el-button size="small" text @click="deleteBtnPopoverByIds = false"
            >取消</el-button
          >
          <el-button size="small" type="primary" @click="deleteArticle(0)"
            >删除</el-button
          >
        </div>
        <template #reference>
          <el-button
            :disabled="ids.length > 0 ? false : true"
            type="danger"
            plain
            @click="deleteBtnPopoverByIds = true"
            >删除</el-button
          >
        </template>
      </el-popover>
      <el-table
        :data="articleList.data"
        stripe
        style="width: 100%; height: calc(100vh - 328px)"
        @selection-change="selected"
      >
        <el-table-column type="selection" width="55"> </el-table-column>
        <el-table-column prop="title" label="标题" fit> </el-table-column>
        <el-table-column label="文章分类" width="300">
          <template #default="scope">
            <el-tag
              :style="'color: ' + tagColor(item.id)"
              style="margin-right: 2px; margin-bottom: 2px"
              v-for="item in scope.row.articleTypes"
            >
              {{ item.typeName }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="文章标签" width="180">
          <template #default="scope">
            <el-tag
              :style="'color: ' + tagColor(item.id)"
              style="margin-right: 2px; margin-bottom: 2px"
              v-for="item in scope.row.articleLabels"
            >
              {{ item.labelName }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="文章状态" width="80">
          <template #default="scope">
            <el-tag v-if="scope.row.articleStatus == 0" class="ml-2" type="info">
              {{ articleStatus(scope.row.articleStatus) }}
            </el-tag>
            <el-tag v-if="scope.row.articleStatus == 1" class="ml-2" type="success">
              {{ articleStatus(scope.row.articleStatus) }}
            </el-tag>
            <el-tag v-if="scope.row.articleStatus == 2" class="ml-2">
              {{ articleStatus(scope.row.articleStatus) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="updateTime" label="最近更新" width="162">
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="100">
          <template #default="scope">
            <el-button @click="editArticle(scope.row)" size="small" text>
              <MyIcon type="icon-edit" />
            </el-button>
            <el-popover
              :visible="deleteBtnPopoverById && selectRow === scope.$index"
              placement="top"
              :width="160"
              :ref="`popover-${scope.$index}`"
            >
              <p>删除所选文章？</p>
              <div style="text-align: right; margin: 0">
                <el-button size="small" text @click="deleteBtnPopoverById = false"
                  >取消</el-button
                >
                <el-button
                  size="small"
                  type="primary"
                  @click="deleteArticle(scope.row.id)"
                  >删除</el-button
                >
              </div>
              <template #reference>
                <el-button
                  style="margin: 0; padding: 8px"
                  @click="
                    deleteBtnPopoverById = true;
                    selectRow = scope.$index;
                  "
                  size="small"
                  text
                >
                  <MyIcon type="icon-delete" />
                </el-button>
              </template>
            </el-popover>
          </template>
        </el-table-column>
      </el-table>
      <div style="margin: 20px 0 50px 0">
        <el-pagination
          background
          v-model:page-size="size"
          :page-sizes="[10, 20, 50, 100]"
          style="float: right"
          layout="total, sizes, prev, pager, next, jumper"
          @current-change="getArticleListFun"
          :page-size="size"
          :total="total"
        >
        </el-pagination>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from "vue";
import { ElMessage } from "element-plus";
import { useRouter } from "vue-router";
import { contentStore } from "@/store/content";
import { getArticleListApi, deleteArticleByIdsApi } from "@/api/content";
import { tagsStore } from "@/store/tag";
import icon from "@/utils/icon";
import color from "@/utils/color";
import mixin from "@/mixins/article";

let {
  page,
  size,
  total,
  deleteBtnPopoverByIds,
  deleteBtnPopoverById,
  articleList,
  ids,
  getArticleListFun,
  editArticle,
  selected,
  deleteArticle,
} = articleFn();

const router = useRouter();
const store = tagsStore();
const cStore = contentStore();

let { MyIcon } = icon();
let { tagColor } = color();
let { articleStatus } = mixin();

onMounted(() => {
  getArticleListFun(page.value);
});

/**
 * 管理页文章展示页面方法合集
 */
function articleFn(): any {
  // 文章分页信息
  let page = ref<number>(1);
  let size = ref<number>(20);
  let total = ref<number>(0);

  // 多选删除文章Popover弹窗展示
  let deleteBtnPopoverByIds = ref(false);
  // 单选删除文章Popover弹窗展示
  let deleteBtnPopoverById = ref(false);

  // 文章列表
  const articleList: any = reactive({ data: [] });

  // 勾选文章id 用于批量删除
  let ids = reactive([]);

  /**
   * 分页获取文章数据
   */
  async function getArticleListFun(page: any) {
    const params = {
      pageNum: page,
      pageSize: size.value,
      type: 1,
      selectStatus: "0,1,2",
      sortType: "1",
    };
    getArticleListApi(params).then((res: any) => {
      if (res.code === 200) {
        articleList.data = res.result.list;
        total.value = res.result.total;
      }
    });
  }

  /**
   * 编辑文章，跳转页面
   */
  const editArticle = (article?: any) => {
    let path = "/admin/article/editor";
    store.addTag("编辑文章", path, true);
    router.push(path);
    cStore.setArticle(article);
  };

  /**
   * 获取勾选文章id
   */
  const selected = (val: any) => {
    ids.splice(0, ids.length);
    for (let i = 0; i < val.length; i++) {
      ids.unshift(val[i].id);
    }
  };

  /**
   * 删除文章
   */
  const deleteArticle = (id?: any) => {
    if (id === 0) {
      if (ids.length !== 0) {
        deleteArticleByIdsApi(ids.join()).then((res: any) => {
          if (res.code === 200) {
            deleteBtnPopoverByIds.value = false;
            getArticleListFun(1);
            ElMessage.success({ message: "文章删除成功", type: "success" });
          }
        });
      }
    } else {
      deleteArticleByIdsApi(id).then((res: any) => {
        getArticleListFun(1);
        deleteBtnPopoverById.value = false;
        ElMessage.success({ message: "文章删除成功", type: "success" });
      });
    }
  };

  return {
    page,
    size,
    total,
    deleteBtnPopoverByIds,
    deleteBtnPopoverById,
    articleList,
    ids,
    getArticleListFun,
    editArticle,
    selected,
    deleteArticle,
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
