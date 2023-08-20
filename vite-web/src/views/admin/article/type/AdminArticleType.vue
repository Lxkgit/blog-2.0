<template>
    <div>
        <div class="title_style">
            <span>文章分类</span>
        </div>
        <el-card style="margin: 18px 2%;width: 95%">
            <el-button type="primary" plain @click="saveArticleTypeDialogFun">新增</el-button>
            <el-button type="danger" plain @click="deleteArticleTypeByIdsFun()">删除</el-button>
            <el-table :data="articleTypeList.data" stripe style="width: 100%" row-key="id" height="660"
                @selection-change="selected">
                <el-table-column type="selection" width="55">
                </el-table-column>
                <el-table-column prop="typeName" label="文章分类名称">
                </el-table-column>
                <el-table-column prop="num" label="分类文章数量">
                </el-table-column>
                <el-table-column prop="blogUser.username" label="创建用户">
                </el-table-column>
                <el-table-column prop="createTime" label="创建时间">
                </el-table-column>
                <el-table-column prop="updateTime" label="最后修改时间">
                </el-table-column>
                <el-table-column fixed="right" label="操作" width="100">
                    <template #default="scope">
                        <el-button @click.native.prevent="updateArticleTypeDialogFun(scope.row)" size="small" text>
                            <MyIcon type="icon-edit" />
                        </el-button>
                        <el-button style="margin-left: 0;" @click.native.prevent="deleteArticleTypeByIdsFun(scope.row.id)"
                            size="small" text>
                            <MyIcon type="icon-delete" />
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>
        <el-dialog v-model="createArticleTypeDialog" title="创建文章分类" width="460px">
            <div>
                <el-form :model="createForm" label-width="120px">
                    <el-form-item label="分类名称：">
                        <el-input v-model="createForm.articleTypeName" style="width: 214px;" />
                    </el-form-item>
                    <el-form-item label="分类组织结构：">
                        <el-tree-select v-model="createForm.articleTypeParentId" :data="articleTypeList.data"
                            :render-after-expand="false" check-strictly clearable />
                    </el-form-item>
                    <el-form-item>
                        <el-button @click="createArticleTypeDialog = false">取消</el-button>
                        <el-button type="primary" @click="saveArticleTypeFun">
                            保存
                        </el-button>
                    </el-form-item>
                </el-form>
            </div>
        </el-dialog>

        <el-dialog v-model="updateArticleTypeDialog" title="修改文章分类" width="460px">
            <div>
                <el-form :model="updateArticleTypeForm" label-width="120px">
                    <el-form-item label="分类名称：">
                        <el-input v-model="updateArticleTypeForm.articleTypeName" style="width: 214px;" />
                    </el-form-item>
                    <el-form-item label="分类组织结构：">
                        <el-tree-select v-model="updateArticleTypeForm.articleTypeParentId" :data="articleTypeList.data"
                            :render-after-expand="false" check-strictly clearable />
                    </el-form-item>
                    <el-form-item>
                        <el-button @click="updateArticleTypeDialog = false">取消</el-button>
                        <el-button type="primary" @click="updateArticleTypeFun">
                            保存
                        </el-button>
                    </el-form-item>
                </el-form>
            </div>
        </el-dialog>
    </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import { ElMessage } from 'element-plus'
import { getArticleTypeTreeApi, saveArticleTypeApi, deleteArticleTypeByIdsApi, updateArticleTypeApi } from "@/api/content";
import icon from '@/utils/icon'

let { MyIcon } = icon()
// 文章分类列表
const articleTypeList: any = reactive({
    data: [],
})

onMounted(() => {
    getArticleTypeTreeFun()
});

/**
 * dialog是否展示
 */
const createArticleTypeDialog = ref(false)
const updateArticleTypeDialog = ref(false)

/**
 * 新增文章分类表单
 */
let createForm = reactive({
    articleTypeName: '',
    articleTypeParentId: '',
})

/**
 * 修改文章分类表单
 */
let updateArticleTypeForm = reactive({
    articleTypeName: '',
    articleTypeParentId: '',
})

/**
 * 获取文章分类树接口
 */
const getArticleTypeTreeFun = () => {
    getArticleTypeTreeApi().then((res: any) => {
        if (res.code === 200) {
            articleTypeList.data = res.result
            articleTypeList.data
        }
    })
}

/**
 * 修改文章分类打开dialog方法
 */
const updateArticleTypeDialogFun = (value: any) => {
    updateArticleTypeDialog.value = true
    updateArticleTypeForm.articleTypeName = value.typeName
    updateArticleTypeForm.articleTypeParentId = value.parentId +''
}

/**
 * 修改文章分类调用api方法
 */
const updateArticleTypeFun = () => {
    updateArticleTypeDialog.value = false
    console.log(updateArticleTypeForm)
}

/**
 * 新增文章分类打开dialog方法
 */
const saveArticleTypeDialogFun = () => {
    createForm.articleTypeName = ''
    createForm.articleTypeParentId = ''
    createArticleTypeDialog.value = true
}

/**
 * 新增文章分类调用api方法
 */
const saveArticleTypeFun = () => {
    createArticleTypeDialog.value = false
    saveArticleTypeApi(
        {
            parentId: createForm.articleTypeParentId === null ? 0 : createForm.articleTypeParentId,
            typeName: createForm.articleTypeName
        }).then((res: any) => {
            if (res.code === 200) {
                ElMessage({ message: '文章分类创建成功', type: 'success' })
                getArticleTypeTreeFun()
            }
        })
}

/**
 * 获取多选删除ids
 */
let ids = new Array();
const selected = (val: any) => {
    ids.splice(0, ids.length)
    for (let i = 0; i < val.length; i++) {
        ids.unshift(val[i].id)
    }
}

/**
 * 删除文章分类接口方法
 * @param id 不传id是多选删除 从ids中获取数据， 传id为要删除的分类id
 */
const deleteArticleTypeByIdsFun = (id?: any) => {
    deleteArticleTypeByIdsApi(id === undefined ? ids.join() : id).then((res: any) => {
        if (res.code === 200) {
            ElMessage({ message: '文章分类删除成功', type: 'success' })
            getArticleTypeTreeFun()
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

