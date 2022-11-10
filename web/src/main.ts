import { createApp } from 'vue'
import App from './App.vue'
import 'font-awesome/css/font-awesome.css';
import router from './router'
import store from './store'

// main.js
import VMdPreview from '@kangc/v-md-editor/lib/preview';
import '@kangc/v-md-editor/lib/style/preview.css';

import VMdEditor from '@kangc/v-md-editor/lib/codemirror-editor';
import '@kangc/v-md-editor/lib/style/codemirror-editor.css';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';

// highlightjs
import hljs from 'highlight.js';

// codemirror 编辑器的相关资源
import Codemirror from 'codemirror';
// mode
import 'codemirror/mode/markdown/markdown';
import 'codemirror/mode/javascript/javascript';
import 'codemirror/mode/css/css';
import 'codemirror/mode/htmlmixed/htmlmixed';
import 'codemirror/mode/vue/vue';
// edit
import 'codemirror/addon/edit/closebrackets';
import 'codemirror/addon/edit/closetag';
import 'codemirror/addon/edit/matchbrackets';
// placeholder
import 'codemirror/addon/display/placeholder';
// active-line
import 'codemirror/addon/selection/active-line';
// scrollbar
import 'codemirror/addon/scroll/simplescrollbars';
import 'codemirror/addon/scroll/simplescrollbars.css';
// style
import 'codemirror/lib/codemirror.css';

import VueMarkdownEditor from '@kangc/v-md-editor';
import createLineNumbertPlugin from '@kangc/v-md-editor/lib/plugins/line-number/index';
import createTodoListPlugin from '@kangc/v-md-editor/lib/plugins/todo-list/index';
import '@kangc/v-md-editor/lib/plugins/todo-list/todo-list.css';

VMdEditor.Codemirror = Codemirror;
VMdEditor.use(githubTheme, {Hljs: hljs,});
VMdEditor.use(createLineNumbertPlugin());
VMdEditor.use(createTodoListPlugin());


VMdPreview.use(githubTheme, {Hljs: hljs,});
VMdPreview.use(createLineNumbertPlugin());
VMdPreview.use(createTodoListPlugin());


const app = createApp(App);
app.use(store)
app.use(router)
app.use(VMdEditor)
app.use(VMdPreview)
app.mount('#app')