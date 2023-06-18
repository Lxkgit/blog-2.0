<template>
  <el-config-provider :locale="locale">
    <div class="router-view">
      <router-view v-slot="{ Component }">
        <keep-alive :include="includeList">
          <component :is="Component" />
        </keep-alive>
      </router-view>
    </div>
  </el-config-provider>
</template>

<script setup>
import zhCn from 'element-plus/lib/locale/lang/zh-cn'
import { onMounted, ref, watch } from "vue";
import { ElMessageBox } from 'element-plus'
import dark from "@/utils/dark";
import { systemStore } from "@/store/system"
import { useRouter } from "vue-router";
import  socketAll  from '@/utils/socketAll';
import { getCurrentInstance } from "vue";


const instance = getCurrentInstance();
const store = systemStore()
let { setDark } = dark()
let { openSocket } = socketAll()
const locale = zhCn
const includeList = ref([])
const router = useRouter()
watch(() => router, (newValue) => {
  if (newValue.currentRoute.value.meta.keepAlive && includeList.value.indexOf(newValue.currentRoute.value.name) === -1) {
    includeList.value.push(newValue.currentRoute.value.name);
  }
}, { deep: true })
onMounted(() => {
  openSocket();
  instance?.proxy?.$emitter.on("chat", (data) => {
    console.log("chat" + data)
  });
  const is_dark = window.matchMedia('(prefers-color-scheme: dark)').matches
  if (is_dark) {
    setDark(is_dark)
  } else {
    setDark(store.isDark)
  }
  // if (document.body.clientWidth <= 1200) {
  //   ElMessageBox.alert('检测到您使用移动设备访问，点击确定后跳转至移动版网站', {
  //     confirmButtonText: '确定',
  //     showClose: false,
  //     center: true,
  //     callback: () => {
  //       // location.href = "https://m.cuiliangblog.cn"
  //     },
  //   });
  // }
  try {
    document.body.removeChild(document.getElementById('Loading'))
    setTimeout(function () {
      document.getElementById('app').style.display = 'block';
    }, 500)
  } catch (e) {
    console.log(e)
  }
})

</script>

<style lang="scss">
.router-view {
  color: var(--el-text-color-primary);
  background-color: var(--el-background-color-base);
  transition: background 1s, color 0.6s;
  width: 100%;
  height: max-content;
  min-height: 100vh;
  position: absolute;
  top: 0;
  bottom: 0;
  margin: 0 auto;
  -webkit-overflow-scrolling: touch;
  animation-timing-function: linear;
}
</style>
