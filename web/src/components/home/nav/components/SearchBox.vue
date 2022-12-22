<template>
  <div class="m-inner m-layout" style="height: 100%;">
    <div class="wd-search clearfix" role="search">
      <div class="logo">
        <div class="search-logo">
          <img class="img web" :src="topSearch.data.img" :title="topSearch.data.name" @click="toggleShow">
        </div>
        <div v-for="(item, index) in nowChoose" v-show="show" :key="index" :title="item.name" class="logo-list"
          @click="changeSearch(item)">
          <div class="img-box">
            <img class="logo-img" :src="item.img">
          </div>
        </div>
      </div>
      <div class="main-form">
        <ul class="services" data-hook="services">
          <li class="item" :class="now === 'web' ? 'active' : ''" @click="changeItem('web')">
            <label>网页</label><i :class="now === 'web' ? 'pt' : ''"></i>
          </li>
          <li class="item" :class="now === 'video' ? 'active' : ''" @click="changeItem('video')">
            <label>视频</label><i :class="now === 'video' ? 'pt' : ''"></i>
          </li>
          <li class="item" :class="now === 'community' ? 'active' : ''" @click="changeItem('community')">
            <label>社区</label><i :class="now === 'community' ? 'pt' : ''"></i>
          </li>
          <li class="item" :class="now === 'img' ? 'active' : ''" @click="changeItem('img')">
            <label>图片</label><i :class="now === 'img' ? 'pt' : ''"></i>
          </li>
          <li class="item" :class="now === 'music' ? 'active' : ''" @click="changeItem('music')">
            <label>音乐</label><i :class="now === 'music' ? 'pt' : ''"></i>
          </li>
          <li class="item" :class="now === 'translate' ? 'active' : ''" @click="changeItem('translate')">
            <label>翻译</label><i :class="now === 'translate' ? 'pt' : ''"></i>
          </li>
        </ul>
        <div class="box">
          <div>
            <input v-model="searchText" class="wd" type="text" :placeholder="topSearch.data.tip"
              @keyup.enter="openSearch(topSearch.data.url)">
            <button @click="openSearch(topSearch.data.url)">
              <i  class="fa fa-search" style=" width: 21px; height: 21px; opacity: 0.5;" aria-hidden="true"></i>
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue';

const web = [
  { name: '百度搜索', img: '/src/assets/images/nav/baidu.png', url: 'https://www.baidu.com/s?wd=%s', tip: '请输入关键词进行搜索' },
  { name: '谷歌搜索', img: '/src/assets/images/nav/google.png', url: 'https://www.google.com.hk/search?q=%s', tip: '请输入关键词进行搜索' },
  { name: 'bing搜索', img: '/src/assets/images/nav/bing.png', url: 'https://www.bing.com/search?q=%s', tip: '请输入关键词进行搜索' },
  { name: '搜狗搜索', img: '/src/assets/images/nav/sogou.png', url: 'https://www.sogou.com/web?query=%s', tip: '请输入关键词进行搜索' },
  { name: '360搜索', img: '/src/assets/images/nav/360.png', url: 'https://www.so.com/s?q=%s', tip: '请输入关键词进行搜索' }
];
// 视频搜索引擎
const video = [
  { name: '哔哩哔哩', img: '/src/assets/images/nav/bilibili.png', url: 'https://search.bilibili.com/all?keyword=%s', tip: '请输入关键词进行搜索' },
  { name: 'AcFun', img: '/src/assets/images/nav/acfun.png', url: 'https://www.acfun.cn/search?keyword=%s', tip: '请输入关键词进行搜索' },
  { name: 'YouTub', img: '/src/assets/images/nav/youtub.png', url: 'https://www.youtube.com/results?search_query=%s', tip: '请输入关键词进行搜索' },
]
// 社区
const community = [
  { name: 'CSDN', img: '/src/assets/images/nav/csdn.png', url: 'https://so.csdn.net/so/search/s.do?q=%s', tip: '请输入关键词进行搜索' },
  { name: '游侠网', img: '/src/assets/images/nav/youxia.png', url: 'https://so.ali213.net/s/c?keyword=%s', tip: '请输入关键词进行搜索' },
  { name: '知乎', img: '/src/assets/images/nav/zhihu.png', url: 'https://www.zhihu.com/search?q=%s', tip: '请输入关键词进行搜索' },
  { name: '掘金', img: '/src/assets/images/nav/juejin.png', url: 'https://juejin.im/search?query=%s&type=all', tip: '请输入关键词进行搜索' },
  { name: '思否', img: '/src/assets/images/nav/sifou.png', url: 'https://segmentfault.com/search?q=%s', tip: '请输入关键词进行搜索' }
]
// 图片
const image = [
  { name: '百度图片', img: '/src/assets/images/nav/baiduimg.png', url: 'https://image.baidu.com/search/index?tn=baiduimage&word=%s', tip: '请输入关键词进行搜索' },
  { name: '谷歌图片', img: '/src/assets/images/nav/google.png', url: 'https://www.google.com.hk/search?q=%s&tbm=isch', tip: '请输入关键词进行搜索' },
  { name: 'Pixiv', img: '/src/assets/images/nav/pixiv.svg', url: 'https://www.pixiv.net/tags/%s/artworks', tip: '请输入关键词进行搜索' },
  { name: '花瓣', img: '/src/assets/images/nav/huaban.png', url: 'https://huaban.com/search/?q=%s', tip: '请输入关键词进行搜索' },
  { name: 'Flickr', img: '/src/assets/images/nav/flickr.png', url: 'https://www.flickr.com/search/?text=%s', tip: '请输入关键词进行搜索' },
  { name: 'Picsearch', img: '/src/assets/images/nav/picsearch.png', url: 'https://cn.picsearch.com/index.cgi?q=%s', tip: '请输入关键词进行搜索' }
]
// 音乐
const music = [
  { name: 'QQ音乐', img: '/src/assets/images/nav/qqmusic.png', url: 'https://y.qq.com/portal/search.html?w=%s', tip: '请输入关键词进行搜索' },
  { name: '网易云音乐', img: '/src/assets/images/nav/music126.png', url: 'https://music.163.com/#/search/m/?s=%s', tip: '请输入关键词进行搜索' },
  { name: '酷狗音乐', img: '/src/assets/images/nav/kugou.png', url: 'https://www.kugou.com/yy/html/search.html?searchKeyWord=%s', tip: '请输入关键词进行搜索' },
  { name: '酷我音乐', img: '/src/assets/images/nav/kuwo.png', url: 'http://www.kuwo.cn/search/list?key=%s', tip: '请输入关键词进行搜索' }
]
// 翻译
const translate = [
  { name: '有道翻译', img: '/src/assets/images/nav/youdao.png', url: 'https://dict.youdao.com/search?q=%s', tip: '请输入中文,翻译结果为英文' },
  { name: '百度翻译', img: '/src/assets/images/nav/baidutranslate.png', url: 'https://fanyi.baidu.com/#zh/en/%s', tip: '请输入中文,翻译结果为英文' },
  { name: '谷歌翻译', img: '/src/assets/images/nav/google.png', url: 'https://translate.google.cn/#view=home&op=translate&sl=zh-CN&tl=en&text=%s', tip: '请输入中文,翻译结果为英文' }
]

let now = ref("web");
let nowChoose = web;
let show = ref(false);
let topSearch = reactive({
  data: {
    name: '百度搜索',
    img: '/src/assets/images/nav/baidu.png',
    url: 'https://www.baidu.com/s?wd=%s',
    tip: '输入你想搜索的网页'
  }
});
let searchText = ref();

const changeItem = (item: any) => { // 切换搜索内容
  show.value = false
  now.value = item
  // 切换不同的搜索内容
  switch (item) {
    case 'web':
      nowChoose = web
      topSearch.data = web[0]
      break
    case 'video':
      nowChoose = video
      topSearch.data = video[0]
      break
    case 'community':
      nowChoose = community
      topSearch.data = community[0]
      break
    case 'img':
      nowChoose = image
      topSearch.data = image[0]
      break
    case 'music':
      nowChoose = music
      topSearch.data = music[0]
      break
    case 'translate':
      nowChoose = translate
      topSearch.data = translate[0]
      break
  }
}

const changeSearch = (item: any) => { // 修改搜索引擎
  topSearch.data = item
  show.value = false
};

const openSearch = (url: any) => {
  // 先替换字符串
  window.open(url.replace('%s', searchText.value))
  searchText.value = ''
};

const toggleShow = () => {
  show.value = !show.value
}

</script>

<style scoped>
.m-inner {
  width: 1200px;
  margin: 51px auto 0 auto;
  zoom: 1;

}

.wd-search {
  display: flex;
  right: 0;
  bottom: 22px;
  margin-top: 240px;
  justify-content: center;
}

.logo {
  width: 128px;
  height: 42px;
  padding: 44px 10px 0;
  z-index: 9999;
}

.search-logo {
  width: 128px;
  height: 10px;
  display: flex;
  margin-bottom: 30px;
  align-items: center;
}

.web {
  cursor: pointer;
  width: 128px;
}

.logo-list {
  border: 1px solid rgba(0, 0, 0, 0.1);
  background-color: rgba(255, 255, 255, 0.1);
  border-bottom: none;
}

.img-box {
  background-color: rgba(255, 255, 255, 0.5);
  border-bottom: 1px solid rgba(0, 0, 0, 0.5);
  width: 100%;
  height: 100%;
  text-align: center;
}

.img-box :hover {
  background-color: #eee;
}

.logo-img {
  margin: 5px 0;
  width: 120px;
  height: auto;
  cursor: pointer;
}

.main-form {
  margin: 10px 0 0 0;
  padding: 0;
  float: left;
}

.services {
  height: 31px;
  width: 460px;
  /*margin-right: -10px;*/
  overflow: hidden;
  line-height: 24px;
  padding: 0;
}

.item {
  display: inline;
  float: left;
  height: 24px;
  line-height: 24px;
  width: 60px;
  position: relative;
  margin-right: 5px;
  cursor: pointer;
  color: #000;
  text-align: center;
  font-size: 14px;
  backdrop-filter: blur(8px);
}

.item input {
  display: none;
}

.item label {
  cursor: pointer
}

.active {
  background: #00a1d6;
  position: relative;
}

.pt {
  position: absolute;
  bottom: -5px;
  left: 50%;
  margin-left: -3px;
  width: 0;
  height: 0;
  font-size: 0;
  line-height: 0;
  border-top: solid 5px #00a1d6;
  border-left: dashed 3px transparent;
  border-right: dashed 3px transparent;
  border-bottom: 0
}

.box {
  width: 490px;
  height: 50px;
  margin-top: -9px;
  position: relative;
  vertical-align: -0.15em;
  fill: currentColor;
  overflow: hidden;
}

.box div {
  width: 100%;
  height: 37px;
  display: block;
  margin: 10px auto 30px;
  position: relative;
}


.box button {
  width: 40px;
  height: 40px;
  display: block;
  position: absolute;
  z-index: 10;
  right: 5px;
  top: 0px;
  cursor: pointer;
  font-size: 22px;
  line-height: 40px;
  border-radius: 50%;
  color: #777;
}

.box button {
  padding: 0;
  margin: 0;
  border: none;
  outline: none;
  background: none;
}

.wd {
  width: 99%;
  height: 100%;
  display: block;
  border: 1px solid #ddd;
  border-radius: 5px;
  line-height: 100%;
  font-size: 15px;
  text-indent: 7px;
  transition: 0.3s all;
}

</style>
