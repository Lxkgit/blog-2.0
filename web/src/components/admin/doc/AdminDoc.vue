<template>
    <div>
        <div class="title_style">
            <span>文档管理</span>
        </div>
        <el-card style="margin: 18px 2%;width: 95%; height: 780px;">
            <el-button type="danger" plain @click="">删除</el-button>
            <el-table :data="docList.data" lazy :load="load" row-key="id"
                :tree-props="{ children: 'children', hasChildren: 'hasChildren' }" stripe style="width: 100%"
                height="630" @selection-change="selected">
                <el-table-column type="selection" width="55">
                </el-table-column>
                <el-table-column prop="docName" label="文档名称" fit>
                </el-table-column>
                <el-table-column prop="docType" label="文档类型" width="162">
                </el-table-column>
                <el-table-column prop="updateTime" label="最近更新" width="162">
                </el-table-column>
                <el-table-column fixed="right" label="操作" width="90">
                    <template #default="scope">
                        <el-button @click="" size="small" text>
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
        </el-card>
    </div>
</template>

<script setup lang="ts">
import type Node from 'element-plus/es/components/tree/src/model/node'
import type { DragEvents } from 'element-plus/es/components/tree/src/model/useDragNode'
import type {
    AllowDropType,
    NodeDropType,
} from 'element-plus/es/components/tree/src/tree.type'
import { getDocCatalogList, getDocCatalogListById } from "../../../api/article"
import { ref, reactive, onMounted } from 'vue';

let ids = new Array();
let text = ref("");
let docList: any = reactive({ data: [] });
let size = ref(14);
let total = ref(0);


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
    console.log("row:", JSON.stringify(row))
    console.log("row:", JSON.stringify(treeNode))
    getDocCatalogListById({
        id: row.id
    }).then((res: any)=> {
        console.log(res.result)
        resolve(
            res.result
        // {
        //     id: 31,
        //     docName: 'wangxiaohu',
        //     docType: 'No. 189, Grove St, Los Angeles',
        // },
        // {
        //     id: 32,
        //     docName: 'wangxiaohu',
        //     docType: 'No. 189, Grove St, Los Angeles',
        // },
    )

    })

    
}

const selected = (val: any) => {
    ids.splice(0, ids.length)
    for (let i = 0; i < val.length; i++) {
        ids.unshift(val[i].id)
    }
}

const handleDragStart = (node: Node, ev: DragEvents) => {
    console.log('drag start', node)
}
const handleDragEnter = (
    draggingNode: Node,
    dropNode: Node,
    ev: DragEvents
) => {
    console.log('tree drag enter:', dropNode.label)
}
const handleDragLeave = (
    draggingNode: Node,
    dropNode: Node,
    ev: DragEvents
) => {
    console.log('tree drag leave:', dropNode.label)
}
const handleDragOver = (draggingNode: Node, dropNode: Node, ev: DragEvents) => {
    console.log('tree drag over:', dropNode.label)
}
const handleDragEnd = (
    draggingNode: Node,
    dropNode: Node,
    dropType: NodeDropType,
    ev: DragEvents
) => {
    console.log('tree drag end:', dropNode && dropNode.label, dropType)
}
const handleDrop = (
    draggingNode: Node,
    dropNode: Node,
    dropType: NodeDropType,
    ev: DragEvents
) => {
    console.log('tree drop:', dropNode.label, dropType)
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

