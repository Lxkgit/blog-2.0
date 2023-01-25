<template>
    <div class="nav-box" style="margin-bottom: 20px">
        <div class="m-background">
            <div class="m-background-mask"> </div>
        </div>
        <SearchBox class="nav-search"></SearchBox>
        <div class="m-cover">
            <div class="m-inner">
                <ul class="m-shortcut-panel clearfix">
                    <li v-for="(item, index) in topWeb" :key="index" class="item">
                        <a target="_blank" :href="item.url">
                            <img :alt="item.name" :title="item.name" :src="getImageUrl(item)" width="54" height="54">
                            <div class="title"><span>{{ item.name }}</span></div>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">

// 顶部的图标
const topWeb = [
    { name: '哔哩哔哩', img: 'bilibili.png', url: 'https://www.bilibili.com/', type: 1 },
    { name: 'Steam', img: 'steam.jpg', url: 'https://store.steampowered.com/', type: 1 },
    { name: 'zzzFun', img: 'http://www.zzzfun.com/favicon.ico', url: 'http://www.zzzfun.com/', type: 0 }
]

const getImageUrl = (item: any) => {
    if(item.type === 1) {
        return new URL(`../../../assets/images/nav/${item.img}`, import.meta.url).href
    } else { 
        return item.img
    }
}

</script>

<style scoped>
ul,
li {
    list-style: none;
}

.nav-box {}

.m-background {
    position: fixed;
    zoom: 1;
    width: 100%;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: transparent no-repeat top center;
    background-size: cover;


}


.m-background-mask {
    height: 90px;
    left: 0;
    right: 0;
    bottom: 0;
    position: absolute;
    background: linear-gradient(to top, white, rgba(255, 255, 255, 0.5) 10%, rgba(255, 255, 255, 0));
    
}

.nav-search {
    width: 293px;
    margin-left: auto;
    margin-right: auto;
}


.m-cover {
    min-height: 160px;
    font-size: 12px;
    clear: both;
    position: relative;
    z-index: 120;


}

.m-inner {
    width: 60%;
    margin-left: auto;
    margin-right: auto;
    margin-top: 40px;
    zoom: 1;


}


.m-shortcut-panel {
    margin-left: auto;
    margin-right: auto;
    padding-top: 7px;
    padding-bottom: 7px;
    transition: 0.3s border;
    display: flex;
    position: relative;
    text-align: center;
    justify-content: center;
    opacity: .8;
    background-color: rgba(254, 254, 254, 0.2);


}

.item a {
    overflow: hidden;
    display: block;
    width: 80px;
    height: 85px;
    border-radius: 2px;
    padding-top: 10px;
    padding-bottom: 0;
    margin: 0 auto;
    text-align: center;
    position: relative;


}

img {
    border-radius: 8px;
    display: block;
    margin: 0 auto;
    width: 54px;
    height: 54px;
    font-size: 24px;
    transition: 0.2s all ease-out;
    text-align: center;
    overflow: hidden;
    text-decoration: none;
    position: relative;
}

.title {
    color: #000;
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    text-align: center;
    display: block;
    line-height: 20px;
    height: 24px;
    font-size: 13px;
    width: 100%;
    border-radius: 12px;
}
</style>
