<template>
<!--     <div class="window-container">
        <div class="window-body">
            <div class="sidebar">
                <div class="sidebar-header">
                    <a href="#/index" data-no-pjax="true" class="title"><i class="fa fa-wa fa-home fa-lg"></i>个人文档系统</a>
                    <div class="search-form">
                        <div class="ui small fluid icon input">
                            <input v-model="filterText" type="text" placeholder="请输入搜索关键词...">
                            <font-awesome-icon icon="search" />
                        </div>
                    </div>
                </div>
                <div class="sidebar-body">
                    <div class="catalog-body">
                        <el-tree id="tree" ref="tree" :data="docCatalogList" :props="defaultProps"
                            :default-expanded-keys="key" :default-checked-keys="check" :highlight-current="true"
                            :default-expand-all="true" :expand-on-click-node="false" :filter-node-method="filterNode"
                            @node-click="click" />
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
                        <div class="article-body markdown-body" v-html="docContent.content_html"></div>
                    </div>
                </div>
            </div>
        </div>
    </div> -->
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue';
import Catalog from '/src/cpmponents/home/doc/components/Catalog.vue'

let key = ref("")
let filterText = ref("")
let check = reactive([])
let docCatalogList = reactive([])
let docContent = reactive({
    title: "123123",
    content_html: "asdasdasdasd"
})
let defaultProps = reactive([
    {
        children: 'children',
        label: 'title'
    }
])
const click = () => {

}

const filterNode = (value: any, data: { label: string | any[]; }) => {
    if (!value) { return true }
    return data.label.includes(value)
}



    // data() {
    //     return {
    //         data: [],
    //         filterText: '',
    //         key: [],
    //         check: [],
    //         defaultProps: {
    //             children: 'children',
    //             label: 'title'
    //         }
    //     }
    // },
    // computed: {
    //     // 把getter混入到computed对象中,我们可以直接调用vex里面的内容即可
    //     ...mapGetters('doc', ['docCatalogList']),
    //     ...mapGetters('doc', ['docContent'])
    // },
    // watch: {
    //     filterText(val) {
    //         this.$refs.tree.filter(val)
    //     }
    // },
    // async mounted() {
    //     this.selectDocList()
    //     const link = document.createElement('link')
    //     link.type = 'text/css'
    //     link.rel = 'stylesheet'
    //     link.href = 'https://cdn.bootcss.com/github-markdown-css/2.10.0/github-markdown.min.css'
    //     // link.href = 'https://cdnjs.cloudflare.com/ajax/libs/github-markdown-css/2.9.0/github-markdown.min.css'
    //     document.head.appendChild(link)

    // },
    // methods: {
    //     selectDocList() {
    //         this.$store.dispatch('doc/selectDocList')
    //     },

    //     // 过滤子节点
    //     filterNode(value, data) {
    //         if (!value) { return true }
    //         return data.label.includes(value)
    //     },

    //     click(choose) {

    //         this.$store.dispatch('doc/selectDocContentById', choose.value)
    //     },


</script>

<style scoped>
.window-container {}

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
    border-bottom: 1px solid #ddd;
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
    padding: 12px 0;
    overflow-y: scroll;
    height: 95%;
    display: flex;
    flex-direction: column;
}

.workspace {
    /*overflow-y: scroll;*/
    float: right;
    width: calc(100% - 280px);
    margin-left: 10px;
    color: #fff;



}

.article {}

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
</style> -->
