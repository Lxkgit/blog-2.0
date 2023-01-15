<template>
    <div>
        <div class="title_style">
            <span>文档管理</span>
        </div>
        <el-card style="margin: 18px 2%;width: 95%; height: 780px;">
            <el-button type="success" plain @click="createCatalogOrDocFun">创建</el-button>
            <el-button type="danger" plain @click="">删除</el-button>
            <el-table :data="docList.data" lazy :load="load" row-key="id"
                :tree-props="{ children: 'children', hasChildren: 'hasChildren' }" stripe style="width: 100%"
                height="630" @selection-change="selected">
                <el-table-column type="selection" width="55">
                </el-table-column>
                <el-table-column prop="docName" label="文档名称" fit>
                </el-table-column>
                <el-table-column prop="docType" label="文档类型" width="162">
                    <template #default="scope">
                        <el-tag v-if="(scope.row.docType == 'catalog')" class="ml-2" type="info">
                            {{ docType(scope.row.docType) }}
                        </el-tag>
                        <el-tag v-if="(scope.row.docType == 'content')" class="ml-2" type="success">
                            {{ docType(scope.row.docType) }}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="updateTime" label="最近更新" width="162">
                </el-table-column>
                <el-table-column fixed="right" label="操作" width="90">
                    <template #default="scope">
                        <el-button v-if="scope.row.docType === 'catalog'" @click="editCatalogFun(scope.row)" size="small" text>
                            <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
                        </el-button>
                        <el-button v-else @click="editContentFun(scope.row)" size="small" text>
                            <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
                        </el-button>
                        <el-button style="margin-left: 0;" @click="" size="small" text>
                            <i class="fa fa-trash" aria-hidden="true"></i>
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style="margin: 20px 0 50px 0">
                <el-pagination background style="float:right;" layout="total, prev, pager, next, jumper"
                    @current-change="" :page-size="size" :total="total">
                </el-pagination>
            </div>
            <el-dialog v-model="showUpdateDocDialog" title="修改文档" width="460">
                <div>
                    <el-form :model="updateForm" label-width="120px">
                        <el-form-item label="文档名称：">
                            <el-input v-model="updateForm.docName" style="width: 214px;" />
                        </el-form-item>
                        <el-form-item label="文档类型：">
                            <el-select v-model="updateForm.docType" placeholder="修改文档分类">
                                <el-option label="目录" value="catalog" />
                                <el-option label="文章" value="content" />
                            </el-select>
                        </el-form-item>
                        <el-form-item label="文档结构：">
                            <el-tree-select v-model="updateForm.parentId"
                                :props="{ label: 'docName', children: 'list', isLeaf: 'isLeaf' }" lazy :load="loadTree"
                                :cache-data="updateForm.parentId" check-strictly />
                        </el-form-item>
                        <el-form-item  label="创建时间：">
                            <el-date-picker v-model="updateForm.createTime" disabled type="datetime" />
                        </el-form-item>
                        <el-form-item label="修改时间：">
                            <el-date-picker v-model="updateForm.updateTime" disabled type="datetime" />
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="updateCatalogFun">保存</el-button>
                            <el-button @click="showUpdateDocDialog = false">取消</el-button>
                        </el-form-item>
                    </el-form>
                </div>
            </el-dialog>
            <el-dialog v-model="showInsertDocDialog" title="创建文档" width="460">
                <div>
                    <el-form :model="createForm" label-width="120px">
                        <el-form-item label="文档名称：">
                            <el-input v-model="createForm.docName" style="width: 214px;" />
                        </el-form-item>
                        <el-form-item label="文档类型：">
                            <el-select v-model="createForm.docType" placeholder="选择文档分类">
                                <el-option label="目录" value="catalog" />
                                <el-option label="文章" value="content" />
                            </el-select>
                        </el-form-item>
                        <el-form-item label="文档结构：">
                            <el-tree-select v-model="createForm.parentId"
                                :props="{ label: 'docName', children: 'list', isLeaf: 'isLeaf' }" lazy :load="loadTree"
                                check-strictly />
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="createCatalogFun">创建</el-button>
                            <el-button @click="showInsertDocDialog = false">取消</el-button>
                        </el-form-item>
                    </el-form>
                </div>
            </el-dialog>
        </el-card>
    </div>
</template>

<script setup lang="ts">
import { getDocCatalogList, getDocCatalogListById, createCatalog } from "../../../api/article"
import { ref, reactive, onMounted } from 'vue';
import mixin from "../../../mixins/doc"

let { docType } = mixin();

let ids = new Array();
let text = ref("");
let docList: any = reactive({ data: [] });
let size = ref(14);
let total = ref(0);
let showUpdateDocDialog = ref(false);
let showInsertDocDialog = ref(false);

let createForm = reactive({
    parentId: '',
    docName: '',
    docType: ''
})

let updateForm: any = reactive({
    id: 0,
    userId: 0,
    parentId: 0,
    docName: "",
    docType: "",
    createTime: "",
    updateTime: "",
})

onMounted(() => {
    getDocCatalogListFun(1)
});

const getDocCatalogListFun = (page: any) => {
    getDocCatalogList({
        pageNum: page,
        pageSize: 14,
    }).then((res: any) => {
        if (res.code === 200) {
            docList.data = res.result.list
            size.value = res.result.size
            total.value = res.result.total
        }
    })
}

const load = (row: any, treeNode: any, resolve: (date: any) => void) => {
    getDocCatalogListById({
        type: 0,
        parentId: row.id
    }).then((res: any) => {
        resolve(res.result)
    })
}

const loadTree = (node: any, resolve: (date: any) => void) => {
    if (node.level === 0) {
        getDocCatalogListById({
            type: 0,
            parentId: 0,
            docType: "catalog"
        }).then((res: any) => {
            resolve(res.result)
        })
    } else {
        getDocCatalogListById({
            type: 0,
            parentId: node.data.id,
            docType: "catalog"
        }).then((res: any) => {
            resolve(res.result)
        })
    }
}

const selected = (val: any) => {
    ids.splice(0, ids.length)
    for (let i = 0; i < val.length; i++) {
        ids.unshift(val[i].id)
    }
}

const editCatalogFun = (row: any) => {
    showUpdateDocDialog.value = true
    updateForm = []
    updateForm = row
}

const updateCatalogFun = () => {
    showUpdateDocDialog.value = false
}

const createCatalogOrDocFun = () => {
    showInsertDocDialog.value = true
}

// 创建文档方法
const createCatalogFun = () => {
    createCatalog({
        parentId: createForm.parentId === "" ? 0 : createForm.parentId,
        docName: createForm.docName,
        docType: createForm.docType
    }).then((res: any) => {
        if(res.code === 200) {
            getDocCatalogListFun(1)
        }
    })

    showInsertDocDialog.value = false
}

const editContentFun = (row: any) => {
    
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

