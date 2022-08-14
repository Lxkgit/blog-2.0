import { createApp } from 'vue'
import App from './App.vue'
import 'font-awesome/css/font-awesome.css';
import router from './router'

import { createPinia } from 'pinia'


const app = createApp(App);


app.use(router)
app.use(createPinia())
app.mount('#app')