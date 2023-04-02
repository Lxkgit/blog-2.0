import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from '@/plugins/ElementPlus'
import store from '@/store'
import router from '@/router'
import mitt from 'mitt'

import "animate.css";
import 'element-plus/dist/index.css'
import 'element-plus/theme-chalk/dark/css-vars.css'
import 'nprogress/nprogress.css'
import '@/assets/style/index.scss'
import '@/assets/style/css-vars.css'
import "@/assets/style/normalize.css"
import "@/assets/style/hover-min.css"

// router.beforeEach((to, from, next) => {
// 	if(to.matched.some((auth) => auth.meta.isAuth)) {
		
// 	} else {
// 		next()
// 	}
// })

const app = createApp(App)
//绑定事件总线
app.config.globalProperties.$bus = new (mitt as any);
app.use(router)
app.use(store)
app.use(ElementPlus)
app.mount('#app')
// 自定义指令-动态title
app.directive('title', {
	updated(el, binding) {
		document.title = binding.value
	}
})