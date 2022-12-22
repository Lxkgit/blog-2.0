<template>
  <div class="site_list">
    <div v-for="(item, index) in navigations" :key="index" class="cate-item">
      <div class="title">
        <div class="line" :style="'background:' + item.color" ></div>
        <span :style="'color:' + item.color">{{ item.name }}</span>
      </div>
      <div class="item-list">
        <div class="section-list clearfix">
          <div v-for="(item2, index2) in item.child" :key="index2" class="section-item">
            <div class="link">
              <a :href="item2.url" target="_blank" :title="item2.name">
                <div class="link-ico"><img :src="'https://favicon.link/v2/ico.php?url=' + item2.url"
                    :title="item2.name"></div>
                <span class="title">{{ item2.name }}</span>
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">

const navigations = [
  {
    color: '#ff0000',
    name: 'baidu',
    child: [
      {
        url: 'www.baidu.com',
        name: 'baidu'
      }
    ]
  }
]

</script>

<style scoped>
.section-item .link a {
  display: flex;
}

.section-item .link .link-ico {
  display: flex;
  align-items: center;
}

.section-item .link .link-ico img {
  width: 16px;
  height: 16px;
}

.site_list {
  margin-top: -28px;
  padding-bottom: 20px;




}

.site_list span.title {
  margin-left: 3px;
  font-size: 16px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 150px;
  display: block;
}

.item-list {
  margin: -8px 12px;
}

.section-list {
  margin-top: 8px;
  display: flex;
  flex-wrap: wrap;
}

.section-item {
  margin: 10px 0;
  position: relative;
  padding: 0 5px;
  box-sizing: border-box;
  display: block;
  overflow: hidden;
  border: 1px solid transparent;
}

.section-item .link {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.cate-item {
  box-sizing: border-box;
  position: relative;
  page-break-inside: avoid;
  -moz-column-break-inside: avoid;
  break-inside: avoid;
  counter-increment: item-counter;
  height: 100%;
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
  border-left: 1px solid #e5e5e5;
  padding: 20px 10px 0;


}

.title {
  background: linear-gradient(to right, #f2f2f2, rgba(222, 222, 222, 0));
  display: flex;
  align-items: center;


}

.line {
  width: 5px;
  height: 32px;
  background: #78be40;
  display: inline-block;
}

.title span {
  color: #78be40;
  font-size: 15px;
  font-weight: bolder;
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
  margin-left: 10px;
  padding-top: 5px;
  display: block;
  line-height: 30px;
}
</style>
