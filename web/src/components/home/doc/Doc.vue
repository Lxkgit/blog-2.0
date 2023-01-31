<template>
    <div class="window-container">
        <div class="window-body">
            <div class="sidebar">
                <div class="sidebar-header">
                    <div
                        style="display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #ddd;">
                        <div>
                            <a href="#/index" data-no-pjax="true" class="title">
                                <i class="fa fa-wa fa-home fa-lg"></i>&nbsp;个人文档系统
                            </a>
                        </div>
                        <div style="margin-right: 10px;">
                            <el-select v-model="docFiliter.filterUser" collapse-tags placeholder="选择用户文档"
                                @change="selectUserDoc" style="width: 130px">
                                <el-option v-for="item in userList.data" :key="item.id" :label="item.username"
                                    :value="item.id" />
                            </el-select>
                        </div>
                    </div>
                    <div class="search-form">
                        <div class="ui small fluid icon input">
                            <input v-model="docFiliter.filterText" type="text" placeholder="请输入搜索关键词..."
                                style="width: 90%; height: 100%; display: block; border: 1px solid #ddd; border-radius: 5px; line-height: 100%; font-size: 15px; text-indent: 0px; transition: 0.3s all;">
                            <button style="border: none; border-radius: 5px; width: 25px;">
                                <i class="fa fa-search" style="opacity: 0.5;" aria-hidden="true"></i>
                            </button>

                        </div>
                    </div>
                </div>
                <div class="sidebar-body">
                    <div class="catalog-body">
                        <el-tree ref="treeRef" :props="{ label: 'docName', children: 'list', isLeaf: 'leaf' }"
                            node-key="id" @node-click="loadDoc" lazy :load="loadTree" check-strictly clearable
                            :filter-node-method="filterNode" />
                    </div>
                </div>
            </div>
            <div class="workspace">
                <div class="article">
                    <div class="article-head">
                        <div class="tools"></div>
                        <h1>{{ docContent.title }}</h1>
                    </div>
                    <div class="content-div">
                        <div class="article-body markdown-body">
                            <v-md-preview :text="docContent.contentMd"></v-md-preview>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, watch } from 'vue';
import { getDocCatalogListById, selectDocContent } from "../../../api/article"
import { selectDocUserListApi } from "../../../api/user"
import { ElTree } from 'element-plus'

const treeRef = ref<InstanceType<typeof ElTree>>()
let userList: any = reactive({
    data: [
        {
            id: 0,
            username: "全部"
        }
    ]
});

interface DocFiliter {
    filterText: string
    filterUser: number
}

let docFiliter: DocFiliter = reactive({
    filterText: "",
    filterUser: 0
})

watch(docFiliter, (val) => {
    treeRef.value!.filter(val)
})

onMounted(() => {
    selectDocUserListApi().then((res: any) => {
        if (res.code === 200) {
            userList.data.push(...res.result.list)
        }
    })
})

let docContent = reactive({
    title: "请选择文档",
    contentMd: ""
})

const loadTree = (node: any, resolve: (date: any) => void) => {
    if (node.level === 0) {
        getDocCatalogListById({
            type: 1,
            parentId: 1,
        }).then((res: any) => {
            resolve(res.result)
        })
    } else {
        getDocCatalogListById({
            type: 1,
            parentId: node.data.id,
        }).then((res: any) => {
            resolve(res.result)
        })
    }
}

const loadDoc = (data: any) => {
    if (data.docType === "content") {
        selectDocContent(data.id).then((res: any) => {
            if (res.code === 200) {
                docContent.title = data.docName
                if (res.result.docContentMd === null) {
                    docContent.contentMd = ""
                } else {
                    docContent.contentMd = res.result.docContentMd
                }
            }
        })
    }
}

const selectUserDoc = (val: any) => {
    docFiliter.filterUser = val
}

const filterNode = (value: DocFiliter, data: any) => {
    if (value.filterUser === 0) {
        if (data.docName.includes(value.filterText)) {
            return true
        }
    } else {
        if (data.docName.includes(value.filterText) && data.userId === value.filterUser) {
            return true
        }
    }
    return false
}

</script>

<style scoped>
.window-body {
    display: contents;
}

.sidebar {
    margin-left: 0;
    width: 280px;
    border-right: 1px solid #ddd;
    background-color: #fafafa;
    display: flex;
    flex-direction: column;
    flex-wrap: nowrap;
    transition: all .25s ease;
    float: left;
    position: fixed;
    bottom: 0;
    top: 0;
    left: 0;

}

.sidebar-header {
    position: relative;
    flex: none;
}

.title {
    text-decoration: none;
    padding: 0 12px;
    line-height: 55px;
    color: inherit;
    display: block;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    font-weight: 700;
    font-size: 16px;
}

.title-icon {
    size: 20px;
}

.search-form {
    padding: 12px;
    border-bottom: 1px solid #ddd;
    flex: none;
}

.sidebar-body {
    flex: auto;
    overflow: hidden;
    position: relative;
}

.catalog-body {
    padding-top: 12px;
    padding-left: 12px;
    padding-right: 12px;
    overflow-y: scroll;
    height: 100%;
    display: flex;
    flex-direction: column;
}

.workspace {
    /*overflow-y: scroll;*/
    float: right;
    width: calc(100% - 280px);
    margin-left: 10px;
    /* color: #000; */
}

.article-head {
    padding: 30px 20px;
    height: 50px;
    display: flex;
}

.tools {
    background: #fff;
    margin: 6px -10px;
    position: fixed;
    z-index: 20;
}

.content-div {
    display: flex;
}

.article-body {
    /*min-width: 1200px;*/
    width: 1100px;
    margin-left: auto !important;
    margin-right: auto !important;
    padding: 9px 24px;
    display: block;
}

/*搜索按钮*/
.ui.fluid.input svg {
    right: 21px;
    top: 79px;
    position: absolute;
}

.ui.small.input {
    font-size: .92857143em;
}

.ui.fluid.input {
    display: flex;
}

.ui.fluid.input>input {
    width: 0 !important;
}

.ui.icon.input>input {
    padding-right: 2.67142857em !important;
}

.ui.input>input {
    margin: 0;
    max-width: 100%;
    flex: 1 0 auto;
    outline: none;
    -webkit-tap-highlight-color: rgba(255, 255, 255, 0);
    text-align: left;
    line-height: 1.28571429em;
    font-family: Helvetica Neue, NotoSansHans-Regular, AvenirNext-Regular, arial, Hiragino Sans GB, Microsoft Yahei, WenQuanYi Micro Hei, Arial, Helvetica, sans-serif;
    padding: .64285714em 1em;
    background: #fff;
    border: 1px solid rgba(34, 36, 38, .15);
    color: rgba(0, 0, 0, .87);
    border-radius: .28571429rem;
    transition: box-shadow .1s ease, border-color .1s ease;
    box-shadow: none;
}

.window-body .sidebar .sidebar-body>div {
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
}

.window-body .workspace .article .article-head h1 {
    flex: 1;
    margin: 0;
    font-size: 30px;
    font-weight: 200;
    text-align: center;
    line-height: 30px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    color: #7e888b;
}

/*侧边栏开合样式*/
.window-body .workspace .article .article-head .tools>.item {
    color: #ccc;
    display: block;
    height: 30px;
    padding: 0 10px;
    text-transform: uppercase;
    line-height: 30px;
    position: relative;
    font-size: 14px;
    text-align: center;
    cursor: pointer;
}

.window-body .workspace .article .article-head .tools>.item :hover {
    color: #7e888b;
}
</style>
