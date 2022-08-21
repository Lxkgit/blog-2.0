import { createApp } from 'vue'
import App from './App.vue'
import 'font-awesome/css/font-awesome.css';
import router from './router'
import { createPinia } from 'pinia'

import VueMarkdownEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';
import '@kangc/v-md-editor/lib/theme/style/vuepress.css';
import Prism from 'prismjs';

VueMarkdownEditor.use(vuepressTheme, {
  Prism,
});


const app = createApp(App);
app.use(VueMarkdownEditor)
app.use(router)
app.use(createPinia())
app.mount('#app')