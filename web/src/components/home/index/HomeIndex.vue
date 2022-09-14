<template>
    <div class="home_index" style="width: 100%;">
        <div class="menu" id="sub_menu">
            <ul>
                <li v-for="item in typeList.data">{{  item.name  }}</li>
            </ul>
        </div>
        <div style="margin-top: 100px;">
            <el-row justify="center">
                <el-col :lg="10" :md="16" :sm="16" :xs="16">

                    <!-- <div id="blog-list" class="blog-list">
                        <div id="blog-post" class="blog-post">
                            <div v-for="(item, index) in articleList.data" :key="index" class="panel">
                                <div class="post-meta wrapper-lg">
                                    <h2 class="m-t-none index-post-title">
                                        <router-link style="text-decoration: none;"
                                            :to="{ path: '/article/articleDetail', query: { id: item.id } }">
                                            {{  item.title }}
                                        </router-link>
                                    </h2>
                                    <p class="summary text-muted">
                                        {{  item.content_html }}
                                    </p>
                                    <div class="line b-b b-light" ></div>
                                    <div class="text-muted">
                                        <span>
                                            <i class="fa fa-eye" aria-hidden="true"></i>&nbsp;{{item.browseCount  }}次阅读
                                        </span>
                                        <span class="good">
                                            <i class="fa fa-thumbs-up" aria-hidden="true"></i>&nbsp;{{item.likeCount  }}人点赞
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <el-button style="display: flex; align-items: center; justify-content: center;">加载更多</el-button>
                        </div>
                    </div> -->
                    <div v-for="item in articleList.data">
                        <div style="border-style:solid; border-width:1px; margin-bottom: 15px;">
                            <h3 style="margin: 10px 5px; font-size: 20px; overflow: hidden;">
                                <a>{{ item.title }}</a>
                            </h3>
                            <el-row justify="center">
                                <el-col :lg="19" :md="16" :sm="16" :xs="16">
                                    <!-- <p v-html="item.contentHtml"></p>
                                    <v-md-editor :model-value="item.contentMd" mode="preview"></v-md-editor> -->
                                </el-col>
                                <el-col :lg="5" :md="16" :sm="16" :xs="16" style="height: 80px">
                                    <span>
                                        <a>
                                            <img src="" title="图片" />
                                        </a>
                                    </span>
                                </el-col>
                            </el-row>
                            <div>
                            </div>
                            <div>
                            </div>
                            <div style="display: flex; align-items: center; margin: 10px;">
                                <div style="display: flex; align-items: center;">
                                    <img style="width: 32px; border-radius: 16px; " :src="item.blogUser.headImg" />
                                    <div style="margin-left: 10px;">{{ item.blogUser.username }}</div>
                                </div>
                                <div >
                                    <i style="margin-left: 10px;" class="fa fa-eye" aria-hidden="true"></i>&nbsp;{{item.browseCount  }}
                                    <i style="margin-left: 10px;" class="fa fa-thumbs-o-up" aria-hidden="true">{{item.likeCount}}</i>
                                    <i style="margin-left: 10px;" class="fa fa-thumbs-o-down" aria-hidden="true"></i>
                                    <i style="margin-left: 10px;" class="fa fa-star-o" aria-hidden="true">收藏</i>
                                    <i style="margin-left: 10px;" class="fa fa-clock-o" aria-hidden="true">{{item.createTime}}</i>
                                </div>
                            </div>
                        </div>
                    </div>
                </el-col>
                <el-col :lg="4" :md="0" :sm="0" :xs="0">
                    <div>
                        右栏
                    </div>
                </el-col>
            </el-row>
        </div>
    </div>


</template>

<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue';
import { getBlogList, getBlogType } from "../../../api/article";

const typeList = reactive({
    data: [
        { name: "java后端1" },
        { name: "java后端2" },
        { name: "java后端3" },
        { name: "java后端4" },
        { name: "java后端5" },
        { name: "java后端6" },
        { name: "java后端7" },

    ]
});

let articleList: any = reactive({ data: [] })

onMounted(() => {
    getBlogList(1, 5).then((res: any) => {

        articleList.data = res.list;

    });
});



window.onscroll = function () {
    let topScroll = get_scrollTop_of_body(); //滚动的距离,距离顶部的距离
    let subMenu = document.getElementById("sub_menu"); //获取到导航栏id
    if (subMenu !== null) {
        if (topScroll !== undefined && topScroll > 90) {
            subMenu.style.position = "fixed";
            subMenu.style.top = "0";
            subMenu.style.left = "0";
            subMenu.style.right = "0";
            subMenu.style.zIndex = "100";
            subMenu.style.borderBottom = "1px";
        } else {
            subMenu.style.position = "static";
        }
    }
};

/*解决浏览器兼容问题*/
function get_scrollTop_of_body() {
    let scrollTop;
    if (typeof window.pageYOffset != "undefined") {
        //pageYOffset指的是滚动条顶部到网页顶部的距离
        scrollTop = window.pageYOffset;
    } else if (
        typeof document.compatMode != "undefined" &&
        document.compatMode != "BackCompat"
    ) {
        scrollTop = document.documentElement.scrollTop;
    } else if (typeof document.body != "undefined") {
        scrollTop = document.body.scrollTop;
    }
    return scrollTop;
}


</script>

<style scoped>
.home_index {
    /* display: flex;
    align-items: center;
    justify-items: center; */
    width: 100%;

}

.menu {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 50px;
    width: 100%;
    background-color: antiquewhite;
}

.menu .item {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 20px;
    height: 100%;
    cursor: pointer;
}

.menu ul {
    display: flex;
    align-items: center;
    justify-content: left;
    list-style: none;
    margin: 0 20px;
    width: 1000px;
    overflow-x: auto;
    height: 100%;
}

/*  去除ul底部滚动条 */
.menu ul::-webkit-scrollbar {
    display: none;
}

.menu ul li {
    display: inline-table;
    width: 100px;
    cursor: pointer;
}

.menu ul li+li {
    margin-left: 15px;
}

.blog-list .blog-post .panel {
    border: 0;
    border-radius: 5px;
    margin-bottom: 15px;
    position: relative;
    transition: all 0.2s;
    box-shadow: 1px 1px 3px 1px rgba(0, 0, 0, 0.2) !important;
}

.blog-list .blog-post .panel .index-post-img {
    border-top-left-radius: 5px;
    border-top-right-radius: 5px;
    overflow: hidden;
}

.blog-list .blog-post .panel .index-post-img .router-link .item-thumb {
    min-height: 250px;
    position: relative;
    display: block;
    background-position: 50% 50%;
    background-size: cover;
    border-top-left-radius: 4px;
    border-top-right-radius: 4px;
    transition: all 0.3s;
}

.blog-list .blog-post .panel .wrapper-lg {
    padding: 20px;
    background-color: white;
    /*图标相关*/
}

.blog-list .blog-post .panel .wrapper-lg .m-t-none {
    margin-top: 0 !important;
}

.blog-list .blog-post .panel .wrapper-lg .index-post-title {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    font-size: 22px;
    transition: all 0.3s;
}

.blog-list .blog-post .panel .wrapper-lg .b-b {
    border-bottom: 1px solid #dee5e7;
}

.blog-list .blog-post .panel .wrapper-lg .text-muted span {
    margin-right: 15px;
    padding-right: 4px;
}

@media screen and (min-width: 1200px) {
    .panel .item-thumb {
        height: 300px;
    }

    .panel .post-meta {
        position: relative;
        margin-top: 0px;
        height: 168px;
        padding-top: 133px !important;
        padding-bottom: 0 !important;
        background-color: rgba(0, 0, 0, .3) !important;
        color: #fff !important;
        transition: all .3s;
        border-radius: 5px;
    }

    .panel .post-meta>h2,
    #posts>div.blog-post>.panel-small .post-meta>h2 {
        text-align: center;
        text-shadow: 0 0 3px #fff;
    }

    .blog-post>.panel:hover .summary {
        transform: translateY(10px);
        opacity: 1;
    }

    .blog-post>.panel:hover .index-post-title {
        transform: translateY(-30px) scale(1.2, 1.2);
        opacity: 1;
        padding: 0 60px;
    }

    .blog-post>.panel:hover .item-thumb {
        transform: scale(1.2, 1.2);
    }

    .blog-post>.panel:hover .text-muted {
        transform: translateY(10px);
        opacity: 1;
    }

    .blog-post>.panel:hover .line {
        transform: translateY(10px);
        opacity: 1;
    }

    .panel .post-meta>.text-muted {
        position: absolute;
        bottom: 16px;
        opacity: 0;
        transition: all .3s;
    }

    .panel .post-meta>.summary {
        /*position: absolute;*/
        bottom: 60px;
        /*width: 710px;*/
        padding: 3px;
        opacity: 0;
        transition: all .3s;
    }

    .panel .post-meta>.line {
        position: absolute;
        bottom: 40px;
        width: 700px;
        opacity: 0;
        transition: all .3s;
    }

    #posts>div.blog-post>.panel-small .post-meta>p,
    #posts>div.blog-post>.panel .post-meta>div {
        transition: all .3s;
        transform: translate(-10px);
        opacity: 0;
    }

    .blog-post>.panel:hover .index-post-img {
        filter: blur(3px);
    }

    .panel .post-meta * {
        color: #fff !important;
    }

    .blog-post {
        position: relative;
    }

    .b-light {
        border-color: #bbb4;
    }
}
</style>
