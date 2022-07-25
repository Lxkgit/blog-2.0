import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import {resolve}  from 'path'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { AntDesignVueResolver } from 'unplugin-vue-components/resolvers'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    AutoImport({
      imports: ['vue', 'vue-router']
    }),
    Components({
      resolvers: [AntDesignVueResolver()]
    })
  ],
  resolve:{
    alias:{
      "@": resolve(__dirname, "src")
    }
  }
})
