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
                    <div v-for="item in articleList" :key="item.id" class="blogs" data-scroll-reveal="enter bottom over 1s">
                        <h3 class="blogtitle">
                            <router-link :to="{path:'/article/articleDetail', query: {id: item.id}}">{{item.title}}</router-link>
                        </h3>
                        <p class="blogtext">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{msg}}</p>
                        <div class="bloginfo">
                            <ul style="display:flex; align-items: center;">
                                <li class="author" style="display:flex; align-items: center;">
                                    <img style="width: 38px; border-radius: 19px;  margin-right: 10px;" :src="item.blogUser.headImg"/>
                                    <a href="javascript:void(0);">{{item.blogUser.nickname}}</a>
                                </li>
                                <li class="lmname">
                                    <i class="fa fa-thumbs-o-up" aria-hidden="true"></i>
                                    {{item.likeCount}}
                                </li>
                                <li class="createTime">
                                    <i class="fa fa-clock-o iconfont" aria-hidden="true"></i>
                                    {{item.createTime}}
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div style="display: flex; justify-content: center; align-items: center;">
                        <el-button link @click="addArticle" :loading="loading">加载更多</el-button>
                    </div>
                </el-col>
                <el-col :lg="4" :md="0" :sm="0" :xs="0">
                    <div style="margin-left: 15px;">
                        <el-card shadow="always" style="height: 200px;">
                            <template #header>
                                <div class="card-header">
                                    <span>
                                        公告栏
                                    </span>
                                </div>
                            </template>
                            <div>{{msg}}</div>
                        </el-card>
                    </div>
                </el-col>
            </el-row>
        </div>
    </div>


</template>

<script setup lang="ts">
import { readFileSync } from 'fs';
import { reactive, ref, onMounted } from 'vue';
import { getBlogList } from "../../../api/article";

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
let loading = false;
let msg = "今天没有公告今天没有公告今天没有公有公告今天没有公告今天没有公告今天没有公有公告今天没有公告今天没有公告今天没有公有公告今天没有公告"
let page = 1;
let articleList: any = reactive([])

onMounted(() => {
    getBlogList({
        pageNum: 1,
        pageSize: 5,
    }).then((res: any) => {
        if(res.code==200) {
            articleList.push(...res.result.list)
        }
    });
});

const addArticle = () => {
    loading = true;
    page = page + 1
    getBlogList({
        pageNum: page,
        pageSize: 5,
    }).then((res: any) => {
        if(res.code==200) {
            articleList.push(...res.result.list)
        }
    });
    loading = false;
} 

const showButton = () => {
    
}

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

a {text-decoration: none;}

.blogs { overflow: hidden; margin-bottom: 20px; padding: 20px; background: #FFF; -webkit-box-shadow: 0 2px 5px 0 rgba(146, 146, 146, .1); -moz-box-shadow: 0 2px 5px 0 rgba(146, 146, 146, .1); box-shadow: 0 2px 5px 0 rgba(146, 146, 146, .4); -webkit-transition: all 0.6s ease; -moz-transition: all 0.6s ease; -o-transition: all 0.6s ease; transition: all 0.6s ease; }
.blogs .blogpic { float: right; width: 20%;  max-height: 170px; margin-right: 20px; display: block; overflow: hidden; }
.blogs .blogpic img { width: 100%; height: auto; -webkit-transition: all 0.6s ease; -moz-transition: all 0.6s ease; -o-transition: all 0.6s ease; transition: all 0.6s ease; margin-bottom: 10px }
.blogs .blogpic :hover img { transform: scale(1.1) }
.blogs .blogtitle { margin: 0 0 10px 0; font-size: 20px; overflow: hidden; }
.blogs .blogtitle a { color: #000; }
.blogs .blogtitle a:hover { color: #337ab7; }
.blogs .blogtext { font-size: 14px; color: #566573; overflow: hidden; text-overflow: ellipsis; -webkit-box-orient: vertical; display: -webkit-box; -webkit-line-clamp: 3; margin-top: 20px }
.bloginfo { overflow: hidden; margin-top: 30px; display: flex; }
.bloginfo ul li { float: left; font-size: 12px; padding: 0 0 0 20px; color: #748594; line-height: 1.5; display: inline-block; }
.bloginfo ul li a { color: #748594; }
.bloginfo ul li a:hover { color: #000 }
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
