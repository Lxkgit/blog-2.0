import { createApp } from 'vue'
import App from './App.vue'

import ant from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'

import router from './router'

const app = createApp(App);


app.use(ant)
app.use(router)

app.mount('#app')
