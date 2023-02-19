// 暗黑模式
import { useDark, useToggle } from '@vueuse/core'
import { systemStore } from "@/store/system";
import { computed, onMounted } from "vue";


function dark() {
  const store = systemStore()
  const isDark = computed(() => store.isDark)
  const use_dark = useDark()
  const toggleDark = useToggle(use_dark)
  const setDark = (value) => {
    if (value !== use_dark.value) {
      toggleDark()
    }
    store.setDark(value)
  }
  return {
    setDark, isDark
  }
}

export default dark