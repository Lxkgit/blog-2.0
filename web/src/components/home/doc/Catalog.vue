<template>
  <div v-show="list !=null && list.length!==0" id="article-index" :style="'top:'+top+';left:'+left">
    <div id="article-index-move">
      文章目录
      <div class="window-option">
        <div v-show="showCatalog" @click="changeShow(false)">
          <font-awesome-icon class="catalog-min" icon="window-minimize" />
        </div>
        <div v-show="!showCatalog" @click="changeShow(true)">
          <font-awesome-icon class="catalog-max" icon="window-maximize" />
        </div>
        <div @click="list=null">
          <font-awesome-icon id="category-close" icon="window-close" />
        </div>
      </div>
    </div>
    <ol v-show="showCatalog" id="index-ul">
      <li v-for="(item,index) in list" :key="index" :class="(item.name === 'H3'?'index-ul-li':'')+' index-list '+item.id" :title="item.title">
        <a :href="'#'+item.id">{{ item.title }}</a>
      </li>
    </ol>
  </div>
</template>

<script>
import $ from 'jquery'
import Vue from 'vue'
export default {
  name: 'Catalog',
  data () {
    return {
      // list: null, // 目录列表
      list: [
        {
          'id': '1',
          'title': '2'
        }
      ], // 目录列表
      top: '289px',
      left: '20px', // top和left都是目录的位置
      showCatalog: false // 是否显示目录
    }
  },
  mounted () {
    // 从localstorage里面获取位置信息
    // const top = localStorage.getItem('catalogTop')
    // const left = localStorage.getItem('catalogLeft')
    // const show = localStorage.getItem('catalogShow')
    const top = "localStorage.getItem('catalogTop')"
    const left = "localStorage.getItem('catalogLeft')"
    const show = "localStorage.getItem('catalogShow')"
    if (top != null) {
      this.top = top
      this.left = left
    }
    if (show != null) {
      this.showCatalog = show === '1'
    }
  },
  methods: {
    changeShow (show) {
      this.showCatalog = show
      // 保存设置
      localStorage.setItem('catalogShow', show ? '1' : '0')
    },
    getCatalog () { // 获取文章目录
      const category = []
      let h2 = 0
      let h3 = 0
      // 先批量遍历标题
      $('h2,h3').each(function (e) {
        const data = {}
        // 先给标签加上id
        const id = 'title' + e
        const title = $(this).text()
        const name = $(this)[0].tagName
        // 给标题加上id
        $(this).attr('id', id)
        // 修改text
        if (name === 'H2') {
          h3 = 0
          h2++
          // 获取标题内容
          data.title = h2 + ':' + title
        } else {
          h3++
          data.title = h2 + '.' + h3 + ':' + title
        }
        data.id = id
        // 获取标签的名字
        data.name = name
        category.push(data)
      })
      // 网站添加目录
      Vue.set(this, 'list', category)
      let $currentHeading = $('h2')
      // 滚轮移动的时候自动高亮当前目录位置
      window.onscroll = function () {
        // 自动判断当前的位置(找出第一个超过顶部20的标题)
        $('h2,h3').each(function (e) {
          if ($(this).offset().top - $(document).scrollTop() > 20) {
            return
          }
          $currentHeading = $(this)
        })
        // 给标题设置属性
        const id = $currentHeading.attr('id')
        const $catalog = $('.' + id)
        if (!$catalog.hasClass('catalog-active')) {
          $('.catalog-active').removeClass('catalog-active')
          $catalog.addClass('catalog-active')
        }
      }
      // 文章目录模块拖动
      // // 点击关闭后目录消失
      // $('#category-close').click(function () {
      //     $('#article-index').remove()
      // })
      let dragJob = false
      $(document).on('mousedown', '#article-index-move', function (e) {
        dragJob = true
      })
      // 目录拖动事件
      document.onmousemove = function (e) {
        if (dragJob) {
          e = e || window.event
          const height = $(window).height()
          const width = $(window).width()
          const widthJob = $('#article-index').width()
          const heightJob = $('#article-index').height()
          const left = e.clientX - widthJob / 2
          const top = e.clientY - 17
          if (top >= 0 && top < height - heightJob) {
            $('#article-index').css('top', top)
          }
          if (left >= 0 && left < width - widthJob) {
            $('#article-index').css('left', left)
          }
          return false
        }
      }
      // 停止拖动事件
      $(document).mouseup(function (e) {
        dragJob = false
        const list = {
          top: $('#article-index').css('top'),
          left: $('#article-index').css('left')
        }
        if (list.top !== undefined && list.left !== undefined) {
          // 有数据才进行保存
          localStorage.setItem('catalogTop', list.top)
          localStorage.setItem('catalogLeft', list.left)
        }
      })
    }
  }
}
</script>

<style scoped>
  /*文章目录部分*/
  .catalog-active a{
    color: #618eba!important;
  }
  #article-index {
    border-radius: 3px;
    width: 200px;
    max-height: 290px;
    position: fixed;
    line-height: 23px;
    background: #ffffff;
    left: 0;
    z-index: 555555;
  }
  #article-index-move {
    display: flex;
    flex-direction: row;
    cursor: move;
    padding: 5px;
    border-bottom: 1px solid #dfe1e5;
  }
  #category-close {
    font-weight: 800;
    color: #848484;
    cursor: pointer;
    margin: 1px 6px 0 8px;
  }
  #index-ul {
    height: auto;
    overflow-y: auto;
    max-height: 250px;
    font-size: 15px;
    padding: 0;
    margin: 0 0 0 13px;
  }
  #index-ul li {
    padding-top: 2px;
    cursor: pointer;
    color: #24292e;
    list-style: none;
    white-space: nowrap;
    text-overflow: ellipsis;
    overflow: hidden;
    margin-right: 9px;
  }
  .window-option{
    flex: 1;
    display: flex;
    justify-content: flex-end;
    align-items: center;
  }
  .catalog-min{
    margin-top: -10px;
    cursor: pointer;
    color: #848484;
  }
  .catalog-max{
    cursor: pointer;
    margin-left: 5px;
    color: #848484;
  }
</style>
