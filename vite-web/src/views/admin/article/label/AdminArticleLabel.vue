<template>
    <div>
        <div class="title_style">
            <span>文章标签</span>
        </div>
        <el-card style="margin: 18px 2%;width: 95%">
            <el-button type="primary" plain @click="createArticleLabelTypeDialog = true">新增</el-button>
            <el-button type="danger" plain @click="deleteArticleLabelTypeByIdsFun()">删除</el-button>
            <el-table :data="articleLabelTypeList.list" style="width: 100%" height="660" @selection-change="selected">
                <el-table-column type="selection" width="55">
                </el-table-column>
                <el-table-column type="expand">
                    <template #default="props">
                        <div m="4">
                            <el-table :data="props.row.labelList" border stripe style="width: 93%; float: right;">
                                <el-table-column label="标签名称" prop="labelName" />
                                <el-table-column label="文章数量" prop="articleNum" />
                                <el-table-column label="创建用户" prop="blogUser.nickname" />
                                <el-table-column label="创建时间" prop="createTime" />
                                <el-table-column label="修改时间" prop="updateTime" />
                                <el-table-column fixed="right" label="操作" width="100">
                                    <template #default="scope">
                                        <el-button @click.native.prevent="" size="small" text>
                                            <MyIcon type="icon-edit" />
                                        </el-button>
                                        <el-button style="margin-left: 0;" @click.native.prevent="deleteArticleLabelByIdsFun(scope.row.id)" size="small" text>
                                            <MyIcon type="icon-shanchu" />
                                        </el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </div>
                    </template>
                </el-table-column>

                <el-table-column prop="typeName" label="名称" fit>
                </el-table-column>
                <el-table-column prop="blogUser.nickname" label="创建用户">
                </el-table-column>
                <el-table-column prop="createTime" label="创建时间">
                </el-table-column>
                <el-table-column prop="updateTime" label="最近更新">
                </el-table-column>
                <el-table-column fixed="right" label="操作" width="100">
                    <template #default="scope">
                        <el-button @click.native.prevent="" size="small" text>
                            <MyIcon type="icon-edit" />
                        </el-button>
                        <el-button style="margin-left: 0;" @click.native.prevent="deleteArticleLabelTypeByIdsFun(scope.row.id)" size="small" text>
                            <MyIcon type="icon-delete" />
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>

        <el-dialog v-model="createArticleLabelTypeDialog" title="创建标签" width="460px">
            <div>
                <el-form :model="createForm" label-width="120px">
                    <el-form-item label="标签分类名称：">
                        <el-input v-model="createForm.name" style="width: 214px;" />
                    </el-form-item>
                    <el-form-item label="创建类型：">
                        <el-select v-model="createForm.type" placeholder="选择分类">
                            <el-option label="标签" value="label" />
                            <el-option label="分类" value="type" />
                        </el-select>
                    </el-form-item>
                    <el-form-item v-if="createForm.type === 'label'" label="分类组织结构：">
                        <el-tree-select v-model="createForm.labelType" :data="articleLabelTypeList.list"
                            :render-after-expand="false" check-strictly clearable />
                    </el-form-item>
                    <el-form-item>
                        <el-button @click="createArticleLabelTypeDialog = false">取消</el-button>
                        <el-button type="primary" @click="saveArticleLabelFun">
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
import { } from "@/api/content";
import icon from '@/utils/icon'
import { ElMessage } from 'element-plus'
import { getArticleLabelTypeListApi, saveArticleLabelTypeApi, saveArticleLabelApi, deleteArticleLabelTypeByIdsApi, deleteArticleLabelByIdsApi } from "@/api/content"
import { ifError } from 'assert';

let { MyIcon } = icon()

// 文章标签分类列表
const articleLabelTypeList: any = reactive({ list: [] })

onMounted(() => {
    getArticleLabelTypeListFun()
});

/**
 * dialog是否展示
 */
const createArticleLabelTypeDialog = ref(false)

/**
 * 新增文章分类表单
 */
let createForm = reactive({
    name: '',
    type: '',
    labelType: ''
})

/**
 * 获取文章标签（分类+标签）
 */
const getArticleLabelTypeListFun = () => {
    getArticleLabelTypeListApi().then((res: any) => {
        if (res.code === 200) {
            articleLabelTypeList.list = res.result
            console.log(res)
        }
    })
}

const saveArticleLabelFun = () => {
    if (createForm.type === '') {
        ElMessage({ message: '创建类型必须选择', type: 'error' })
    } else {
        if (createForm.type === 'type') {
            saveArticleLabelTypeApi({
                typeName: createForm.name
            }).then((res: any) => {
                if (res.code === 200) {
                    ElMessage({ message: '文章标签分类创建成功', type: 'success' })
                    getArticleLabelTypeListFun()
                }
            })
        } else if (createForm.type === 'label') {
            saveArticleLabelApi({
                labelType: createForm.labelType,
                labelName: createForm.name
            }).then((res: any) => {
                if (res.code === 200) {
                    ElMessage({ message: '文章标签创建成功', type: 'success' })
                    getArticleLabelTypeListFun()
                }
            })
        }
        createArticleLabelTypeDialog.value = false
    }
}

/**
 * 获取id
 */
let ids = new Array();
const selected = (val: any) => {
    ids.splice(0, ids.length)
    for (let i = 0; i < val.length; i++) {
        ids.unshift(val[i].id)
    }
}

const deleteArticleLabelTypeByIdsFun = (id?: any) => {
    deleteArticleLabelTypeByIdsApi((id === undefined ? ids.join() : id)).then((res: any) => {
        if(res.code === 200) {
            ElMessage({ message: '文章标签分类删除成功', type: 'success' })
            getArticleLabelTypeListFun()
        }
    })
}

const deleteArticleLabelByIdsFun = (id: any) => {
    deleteArticleLabelByIdsApi(id).then((res: any) => {
        if(res.code=== 200) {
            ElMessage({ message: '文章标签删除成功', type: 'success' })
            getArticleLabelTypeListFun()
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

