// 暗黑模式
import { computed, onMounted, ref } from "vue";
import { systemStore } from "@/store/system"
import { useCssVar } from "@vueuse/core";

const store = systemStore()
function theme() {

  // 当前默认主题色
  const themeValue = computed(() => store.theme)
  // 切换主题色
  const setTheme = (value: any) => {
    store.setTheme(value)
    const el = ref(null)
    const primary_color = useCssVar('--el-color-primary', el)
    primary_color.value = themeValue.value
  }
  onMounted(() => {
    setTheme(themeValue.value)
  })
  return {
    setTheme,
  }
}

export default theme
