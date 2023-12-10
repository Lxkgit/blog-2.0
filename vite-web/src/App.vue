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
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import { onMounted, ref, watch } from "vue";
import { useRouter } from "vue-router";
import { systemStore } from "@/store/system"
import dark from "@/utils/dark";
import socketAll from '@/utils/socketAll';
import socketUser from '@/utils/socketUser'
import user from "@/utils/user";
import mitter from "@/utils/mitt";
import { selectBlogSettingByIdApi } from "@/api/file"
import SettingEnum from "@/enums/blogSettingEnum"

let { isLogin, userId } = user();
const store = systemStore()
let { setDark } = dark()
let { openSocketAll } = socketAll()
let { openSocketUser, closeWebSocketUser } = socketUser()
const locale = zhCn
const includeList = ref([])
const router = useRouter()
watch(() => router, (newValue) => {
  if (newValue.currentRoute.value.meta.keepAlive && includeList.value.indexOf(newValue.currentRoute.value.name) === -1) {
    includeList.value.push(newValue.currentRoute.value.name);
  }
}, { deep: true })

mitter.on("login", (data) => {
  const msg = JSON.parse(data)
  if (msg.state === true && store.userSocket) {
    openSocketUser(msg.userId);
  } else {
    closeWebSocketUser()
  }
})

onMounted(() => {
  // console.log(window.config.api)
  selectBlogSettingByIdFun()
  const is_dark = window.matchMedia('(prefers-color-scheme: dark)').matches
  if (is_dark) {
    setDark(is_dark)
  } else {
    setDark(store.isDark)
  }
  try {
    document.body.removeChild(document.getElementById('Loading'))
    setTimeout(function () {
      document.getElementById('app').style.display = 'block';
    }, 500)
  } catch (e) {
    console.log(e)
  }
})

async function selectBlogSettingByIdFun() {
  if (!store.socketFlag) {
    let fGlobal = false
    let fUser = false
    if (store.serviceIP === "") {
      await selectBlogSettingByIdApi(SettingEnum.getEnumValueByLabel("serviceIP")).then((res) => {
        if (res.code === 200) {
          store.setServiceIP(res.result.value)
        }
      })
    }
    await selectBlogSettingByIdApi(SettingEnum.getEnumValueByLabel("globalSocket")).then((res) => {
      if (res.code === 200) {
        store.setGlobalSocket(res.result.bool)
        fGlobal = true
      }
    })
    await selectBlogSettingByIdApi(SettingEnum.getEnumValueByLabel("userSocket")).then((res) => {
      if (res.code === 200) {
        store.setUserSocket(res.result.bool)
        fUser = true
      }
    })
    if (fGlobal && fUser) {
      store.setSocketFlag(true)
    }
  }
  if (store.globalSocket) {
    openSocketAll();
  }
  if (store.userSocket) {
    if (isLogin.value) {
      openSocketUser(userId.value);
    }
  }
}

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

// 全局设置滚动条样式

* {}

::-webkit-scrollbar {
  width: 10px;
}

::-webkit-scrollbar-thumb {
  /* 滑块颜色   */
  background: #ccc;
  /* 滑块圆角 */
  border-radius: 5px;
}

::-webkit-scrollbar-thumb:hover {
  /* 鼠标移入滑块变色 */
  background: var(--el-color-primary);
}

::-webkit-scrollbar-track {
  box-shadow: inset 0 0 5px var(--el-color-primary);
  background: #ededed;
  border-radius: 5px;
}
</style>
