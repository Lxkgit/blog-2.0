/// <reference types="vite/client" />

declare module '*.vue' {
  import type { DefineComponent } from 'vue'
  // eslint-disable-next-line @typescript-eslint/no-explicit-any, @typescript-eslint/ban-types
  const component: DefineComponent<{}, {}, any>
  export default component
}

declare module '@kangc/v-md-editor/lib/codemirror-editor';
declare module '@kangc/v-md-editor/lib/style/codemirror-editor.css';
declare module '@kangc/v-md-editor/lib/theme/github.js';
declare module '@kangc/v-md-editor/lib/theme/style/github.css';
declare module '@kangc/v-md-editor/lib/preview';
declare module 'codemirror';