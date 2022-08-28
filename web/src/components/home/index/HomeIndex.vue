<template>
    <div class="home_index" style="width: 100%;">
        <div class="menu" id="sub_menu">
            <ul>
                <li v-for="item in typeList.data">{{ item.name }}</li>
            </ul>
        </div>
        <div style="margin-top: 100px;">
            <el-row justify="center">
                <el-col :lg="10" :md="16" :sm="16" :xs="16">
                    <div style="height: 1000px;">
                        <div style="border-style:solid; border-width:1px;">
                            <h3 style="margin: 0 0 10px; font-size: 20px; overflow: hidden;">
                                <a>文章标题</a>
                            </h3>
                            <el-row justify="center">
                                <el-col :lg="19" :md="16" :sm="16" :xs="16">
                                    <p>
                                        我是文章内容，我是文章内容，我是文章内容，我是文章内容，我是文章内容，我是文章内容，我是文章内容，我是文章内容
                                        我是文章内容，我是文章内容，我是文章内容，我是文章内容，我是文章内容，我是文章内容，我是文章内容，我是文章内容
                                        我是文章内容，我是文章内容，我是文章内容，我是文章内容，我是文章内容，我是文章内容，我是文章内容，我是文章内容
                                    </p>
                                </el-col>
                                <el-col :lg="5" :md="16" :sm="16" :xs="16" style="height: 60px">
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
                            <!-- <div>
                                <ul>
                                    <div>
                                        <li></li>
                                    </div>
                                </ul>
                            </div> -->
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
import { reactive, ref } from 'vue';

const typeList = reactive({
    data: [
        { name: "java后端1" },
        { name: "java后端2" },
        { name: "java后端3" },
        { name: "java后端4" },
        { name: "java后端5" },
        { name: "java后端6" },
        { name: "java后端7" },
        { name: "java后端8" },
        { name: "java后端9" },
        { name: "java后端10" },
        { name: "java后端11" },
        { name: "java后端12" },
        { name: "java后端13" },
        { name: "java后端14" },
        { name: "java后端15" },
        { name: "java后端16" },
    ]
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
    /* display: none; */
}

.menu ul li {
    display: inline-table;
    width: 100px;
    cursor: pointer;
}

.menu ul li+li {
    margin-left: 15px;
}
</style>
